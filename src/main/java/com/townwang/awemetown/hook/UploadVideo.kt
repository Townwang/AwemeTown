/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.hook
import com.townwang.awemetown.base.HookImpl.HookImpl
import com.townwang.awemetown.config.Config
import com.townwang.awemetown.mvp.bean.HookBean
import com.townwang.awemetown.utils.JsonUtils
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge

/**
 * @author Town
 * @created at 2018/7/16 0:21
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/16 0:21
 * @Remarks  upload video
 */

class UploadVideo:HookImpl(){
    override fun initHookBean(): HookBean? {
        return try {
            val string = JsonUtils.loadJsonDatafromLocal(Config.folderName, Config.awemeTownName)
            JsonUtils.stringToBean(string.toString(), HookBean::class.java)
        }catch (e:Exception){
            null
        }
    }

    override fun hookFunction() {
        findAndHookMethod( object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun afterHookedMethod(param: MethodHookParam?) {
                super.afterHookedMethod(param)
                try {
                    param?.result = 59
                }catch (e:Exception){
                    XposedBridge.log("hook set value err")
                }
            }
        })
    }
}