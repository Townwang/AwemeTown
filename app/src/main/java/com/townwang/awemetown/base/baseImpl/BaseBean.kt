/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.base.baseImpl

import java.io.Serializable

/**
 * @author Town
 * @created at 2018/7/15 18:58
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/15 18:58
 * @Remarks  TODO 请求结果基础bean
 */
open class BaseBean<T>(var name:String,var data:T) : Serializable