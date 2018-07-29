/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.hook

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * @author Town
 * @created at 2018/7/27 19:31
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/27 19:31
 * @Remarks main
 */

class HookMain: IXposedHookLoadPackage{

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        SelfTest().hook(lpparam)
        UploadVideo().hook(lpparam)
        AdBlock().hook(lpparam)
    }

}