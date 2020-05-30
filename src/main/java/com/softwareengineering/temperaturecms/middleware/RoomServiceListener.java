package com.softwareengineering.temperaturecms.middleware;

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

import static com.softwareengineering.temperaturecms.consts.CMSConst.AC_ON_QUEUE;
import static com.softwareengineering.temperaturecms.consts.CMSConst.ROOM_SERVICE_REDIS_KEY;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-29
 */
@Component
@Slf4j
@RabbitListener(queues = AC_ON_QUEUE)
public class RoomServiceListener {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RoomStatusService roomStatusService;

    @RabbitHandler
    public void receive(Integer id){
        //todo:update into redis
        String redisKey = String.format(ROOM_SERVICE_REDIS_KEY,id);
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();

        RoomStatus roomStatusById = roomStatusService.getRoomStatusById(id);

        opsForValue.set(redisKey, JsonUtils.toJson(roomStatusById));
    }
}
