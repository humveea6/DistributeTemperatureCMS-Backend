package com.softwareengineering.temperaturecms.enums;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-06-11
 */
public enum StateEnum {

    IN_SERVICE(0,"IN_SERVICE"),
    WAITING(1,"WAITING"),
    FREE(2,"FREE"),
    NO_SUCH_ROOM(-1,"NO_SUCH_ROOM")
    ;


    private Integer state;

    private String desc;

    StateEnum(Integer state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public Integer getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }
}
