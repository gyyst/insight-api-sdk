package com.gyyst.insight.sdk.insightapisdk.client;


import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.gyyst.insight.sdk.insightapisdk.annotation.Sign;
import com.gyyst.insight.sdk.insightapisdk.common.BaseResponse;
import com.gyyst.insight.sdk.insightapisdk.model.Auth;
import com.gyyst.insight.sdk.insightapisdk.model.request.InvokeApiRequest;
import com.gyyst.insight.sdk.insightapisdk.utils.HttpUtil;
import lombok.Data;


@Data
public class InsightApiClient {
    private static final Long chunkSize = 5 * 1024 * 1024L;
    private Auth publicAuth;
    private ThreadLocal<Auth> auth;

    public InsightApiClient(Auth auth) {
        publicAuth = auth;
    }

    @Sign
    public BaseResponse invokeApi(InvokeApiRequest invokeApiRequest) {
        HttpResponse post = HttpUtil.post(auth.get().headers, JSONUtil.toJsonStr(invokeApiRequest));
        BaseResponse baseResponse = JSONUtil.toBean(post.body(), BaseResponse.class);
        return baseResponse;
    }

    public void init() {
        try {
            auth.set(publicAuth.clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        auth.remove();
    }
}