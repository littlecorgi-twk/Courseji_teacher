package com.littlecorgi.courseji.schedule_import.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.webkit.JavascriptInterface
import android.webkit.SslErrorHandler
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.littlecorgi.courseji.R
import com.littlecorgi.courseji.databinding.FragmentWebViewLoginBinding
import com.littlecorgi.courseji.schedule_import.vm.ImportViewModel
import com.littlecorgi.courseji.utils.Const
import com.littlecorgi.courseji.utils.getPrefer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WebViewLoginFragment : Fragment() {
    private lateinit var url: String
    private val viewModel by activityViewModels<ImportViewModel>()
    private var zoom = 100
    private lateinit var binding: FragmentWebViewLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString("url")!!
            viewModel.importType = it.getString("importType")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_web_view_login, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (url != "") {
            binding.etUrl.setText(url)
            startVisit()
        } else {
            val url = requireContext().getPrefer().getString(Const.KEY_SCHOOL_URL, "")
            if (url != "") {
                binding.etUrl.setText(url)
            } else {
                binding.etUrl.setText("https://www.baidu.com")
            }
            startVisit()
        }

        binding.wvCourse.settings.javaScriptEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.wvCourse.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        binding.wvCourse.addJavascriptInterface(InJavaScriptLocalObj(), "local_obj")
        binding.wvCourse.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                handler.proceed() // ???????????????????????????
            }
        }
        binding.wvCourse.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    binding.pbLoad.progress = newProgress
                    binding.pbLoad.visibility = View.GONE
                    // Toasty.info(activity!!, wv_course.url, Toast.LENGTH_LONG).show()
                } else {
                    binding.pbLoad.progress = newProgress * 5
                    binding.pbLoad.visibility = View.VISIBLE
                }
            }
        }
        // ????????????????????????????????????
        binding.wvCourse.settings.useWideViewPort = true // ????????????????????????WebView?????????
        binding.wvCourse.settings.loadWithOverviewMode = true // ????????????????????????
        // ????????????
        binding.wvCourse.settings.setSupportZoom(true) // ????????????????????????true??????????????????????????????
        binding.wvCourse.settings.builtInZoomControls = true // ????????????????????????????????????false?????????WebView????????????
        binding.wvCourse.settings.displayZoomControls = false // ???????????????????????????wvCourse.settings
        binding.wvCourse.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.wvCourse.settings.domStorageEnabled = true
        // binding.wvCourse.settings.userAgentString =
        //     binding.wvCourse.settings.userAgentString.replace("Mobile", "eliboM")
        //         .replace("Android", "diordnA")
        binding.wvCourse.settings.textZoom = 100
        initEvent()
    }

    private fun initEvent() {

        binding.chipMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.wvCourse.settings.userAgentString =
                    binding.wvCourse.settings.userAgentString.replace("Mobile", "eliboM")
                        .replace("Android", "diordnA")
            } else {
                binding.wvCourse.settings.userAgentString =
                    binding.wvCourse.settings.userAgentString.replace("eliboM", "Mobile")
                        .replace("diordnA", "Android")
            }
            binding.wvCourse.reload()
        }

        binding.chipZoom.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(requireActivity())
                .setTitle("????????????")
                .setView(R.layout.dialog_edit_text)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.sure, null)
                .create()
            dialog.show()
            val inputLayout = dialog.findViewById<TextInputLayout>(R.id.text_input_layout)
            val editText = dialog.findViewById<TextInputEditText>(R.id.edit_text)
            inputLayout?.helperText = "?????? 10 ~ 200"
            inputLayout?.suffixText = "%"
            editText?.inputType = InputType.TYPE_CLASS_NUMBER
            val valueStr = zoom.toString()
            editText?.setText(valueStr)
            editText?.setSelection(valueStr.length)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val value = editText?.text
                if (value.isNullOrBlank()) {
                    inputLayout?.error = "?????????????????????>_<"
                    return@setOnClickListener
                }
                val valueInt = try {
                    value.toString().toInt()
                } catch (e: Exception) {
                    inputLayout?.error = "????????????>_<"
                    return@setOnClickListener
                }
                if (valueInt < 10 || valueInt > 200) {
                    inputLayout?.error = "???????????? 10 ~ 200"
                    return@setOnClickListener
                }
                zoom = valueInt
                binding.wvCourse.settings.textZoom = zoom
                binding.chipZoom.text = "???????????? $zoom%"
                binding.wvCourse.reload()
                dialog.dismiss()
            }
        }

        var zfChipId = R.id.chip_zf1
        binding.cgZf.setOnCheckedChangeListener { chipGroup, id ->
            when (id) {
                R.id.chip_zf1 -> {
                    zfChipId = id
                    viewModel.zfType = 0
                }
                R.id.chip_zf2 -> {
                    zfChipId = id
                    viewModel.zfType = 1
                }
                else -> {
                    chipGroup.findViewById<Chip>(zfChipId).isChecked = true
                }
            }
        }

        binding.tvGo.setOnClickListener {
            startVisit()
        }

        binding.etUrl.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                startVisit()
            }
            return@setOnEditorActionListener false
        }

        val js = "javascript:var ifrs=document.getElementsByTagName(\"iframe\");" +
                "var iframeContent=\"\";" +
                "for(var i=0;i<ifrs.length;i++){" +
                "iframeContent=iframeContent+ifrs[i].contentDocument.body.parentElement.outerHTML;" +
                "}\n" +
                "var frs=document.getElementsByTagName(\"frame\");" +
                "var frameContent=\"\";" +
                "for(var i=0;i<frs.length;i++){" +
                "frameContent=frameContent+frs[i].contentDocument.body.parentElement.outerHTML;" +
                "}\n" +
                "window.local_obj.showSource(document.getElementsByTagName('html')[0].innerHTML + iframeContent + frameContent);"

        binding.fabImport.setOnClickListener {
            binding.wvCourse.loadUrl(js)
        }

        binding.btnBack.setOnClickListener {
            if (binding.wvCourse.canGoBack()) {
                binding.wvCourse.goBack()
            }
        }
    }

    private fun startVisit() {
        binding.wvCourse.visibility = View.VISIBLE
        binding.llError.visibility = View.GONE
        val url =
            if (binding.etUrl.text.toString().startsWith("http://") || binding.etUrl.text.toString()
                    .startsWith("https://")
            )
                binding.etUrl.text.toString() else "http://" + binding.etUrl.text.toString()
        if (URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url)) {
            binding.wvCourse.loadUrl(url)
        } else {
            // Toasty.error(requireContext(), "???????????????????????????(???^???)???").show()
            Toast.makeText(requireContext(), "???????????????????????????(???^???)???", Toast.LENGTH_SHORT).show()
        }
    }

    internal inner class InJavaScriptLocalObj {
        @JavascriptInterface
        fun showSource(html: String) {
            launch {
                try {
                    Log.d("?????????html", "showSource: ")
                    Log.d("?????????html", html)
                    val result = viewModel.importSchedule(html)
                    // Toasty.success(activity!!, "???????????? $result ?????????(?????????)/\n??????????????????????????????").show()
                    Toast.makeText(
                        activity!!,
                        "???????????? $result ?????????(?????????)/\n??????????????????????????????",
                        Toast.LENGTH_SHORT
                    ).show()
                    activity!!.setResult(Activity.RESULT_OK)
                    activity!!.finish()
                } catch (e: Exception) {
                    // Toasty.error(activity!!, "????????????>_<\n${e.message}", Toast.LENGTH_LONG).show()
                    Toast.makeText(activity!!, "????????????>_<\n${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun launch(block: suspend CoroutineScope.() -> Unit): Job = lifecycleScope.launch {
        lifecycle.whenStarted(block)
    }

    override fun onDestroyView() {
        // todo ???????????????????????????????????????
        // binding.wvCourse.webViewClient = null
        binding.wvCourse.webChromeClient = null
        binding.wvCourse.clearCache(true)
        binding.wvCourse.clearHistory()
        binding.wvCourse.removeAllViews()
        binding.wvCourse.destroy()
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String = "", importType: String = "") =
            WebViewLoginFragment().apply {
                arguments = Bundle().apply {
                    putString("url", url)
                    putString("importType", importType)
                }
            }
    }
}
