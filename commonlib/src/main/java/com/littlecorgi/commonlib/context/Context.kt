package com.littlecorgi.commonlib.context

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * {@link android.content.Context} 类扩展函数
 * @author littlecorgi 2020/10/19
 */

/**
 * 从当前Context启动「Activity A」。configIntent可以设置intent一些内容，可选项
 */
inline fun <reified A : Activity> Context.start(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(this, A::class.java).apply(configIntent))
}

/**
 * 通过action去启动intent跳转
 */
inline fun Context.startActivity(action: String, configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(action).apply(configIntent))
}

/**
 * 从当前Activity启动「Activity A」。configIntent可以设置intent一些内容，可选项
 */
inline fun <reified A : Activity> Activity.start(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(this, A::class.java).apply(configIntent))
}

/**
 * 通过action去启动intent跳转
 */
inline fun Activity.startActivity(action: String, configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(action).apply(configIntent))
}

/**
 * 通过action去启动intent跳转，并获取结果
 */
inline fun <reified A : Activity> Activity.startActivityForResult(
    requestCode: Int,
    configIntent: Intent.() -> Unit = {}
) {
    startActivityForResult(Intent(this, A::class.java).apply(configIntent), requestCode)
}

/**
 * 通过action去启动intent跳转，并获取结果
 */
inline fun Activity.startActivityForResult(
    action: String,
    requestCode: Int,
    configIntent: Intent.() -> Unit = {}
) {
    startActivityForResult(Intent(action).apply(configIntent), requestCode)
}
