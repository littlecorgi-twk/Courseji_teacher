package com.littlecorgi.commonlib.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

/**
 * 监听Activity生命周期
 * Application#registerActivityLifecycleCallbacks()
 * @author littlecorgi 2020/10/20
 */
class MyActivityLifecycleCallbacks(private val TAG: String) :
        Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated: ")
    }

    override fun onActivityStarted(activity: Activity) {
        Log.d(TAG, "onActivityStarted: ")
    }

    override fun onActivityResumed(activity: Activity) {
        Log.d(TAG, "onActivityResumed: ")
    }

    override fun onActivityPaused(activity: Activity) {
        Log.d(TAG, "onActivityPaused: ")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.d(TAG, "onActivityStopped: ")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.d(TAG, "onActivitySaveInstanceState: ")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.d(TAG, "onActivityDestroyed: ")
    }
}