package com.littlecorgi.commonlib.context

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment

/**
 *
 * @author littlecorgi 2021/4/9
 */

/**
 * 通过action去启动intent跳转，并获取结果
 */
inline fun <reified A : Activity> Fragment.startActivityForResult(
    requestCode: Int,
    configIntent: Intent.() -> Unit = {}
) {
    startActivityForResult(Intent(requireContext(), A::class.java).apply(configIntent), requestCode)
}

/**
 * 通过action去启动intent跳转，并获取结果
 */
inline fun Fragment.startActivityForResult(
    action: String,
    requestCode: Int,
    configIntent: Intent.() -> Unit = {}
) {
    startActivityForResult(Intent(action).apply(configIntent), requestCode)
}
