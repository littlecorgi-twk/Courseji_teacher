package com.littlecorgi.courseji.schedule_setting.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.littlecorgi.courseji.R
import com.littlecorgi.courseji.schedule.logic.model.bean.TableBean
import com.littlecorgi.courseji.schedule_setting.logic.SettingsMultipleEntity
import com.littlecorgi.courseji.schedule_setting.ui.adapter.ScheduleSettingsRecyclerAdapter
import com.littlecorgi.courseji.schedule_setting.vm.ScheduleSettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 设置页面的Activity
 */
class ScheduleSettingsActivity : AppCompatActivity() {

    private val mViewModel by viewModels<ScheduleSettingsViewModel>()
    private lateinit var mToolbar: Toolbar
    private lateinit var mRecycler: RecyclerView
    private lateinit var mAdapter: ScheduleSettingsRecyclerAdapter
    private val mSettingsMultipleEntities = ArrayList<SettingsMultipleEntity>()

    private lateinit var currentWeekItem: SettingsMultipleEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_settings)

        mToolbar = findViewById(R.id.toolbar)

        mViewModel.table = intent.extras!!.getParcelable<TableBean>("tableData") as TableBean
        mViewModel.termStartList = mViewModel.table.startDate.split("-")
        mViewModel.mYear = Integer.parseInt(mViewModel.termStartList[0])
        mViewModel.mMonth = Integer.parseInt(mViewModel.termStartList[1])
        mViewModel.mDay = Integer.parseInt(mViewModel.termStartList[2])
        currentWeekItem = SettingsMultipleEntity(
            SettingsMultipleEntity.SETTINGS_TEXT,
            "当前周",
            mViewModel.getCurrentWeek().toString()
        )

        mRecycler = findViewById(R.id.rv_settings)
        initItem()
        initRecycler()

        initToolbar()
    }

    private fun initItem() {
        mSettingsMultipleEntities.apply {
            add(
                SettingsMultipleEntity(
                    SettingsMultipleEntity.SETTINGS_TEXT,
                    "学期开始时间",
                    mViewModel.table.startDate
                )
            )
            add(
                SettingsMultipleEntity(
                    SettingsMultipleEntity.SETTINGS_TEXT,
                    "学期周数",
                    mViewModel.table.maxWeek.toString()
                )
            )
            add(currentWeekItem)
        }
    }

    private fun initRecycler() {
        mRecycler.apply {
            mAdapter = ScheduleSettingsRecyclerAdapter(mSettingsMultipleEntities)
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@ScheduleSettingsActivity)
            mAdapter.notifyDataSetChanged()
        }

        mAdapter.setOnItemClickListener { _, _, position ->
            val item = mSettingsMultipleEntities[position]
            when (item.title) {
                "学期开始时间" -> {
                    DatePickerDialog(
                        this,
                        { _, year, monthOfYear, dayOfMonth ->
                            mViewModel.mYear = year
                            mViewModel.mMonth = monthOfYear + 1
                            mViewModel.mDay = dayOfMonth
                            val mDate =
                                "${mViewModel.mYear}-${mViewModel.mMonth}-${mViewModel.mDay}"
                            item.value = mDate
                            mViewModel.table.startDate = mDate
                            currentWeekItem.value = mViewModel.getCurrentWeek().toString()
                            mAdapter.notifyItemChanged(position)
                            mAdapter.notifyItemChanged(position + 1)
                        },
                        mViewModel.mYear,
                        mViewModel.mMonth - 1,
                        mViewModel.mDay
                    ).show()
                    if (mViewModel.table.sundayFirst) {
                        // Toasty.success(this, "为了周数计算准确，建议选择周日哦", Toast.LENGTH_LONG).show()
                        Toast.makeText(this, "为了周数计算准确，建议选择周日哦", Toast.LENGTH_LONG).show()
                    } else {
                        // Toasty.success(this, "为了周数计算准确，建议选择周一哦", Toast.LENGTH_LONG).show()
                        Toast.makeText(this, "为了周数计算准确，建议选择周一哦", Toast.LENGTH_LONG).show()
                    }
                }
                "当前周", "学期周数" -> {
                    onSeekBarItemClick(item, 0, mViewModel.table.maxWeek, "周", position)
                }
            }
        }
    }

    private fun onSeekBarItemClick(
        item: SettingsMultipleEntity,
        min: Int,
        max: Int,
        unitText: String,
        position: Int
    ) {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(item.title)
            .setView(R.layout.dialog_edit_text)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.sure, null)
            .setCancelable(false)
            .create()
        dialog.show()
        val inputLayout = dialog.findViewById<TextInputLayout>(R.id.text_input_layout)
        val editText = dialog.findViewById<TextInputEditText>(R.id.edit_text)
        inputLayout?.helperText = "范围 $min ~ $max"
        if (item.title.isNotEmpty()) {
            inputLayout?.prefixText = item.title
        }
        inputLayout?.suffixText = unitText
        editText?.inputType = InputType.TYPE_CLASS_NUMBER
        var valueInt: Int = item.value.toInt()
        if (valueInt < min) {
            valueInt = min
        }
        if (valueInt > max) {
            valueInt = max
        }
        val valueStr = valueInt.toString()
        editText?.setText(valueStr)
        editText?.setSelection(valueStr.length)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val value = editText?.text
            if (value.isNullOrBlank()) {
                inputLayout?.error = "数值不能为空哦>_<"
                return@setOnClickListener
            }
            val editInt = try {
                value.toString().toInt()
            } catch (e: Exception) {
                inputLayout?.error = "输入异常>_<"
                return@setOnClickListener
            }
            if (editInt < min || editInt > max) {
                inputLayout?.error = "注意范围 $min ~ $max"
                return@setOnClickListener
            }
            when (item.title) {
                "学期周数" -> {
                    mViewModel.table.maxWeek = editInt
                }
                "当前周" -> {
                    mViewModel.setCurrentWeek(editInt)
                    item.value = editInt.toString()
                    // 代表学习期开始时间的那个item
                    mAdapter.data[position - 2].value = mViewModel.table.startDate
                    mAdapter.notifyItemChanged(position - 2)
                    mAdapter.notifyItemChanged(position)
                    dialog.dismiss()
                }
            }
            item.value = "$editInt"
            mAdapter.notifyItemChanged(position)
            dialog.dismiss()
        }
    }

    private fun initToolbar() {
        mToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        lifecycleScope.launch(Dispatchers.IO) {
            mViewModel.saveSettings()
            // 切换到主线程进行
            launch(Dispatchers.Main) {
                setResult(RESULT_OK)
                finish()
            }
        }
    }
}