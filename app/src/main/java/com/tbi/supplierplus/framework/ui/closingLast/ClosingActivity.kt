package com.tbi.supplierplus.framework.ui.closingLast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tbi.supplierplus.R

class ClosingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_closing)
        supportActionBar!!.hide()

    }
}