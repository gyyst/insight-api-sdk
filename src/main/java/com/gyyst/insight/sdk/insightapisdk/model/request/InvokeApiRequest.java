package com.gyyst.insight.sdk.insightapisdk.model.request;

import cn.hutool.json.JSONUtil;
import lombok.Builder;
import lombok.Data;

import java.util.Map;


@Data
@Builder
public class InvokeApiRequest {
    /**
     * api的id
     */
    private Long apiId;

    /**
     * 请求参数
     */
    private Map<String, Object> requestParams;
    /**
     * 请求体
     */
    private Map<String, Object> requestBody;
    /**
     * 请求头
     */
    private Map<String, Object> requestHeader;

    public void requestParams(String json) {
        this.requestParams = JSONUtil.toBean(json, Map.class);
    }

    public void requestBody(String json) {
        this.requestBody = JSONUtil.toBean(json, Map.class);
    }

    public void requestHeader(String json) {
        this.requestHeader = JSONUtil.toBean(json, Map.class);
    }
}
