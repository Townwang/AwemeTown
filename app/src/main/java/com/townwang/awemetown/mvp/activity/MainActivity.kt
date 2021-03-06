package com.townwang.awemetown.mvp.activity

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.didikee.donate.AlipayDonate
import android.didikee.donate.WeiXinDonate
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import com.townwang.awemetown.R
import com.townwang.awemetown.base.baseImpl.BaseActivity
import com.townwang.awemetown.config.Config
import com.townwang.awemetown.mvp.bean.HookBean
import com.townwang.awemetown.mvp.bean.PermissBean
import com.townwang.awemetown.mvp.contact.MainContact
import com.townwang.awemetown.mvp.presenter.MainPresenter
import com.townwang.awemetown.type.LoadType
import com.townwang.awemetown.utils.JsonUtils
import com.townwang.awemetown.utils.Preference
import com.townwang.phone.PhoneUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File






class MainActivity : BaseActivity<MainContact.presenter>(), MainContact.view, BaseActivity.PermisstionListener, View.OnClickListener {


    //检查更新请求码
    private var RESULT_UPLOAD_CODE = 0X0024
    //储存微信二维码请求码
    private var RESULT_WECHAT_CODE = 0X0022
    //权限弹框Bean数据
    private var permissBean: PermissBean? = null
    //支付宝捐赠code
    private val payCode = "FKX05865PGDXNRUEVLXGC2"
    private var downUrl: String? = null
    private var checked by Preference(this, "checked", false)
    override fun initPresenter(): MainContact.presenter {
        return MainPresenter(this)
    }

    override fun initLayout(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        try {
            phoneModel.text = PhoneUtils.getSystemModel()
            systemVersion.text = PhoneUtils.getSystemVersion()
            phoneMF.text = android.os.Build.BRAND
        } catch (e: Exception) {
        }

        softwareGroup.setOnClickListener {
            joinQQGroup("odUuTNIRX00HhdT1OILiNJMnNQ7Lsucv")
        }
        faceBase.setOnClickListener {
            joinQQGroup("2ODMKGLdwzZDDJZJ6-Zru8A9ixZ_3Y8L")
        }

        aliPay.setOnClickListener {
            val hasInstalledAlipayClient = AlipayDonate.hasInstalledAlipayClient(this)
            if (hasInstalledAlipayClient) {
                AlipayDonate.startAlipayClient(this, payCode)
            }
        }

        weChatPay.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                permissBean = PermissBean(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        , RESULT_WECHAT_CODE
                        , "存储权限不可用"
                        , "由于抖音插件需要获取存储空间，为你存储微信二维码；\n否则，您将无法捐赠"
                        , "存储权限不可用"
                        , "请在-应用设置-权限-中，允许抖音插件使用存储权限来保存二维码"
                )
            }
            setPermissions(permissBean!!, this)
        }

        awemeVersion.text = getAwemeVersion()
        townVersion.text = getVerName()
        github.setOnClickListener {
            val uri = Uri.parse("https://github.com/Townwang")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        blog.setOnClickListener {
            val uri = Uri.parse("https://townwang.com")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        switchBtn.isChecked =  checked
        switchBtn.setOnCheckedChangeListener({ _, isChecked ->
            setComponentEnabled(isChecked)
            checked = isChecked

        })

        presenter.checkUpdate()
    }


    override fun permisstionResult(baseRequestCode: Int) {
        when (baseRequestCode) {
            RESULT_WECHAT_CODE -> {
                Toast.makeText(this, "扫描相册中二维码即可", Toast.LENGTH_SHORT).show()
                val weixinQrIs = resources.openRawResource(R.raw.wechattown)
                val qrPath = Environment.getExternalStorageDirectory().absolutePath + File.separator + "Townwang" + File.separator +
                        "wechat-townwnag.png"
                WeiXinDonate.saveDonateQrImage2SDCard(qrPath, BitmapFactory.decodeStream(weixinQrIs))
                WeiXinDonate.donateViaWeiXin(this, qrPath)
            }
        }

    }

    override fun setDownLoad(url: String) {
        this.downUrl = url
        loading.setOnClickListener(this)
        loading.isEnabled = true
    }
    override fun onClick(p0: View?) {
        if (downUrl != null) {
            val uri = Uri.parse(downUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        } else {
            setLoading("下载链接有误", LoadType.ERROR)
        }
    }
    override fun saveUpload() {
        if (isModuleActive()) {
            isModule.text = "已启用"
            setLoading("检测完毕", LoadType.SUCCESS)
        } else {
            isModule.text = "未启用"
            setLoading("Xposed重新打钩后重启", LoadType.ERROR)
        }
    }

    /****************
     *
     * 发起添加群流程。群号：软件开发交流群(312874694) 的 key 为： odUuTNIRX00HhdT1OILiNJMnNQ7Lsucv
     * 调用 joinQQGroup(odUuTNIRX00HhdT1OILiNJMnNQ7Lsucv) 即可发起手Q客户端申请加群 软件开发交流群(312874694)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     */
    fun joinQQGroup(key: String): Boolean {
        val intent = Intent()
        intent.data = Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D$key")
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return try {
            startActivity(intent)
            true
        } catch (e: Exception) {
            // 未安装手Q或安装的版本不支持
            Toast.makeText(this, "未安装手Q或安装的版本不支持", Toast.LENGTH_SHORT).show()
            false
        }

    }

    /**
     * get app version
     */
    fun getVerName(): String {
        var verName = "未知"
        try {
            verName = packageManager.getPackageInfo(packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return verName
    }

    /**
     * get app version
     */
    override fun getVerCode(): Int {
        var verCode = 0
        try {
            @Suppress("DEPRECATION")
            verCode = packageManager.getPackageInfo(packageName, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return verCode
    }


    /**
     * get awemeApp version
     */
    fun getAwemeVersion(): String {
        val packages = packageManager.getInstalledPackages(0)!!
        for (packag in packages) {
            if (packag.packageName == "com.ss.android.ugc.aweme") {
                @Suppress("DEPRECATION")
                return packag.versionName + "（code：${packag.versionCode} )"
            }
        }
        return "未知"
    }


    override fun setLoading(string: String, type: LoadType) {
        loadingContent.text = string
        when (type) {
            LoadType.LOADING -> loadingContent.setTextColor(ContextCompat.getColor(this, R.color.loading_loading))
            LoadType.SUCCESS -> loadingContent.setTextColor(ContextCompat.getColor(this, R.color.loading_success))
            LoadType.ERROR -> loadingContent.setTextColor(ContextCompat.getColor(this, R.color.loading_error))
        }
    }

    /**
     * 检测是否开启了模块
     */
    fun isModuleActive(): Boolean {
        return false
    }

    /**
    控制图标显示和隐藏
    @param clazz
    @param enabled true:显示、 false：隐藏
     */
    fun  setComponentEnabled(enabled:Boolean) {
        val componentName = ComponentName(this, "$packageName.SettingsLauncher")
        packageManager.setComponentEnabledSetting(componentName,
                if (enabled) PackageManager.COMPONENT_ENABLED_STATE_DISABLED else PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP)
    }

}
