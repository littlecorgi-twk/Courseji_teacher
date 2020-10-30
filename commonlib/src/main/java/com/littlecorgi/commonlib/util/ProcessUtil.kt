package com.littlecorgi.commonlib.util

import android.text.TextUtils
import java.io.BufferedReader
import java.io.FileReader

/**
 * 与Process相关的工具类
 * @author littlecorgi 2020/10/30
 */

fun getProcessName(pid: Int): String? {
    val reader: BufferedReader
    try {
        reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
        var processName = reader.readLine()
        if (!TextUtils.isEmpty(processName)) {
            processName = processName.trim()
        }
        return processName
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
    }
    return null
}