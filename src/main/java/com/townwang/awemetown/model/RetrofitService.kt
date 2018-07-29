/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.model

import com.townwang.awemetown.base.baseImpl.BaseBean
import com.townwang.awemetown.mvp.bean.HookBean
import com.townwang.awemetown.mvp.bean.UpdateBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * @author Town
 * @created at 2018/7/15 18:58
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/15 18:58
 * @Remarks 接口示例
 */
interface RetrofitService  {
    //伴生对象 提供顶级Url
    companion object {
        //服务器接口
        const val BASE_URL = "https://townwang.com/"
    }
    /**
     *   GET测试示例
     *  {id} Url参数拼接 {@path("id") 名字:类型 }
     *  {map}body参数
     *  返回BaseBean参数解析后数据
     */
    @GET("test/{id}")
    fun testGET(@Path("id") id: Int, @QueryMap map: Map<String, Any>): Observable<BaseBean<*>>


    /**
     * POST测试示例
     *  {id} Url参数拼接 {@path("id") 名字:类型 }
     *  {map} body参数
     *  返回BaseBean参数解析后数据
     */
    @POST("test/{id}")
    fun testPOST(@Path("id") id: Int, @QueryMap map: Map<String, Any>): Observable<BaseBean<*>>
    /**
     * check update
     */
    @GET("app/json/aweme/check-update.json")
    fun checkUpdate():Observable<BaseBean<UpdateBean>>

    /**
     * get aweme town
     */
    @GET("app/json/aweme/hook/hook-{id}.json")
    fun getAwemeTown(@Path("id") id: Int):Observable<BaseBean<HookBean>>
}