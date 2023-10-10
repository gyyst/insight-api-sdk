package com.gyyst.insight.sdk.insightapisdk.model.request;

import lombok.Data;

import java.util.Map;

/**
 * @author gyyst
 * @Description
 * @Create by 2023/9/20 22:57
 */
@Data
public class InvokeApiRequest {
    private Long apiId;
    /**
     * 请求参数
     */
    private Map<String, String> requestParams;

    /**
     * 请求体
     */
    private Map<String, String> requestBody;

    /**
     * 请求头
     */
    private Map<String, String> requestHeader;
}
