package com.gyyst.insight.sdk.insightapisdk.aop;

import cn.hutool.core.util.RandomUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.gyyst.insight.sdk.insightapisdk.client.InsightApiClient;
import com.gyyst.insight.sdk.insightapisdk.model.Auth;
import jakarta.annotation.Resource;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


@Aspect
@Component
public class ExecuteAspect {

    private final Cache<Long, Boolean> nonceCache = Caffeine.newBuilder()
            //初始数量
            .initialCapacity(100)
            //最大条数
            .maximumSize(100_000)
            //expireAfterWrite和expireAfterAccess同时存在时，以expireAfterWrite为准
            //最后一次写操作后经过指定时间过期
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .build();
    @Resource
    private InsightApiClient insightApiClient;

    @Before("@annotation(com.gyyst.insight.sdk.insightapisdk.annotation.Sign)")
    public void sign() {
        insightApiClient.init();
        Auth auth = insightApiClient.getAuth().get();
        long nonce;
        do {
            nonce = RandomUtil.randomLong();
        } while (nonceCache.getIfPresent(nonce) == null);
        nonceCache.put(nonce, Boolean.TRUE);
        auth.sign(nonce, LocalDateTime.now());
        insightApiClient.close();
    }
}
