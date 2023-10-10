package com.gyyst.insight.sdk.insightapisdk.client;


import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.gyyst.insight.sdk.insightapisdk.annotation.Sign;
import com.gyyst.insight.sdk.insightapisdk.common.BaseResponse;
import com.gyyst.insight.sdk.insightapisdk.model.Auth;
import com.gyyst.insight.sdk.insightapisdk.model.request.InvokeApiRequest;
import com.gyyst.insight.sdk.insightapisdk.utils.HttpUtil;
import lombok.Data;


/**
 * @author gyyst
 * @Description
 * @Create by 2023/2/3 19:10
 */

@Data
public class InsightApiClient {
    private static final Long chunkSize = 5 * 1024 * 1024L;
    private Auth auth;

    public InsightApiClient(Auth auth) {
        this.auth = auth;
    }

    @Sign
    public BaseResponse invokeApi(InvokeApiRequest invokeApiRequest) {
        HttpResponse post = HttpUtil.post(auth.headers, JSONUtil.toJsonStr(invokeApiRequest));
        BaseResponse baseResponse = JSONUtil.toBean(post.body(), BaseResponse.class);
        return baseResponse;
    }
}
