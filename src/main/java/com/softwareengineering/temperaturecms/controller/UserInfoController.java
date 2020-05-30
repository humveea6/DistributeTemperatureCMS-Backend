package com.softwareengineering.temperaturecms.controller;

import com.softwareengineering.temperaturecms.consts.CMSConst;
import com.softwareengineering.temperaturecms.enums.ResponseEnum;
import com.softwareengineering.temperaturecms.form.UserLoginform;
import com.softwareengineering.temperaturecms.form.UserRegisterform;
import com.softwareengineering.temperaturecms.pojo.User;
import com.softwareengineering.temperaturecms.service.UserInfoService;
import com.softwareengineering.temperaturecms.utils.WebResultUtil;
import com.softwareengineering.temperaturecms.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-23
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegisterform userRegisterform,
                                   BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("参数错误,{}",bindingResult.getFieldError().getDefaultMessage());
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.PARAM_ERROR,bindingResult.getFieldError().getDefaultMessage()), HttpStatus.OK);
        }
        User user = new User();
        user.setUserName(userRegisterform.getUsername());
        user.setPassWord(userRegisterform.getPassword());
        if(userRegisterform.getEmail()!=null){
            user.setEmail(userRegisterform.getEmail());
        }
        if(userRegisterform.getPhone()!=null){
            user.setPhone(userRegisterform.getPhone());
        }
        user.setRole(1);

        return userInfoService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginform userLoginform,
                                  BindingResult bindingResult, HttpSession httpSession){
        if(bindingResult.hasErrors()){
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.PARAM_ERROR,bindingResult.getFieldError().getDefaultMessage()),HttpStatus.OK);
        }

        ResponseVo<User> userResponseVo = userInfoService.login(userLoginform.getUsername(), userLoginform.getPassword());

        //session
        httpSession.setAttribute(CMSConst.CURRENT_USER,userResponseVo.getData());

        return WebResultUtil.buildResult(userResponseVo,HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseVo<User> userInfo(HttpSession httpSession){
        User user = (User)httpSession.getAttribute(CMSConst.CURRENT_USER);

        return ResponseVo.success(user);
    }

    @PostMapping("/logout")
    public ResponseVo logout(HttpSession httpSession){
        httpSession.removeAttribute(CMSConst.CURRENT_USER);

        return ResponseVo.success("退出成功");
    }
}
