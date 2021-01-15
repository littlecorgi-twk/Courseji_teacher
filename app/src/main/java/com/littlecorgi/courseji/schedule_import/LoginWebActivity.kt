package com.littlecorgi.courseji.schedule_import

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.littlecorgi.courseji.R
import com.littlecorgi.courseji.schedule_import.ui.ImportSettingFragment
import com.littlecorgi.courseji.schedule_import.ui.WebViewLoginFragment
import com.littlecorgi.courseji.schedule_import.vm.ImportViewModel

class LoginWebActivity : AppCompatActivity() {

    private val viewModel by viewModels<ImportViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_web)

        val fragment = WebViewLoginFragment.newInstance(
            intent.getStringExtra("url")!!,
            intent.getStringExtra("importType")!!
        )

        fragment.let { frag ->
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(android.R.id.content, frag, viewModel.school)
            transaction.commit()
            showImportSettingDialog()
        }
    }

    private fun showImportSettingDialog() {
        ImportSettingFragment().apply {
            isCancelable = false
        }.show(supportFragmentManager, null)
    }
}