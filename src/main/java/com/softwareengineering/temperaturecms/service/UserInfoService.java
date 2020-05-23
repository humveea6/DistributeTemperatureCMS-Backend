package com.softwareengineering.temperaturecms.service;

import com.softwareengineering.temperaturecms.pojo.User;
import com.softwareengineering.temperaturecms.vo.ResponseVo;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-23
 */
public interface UserInfoService {
    /*
    注册
     */
    ResponseVo<User> register(User user);
    /*
    登录
     */
    ResponseVo<User> login(String username,String password);
}
