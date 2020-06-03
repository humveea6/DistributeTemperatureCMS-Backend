package com.softwareengineering.temperaturecms.middleware;

import com.softwareengineering.temperaturecms.dto.ChangeTargetTemperatureDto;
import com.softwareengineering.temperaturecms.pojo.RoomStatus;
import com.softwareengineering.temperaturecms.service.RoomStatusService;
import com.softwareengineering.temperaturecms.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.softwareengineering.temperaturecms.consts.CMSConst.CHANGE_TARGET_TEMPERATURE_QUEUE;
import static com.softwareengineering.temperaturecms.consts.CMSConst.ROOM_SERVICE_REDIS_KEY;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-31
 */
@Component
@Slf4j
@RabbitListener(queues = CHANGE_TARGET_TEMPERATURE_QUEUE)
public class ChangeTargetTemperatureListener {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RabbitHandler
    public void receive(String msg){
        //todo:update into redis
        ChangeTargetTemperatureDto changeTargetTemperatureDto = JsonUtils.fromJson(msg, ChangeTargetTemperatureDto.class);

        log.info("ChangeTargetTemperatureListener changeTargetTemperatureDto: "+JsonUtils.toJson(changeTargetTemperatureDto));
        String redisKey = String.format(ROOM_SERVICE_REDIS_KEY,changeTargetTemperatureDto.getId());
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String value = opsForValue.get(redisKey);

        if(StringUtils.isEmpty(value)){
            return;
        }
        RoomStatus roomStatus = JsonUtils.fromJson(value, RoomStatus.class);
        log.info("ChangeTargetTemperatureListener roomstatus: "+JsonUtils.toJson(roomStatus));

        roomStatus.setTargetTemperature(changeTargetTemperatureDto.getTargetTemperature());
        roomStatus.setFansSpeed(changeTargetTemperatureDto.getFanSpeed());

        opsForValue.set(redisKey, JsonUtils.toJson(roomStatus));
    }
}
