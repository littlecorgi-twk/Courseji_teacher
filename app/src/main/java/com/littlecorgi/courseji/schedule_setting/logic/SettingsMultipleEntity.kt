package com.littlecorgi.courseji.schedule_setting.logic

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 *
 * @author littlecorgi 2021/1/31
 */
class SettingsMultipleEntity(
    override val itemType: Int,
    val title: String = "",
    var value: String = "",
    var isCheck: Boolean = false
) : MultiItemEntity {

    companion object {
        const val SETTINGS_TEXT = 1
    }
}
