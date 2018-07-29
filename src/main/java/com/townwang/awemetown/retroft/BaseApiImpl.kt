/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.retroft

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import com.townwang.logutils.Log
import com.townwang.awemetown.base.baseImpl.BaseApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Town
 * @created at 2018/7/15 18:58
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/15 18:58
 * @Remarks
 */
 open class BaseApiImpl(baseUrl: String): BaseApi {
    companion object {
        @Volatile
        private var retrofit: Retrofit? = null
    }

    override fun getRetrofit(): Retrofit? {
        when (retrofit) {
            null -> //锁定代码块
                synchronized(BaseApiImpl::class.java) {
                    if (retrofit == null) retrofit = retrofitBuilder.build()
                    //创建retrofit对象
                }
        }
       return retrofit
    }

    private val retrofitBuilder = Retrofit.Builder()

    private val httpBuilder = OkHttpClient.Builder()
    //持久化数据
    private val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(BaseApplication.instance()))

    init {
        retrofitBuilder.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                        .setLenient()
                        .create()
                ))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpBuilder.cookieJar(cookieJar)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(getLoggerInterceptorBody()).build())
                .baseUrl(baseUrl)
    }


    /**
     * 日志拦截器 JSON
     *
     * @return
     */
    final override fun getLoggerInterceptorBody(): HttpLoggingInterceptor {
        val level = HttpLoggingInterceptor.Level.BODY
        val loggingInterceptor = HttpLoggingInterceptor { message -> Log.v("ApiUrl信息--->$message") }
        loggingInterceptor.level = level
        return loggingInterceptor
    }

}
