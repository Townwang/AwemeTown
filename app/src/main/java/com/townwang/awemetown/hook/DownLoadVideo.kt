/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.hook

import android.os.Parcel
import com.townwang.awemetown.base.baseImpl.BaseHook
import com.townwang.awemetown.mvp.bean.HookBean
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge

/**
 * @author Town
 * @created at 2018/7/31 12:14
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/31 12:14
 * @Remarks (破解抖音新方案[尚未测试])
 */

class DownLoadVideo: BaseHook(){
    override fun initBean(): HookBean {
        bean = HookBean()
        bean.className = "com.ss.android.ugc.aweme.shortvideo.ce"
        bean.funName = "ce"
        return bean
    }
//    override fun initHookBean(): HookBean? {
//
//    }

     fun hookFunction() {
        findAndHookMethod(Parcel::class.javaObjectType,object :XC_MethodHook(){
            override fun afterHookedMethod(param: MethodHookParam) {
                for (a in param.args){
                    XposedBridge.log("打印视频信息:$a")
                }
            }
        })
    }


}