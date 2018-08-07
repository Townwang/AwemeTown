/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.mvp.presenter

import com.townwang.awemetown.base.baseImpl.BaseBean
import com.townwang.awemetown.base.baseImpl.BasePresenterImpl
import com.townwang.awemetown.type.LoadType
import com.townwang.awemetown.model.Api
import com.townwang.awemetown.mvp.bean.HookBean
import com.townwang.awemetown.mvp.bean.UpdateBean
import com.townwang.awemetown.mvp.contact.MainContact
import com.townwang.awemetown.retroft.ExceptionHelper
import com.townwang.awemetown.utils.JsonUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * @author Town
 * @created at 2018/7/15 18:58
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/15 18:58
 * @Remarks 主页逻辑
 */

class MainPresenter(view: MainContact.view) : BasePresenterImpl<MainContact.view>(view), MainContact.presenter {
    /**
     * check update
     */
    override fun checkUpdate() {
        if (view != null) {
            Api.getInstance().checkUpdate()
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe {
                        addDisposable(it)
                        view?.setLoading("正在检测是否有更新...", LoadType.LOADING)
                    }.map(Function<BaseBean<UpdateBean>, UpdateBean> {
                        return@Function it.data
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.status) {
                            view?.setLoading("服务器正常", LoadType.SUCCESS)
                            if (it.versionCode <= view?.getVerCode()!!) {
                                view?.setLoading("软件已经是最新版本", LoadType.SUCCESS)
                                view?.saveUpload()
                            } else {
                                view?.setLoading("软件有更新，请点击下载", LoadType.SUCCESS)
                                view?.setDownLoad(it.downloadUrl)
                            }
                        } else {
                            view?.setLoading("软件不可用，请联系开发者", LoadType.ERROR)
                        }
                    }, {
                        //失败 报错
                        //成功
                        view?.setLoading("服务器异常，请联系开发者", LoadType.ERROR)
                        ExceptionHelper.handleException(it)
                    })

        }
    }
}

