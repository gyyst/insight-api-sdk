package com.gyyst.insight.sdk.insightapisdk.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.util.Map;


public class HttpUtil {
    private static final String SERVER_ADDRESS = "https://oarfish-glad-pony.ngrok-free.app/api/invoker/v1";
    private static final String TEST_ADDRESS = "http://localhost:5000/api/invoker/v1";
    //    private static final String SERVER_ADDRESS = "localhost:7529/api";
    private static final OkHttpClient CLIENT = new OkHttpClient();

    @Deprecated
    public static HttpResponse get(String url, Map<String, String> headers) {
        url = url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
        return HttpRequest
                .get(StrUtil.format("{}/{}", SERVER_ADDRESS, url))
                .headerMap(headers, true)
                .execute();
    }


    public static HttpResponse postForm(String url, Map<String, String> headers, File file, String fileInfo) {
        url = url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
        HttpResponse response = HttpRequest
                .post(StrUtil.format("{}/{}", SERVER_ADDRESS, url))
                .headerMap(headers, true)
                .form("file", file)
                .form("fileInfo", fileInfo)
                .contentType("multipart/form-data").execute();
        return response;
    }

    public static HttpResponse post(String url, Map<String, String> headers, String body) {
        return HttpRequest
                .post(StrUtil.format("{}/{}", SERVER_ADDRESS, url))
                .headerMap(headers, true)
                .body(body)
                .execute();
    }

    public static HttpResponse post(Map<String, String> headers, String body) {
        return HttpRequest
                .post(SERVER_ADDRESS)
                .headerMap(headers, true)
                .body(body)
                .execute();
    }

    public static Response putFileSlice(String url, byte[] file) {
        Request httpRequest = new Request.Builder()
                .put(RequestBody.create(file))
                .url(url)
                .build();
        Response response;
        try {
            response = CLIENT.newCall(httpRequest).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
