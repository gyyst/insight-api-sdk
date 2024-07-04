package com.gyyst.insight.sdk.insightapisdk.utils;

import cn.hutool.crypto.SecureUtil;

import java.util.Arrays;


public class SignUtil {
    public static String getSign(String... args) {
        return SecureUtil.md5(Arrays.toString(args));
    }


}
