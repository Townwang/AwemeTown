/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.hook

import android.os.Bundle
import com.townwang.awemetown.base.baseImpl.BaseHook
import com.townwang.awemetown.config.VConfig
import com.townwang.awemetown.mvp.bean.HookBean
import com.townwang.logutils.Log
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

/**
 * @author Town
 * @created at 2018/7/26 11:30
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/26 11:30
 * @Remarks record a video
 */
object RecordVideo : BaseHook() {
    override fun initBean(): HookBean {
        bean = HookBean()
        bean.className = VConfig.RECORD_CLASS_NAME
        bean.funName = VConfig.FUNC_NAME_ONCREATE
        return bean
    }


     fun hookFunction() {
        try {
        findAndHookMethod(Bundle::class.javaObjectType, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                try {
                    val limitTime = 59000
                    val shortVideoContext = XposedHelpers.getObjectField(
                            param.thisObject, VConfig.FUNC_NAME_C)
                    XposedHelpers.setLongField(
                            shortVideoContext, VConfig.FUNC_NAME_M_MAX_DURATION, limitTime.toLong())
                } catch (e: Exception) {
                    Log.e("record a video err")
                }
            }
        })
        }catch (e:Throwable){
            Log.e("record a video Throwable err")
        }
    }

}