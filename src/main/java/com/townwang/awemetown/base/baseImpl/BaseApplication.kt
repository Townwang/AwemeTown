/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.base.baseImpl
import android.app.Application

/**
 * @author Town
 * @created at 2018/7/15 18:58
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/15 18:58
 * @Remarks 全局Application
 */

class BaseApplication : Application() {
    //伴生对象
    companion object {
        /**
         * 全局Application
         */
        private var app: BaseApplication? = null
        fun instance() = app!!
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}
