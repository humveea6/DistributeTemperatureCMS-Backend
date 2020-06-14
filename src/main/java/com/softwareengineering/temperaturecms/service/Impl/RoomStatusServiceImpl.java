package com.softwareengineering.temperaturecms.service.Impl;

import com.softwareengineering.temperaturecms.dao.RoomServingListMapper;
import com.softwareengineering.temperaturecms.dao.RoomStatusMapper;
import com.softwareengineering.temperaturecms.dto.ChangeTargetTemperatureDto;
import com.softwareengineering.temperaturecms.enums.StateEnum;
import com.softwareengineering.temperaturecms.pojo.RoomServingList;
import com.softwareengineering.temperaturecms.pojo.RoomStatus;
import com.softwareengineering.temperaturecms.pojo.RoomStatusExample;
import com.softwareengineering.temperaturecms.service.RoomStatusService;
import com.softwareengineering.temperaturecms.utils.JsonUtils;
import com.softwareengineering.temperaturecms.vo.DefaultSettingVo;
import com.softwareengineering.temperaturecms.vo.InvoiceVo;
import com.softwareengineering.temperaturecms.vo.RoomDetailListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static com.softwareengineering.temperaturecms.consts.CMSConst.*;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-30
 */
@Service
@Slf4j
public class RoomStatusServiceImpl implements RoomStatusService {

    @Autowired
    private RoomStatusMapper roomStatusMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RoomServingListMapper roomServingListMapper;

    private static ReentrantLock queueLock = new ReentrantLock();

    private static Queue<RoomStatus> waitingQueue = new LinkedList<>();

    private static Queue<Future> waitingFuture = new LinkedList<>();

