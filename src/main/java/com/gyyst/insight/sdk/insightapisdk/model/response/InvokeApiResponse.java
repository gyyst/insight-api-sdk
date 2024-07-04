package com.gyyst.insight.sdk.insightapisdk.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvokeApiResponse {
    /**
     * 可能为Map集合，也可能为String类型
     */
    private Map<String, Object> responseBody;
}
