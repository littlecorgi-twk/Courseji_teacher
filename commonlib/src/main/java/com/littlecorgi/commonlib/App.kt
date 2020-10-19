package com.littlecorgi.commonlib

import android.app.Application
import androidx.startup.AppInitializer
import com.littlecorgi.commonlib.context.AppContextRepository
import com.littlecorgi.commonlib.context.AppContextRepositoryImpl
import com.littlecorgi.commonlib.context.MyAppContextPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * 重写Application
 * @author littlecorgi 2020/10/19
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // context = applicationContext

        // Toasty配置
        // 放在了Startup中，具体见 {@link com.littlecorgi.commonlib.Initializer}

        // LeakCanary手动配置，我想禁止LeakCanary通过他的ContentProvider来初始化，减少ContentProvider
        AppInitializer.getInstance(this).initializeComponent(
            LeakCanaryInitializer::class.java
        )

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