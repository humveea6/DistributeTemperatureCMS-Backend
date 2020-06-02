package com.softwareengineering.temperaturecms.interceptor;

import com.softwareengineering.temperaturecms.consts.CMSConst;
import com.softwareengineering.temperaturecms.exception.UserLoginException;
import com.softwareengineering.temperaturecms.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-17
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("prehandler 233");
        User user = (User)request.getSession().getAttribute(CMSConst.CURRENT_USER);
        if(user == null){
            log.info("user==null");
            throw new UserLoginException();
//            return false;
        }

        return true;
    }
}
