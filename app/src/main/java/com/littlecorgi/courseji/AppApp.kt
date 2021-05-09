package com.littlecorgi.courseji

import android.util.Log
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.littlecorgi.commonlib.App
import com.littlecorgi.courseji.utils.PushHelper
import com.littlecorgi.courseji.utils.TTAdManagerHolder
import com.umeng.commonsdk.utils.UMUtils

/**
 * 自定义的Application。继承自commonlib库中的App。
 * 本质上主要的Application中初始化内容是在commonlib的App中的，但是由于友盟的问题，只能在此初始化，
 * 所以此类目前来说主要的作用就是承接友盟的初始化，而其他的初始化或者与Application类相关的逻辑代码都在commonlib.App中
 * @author littlecorgi 2020/11/17
 */
class AppApp : App() {

    override fun onCreate() {
        super.onCreate()
        //预初始化
        PushHelper.preInit(this);
        //正式初始化
        initPushSDK2()

        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this)
        // 自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        // 包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL)

        // 初始化穿山甲广告SDK
        TTAdManagerHolder.init(this)

        Log.d("UMengInitializer", "onCreate: APP APP初始化了")
    }

    /**
     * 场景二：如果考虑启动速度等，可在子线程做初始化
     */
    private fun initPushSDK2() {
        if (UMUtils.isMainProgress(this)) {
            // Thread { PushHelper.init(applicationContext) }.start()
            PushHelper.init(applicationContext)
        }
    }
}
