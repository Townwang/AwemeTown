/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.utils

import android.content.Context
import com.townwang.awemetown.utils.helper.HookHolder

/**
 * @author Town
 * @created at 2018/8/6 17:15
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/8/6 17:15
 * @Remarks
 */
object VUtils{
    /**
     * get awemeApp version
     */
    fun getAwemeVersionCode(): Int {
        val packages = HookHolder.instance().context!!.packageManager.getInstalledPackages(0)!!
        for (packag in packages) {
            if (packag.packageName == "com.ss.android.ugc.aweme") {
                @Suppress("DEPRECATION")
                return packag.versionCode.toString().toInt()
            }
        }
        return 0
    }

}