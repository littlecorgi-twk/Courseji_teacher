package com.littlecorgi.corseji

import android.app.Application
import android.content.Context
import android.graphics.Typeface
import es.dmoral.toasty.Toasty

/**
 *
 * @author littlecorgi 2020/10/19
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        // Toasty配置
        Toasty.Config.getInstance()
            .tintIcon(true) // 图标着色
            .setToastTypeface(Typeface.DEFAULT_BOLD) // 字体粗度
            .setTextSize(12) // 字体大小
            .allowQueue(true) // 防止多个toast排队
            .apply()
    }

    companion object {
        /**
         * 代表是否处于调试状态
         * 对于有些代码我们需要调试和不调试是两种状态
         *  比如
         *      1. Splash页面停3秒
         *      2. 显示一些Debug的Toast等等
         * 对于这些情况我们就需要此开关
         *
         * true代表调试，false代表线上
         */
        const val isDebug = true

        private lateinit var context: Context

        fun getContext(): Context {
            return context
        }
    }
}