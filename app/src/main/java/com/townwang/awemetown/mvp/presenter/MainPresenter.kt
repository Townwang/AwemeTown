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
    //that a hook json
    var jsonString: String? = null
    // that a upload json
    var jsonUpLoad: String? = null

    /**
     * check update
     */
    override fun checkUpdate(awmeCode: Int, locationCode: Int) {
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
                                if (awmeCode != 0) {
                                    if (locationCode == awmeCode) {
                                        view?.setLoading("配置信息正常", LoadType.SUCCESS)
                                        //成功
                                        view?.setLoading("检测完毕，服务器环境正常", LoadType.SUCCESS)
                                        jsonUpLoad = JsonUtils.beanToString(it)
                                        view?.saveUpload()
                                    } else {
                                        jsonUpLoad = JsonUtils.beanToString(it)
                                        view?.saveUpload()
                                        view?.setLoading("正在获取适配版本", LoadType.ERROR)
                                        getServerData(awmeCode)
                                    }
                                } else {
                                    view?.setLoading("检测不到抖音版本", LoadType.ERROR)
                                }
                            } else {
                                view?.setLoading("软件有更新，请点击下载", LoadType.SUCCESS)
                                view?.setDownLoad(it.downloadUrl)
                            }
                        } else {
                            jsonUpLoad = JsonUtils.beanToString(it)
                            view?.saveUpload()
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

    /**
     * get server data
     */
    override fun getServerData(awemeVrsionCode: Int) {
        if (view != null) {
            Api.getInstance().getAwemeTown(awemeVrsionCode)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe {
                        addDisposable(it)
                        view?.setLoading("正在下载适配数据...", LoadType.LOADING)
                    }.map(Function<BaseBean<HookBean>, HookBean> {
                        return@Function it.data
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        view?.setLoading("适配数据下载完成", LoadType.SUCCESS)
                        jsonString = JsonUtils.beanToString(it)
                        view?.saveData()
                    }, {
                        //失败 报错
                        //成功
                        view?.setLoading("未适配此版本或者网络错误", LoadType.ERROR)
                        ExceptionHelper.handleException(it)
                    })
        }
    }

    /**
     *  upload json
     */
    override fun jsonUpload(): String {
        return jsonUpLoad.toString()
    }

    /**
     * hook json
     */
    override fun jsonString(): String {
        return jsonString.toString()
    }
}

