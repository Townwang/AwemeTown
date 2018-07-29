/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.hook

import com.townwang.awemetown.base.HookImpl.HookImpl
import com.townwang.awemetown.mvp.bean.HookBean
import de.robv.android.xposed.XC_MethodReplacement

/**
 * @author Town
 * @created at 2018/7/24 12:29
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/24 12:29
 * @Remarks self test
 *
 */

class SelfTest:HookImpl(){
    override fun initHookBean(): HookBean? {
        return HookBean()
    }

    override fun hookFunction() {
        findAndHookMethod(XC_MethodReplacement.returnConstant(beanUp != null))
    }

}