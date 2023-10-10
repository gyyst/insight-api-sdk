package com.gyyst.insight.sdk.insightapisdk.utils;

import cn.hutool.crypto.SecureUtil;

import java.util.Arrays;

/**
 * @author gyyst
 * @Description
 * @Create by 2023/2/3 17:21
 */

public class SignUtil {
    public static String getSign(String... args) {
        return SecureUtil.md5(Arrays.toString(args));
    }


}
