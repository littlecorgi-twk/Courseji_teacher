package com.littlecorgi.corseji

import android.os.Bundle
import android.util.Log
import com.littlecorgi.commonlib.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(mTAG, "onCreate: 1")
    }
}