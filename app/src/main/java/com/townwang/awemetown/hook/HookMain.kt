/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.hook

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.townwang.awemetown.config.Config
import com.townwang.awemetown.config.VConfig
import com.townwang.awemetown.utils.helper.HookHolder
import de.robv.android.xposed.*
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * @author Town
 * @created at 2018/7/27 19:31
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/27 19:31
 * @Remarks main
 */

class HookMain : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam != null) {
            @Suppress("DEPRECATED_IDENTITY_EQUALS")
            if (lpparam.appInfo == null || lpparam.appInfo.flags and (ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) !== 0) {
                return
            } else if (lpparam.isFirstApplication and (VConfig.PACKAGE_NAME == lpparam.packageName)) {
                HookContext.hookContext()
            }

            if (VConfig.MY_PACKAGE_NAME== lpparam.packageName) {
                sense()
            }

        }
    }

    private fun sense() {
        try {
            XposedHelpers.findAndHookMethod(Application::class.java, VConfig.FUNC_NAME_ATTACH, Context::class.java, object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val context = (param.args[0] as Context)
                    findAndHookMethod(VConfig.MY_CLASS_NAME, context.classLoader.loadClass(VConfig.MY_CLASS_NAME).classLoader,
                            VConfig.MY_FUNC_NAME_L, XC_MethodReplacement.returnConstant(true))
                }
            })
        }catch (e:Throwable) {

        }
    }

}