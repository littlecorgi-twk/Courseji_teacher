package com.littlecorgi.commonlib

import android.app.Application
import android.content.Context
import android.graphics.Typeface
import androidx.startup.Initializer
import com.alibaba.android.arouter.launcher.ARouter
import com.littlecorgi.commonlib.App.Companion.isDebug
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
