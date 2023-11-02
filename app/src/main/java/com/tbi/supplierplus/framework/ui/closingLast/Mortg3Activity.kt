package com.tbi.supplierplus.framework.ui.closingLast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.ActivityMortg3Binding
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Mortg3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityMortg3Binding
    lateinit var availableItemsViewModel: AvailableItemsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mortg3)
        availableItemsViewModel = ViewModelProvider(this).get(AvailableItemsViewModel::class.java)
        supportActionBar!!.hide()

        val ItemID = intent.getStringExtra("ITEM_ID").toString()
        val itemname = intent.getStringExtra("itemname").toString()
        binding.spinKit.isVisible = false



        binding.SubmittttttID.setOnClickListener {

            if (CheckAllFields()){

                lifecycleScope.launch {
                    availableItemsViewModel.getSubmitChangeQuantity(
                        binding.com.text.toString().toDouble(), ItemID.toInt()
                    ).collect {

                        when (it) {
                            is State.Loading -> {binding.spinKit.isVisible =true}
                            is State.Success -> {
                                binding.spinKit.isVisible =false
                                binding.numOfRecord.setText(it.data.item.Item_Count.toString())

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

        // after all validation return true.
        return true
    }
}