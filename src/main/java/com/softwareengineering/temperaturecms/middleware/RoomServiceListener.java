package com.softwareengineering.temperaturecms.middleware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.softwareengineering.temperaturecms.consts.CMSConst.AC_ON_QUEUE;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-29
 */
@Component
@Slf4j
@RabbitListener(queues = AC_ON_QUEUE)
public class RoomServiceListener {

    @RabbitHandler
    public void receive(Long id){
        //todo:update into redis
    }
}
