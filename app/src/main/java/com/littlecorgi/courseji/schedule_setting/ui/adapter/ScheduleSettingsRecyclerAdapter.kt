package com.littlecorgi.courseji.schedule_setting.ui.adapter

import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.littlecorgi.courseji.R
import com.littlecorgi.courseji.schedule_setting.logic.SettingsMultipleEntity

/**
 * 设置页面RecyclerView的Adapter
 * @author littlecorgi 2021/1/31
 */
class ScheduleSettingsRecyclerAdapter(data: MutableList<SettingsMultipleEntity>) :
    BaseMultiItemQuickAdapter<SettingsMultipleEntity, BaseViewHolder>(data) {

    init {
        // 添加View类型
        addItemType(SettingsMultipleEntity.SETTINGS_TEXT, R.layout.item_settings_text)
    }

    /**
     * 绑定数据
     */
    override fun convert(holder: BaseViewHolder, item: SettingsMultipleEntity) {
        when (holder.itemViewType) {
            SettingsMultipleEntity.SETTINGS_TEXT -> {
                Log.d("ScheduleSettingsAdapter", "convert: 1")
                holder.getView<AppCompatTextView>(R.id.tv_text_start).text = item.title
                holder.getView<AppCompatTextView>(R.id.tv_text_end).text = item.value
            }
        }
    }
}
