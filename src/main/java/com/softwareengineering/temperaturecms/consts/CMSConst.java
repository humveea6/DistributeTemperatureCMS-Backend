package com.softwareengineering.temperaturecms.consts;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-23
 */
public class CMSConst {
    public static final String CURRENT_USER = "currentUser";

    //queue name
    public static final String AC_ON_QUEUE = "AC_ON_QUEUE";

    public static final String AC_OFF_QUEUE = "AC_OFF_QUEUE";

    public final static String CHANGE_TARGET_TEMPERATURE_QUEUE = "CHANGE_TARGET_TEMPERATURE_QUEUE";

    //Redis key
    public final static String ROOM_SERVICE_REDIS_KEY = "room_status_id_%d";

    public final static String LOW_FEE_RATE_REDIS_KEY = "LOW_FEE_RATE";

    public final static String MIDDLE_FEE_RATE_REDIS_KEY = "MIDDLE_FEE_RATE";

    public final static String HIGH_FEE_RATE_REDIS_KEY = "HIGH_FEE_RATE";

    public final static String DEFAULT_MODE_REDIS_KEY = "DEFAULT_MODE";

    public final static String DEFAULT_FANS_SPEED_REDIS_KEY = "DEFAULT_FANS_SPEED_REDIS_KEY";

    public final static String LOWEST_TEMPERATURE_REDIS_KEY = "LOWEST_TEMPERATURE_REDIS_KEY";

    public final static String HIGHEST_TEMPERATURE_REDIS_KEY = "HIGHEST_TEMPERATURE_REDIS_KEY";

    public final static String TARGET_TEMPERATURE_REDIS_KEY = "TARGET_TEMPERATURE";

    public final static String ROOM_STOP_CHARGE_TIMESTAMP_REDIS_KEY = "ROOM_STOP_CHARGE_TIMESTAMP_REDIS_KEY_%d";

    public final static String ROOM_STOP_CHARGE_TOTAL_TIME_REDIS_KEY = "ROOM_STOP_CHARGE_TOTAL_TIME_REDIS_KEY_%d";
}
