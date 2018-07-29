/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.base.baseImpl

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.townwang.awemetown.utils.helper.ViewHolder

/**
 * @author Town
 * @created at 2018/7/15 18:58
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/15 18:58
 * @Remarks 万能适配器
 */
abstract class BaseCommAdapter<T>(private val mDatas: List<T>?) : BaseAdapter() {

    /**
     * 设置布局
     *
     * @return 设置布局
     */
    protected abstract val layoutId: Int

    override fun getCount(): Int {
        return mDatas?.size ?: 0
    }

    override fun getItem(position: Int): T {
        return mDatas!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val holder = ViewHolder
                .newsInstance(convertView, parent.context, layoutId)
        setUI(holder, position, parent.context)
        return holder.getConverView()
    }

    /**
     * 设置UI
     *
     * @param holder 持有者对象
     * @param position 当前位置
     * @param context 上下文
     */
    protected abstract fun setUI(holder: ViewHolder, position: Int, context: Context)
}
