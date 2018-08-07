/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.utils.helper

import android.annotation.SuppressLint
import android.content.Context
import com.townwang.awemetown.hook.AdBlock
import com.townwang.awemetown.hook.RecordVideo
import com.townwang.awemetown.hook.UploadVideo
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge

/**
 * @author Town
 * @created at 2018/8/6 15:19
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/8/6 15:19
 * @Remarks
 */
class HookHolder {
    var context:Context? = null
    var param: XC_MethodHook.MethodHookParam? = null

    //伴生对象
    companion object {
        /**
         * 全局Application
         */
        @SuppressLint("StaticFieldLeak")
        private var app: HookHolder = HookHolder()
        fun instance() = this.app
    }

    fun MainHook(){
        AdBlock.start()
        RecordVideo.start()
        UploadVideo.start()
    }


}