/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.utils.helper

import android.content.Context
import android.util.SparseArray
import android.view.View

/**
 * @author Town
 * @created at 2018/7/15 18:58
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/15 18:58
 * @Remarks 万能适配器之ViewHolder
 */
class ViewHolder private constructor(context: Context, layoutId: Int) {

    /**
     * 保存所有itemview的集合
     */
    private val mViews: SparseArray<View>

    /**
     * 获取根view
     *
     */
    private val converView: View = View.inflate(context, layoutId, null)

    init {
        converView.tag = this
        mViews = SparseArray()
    }

    /**
     * 获取节点view
     *
     */
    fun <T : View> getItemView(id: Int): T {
        var view: View? = mViews.get(id)
        if (view == null) {
            view = converView.findViewById(id)
            mViews.append(id, view)
        }
        @Suppress("UNCHECKED_CAST")
        return (view as T?)!!
    }

    /**
     * 获取根view
     *
     * @return
     * @author Town
     * @date 2017/11/20 11:50
     */
    fun getConverView(): View {
        return converView
    }
    companion object {
        fun newsInstance(convertView: View?, context: Context, layoutId: Int): ViewHolder {
            return if (convertView == null) {
                ViewHolder(context, layoutId)
            } else {
                convertView.tag as ViewHolder
            }
        }
    }

}
