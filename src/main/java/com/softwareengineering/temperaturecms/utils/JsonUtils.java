package com.softwareengineering.temperaturecms.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-29
 */
public class JsonUtils {

        private JsonUtils(){};

        public static String toJson(Object o){
            Gson gson = new Gson();
            return gson.toJson(o);
        }

        public static <T> T fromJson(String json, Class<T> clazz){
            Gson gson = new Gson();
            return gson.fromJson(json,clazz);
        }

        public static String toDecoderJson(Object o){
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            return gson.toJson(o);
        }

        public static String toJsonWithNull(Object o){
            Gson gson = new GsonBuilder().serializeNulls().create();
            return gson.toJson(o);
        }

        public static String toJsonWithoutSlash(Object o){
            Gson gson = new GsonBuilder().serializeNulls().create();
            String ret =  gson.toJson(o).replace("\\","");
            return ret;
        }
}
