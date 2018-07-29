/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.base.HookImpl

import android.app.Application
import android.content.Context
import com.townwang.awemetown.base.BaseHook
import com.townwang.awemetown.config.Config
import com.townwang.awemetown.mvp.bean.HookBean
import com.townwang.awemetown.mvp.bean.UpdateBean
import com.townwang.awemetown.utils.JsonUtils
import com.townwang.awemetown.utils.JsonUtils.stringToBean
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * @author Town
 * @created at 2018/7/16 0:11
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/16 0:11
 * @Remarks Hook Base
 */
abstract class HookImpl :BaseHook{

    lateinit var context: Context

    lateinit var hookclass: Class<*>

    var bean: HookBean? = null

    var beanUp: UpdateBean? =null
    /**
     * init bean
     */
    abstract fun initHookBean(): HookBean?
    /**
     * hook fun
     */

    /**
     * seek package
     */
   fun hook(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (bean == null){
            bean = initHookBean()
        }
        if (checkUpdate() && bean != null && beanUp!=null) {
            if (beanUp!!.status) {
                if (lpparam.packageName != bean!!.packageName) {
                    return
                }
                hookContext()
            }
        }
    }

    /**
     * check
     */
    fun checkUpdate(): Boolean {
        return try {
            val strUpload = JsonUtils.loadJsonDatafromLocal(Config.folderName, Config.updateFileName)
            beanUp = stringToBean(strUpload.toString(), UpdateBean::class.java)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * hook context
     */
    override fun hookContext() {
        XposedHelpers.findAndHookMethod(Application::class.java, bean!!.AppFunName, Context::class.java, object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun afterHookedMethod(param: XC_MethodHook.MethodHookParam?) {
                try {
                    context = (param!!.args[0] as Context)
                    hookClass()
                } catch (e: Exception) {
                    XposedBridge.log("HOOK Context err")
                }
            }
        })
    }


    /**
     * seek classLoader
     */
    override fun hookClass() {
        try {
            hookclass = context.classLoader.loadClass(bean!!.className)
            hookFunction()
        } catch (e: Exception) {
            XposedBridge.log("HOOK class err")
        }
    }

    abstract fun hookFunction()

    /**
     * hook method
     */
    fun findAndHookMethod( vararg parameterTypesAndCallback: Any): XC_MethodHook.Unhook {
        return XposedHelpers.findAndHookMethod(bean!!.className, hookclass.classLoader, bean!!.funName, *parameterTypesAndCallback)
    }
}
