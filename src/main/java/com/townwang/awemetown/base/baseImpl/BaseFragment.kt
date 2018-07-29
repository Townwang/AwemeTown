/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
@file:Suppress("DEPRECATION")

package com.townwang.awemetown.base.baseImpl
import android.app.Fragment
import android.os.Bundle
import com.townwang.awemetown.base.BasePresenter
import com.townwang.awemetown.base.BaseView

/**
 * @author Town
 * @created at 2018/7/15 18:58
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/15 18:58
 * @Remarks Fragment基类
 */
abstract class BaseFragment<P: BasePresenter>: Fragment(), BaseView {
    open lateinit var presenter: P
    private var isViewCreate = false//view是否创建
    private var isViewVisible = false//view是否可见
    private var isFirst = true//是否第一次加载

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = initPresenter()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewCreate = true
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isViewVisible = isVisibleToUser
        if (isVisibleToUser && isViewCreate) {
            visibleToUser()
        }
    }

    /**
     * 懒加载
     * 让用户可见
     * 第一次加载
     */
    open fun firstLoad() {

    }

    /**
     * 懒加载
     * 让用户可见
     */
    private fun visibleToUser() {
        if (isFirst) {
            firstLoad()
            isFirst = false
        }
    }
    override fun onResume() {
        super.onResume()
        if (isViewVisible) {
            visibleToUser()
        }
    }

    override fun onDestroyView() {
        presenter.detach()
        isViewCreate = false
        super.onDestroyView()
    }
    /**
     * 在子类中初始化对应的presenter
     *
     */
    abstract fun initPresenter(): P
}

