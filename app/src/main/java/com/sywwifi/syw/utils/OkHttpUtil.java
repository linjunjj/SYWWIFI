package com.sywwifi.syw.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sywwifi.syw.module.wifi.activity.RegTwoActivity;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp的工具类
 */
public class OkHttpUtil
{

    private static OkHttpClient okHttpClient;
    private static Handler handler = new Handler();
    private static String android;
    private static Context mContext;

    public static void initOkHttp(Context context)
    {
        mContext = context;
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type","application/json")
                                .addHeader("Accept","*/*")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .connectionPool(new ConnectionPool(5, 10, TimeUnit.MINUTES))
                .build();
    }

    /**
     * 异步Get请求
     *
     * @param url
     */
    public static void get(final String url, final OnGetDataListener onGetDataListener)
    {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                if (onGetDataListener != null)
                {
                    onGetDataListener.onFailure(url, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                final String str = response.body().string();
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (onGetDataListener != null)
                        {
                            onGetDataListener.onResponse(url, str);
                        }
                    }
                });
            }
        });
    }


    /**
     * 同步get请求 -- 让子线程调用
     *
     * @return
     */
    public static Response downResponse(String url)
    {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = okHttpClient.newCall(request);
        try
        {
            return call.execute();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Post提交键值对参数
     */
    public static void postSubmitForm(
            final String url,
            Map<String, String> params, final OnGetDataListener onGetDataListener)
    {
        FormBody formBody = null;
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.size() > 0)
        {
            for (String key : params.keySet())
            {
                String value = params.get(key);
                builder.add(key, value);
            }
        }
        formBody = builder.build();
        final Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, final IOException e)
            {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (onGetDataListener != null)
                        {
                            onGetDataListener.onFailure(url,e.getMessage());
                        }
                    }
                });

            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException
            {
                if(response.isSuccessful()){

                    final String str = response.body().string();
                    handler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if (onGetDataListener != null)
                            {
                                onGetDataListener.onResponse(url, str);
                            }
                        }
                    });
                }else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "请检查服务器", Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            }
        });

    }

    private static final MediaType MEDIA_JSON
            = MediaType.parse("application/json; charset=utf-8");

    /**
     * Post提交Json参数，参数放置在Http Body中
     */
    public static void postJSONBody(final String url, Object obj,
                                    final OnGetDataListener onGetDataListener)
    {
        Request request = null;

        if (obj != null)
        {
            Gson gson = new GsonBuilder().create();
            String jsonParam = gson.toJson(obj);

            RequestBody body = RequestBody.create(MEDIA_JSON, jsonParam);

            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        }
        else
        {
            RequestBody body = new FormBody.Builder().build();
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        }

        if (request == null)
        {
            return;
        }

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                if (onGetDataListener != null)
                {
                    onGetDataListener.onFailure(url, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                final String str = response.body().string();
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (onGetDataListener != null)
                        {
                            onGetDataListener.onResponse(url, str);
                        }
                    }
                });
            }
        });
    }

    /**
     * Post提交Json参数，参数放置在Http Body中
     */
    public static void postJSONBody(final String url, String jsonParam,
                                    final OnGetDataListener onGetDataListener)
    {
        RequestBody body = RequestBody.create(MEDIA_JSON, jsonParam);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                if (onGetDataListener != null)
                {
                    onGetDataListener.onFailure(url, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                final String str = response.body().string();
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (onGetDataListener != null)
                        {
                            onGetDataListener.onResponse(url, str);
                        }
                    }
                });
            }
        });
    }

    public interface OnGetDataListener
    {
        void onResponse(String url, String data);

        void onFailure(String url, String error);
    }
}
