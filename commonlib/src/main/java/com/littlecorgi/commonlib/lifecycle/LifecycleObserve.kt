package com.littlecorgi.commonlib.lifecycle

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent

/**
 * Lifecycle: 监听Activity / Fragment生命周期
 * @author littlecorgi 2020/10/20
 */
class LifecycleObserve(private val TAG: String) {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun logOnCreate() {
        Log.d(TAG, "logOnCreate: 1")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun logOnStart() {
        Log.d(TAG, "logOnStart: 1")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun logOnResume() {
        Log.d(TAG, "logOnResume: 1")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun logOnPause() {
        Log.d(TAG, "logOnPause: 1")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun logOnStop() {
        Log.d(TAG, "logOnStop: 1")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun logOnDestroy() {
        Log.d(TAG, "logOnDestroy: 1")
    }
}
