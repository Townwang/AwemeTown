/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.hook
import com.townwang.awemetown.base.baseImpl.BaseHook
import com.townwang.awemetown.config.VConfig
import com.townwang.awemetown.mvp.bean.HookBean
import com.townwang.logutils.Log
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

/**
 * @author Town
 * @created at 2018/7/26 11:30
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/26 11:30
 * @Remarks ad block
 */
object AdBlock: BaseHook(){

    fun start() {
        initBean()
        hookFunction()
    }

    override fun initBean(): HookBean {
        bean = HookBean()
        bean.className = VConfig.ADBLOCK_CLASS_NAME
        bean.funName = VConfig.FUNC_NAME_A
        return bean
    }
    private fun hookFunction() {
        findAndHookMethod(List::class.java,object:XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                try {
                        val list = param.args[0] as MutableList<*>
                        if (list.size > 0) {
                            for (o in list) {
                                val isAd = XposedHelpers.callMethod(o, VConfig.FUNC_NAME_ISAD) as Boolean
                                if (isAd) {
                                    list.remove(o)
                                }
                            }
                        }
                }catch (e:Exception) {
                    Log.e("adBlock err")
                }
            }
        })
    }

}