package com.softwareengineering.temperaturecms.service.Impl;

import com.softwareengineering.temperaturecms.dao.UserMapper;
import com.softwareengineering.temperaturecms.enums.ResponseEnum;
import com.softwareengineering.temperaturecms.pojo.User;
import com.softwareengineering.temperaturecms.pojo.UserExample;
import com.softwareengineering.temperaturecms.service.UserInfoService;
import com.softwareengineering.temperaturecms.utils.WebResultUtil;
import com.softwareengineering.temperaturecms.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-23
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserMapper readUserMapper;

    @Autowired
    private UserMapper writeUserMapper;

    @Override
    public ResponseEntity<String> register(User user) {
        //username不重复
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(user.getUserName());
        if (readUserMapper.countByExample(userExample) > 0){
//            throw new RuntimeException("该username已注册");
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.USERNAME_EXIST), HttpStatus.OK);
        }

        //email不重复
        UserExample userExample1 = new UserExample();
        UserExample.Criteria criteria1 = userExample1.createCriteria();
        criteria1.andEmailEqualTo(user.getEmail());
        if(readUserMapper.countByExample(userExample1) > 0){
//            throw new RuntimeException("该email已被注册");
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.EMAIL_EXIST),HttpStatus.OK);
        }

        //MD5 摘要
        String s = DigestUtils.md5DigestAsHex(user.getPassWord().getBytes(StandardCharsets.UTF_8));
        user.setPassWord(s);

        //写入数据库
        int resultCount = writeUserMapper.insertSelective(user);
        if (resultCount == 0){
//            throw  new RuntimeException("注册失败");
            return WebResultUtil.buildResult(ResponseVo.error(ResponseEnum.ERROR),HttpStatus.OK);
        }

        return WebResultUtil.buildResult(ResponseVo.successByMsg(),HttpStatus.OK);
    }

    @Override
    public ResponseVo<User> login(String username, String password) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(username);
        List<User> userList = readUserMapper.selectByExample(userExample);
        if(userList == null){
            //return error
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        User user = userList.get(0);
        if(!user.getPassWord().equalsIgnoreCase(DigestUtils.
                md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))){
            //password error
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        user.setPassWord("");

        return ResponseVo.success(user);
    }

}
