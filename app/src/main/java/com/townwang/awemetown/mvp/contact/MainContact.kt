/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.mvp.contact

import com.townwang.awemetown.base.BasePresenter
import com.townwang.awemetown.base.BaseView
import com.townwang.awemetown.type.LoadType

/**
 * @author Town
 * @created at 2018/7/15 18:58
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/15 18:58
 * @Remarks  Contact
 */

interface MainContact {
    /**
     * view interface
     */
    interface view: BaseView {
        /**
         * set word show
         */
        fun setLoading(string: String, type: LoadType)

        /**
         * get version code
         */
        fun getVerCode(): Int

        fun setDownLoad(url: String)
        fun saveUpload()
    }

    /**
     * presenter interface
     */
    interface presenter: BasePresenter {
        /**
         * check update
         */
        fun checkUpdate()
    }
}
