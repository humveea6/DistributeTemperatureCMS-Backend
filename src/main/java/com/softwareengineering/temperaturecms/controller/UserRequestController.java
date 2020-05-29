package com.softwareengineering.temperaturecms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-29
 */
@RestController
@Slf4j
@RequestMapping("/room")
public class UserRequestController {

    @PostMapping("/service")
    public ResponseEntity<String> RequestOn(@RequestParam Integer id,
                                            @RequestParam Double currentTemperature){

    }
}