    private static ExecutorService executorService =
            new ThreadPoolExecutor(10,20,10,TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

    private static TreeSet<RoomStatus> roomInservice = new TreeSet<>(new Comparator<RoomStatus>() {
        @Override
        public int compare(RoomStatus o1, RoomStatus o2) {
            if(o1.getFansSpeed().equals(o2.getFansSpeed())){
                if(o1.getLastWorkTime()<o2.getLastWorkTime()){
                    return -1;
                }
                else {
                    return 1;
                }
            }
            else{
                if(o1.getFansSpeed()<o2.getFansSpeed()){
                    return -1;
                }
                else{
                    return 1;
                }
            }
        }
    });

    @Override
    public void addJob(RoomStatus roomStatus) {

        if(roomInservice.size()<3){
            roomStatus.setLastWorkTime(System.currentTimeMillis());
            roomStatus.setState(StateEnum.IN_SERVICE.getState());
            roomInservice.add(roomStatus);
            updateRoomStatusInRedis(roomStatus.getId(),roomStatus);
        }
        else{
            RoomStatus roomStatusFirst = roomInservice.first();
            if(roomStatusFirst.getFansSpeed()<roomStatus.getFansSpeed()) {
                roomStatusFirst.setState(StateEnum.WAITING.getState());

                roomStatus.setState(StateEnum.IN_SERVICE.getState());
                roomStatus.setLastWorkTime(System.currentTimeMillis());
                roomInservice.remove(roomStatusFirst);
                roomInservice.add(roomStatus);

                updateRoomStatusInRedis(roomStatus.getId(), roomStatus);
                updateRoomStatusInRedis(roomStatusFirst.getId(), roomStatusFirst);
                waitingQueue.offer(roomStatusFirst);

                createRDR(roomStatus.getId());
                createRDR(roomStatusFirst.getId());
            }
            else {
                waitingQueue.offer(roomStatus);
                createRDR(roomStatus.getId());
            }
            Future submit = executorService.submit(new RoomWaitJob());
            waitingFuture.offer(submit);

        }
    }

    @Override
    public void endJob(RoomStatus roomStatus) {
        boolean remove = roomInservice.remove(roomStatus);
        roomStatus.setState(StateEnum.SHUTDOWN.getState());
        updateRoomStatusInRedis(roomStatus.getId(),roomStatus);
        createRDR(roomStatus.getId());
        
        if(remove){
            createRDR(roomStatus.getId());
            try{
                queueLock.lock();
                Future poll = waitingFuture.poll();
                poll.cancel(true);
                RoomStatus poll1 = waitingQueue.poll();

                poll1.setState(StateEnum.IN_SERVICE.getState());
                poll1.setLastWorkTime(System.currentTimeMillis());
                roomInservice.add(poll1);
                updateRoomStatusInRedis(poll1.getId(),poll1);
            }
            finally {
                queueLock.unlock();
            }
        }
    }

    class RoomWaitJob implements Callable {
        @Override
        public Object call() throws Exception {
            Thread.sleep(4*1000);
            RoomStatus roomStatus = roomInservice.first();
            for (RoomStatus roomStatus1 : roomInservice) {
                if(roomStatus1.getLastWorkTime()<roomStatus.getLastWorkTime()){
                    roomStatus=roomStatus1;
                }
            }
            log.info("stop service "+JsonUtils.toJson(roomStatus));
            roomStatus.setState(StateEnum.WAITING.getState());
            updateRoomStatusInRedis(roomStatus.getId(),roomStatus);
            roomInservice.remove(roomStatus);
            createRDR(roomStatus.getId());
            waitingQueue.offer(roomStatus);
            Future submit = executorService.submit(new RoomWaitJob());
            waitingFuture.offer(submit);

            try {
                queueLock.lock();
                RoomStatus poll = waitingQueue.poll();
                log.info("waiting service: "+JsonUtils.toJson(poll));
                createRDR(poll.getId());
                waitingFuture.poll();
                poll.setState(StateEnum.IN_SERVICE.getState());
                poll.setLastWorkTime(System.currentTimeMillis());
                roomInservice.add(poll);
                updateRoomStatusInRedis(poll.getId(),poll);
            }
            finally {
                queueLock.unlock();
            }

            return null;
        }
    }

    @Override
    public void modify(RoomStatus roomStatus) {
        Integer id = roomStatus.getId();
        if(roomInservice.contains(roomStatus)){
            roomInservice.remove(roomStatus);
        }
        roomInservice.add(roomStatus);
    }

    @Override
    public void createRDR(Integer id) {
        RoomServingList roomServingList = new RoomServingList();
        RoomStatus roomStatus = getRoomStatusFromRedis(id);

        long roomId = roomStatus.getRoomId();
        roomServingList.setRoomId((int)roomId);
        roomServingList.setStartTime(roomStatus.getLastWorkTime());

        // 原roomStatus中EndTime字段保存当前结束时间
        long endTime = System.currentTimeMillis();
        roomStatus.setEndTime(endTime); //记录结束时间
        roomServingList.setEndTime(endTime); // 详单结束时间

        // 原roomStatus中Fare_rate字段保存前面的调度时间
        long duration = roomStatus.getDuration() - roomStatus.getFareRate().longValue(); //调度时长
        roomStatus.setFareRate((double) duration + roomStatus.getFareRate());  // 更新本次之前的调度时长
        roomServingList.setDuration(duration);

        roomServingList.setSpeed(roomStatus.getFansSpeed().intValue());
        roomServingList.setFeeRate(1d);

        roomServingList.setFee(roomStatus.getFee());

        updateRoomStatusInRedis(id, roomStatus);
        roomServingListMapper.insertSelective(roomServingList);
    }


    @Override
    public DefaultSettingVo ArrangeService(Long roomId, Double currentTemperature) {

        DefaultSettingVo defaultSettingVo = new DefaultSettingVo();

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();

        int mode = 1;
        String s = opsForValue.get(DEFAULT_MODE_REDIS_KEY);
        if (!StringUtils.isEmpty(s)) {
            mode = Integer.parseInt(s);
        }

        Double targetTemperature = 27D;
        s = opsForValue.get(TARGET_TEMPERATURE_REDIS_KEY);
        if (!StringUtils.isEmpty(s)) {
            targetTemperature = Double.parseDouble(s);
        }
        defaultSettingVo.setDefaultTargetTemperature(targetTemperature);

        Double highestTemperature = 35D;
        s = opsForValue.get(HIGHEST_TEMPERATURE_REDIS_KEY);
        if (!StringUtils.isEmpty(s)) {
            highestTemperature = Double.parseDouble(s);
        }
        defaultSettingVo.setHighestTemperature(highestTemperature);

        Double lowestTemperature = 27D;
        s = opsForValue.get(LOWEST_TEMPERATURE_REDIS_KEY);
        if (!StringUtils.isEmpty(s)) {
            lowestTemperature = Double.parseDouble(s);
        }
        defaultSettingVo.setLowestTemperature(lowestTemperature);

        Double fanSpeed = 27D;
        s = opsForValue.get(DEFAULT_FANS_SPEED_REDIS_KEY);
        if (!StringUtils.isEmpty(s)) {
            fanSpeed = Double.parseDouble(s);
        }
        defaultSettingVo.setDefaultFanSpeed(fanSpeed);

        //更新数据库数据
        Integer id = setData(roomId, mode, currentTemperature, targetTemperature, 20D);
        defaultSettingVo.setId(id);

        log.info("RoomStatusServiceImpl update sql success,id: " + id);

        //发送对象消息
        if (id > 0) {
            rabbitTemplate.convertAndSend(AC_ON_QUEUE, id);
            log.info("RoomStatusServiceImpl send msg success");
            log.info("RoomStatusServiceImpl default setting :" + JsonUtils.toJson(defaultSettingVo));
            return defaultSettingVo;
        } else {
            return null;
        }
    }

    @Override
    public RoomStatus getRoomStatusById(Integer id) {
        RoomStatus roomStatus = roomStatusMapper.selectByPrimaryKey(id);

        return roomStatus;
    }

    @Override
    public Integer roomStatusOff(RoomStatus roomStatus) {

        roomStatus.setEndTime(System.currentTimeMillis());
        return roomStatusMapper.updateByPrimaryKeySelective(roomStatus);
    }

    @Override
    public Boolean RequestTemperature(ChangeTargetTemperatureDto changeTargetTemperatureDto) {
        RoomStatus roomStatus = roomStatusMapper.selectByPrimaryKey(changeTargetTemperatureDto.getId());
        if (roomStatus == null) {
            return false;
        }
        rabbitTemplate.convertAndSend(CHANGE_TARGET_TEMPERATURE_QUEUE, JsonUtils.toJson(changeTargetTemperatureDto));

        return true;
    }

    @Override
    public Boolean RequestFanSpeed(ChangeTargetTemperatureDto changeTargetTemperatureDto) {

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String redisKey = String.format(ROOM_SERVICE_REDIS_KEY, changeTargetTemperatureDto.getId());
        String s = opsForValue.get(redisKey);

        if (StringUtils.isEmpty(s)) {
            return false;
        }
        RoomStatus roomStatus = JsonUtils.fromJson(s, RoomStatus.class);
        roomStatus.setFansSpeed(changeTargetTemperatureDto.getFanSpeed());
        opsForValue.set(redisKey, JsonUtils.toJson(roomStatus));
        return true;
    }

    @Override
    public Boolean changeRoomServingState(Integer id, StateEnum stateEnum) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String redisKey = String.format(ROOM_SERVICE_REDIS_KEY, id);
        String s = opsForValue.get(redisKey);

        if (StringUtils.isEmpty(s)) {
            return false;
        }
        RoomStatus roomStatus = JsonUtils.fromJson(s, RoomStatus.class);
        roomStatus.setState(stateEnum.getState());
        opsForValue.set(redisKey, JsonUtils.toJson(roomStatus));
        return true;
    }

