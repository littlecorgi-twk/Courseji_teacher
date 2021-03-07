package com.littlecorgi.courseji.schedule.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.littlecorgi.courseji.R

/**
 * 课程表Recycler的Adapter
 * @author littlecorgi 2021/1/24
 */
// class ScheduleRecyclerAdapter(detail: ArrayList<ScheduleItem>) :
//     BaseQuickAdapter<ScheduleItem, BaseViewHolder>(
//         R.layout.item_schedule_recycler,
//         detail
//     ) {
//
//     override fun convert(holder: BaseViewHolder, item: ScheduleItem) {
//         val textView = holder.getView<TextView>(R.id.tv_schedule_detail)
//         textView.apply {
//             text = item.text
//             textSize = item.textSize
//             setTextColor(item.textColor)
//             setBackgroundColor(item.backgroundColor)
//         }
//     }
// }

class ScheduleRecyclerAdapter(val context: Context, val detail: ArrayList<ScheduleItem>) :
        RecyclerView.Adapter<ScheduleRecyclerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
                LayoutInflater.from(context).inflate(R.layout.item_schedule_recycler, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = detail[position]
        holder.setData(data)
    }

    override fun getItemCount(): Int = detail.size

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv = view.findViewById<AppCompatTextView>(R.id.tv_schedule_detail)
        fun setData(data: ScheduleItem) {
            tv.apply {
                text = data.text
                textSize = data.textSize
                setTextColor(data.textColor)
                setBackgroundColor(data.backgroundColor)
            }
        }
    }
}