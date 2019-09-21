package com.example.solucioncrud_david_android;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HTTPRequests
{
    public static OkHttpClient client;
    private static String host = "http://192.168.253.128/";

    static
    {
        client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).build();
    }

    public static Call POST(String endpoint, RequestBody body)
    {
        Request request = new Request.Builder()
                .url(host + endpoint).post(body).build();

        return execute(request);
    }

    public static Call GET(String endpoint)
    {
        Request request = new Request.Builder()
                .url(host + endpoint).get().build();

        return execute(request);
    }

    public static Call UPDATE(String endpoint, RequestBody body)
    {
        Request request = new Request.Builder()
                .url(host + endpoint).put(body).build();

        return execute(request);
    }

    public static Call DELETE(String endpoint)
    {
        Request request = new Request.Builder()
                .url(host + endpoint).delete().build();

        return execute(request);
    }

    public static Call execute(Request request)
    {
        return client.newCall(request);
    }
}
