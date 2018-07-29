/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.mvp.bean

/**
 * @author Town
 * @created at 2018/7/16 22:45
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/16 22:45
 * @Remarks Hook 数据类
 */

data class HookBean(
        var configCode: Int = 0,
        var packageName: String = "com.townwang.awemetown",
        var className: String = "com.townwang.awemetown.mvp.activity.MainActivity",
        var AppFunName: String = "attach",
        var funName: String = "l")