package com.littlecorgi.commonlib

import android.app.Application
import android.graphics.Typeface
import com.littlecorgi.commonlib.context.AppContextRepository
import com.littlecorgi.commonlib.context.AppContextRepositoryImpl
import com.littlecorgi.commonlib.context.MyAppContextPresenter
import es.dmoral.toasty.Toasty
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 *
 * @author littlecorgi 2020/10/19
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // context = applicationContext

        // Toasty配置
        Toasty.Config.getInstance()
            .tintIcon(true) // 图标着色
            .setToastTypeface(Typeface.DEFAULT_BOLD) // 字体粗度
            .setTextSize(12) // 字体大小
            .allowQueue(true) // 防止多个toast排队
            .apply()

        // AppContextKoinModule 用来通过依赖注入注入Application的Context
        val appContextModule = module {
            single<AppContextRepository> { AppContextRepositoryImpl(applicationContext) }
            single { MyAppContextPresenter(get()) }
        }
        // Koin配置
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appContextModule)
        }
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
        const val isDebug: Boolean = false

        // private lateinit var context: Context
        //
        // fun getContext(): Context {
        //     return context
        // }
    }
}