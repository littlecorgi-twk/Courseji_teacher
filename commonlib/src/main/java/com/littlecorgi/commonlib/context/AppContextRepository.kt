package com.littlecorgi.commonlib.context

import android.content.Context

/**
 *
 * @author littlecorgi 2020/10/19
 */
interface AppContextRepository {
    fun getAppContext(): Context
}

class AppContextRepositoryImpl(private val context: Context) : AppContextRepository {
    override fun getAppContext(): Context = context
}

class MyAppContextPresenter(private val repo: AppContextRepository) {
    fun getAppContext() = repo.getAppContext()
}