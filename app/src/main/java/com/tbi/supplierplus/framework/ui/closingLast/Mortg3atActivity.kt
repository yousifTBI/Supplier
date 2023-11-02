package com.tbi.supplierplus.framework.ui.closingLast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.ActivityMortg3atBinding
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Mortg3atActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMortg3atBinding
    lateinit var availableItemsViewModel: AvailableItemsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_mortg3at)
        availableItemsViewModel = ViewModelProvider(this).get(AvailableItemsViewModel::class.java)
        supportActionBar!!.hide()
        binding.spinKit.isVisible = false
        val ItemID = intent.getStringExtra("ITEM_ID").toString()
        val itemname = intent.getStringExtra("itemname").toString()
        binding.SubmittttttID.setOnClickListener {
            if (CheckAllFields()){
            lifecycleScope.launch {
                availableItemsViewModel.geSubmitChangeMortaga3Quantity(
                    binding.com.text.toString().toDouble(), ItemID.toInt(),binding.numOfRecord.text.toString().toDouble()
                ).collect {

                    when (it) {
                        is State.Loading -> {binding.spinKit.isVisible =true}
                        is State.Success -> {
                            binding.spinKit.isVisible =false
                            binding.comCode.setText(it.data.item.Item_Count.toString())
                            binding.branchcode.setText(it.data.item.Item_Amount.toString())

                        }
                        is State.Error -> {binding.spinKit.isVisible =false}
                    }
                }
            }


        }
        }

    }


    private fun CheckAllFields(): Boolean {
        if (binding.com.length() == 0) {
            binding.com.setError("This field is required")
            return false
        }
        if (binding.numOfRecord.length() == 0) {
            binding.numOfRecord.setError("This field is required")
            return false
        }

        // after all validation return true.
        return true
    }
}