package com.littlecorgi.commonlib.util

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import com.google.android.material.snackbar.Snackbar

/**
 * UI相关工具类
 * @author littlecorgi 2020/12/26
 */
inline fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()
inline fun Context.dp(value: Int): Float = (value * resources.displayMetrics.density)

inline fun View.dip(value: Int) = context.dip(value)
inline fun View.dp(value: Int) = context.dp(value)

inline fun View.snack(
        msg: CharSequence,
        duration: Int = Snackbar.LENGTH_SHORT,
        actionSetup: Snackbar.() -> Unit = {}
) = Snackbar.make(this, msg, duration).apply(actionSetup).also { it.show() }

inline fun View.longSnack(
        msg: CharSequence,
        actionSetup: Snackbar.() -> Unit = {}
) = snack(msg, Snackbar.LENGTH_LONG, actionSetup)

fun Context.colorSL(@ColorRes colorRes: Int): ColorStateList {
    return (if (Build.VERSION.SDK_INT >= 23) getColorStateList(colorRes) else {
        @Suppress("DEPRECATION")
        resources.getColorStateList(colorRes)
    })
}

inline fun View.colorSL(@ColorRes colorRes: Int) = context.colorSL(colorRes)