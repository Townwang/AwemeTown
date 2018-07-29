/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.base

import io.reactivex.disposables.Disposable

/**
 * @author Town
 * @created at 2018/7/15 18:58
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/15 18:58
 * @Remarks 逻辑接口
 */
interface BasePresenter {

    /**
     * Activity关闭把view对象置为空
     */
     fun detach()

    /**
     * 将网络请求的每一个disposable添加进入CompositeDisposable，再退出时候一并注销
     */
     fun addDisposable(subscription: Disposable)

    /**
     * 注销所有请求
     */
     fun unDisposable()
}