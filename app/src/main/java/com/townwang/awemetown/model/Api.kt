/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.model

import com.townwang.awemetown.retroft.BaseApiImpl
/**
 * @author Town
 * @created at 2018/7/15 18:58
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/15 18:58
 * @Remarks 网络访问
 */
class Api(baseUrl: String) : BaseApiImpl(baseUrl) {
    companion object {
        private val api = Api(RetrofitService.BASE_URL)
         fun getInstance(): RetrofitService {
            return api.getRetrofit()?.create(RetrofitService::class.java)!!
        }
    }
}