    @Override
    public StateEnum getRoomServingState(Integer id) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String redisKey = String.format(ROOM_SERVICE_REDIS_KEY, id);
        String s = opsForValue.get(redisKey);

        if (StringUtils.isEmpty(s)) {
            return StateEnum.NO_SUCH_ROOM;
        }
        RoomStatus roomStatus = JsonUtils.fromJson(s, RoomStatus.class);

        if (roomStatus.getState() != null) {
            if (roomStatus.getState().equals(0)) {
                return StateEnum.IN_SERVICE;
            }
            if (roomStatus.getState().equals(1)) {
                return StateEnum.WAITING;
            }
            if (roomStatus.getState().equals(2)) {
                return StateEnum.FREE;
            }
        }

        return StateEnum.NO_SUCH_ROOM;
    }

    @Override
    public Boolean WriteBack(Integer id) {
        rabbitTemplate.convertAndSend(AC_OFF_QUEUE, id);

        return true;
    }

    @Override
    public RoomDetailListVo getRoomDetail(Integer id) {
        RoomDetailListVo roomDetailListVo = new RoomDetailListVo();

        Double fee = getFee(id);
        roomDetailListVo.setFee(fee);
        RoomStatus roomStatus = roomStatusMapper.selectByPrimaryKey(id);
        if (roomStatus.getEndTime().equals(0L)) {
            ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
            String redisKey = String.format(ROOM_SERVICE_REDIS_KEY, id);
            String s = opsForValue.get(redisKey);
            if (!StringUtils.isEmpty(s)) {
                roomStatus = JsonUtils.fromJson(s, RoomStatus.class);
            }
        }

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String s = opsForValue.get(LOW_FEE_RATE_REDIS_KEY);

        Double feeRate = StringUtils.isEmpty(s) ? 0.2D : Double.parseDouble(s);

        roomStatus.setFareRate(feeRate);

        roomDetailListVo.setRoomStatus(roomStatus);

        return roomDetailListVo;
    }

    @Override
    public InvoiceVo getInvoice(Integer id) {
        InvoiceVo invoiceVo = new InvoiceVo();

        RoomStatus roomStatus = roomStatusMapper.selectByPrimaryKey(id);
        invoiceVo.setRoomId(roomStatus.getRoomId());
        invoiceVo.setTotalFee(getFee(id));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        invoiceVo.setRequestTime(simpleDateFormat.format(System.currentTimeMillis()));
        invoiceVo.setDateIn(simpleDateFormat.format(new Date(roomStatus.getStartUp())));
        invoiceVo.setDateOut(simpleDateFormat.format(new Date(roomStatus.getEndTime())));

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String s = opsForValue.get(LOW_FEE_RATE_REDIS_KEY);

        Double feeRate = StringUtils.isEmpty(s) ? 0.2D : Double.parseDouble(s);
        invoiceVo.setFeeRate(feeRate);

        return invoiceVo;
    }

    @Override
    public Double getFee(Integer id) {
        RoomStatus roomStatus = roomStatusMapper.selectByPrimaryKey(id);
        Long endTime = roomStatus.getEndTime();
        if (roomStatus.getEndTime().equals(0L)) {
            endTime = System.currentTimeMillis();
        }

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        Long minutes = (endTime - roomStatus.getStartUp());

        String totalTime = opsForValue.get(String.format(ROOM_STOP_CHARGE_TOTAL_TIME_REDIS_KEY, id));
        Long time = 0L;
        if (!StringUtils.isEmpty(totalTime)) {
            time = Long.parseLong(totalTime);
        }
        minutes = (minutes - time) / 1000 / 60;

        String s = opsForValue.get(LOW_FEE_RATE_REDIS_KEY);

        Double feeRate = StringUtils.isEmpty(s) ? 0.2D : Double.parseDouble(s);

        return feeRate * minutes;
    }

    @Override
    public RoomStatus getRoomStatusFromRedis(Integer id) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String redisKey = String.format(ROOM_SERVICE_REDIS_KEY, id);
        String s = opsForValue.get(redisKey);

        if (StringUtils.isEmpty(s)) {
            return new RoomStatus();
        }
        RoomStatus roomStatus = JsonUtils.fromJson(s, RoomStatus.class);

        return roomStatus;
    }

    @Override
    public Boolean updateRoomStatusInRedis(Integer id, RoomStatus roomStatus) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String redisKey = String.format(ROOM_SERVICE_REDIS_KEY, id);

        opsForValue.set(redisKey, JsonUtils.toJson(roomStatus));

        return true;
    }

    @Override
    public Long countByRoomId(Long roomId) {
        RoomStatusExample roomStatusExample = new RoomStatusExample();
        roomStatusExample.createCriteria().andRoomIdEqualTo(roomId);

        return roomStatusMapper.countByExample(roomStatusExample);
    }

    @Override
    public List<RoomStatus> getRoomStatusList(Long roomId) {
        RoomStatusExample roomStatusExample = new RoomStatusExample();
        roomStatusExample.createCriteria().andRoomIdEqualTo(roomId);

        return roomStatusMapper.selectByExample(roomStatusExample);
    }

    @Override
    public void pauseService(Integer id) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String redisKey = String.format(ROOM_STOP_CHARGE_TIMESTAMP_REDIS_KEY, id);
        Long currentTimeMillis = System.currentTimeMillis();
        opsForValue.set(redisKey, currentTimeMillis.toString());
    }

    @Override
    public Boolean continueService(Integer id) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String redisKey = String.format(ROOM_STOP_CHARGE_TIMESTAMP_REDIS_KEY, id);
        Long currentTimeMillis = System.currentTimeMillis();

        String pauseTimeStamp = opsForValue.get(redisKey);
        if (StringUtils.isEmpty(pauseTimeStamp)) {
            return false;
        }
        opsForValue.set(redisKey, pauseTimeStamp, 20, TimeUnit.SECONDS);
        Long pauseTime = currentTimeMillis - Long.parseLong(pauseTimeStamp);
        String redisKeyForTotalTime = String.format(ROOM_STOP_CHARGE_TOTAL_TIME_REDIS_KEY, id);
        String totTime = opsForValue.get(redisKeyForTotalTime);
        if (!StringUtils.isEmpty(totTime)) {
            pauseTime += Long.parseLong(totTime);
        }
        opsForValue.set(redisKeyForTotalTime, pauseTime.toString());

        return true;
    }

    @Override
    public Double getRoomServiceTime(Integer id) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();

        String redisKeyForTotalTime = String.format(ROOM_STOP_CHARGE_TOTAL_TIME_REDIS_KEY, id);
        String totTime = opsForValue.get(redisKeyForTotalTime);
        long timeInLong = Long.parseLong(totTime);
        return (double) timeInLong / 1000 / 60;
    }

    private Integer setData(Long roomId, Integer mode, Double currentTem, Double targetTemp, Double fanSpeed) {
        RoomStatus roomStatus = new RoomStatus();
        roomStatus.setCurrentTemperature(currentTem);
        roomStatus.setRoomId(roomId);
        roomStatus.setMode(mode);
        roomStatus.setTargetTemperature(targetTemp);
        roomStatus.setFansSpeed(fanSpeed);
        roomStatus.setStartUp(System.currentTimeMillis());
        int insert = roomStatusMapper.insertSelective(roomStatus);

        if (insert > 0) {
            return roomStatus.getId();
        } else {
            return 0;
        }
    }
}
