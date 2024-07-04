package com.gyyst.insight.sdk.insightapisdk.model;

import cn.hutool.core.util.StrUtil;
import com.gyyst.insight.sdk.insightapisdk.utils.SignUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Data
@AllArgsConstructor
public class Auth implements Cloneable {
    private static final String ACCESS_KEY = "accessKey";
    private static final String SIGN = "sign";
    private static final String NONCE = "nonce";
    private static final String TIME_STAMP = "timeStamp";
    public Map<String, String> headers = new HashMap<>();
    private String accessKey;
    private String secretKey;
    private String sign;
    /**
     * 随机数
     */
    private Long nonce;
    /**
     * 时间戳
     */
    private LocalDateTime timeStamp;

    public Auth(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        headers.put(ACCESS_KEY, accessKey);
    }

    public static Auth create(String accessKey, String secretKey) {
        if (StrUtil.isBlank(accessKey)) {
            throw new IllegalArgumentException("empty key");
        }
        return new Auth(accessKey, secretKey);
    }

    @Override
    public Auth clone() throws CloneNotSupportedException {
        return new Auth(accessKey, secretKey);
    }

    public void sign(Long nonce, LocalDateTime timeStamp) {
        headers.put(NONCE, nonce.toString());
        headers.put(TIME_STAMP, timeStamp.toString());
        headers.put(SIGN, SignUtil.getSign(accessKey, secretKey, nonce.toString(), timeStamp.toString()));
    }


}