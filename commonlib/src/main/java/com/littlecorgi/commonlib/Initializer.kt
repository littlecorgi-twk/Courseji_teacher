package com.littlecorgi.commonlib

import android.app.Application
import android.content.Context
import android.graphics.Typeface
import android.util.Log
import androidx.startup.Initializer
import com.alibaba.android.arouter.launcher.ARouter
import com.littlecorgi.commonlib.App.Companion.isDebug
import com.tencent.bugly.crashreport.CrashReport
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import es.dmoral.toasty.Toasty

/**
 * APP Startup 的 Initializer 类
 * @author littlecorgi 2020/10/19
 */

/**
 * 初始化 Toasty，配置为自动初始化
 */
class ToastyInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Toasty.Config.getInstance()
            .tintIcon(true) // 图标着色
            .setToastTypeface(Typeface.DEFAULT_BOLD) // 字体粗度
            .setTextSize(12) // 字体大小
            .allowQueue(true) // 防止多个toast排队
            .apply()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

/**
 * 初始化 友盟
 */
class UMengInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        UMConfigure.init(
            context, "5f9a8e3e1c520d30739bfe55", "Umeng",
            UMConfigure.DEVICE_TYPE_PHONE, "7f70e2dc06073c2988d4d26f6eeee1b5"
        )
        // 获取消息推送代理示例
        val mPushAgent = PushAgent.getInstance(context)

        mPushAgent.isPushCheck = true

        Log.i("UMengInitializer", "create: resourcePackageName: ${mPushAgent.resourcePackageName}")

        // 设置资源包名
        // mPushAgent.resourcePackageName = "com.littlecorgi.commonlib"
        mPushAgent.resourcePackageName = "com.littlecorgi.courseji"

        // mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //服务端控制声音

        // 注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                // 注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i("UMengInitializer", "注册成功：deviceToken：-------->  $deviceToken")
            }

            override fun onFailure(s: String, s1: String) {
                Log.e("UMengInitializer", "注册失败：-------->  s:$s,s1:$s1")
            }
        })

        // /**
        //  * 初始化厂商通道
        //  */
        // //小米通道
        // MiPushRegistar.register(this, "填写您在小米后台APP对应的xiaomi id", "填写您在小米后台APP对应的xiaomi key");
        // //华为通道，注意华为通道的初始化参数在minifest中配置
        // HuaWeiRegister.register(this);
        // //魅族通道
        // MeizuRegister.register(this, "填写您在魅族后台APP对应的app id", "填写您在魅族后台APP对应的app key");
        // //OPPO通道
        // OppoRegister.register(this, "填写您在OPPO后台APP对应的app key", "填写您在魅族后台APP对应的app secret");
        // //VIVO 通道，注意VIVO通道的初始化参数在minifest中配置
        // VivoRegister.register(this);
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

/**
 * 初始化 ARouter
 */
class ARouterInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (isDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(context as Application) // 尽可能早，推荐在Application中初始化
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

/**
 * 初始化 Bugly
 */
class BuglyInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        // 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
        // - 输出详细的Bugly SDK的Log；
        // - 每一条Crash都会被立即上报；
        // - 自定义日志将会在Logcat中输出：true为输出，false为不输出，建议在测试阶段建议设置成true，发布时设置为false。
        CrashReport.initCrashReport(context, "28d25075a1", isDebug)

        // 如果需要测试崩溃，可以模拟设置一个按钮调用以下代码，APP就会崩溃，等待一会(应该不会超过1min)，就能在bugly后台看到信息了
        // CrashReport.testJavaCrash()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

// /**
//  * 初始化 LeakCanary ，配置为手动初始化
//  */
// class LeakCanaryInitializer : Initializer<Unit> {
//     override fun create(context: Context) {
//         val application = context.applicationContext as Application
//         AppWatcher.manualInstall(application)
//     }
//
//     override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
// }
