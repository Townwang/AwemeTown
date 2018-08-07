/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.hook

import android.app.Application
import android.content.Context
import android.os.Bundle
import com.townwang.awemetown.base.baseImpl.BaseHook
import com.townwang.awemetown.config.VConfig
import com.townwang.awemetown.mvp.bean.HookBean
import com.townwang.awemetown.utils.helper.HookHolder
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import java.lang.reflect.Method

/**
 * @author Town
 * @created at 2018/8/6 15:05
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/8/6 15:05
 * @Remarks
 */
object HookContext : BaseHook() {

    fun start() {
        initBean()
        hookContext()
    }

    override fun initBean(): HookBean {
        bean = HookBean()
        bean.className = VConfig.APPLICATION_CLASS_NAME
        bean.funName = VConfig.FUNC_NAME_ATTACH
        return bean
    }
    private fun hookContext() {
        try {
            XposedHelpers.findAndHookMethod(Application::class.java, bean.funName, Context::class.java, object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    if (null == HookHolder.instance().context) {
                        HookHolder.instance().context = (param.args[0] as Context)
                    }
                    if (null == HookHolder.instance().param) {
                        HookHolder.instance().param = param
                    }
                    HookHolder.instance().MainHook()
                }
            })
        }catch (e:Throwable){
            XposedBridge.log("出错了")
        }
    }

}