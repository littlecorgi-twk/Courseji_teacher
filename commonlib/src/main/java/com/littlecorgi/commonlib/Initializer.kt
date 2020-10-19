package com.littlecorgi.commonlib

import android.app.Application
import android.content.Context
import android.graphics.Typeface
import androidx.startup.Initializer
import es.dmoral.toasty.Toasty
import leakcanary.AppWatcher

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
 * 初始化 LeakCanary ，配置为手动初始化
 */
class LeakCanaryInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        val application = context.applicationContext as Application
        AppWatcher.manualInstall(application)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
