/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.retroft


import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * @author Town
 * @created at 2018/7/15 18:58
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/15 18:58
 * @Remarks  Retrofit 顶层接口
 */
interface BaseApi {
    /**
     * 构建Retrofit
     */
      fun getRetrofit():Retrofit?
    /**
     * 日志拦截器
     */
    fun getLoggerInterceptorBody(): HttpLoggingInterceptor
}
