package com.softwareengineering.temperaturecms.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-29
 */
public class WebResultUtil<T> {

    private WebResultUtil(){};

    public static<T> ResponseEntity<T> buildResult(Object obj,HttpStatus httpStatus){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return (ResponseEntity<T>) ResponseEntity.status(httpStatus).headers(headers).body(JsonUtils.toJson(obj));
    }

    public static<T> ResponseEntity<T> buildJsonResult(String obj,HttpStatus httpStatus){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return (ResponseEntity<T>) ResponseEntity.status(httpStatus).headers(headers).body(obj);
    }

    public static<T> ResponseEntity<T> buildObjectResult(Object obj,HttpStatus httpStatus){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return (ResponseEntity<T>) ResponseEntity.status(httpStatus).headers(headers).body(obj);
    }

    public static<T> ResponseEntity<T> buildObjecOritResult(Object obj, HttpStatus httpStatus){
        return (ResponseEntity<T>) ResponseEntity.status(httpStatus).body(obj);
    }

    /**
     * 用于header有自定义参数，放入map中
     * @param obj
     * @param httpStatus
     * @param map
     * @param <T>
     * @return
     */
    public static<T> ResponseEntity<T> buildResult(Object obj, HttpStatus httpStatus, Map<String,String> map){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if(!CollectionUtils.isEmpty(map)){
            map.forEach((k,v) -> {
                headers.add(k,v);
            });
        }
        return (ResponseEntity<T>) ResponseEntity.status(httpStatus).headers(headers).body(JsonUtils.toJson(obj));
    }

    public static<T> ResponseEntity<T> buildDecoderResult(Object obj,HttpStatus httpStatus){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return (ResponseEntity<T>) ResponseEntity.status(httpStatus).headers(headers).body(JsonUtils.toDecoderJson(obj));
    }

    public static<T> ResponseEntity<T> buildResultWithNull(Object obj,HttpStatus httpStatus){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return (ResponseEntity<T>) ResponseEntity.status(httpStatus).headers(headers).body(JsonUtils.toJsonWithNull(obj));
    }

}
