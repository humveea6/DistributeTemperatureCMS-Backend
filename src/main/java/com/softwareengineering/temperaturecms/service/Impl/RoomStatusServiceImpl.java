package com.softwareengineering.temperaturecms.service.Impl;

import com.softwareengineering.temperaturecms.dao.RoomStatusMapper;
import com.softwareengineering.temperaturecms.dto.ChangeTargetTemperatureDto;
import com.softwareengineering.temperaturecms.enums.StateEnum;
import com.softwareengineering.temperaturecms.pojo.RoomStatus;
import com.softwareengineering.temperaturecms.pojo.RoomStatusExample;
import com.softwareengineering.temperaturecms.service.RoomStatusService;
import com.softwareengineering.temperaturecms.utils.JsonUtils;
import com.softwareengineering.temperaturecms.vo.DefaultSettingVo;
import com.softwareengineering.temperaturecms.vo.InvoiceVo;
import com.softwareengineering.temperaturecms.vo.RoomDetailListVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.softwareengineering.temperaturecms.consts.CMSConst.*;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-30
 */
@Service
public class RoomStatusServiceImpl implements RoomStatusService {

    @Autowired
    private RoomStatusMapper roomStatusMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public DefaultSettingVo ArrangeService(Long roomId, Double currentTemperature) {

        DefaultSettingVo defaultSettingVo = new DefaultSettingVo();

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();

        int mode = 1;
        String s = opsForValue.get(DEFAULT_MODE_REDIS_KEY);
        if(!StringUtils.isEmpty(s)){
            mode = Integer.parseInt(s);
        }

        Double targetTemperature = 27D;
        s= opsForValue.get(TARGET_TEMPERATURE_REDIS_KEY);
        if(!StringUtils.isEmpty(s)){
            targetTemperature = Double.parseDouble(s);
        }
        defaultSettingVo.setDefaultTargetTemperature(targetTemperature);

        Double highestTemperature = 35D;
        s= opsForValue.get(HIGHEST_TEMPERATURE_REDIS_KEY);
        if(!StringUtils.isEmpty(s)){
            highestTemperature = Double.parseDouble(s);
        }
        defaultSettingVo.setHighestTemperature(highestTemperature);

        Double lowestTemperature = 27D;
        s= opsForValue.get(LOWEST_TEMPERATURE_REDIS_KEY);
        if(!StringUtils.isEmpty(s)){
            lowestTemperature = Double.parseDouble(s);
        }
        defaultSettingVo.setLowestTemperature(lowestTemperature);

        Double fanSpeed = 27D;
        s= opsForValue.get(DEFAULT_FANS_SPEED_REDIS_KEY);
        if(!StringUtils.isEmpty(s)){
            fanSpeed = Double.parseDouble(s);
        }
        defaultSettingVo.setDefaultFanSpeed(fanSpeed);

        //更新数据库数据
        Integer id = setData(roomId, mode, currentTemperature, targetTemperature, 20D);

        //发送对象消息
        if(id > 0) {
            rabbitTemplate.convertAndSend(AC_ON_QUEUE, id);
            return defaultSettingVo;
        }
        else{
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
        if(roomStatus == null){
            return false;
        }
        rabbitTemplate.convertAndSend(CHANGE_TARGET_TEMPERATURE_QUEUE, JsonUtils.toJson(changeTargetTemperatureDto));

        return true;
    }

    @Override
    public Boolean RequestFanSpeed(ChangeTargetTemperatureDto changeTargetTemperatureDto) {

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String redisKey = String.format(ROOM_SERVICE_REDIS_KEY,changeTargetTemperatureDto.getId());
        String s = opsForValue.get(redisKey);

        if(StringUtils.isEmpty(s)){
            return false;
        }
        RoomStatus roomStatus = JsonUtils.fromJson(s, RoomStatus.class);
        roomStatus.setFansSpeed(changeTargetTemperatureDto.getFanSpeed());
        opsForValue.set(redisKey,JsonUtils.toJson(roomStatus));
        return true;
    }

    @Override
    public Boolean changeRoomServingState(Integer id, StateEnum stateEnum) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String redisKey = String.format(ROOM_SERVICE_REDIS_KEY,id);
        String s = opsForValue.get(redisKey);

        if(StringUtils.isEmpty(s)){
            return false;
        }
        RoomStatus roomStatus = JsonUtils.fromJson(s, RoomStatus.class);
        roomStatus.setState(stateEnum.getState());
        opsForValue.set(redisKey,JsonUtils.toJson(roomStatus));
        return true;
    }

