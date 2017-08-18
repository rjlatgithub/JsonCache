package com.makeunion.jsoncachelib.util;

/**
 * Created by renjialiang on 2017/8/7.
 */

public class StringUtil {

    public static final boolean isNull(String str) {
        return str == null || str.equals("") || str.equalsIgnoreCase("null");
    }
}
