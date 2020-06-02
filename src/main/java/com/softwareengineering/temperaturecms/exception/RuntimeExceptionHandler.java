package com.softwareengineering.temperaturecms.exception;


import com.softwareengineering.temperaturecms.enums.ResponseEnum;
import com.softwareengineering.temperaturecms.utils.WebResultUtil;
import com.softwareengineering.temperaturecms.vo.ResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-14
 */
@ControllerAdvice
public class RuntimeExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handle(RuntimeException e){
        return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.ERROR,e.getMessage()),HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(UserLoginException.class)
    public ResponseEntity<String> userLoginHnadler(){
        return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.NEED_LOGIN), HttpStatus.UNAUTHORIZED);
    }
}
