/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.hook

import com.townwang.awemetown.base.HookImpl.HookImpl
import com.townwang.awemetown.mvp.bean.HookBean
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

/**
 * @author Town
 * @created at 2018/7/26 11:30
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/26 11:30
 * @Remarks ad block
 */
class AdBlock:HookImpl(){
    override fun initHookBean(): HookBean? {
        bean = HookBean()
        bean?.packageName = "com.ss.android.ugc.aweme"
        bean?.AppFunName = "attach"
        bean?.className = "com.ss.android.ugc.aweme.feed.adapter.h"
        bean?.funName = "a"
        return bean
    }

    override fun hookFunction() {

        findAndHookMethod(List::class.java,object:XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam?) {
                super.beforeHookedMethod(param)
                val list = param!!.args[0] as MutableList<*>
                if (list.size > 0) {
                    for (o in list) {
                        val isAd = XposedHelpers.callMethod(o, "isAd") as Boolean
                        if (isAd) {
                            list.remove(o)
                        }
                    }
                }
            }
        })
    }

}