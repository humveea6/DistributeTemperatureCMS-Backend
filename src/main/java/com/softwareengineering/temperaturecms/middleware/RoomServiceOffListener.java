package com.softwareengineering.temperaturecms.middleware;

import com.softwareengineering.temperaturecms.pojo.RoomStatus;
import com.softwareengineering.temperaturecms.service.RoomStatusService;
import com.softwareengineering.temperaturecms.utils.JsonUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

import static com.softwareengineering.temperaturecms.consts.CMSConst.AC_OFF_QUEUE;
import static com.softwareengineering.temperaturecms.consts.CMSConst.ROOM_SERVICE_REDIS_KEY;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-31
 */
@Component
@RabbitListener(queues = AC_OFF_QUEUE)
public class RoomServiceOffListener {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RoomStatusService roomStatusService;

    @RabbitHandler
    public void receive(Integer id){

        String redisKey = String.format(ROOM_SERVICE_REDIS_KEY,id);
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String value = opsForValue.get(redisKey);

        if(StringUtils.isEmpty(value)){
            return;
        }
        RoomStatus roomStatus = JsonUtils.fromJson(value, RoomStatus.class);
        roomStatus.setEndTime(System.currentTimeMillis());

        roomStatusService.roomStatusOff(roomStatus);
        opsForValue.set(redisKey,JsonUtils.toJson(roomStatus),60, TimeUnit.SECONDS);
    }
}
