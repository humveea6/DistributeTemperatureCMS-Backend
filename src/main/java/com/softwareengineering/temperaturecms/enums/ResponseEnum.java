package com.softwareengineering.temperaturecms.enums;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-23
 */
public enum ResponseEnum {

    ERROR(-1, "服务端错误"),

    SUCCESS(0, "成功"),

    PASSWORD_ERROR(1,"密码错误"),

    USERNAME_EXIST(2, "用户名已存在"),

    PARAM_ERROR(3, "参数错误"),

    EMAIL_EXIST(4, "邮箱已存在"),

    NEED_LOGIN(10, "用户未登录, 请先登录"),

    USERNAME_OR_PASSWORD_ERROR(11, "用户名或密码错误"),

    AC_ON_FAIL(12,"中央空调启动失败"),

    CHANGE_TARGET_TEMPERATURE_FAIL(13,"修改温度失败"),

    AC_OFF_FAIL(14,"中央空调关机失败"),

    ;
    Integer code;

    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
