/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.base.baseImpl

import com.townwang.awemetown.mvp.bean.HookBean
import com.townwang.awemetown.utils.helper.HookHolder
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

/**
 * @author Town
 * @created at 2018/8/6 14:32
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/8/6 14:32
 * @Remarks
 */

abstract class BaseHook{
    var bean: HookBean = initBean()

    abstract fun initBean(): HookBean


    fun loadClass():Class<*>{
      return  HookHolder.instance().context!!.classLoader.loadClass(bean.className)
    }
    /**
     * hook method
     */
    fun findAndHookMethod(vararg parameterTypesAndCallback: Any): XC_MethodHook.Unhook {
        return XposedHelpers.findAndHookMethod(this.bean.className, loadClass().classLoader,bean.funName, *parameterTypesAndCallback)
    }


}