/*
 * Copyright © 文科中的技术宅
 * blog:https://www.townwang.com
 */
package com.townwang.awemetown.base.baseImpl

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.townwang.awemetown.base.BasePresenter
import com.townwang.awemetown.base.BaseView
import com.townwang.awemetown.mvp.bean.PermissBean

/**
 * @author Town
 * @created at 2018/7/15 18:58
 * @Last Modified by: Town
 * @Last Email: android@townwang.com
 * @Last Modified time: 2018/7/15 18:58
 * @Remarks Activity基类
 */
abstract class BaseActivity<P : BasePresenter> : Activity(), BaseView {
    //that a presenter
    lateinit var presenter: P
    //that a context
    lateinit var context: Context
    //that a permission dialog
    lateinit var dialog: AlertDialog
    //that a permission data Bean
    private lateinit var permissionBean: PermissBean
    //that a result
    private lateinit var listener: PermisstionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayout())
        context = this
        presenter = initPresenter()
        initData()
    }

    fun setPermissions(permissionBean: PermissBean, listener: PermisstionListener) {
        this.permissionBean = permissionBean
        this.listener = listener
        initPermission()

    }

    private fun initPermission() {
            // SDK version judgment
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //  permission has been obtained
                    val i = ContextCompat.checkSelfPermission(this, permissionBean.permiss)
                    //  GRANTED---授权  DINIED---拒绝
                    if (i != PackageManager.PERMISSION_GRANTED) {
                        //Prompt the user
                        showDialogTipUserRequestPermission()
                    }else{
                        listener.permisstionResult(permissionBean.requestCode)
                    }
                }else{
                listener.permisstionResult(permissionBean.requestCode)
            }
    }

    private fun showDialogTipUserRequestPermission() {
        AlertDialog.Builder(this)
                .setTitle(permissionBean.showDialogTitle).setMessage(permissionBean.showDialogContent)
                .setPositiveButton("立即开启", { _, _ ->
                    startRequestPermission()
                }).setNegativeButton("取消", { _, _ ->
                    //                    finish()
                }).setCancelable(false).show()
    }

    private fun startRequestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(permissionBean.permiss),permissionBean.requestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == permissionBean.requestCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults!![0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    val b = shouldShowRequestPermissionRationale(permissionBean.permiss)
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting()
                    } else {
                        finish()
                    }
                } else {
                    listener.permisstionResult(requestCode)
                }
            }
        }
    }

    private fun showDialogTipUserGoToAppSettting() {
        dialog = AlertDialog.Builder(this)
                .setTitle(permissionBean.goToSetTitle)
                .setMessage(permissionBean.goToSetContent)
                .setPositiveButton("立即开启", { _, _ ->
                    goToSetting()
                }).setNegativeButton("取消", { _, _ ->
                    //                    finish()
                }).setCancelable(false).show()
    }

    /**
     * go to setting
     */
    private fun goToSetting() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, permissionBean.requestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == permissionBean.requestCode) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                val i = ContextCompat.checkSelfPermission(this,permissionBean.permiss)
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting()
                } else {
                    if (dialog.isShowing) {
                        dialog.dismiss()
                    }
                    listener.permisstionResult(requestCode)
                }
            }
        }
    }
    override fun onDestroy() {
        presenter.detach()//在presenter中解绑释放view
        super.onDestroy()
    }


    /**
     * 在子类中初始化对应的presenter
     *
     */
    abstract fun initPresenter(): P

    /**
     * 初始化布局
     */
    abstract fun initLayout(): Int

    /**
     * 设置数据
     */
    abstract fun initData()

    /**
     * 权限回调
     */
    interface PermisstionListener {
        fun permisstionResult(baseRequestCode: Int)
    }
}