    @Override
    public StateEnum getRoomServingState(Integer id) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String redisKey = String.format(ROOM_SERVICE_REDIS_KEY,id);
        String s = opsForValue.get(redisKey);

        if(StringUtils.isEmpty(s)){
            return StateEnum.NO_SUCH_ROOM;
        }
        RoomStatus roomStatus = JsonUtils.fromJson(s, RoomStatus.class);

        if(roomStatus.getState()!=null){
            if(roomStatus.getState().equals(0)){
                return StateEnum.IN_SERVICE;
            }
            if(roomStatus.getState().equals(1)){
                return StateEnum.WAITING;
            }
            if(roomStatus.getState().equals(2)){
                return StateEnum.FREE;
            }
        }

        return StateEnum.NO_SUCH_ROOM;
    }

    @Override
    public Boolean WriteBack(Integer id){
        rabbitTemplate.convertAndSend(AC_OFF_QUEUE,id);

        return true;
    }

    @Override
    public RoomDetailListVo getRoomDetail(Integer id) {
        RoomDetailListVo roomDetailListVo = new RoomDetailListVo();

        Double fee = getFee(id);
        roomDetailListVo.setFee(fee);
        RoomStatus roomStatus = roomStatusMapper.selectByPrimaryKey(id);
        if(roomStatus.getEndTime().equals(0L)){
            ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
            String redisKey = String.format(ROOM_SERVICE_REDIS_KEY,id);
            String s = opsForValue.get(redisKey);
            if(!StringUtils.isEmpty(s)){
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
        if(roomStatus.getEndTime().equals(0L)){
            endTime = System.currentTimeMillis();
        }

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        Long minutes = (endTime-roomStatus.getStartUp());

        String totalTime = opsForValue.get(String.format(ROOM_STOP_CHARGE_TOTAL_TIME_REDIS_KEY, id));
        Long time = 0L;
        if(!StringUtils.isEmpty(totalTime)) {
            time = Long.parseLong(totalTime);
        }
        minutes = (minutes-time)/1000/60;

        String s = opsForValue.get(LOW_FEE_RATE_REDIS_KEY);

        Double feeRate = StringUtils.isEmpty(s) ? 0.2D : Double.parseDouble(s);

        return feeRate*minutes;
    }

    @Override
    public RoomStatus getRoomStatusFromRedis(Integer id) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String redisKey = String.format(ROOM_SERVICE_REDIS_KEY,id);
        String s = opsForValue.get(redisKey);

        if(StringUtils.isEmpty(s)){
            return new RoomStatus();
        }
        RoomStatus roomStatus = JsonUtils.fromJson(s, RoomStatus.class);

        return roomStatus;
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
    public void pauseFee(Integer id) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String redisKey = String.format(ROOM_STOP_CHARGE_TIMESTAMP_REDIS_KEY,id);
        Long currentTimeMillis = System.currentTimeMillis();
        opsForValue.set(redisKey,currentTimeMillis.toString());
    }

    @Override
    public Boolean continueFee(Integer id) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String redisKey = String.format(ROOM_STOP_CHARGE_TIMESTAMP_REDIS_KEY,id);
        Long currentTimeMillis = System.currentTimeMillis();

        String pauseTimeStamp = opsForValue.get(redisKey);
        if(StringUtils.isEmpty(pauseTimeStamp)){
            return false;
        }
        opsForValue.set(redisKey,pauseTimeStamp,20, TimeUnit.SECONDS);
        Long pauseTime = currentTimeMillis - Long.parseLong(pauseTimeStamp);
        String redisKeyForTotalTime = String.format(ROOM_STOP_CHARGE_TOTAL_TIME_REDIS_KEY,id);
        String totTime = opsForValue.get(redisKeyForTotalTime);
        if(!StringUtils.isEmpty(totTime)){
            pauseTime += Long.parseLong(totTime);
        }
        opsForValue.set(redisKeyForTotalTime,pauseTime.toString());

        return true;
    }

    private Integer setData(Long roomId, Integer mode, Double currentTem, Double targetTemp, Double fanSpeed){
        RoomStatus roomStatus = new RoomStatus();
        roomStatus.setCurrentTemperature(currentTem);
        roomStatus.setRoomId(roomId);
        roomStatus.setMode(mode);
        roomStatus.setTargetTemperature(targetTemp);
        roomStatus.setFansSpeed(fanSpeed);
        roomStatus.setStartUp(System.currentTimeMillis());
        int insert = roomStatusMapper.insertSelective(roomStatus);

        if(insert>0) {
            return roomStatus.getId();
        }
        else {
            return 0;
        }
    }
}
