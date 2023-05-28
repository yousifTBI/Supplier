package com.tbi.supplierplus.framework.ui2.availableitemsBB

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.InvoicRequest
import com.tbi.supplierplus.databinding.ActivityBill2Binding
import com.tbi.supplierplus.databinding.ActivityStockRequestBinding
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint

class StockRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStockRequestBinding
    lateinit var viewModel: AvailableItemsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_stock_request)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stock_request);
        viewModel = ViewModelProvider(this).get(AvailableItemsViewModel::class.java)

        val ItemID = intent.getStringExtra("ItemID  ").toString()
        val ItemName = intent.getStringExtra("ItemName").toString()
        val Barcode = intent.getStringExtra("Barcode ").toString()
        val Supply_Price = intent.getStringExtra("Supply_Price  ").toString()
        val Selling_Price = intent.getStringExtra("Selling_Price ").toString()
        val availableCount = intent.getStringExtra("availableCount").toString()
        val Supplier_Id = intent.getStringExtra("Supplier_Id").toString()
        binding.textView8.setText(ItemName)
        binding.textView3.setText(Barcode)

        binding.textView2.setText(Selling_Price)

        binding.Size1.setText(Supply_Price)

        binding.textView8.setText(ItemName)
        binding.sours1.setText(availableCount)


        binding.spinKit.isVisible = false

        binding.button2b.setOnClickListener {
            if (binding.counterEditTextm.text.toString().isNullOrEmpty()) {
            } else {
                binding.spinKit.isVisible = true
                val coun = binding.counterEditTextm.text.toString()
                var count = coun.toDouble()
                lifecycleScope.launch {
                    viewModel.InvoicRequestVM(
                        InvoicRequest(
                            ItemID.toInt(),
                            count, SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt(), Supplier_Id.toInt(),
                            0, SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt(), 1

                        )
                    ).collect {
                        when (it) {

                            is State.Loading -> {}

                            is State.Success -> {
                                viewModel.getAvailableItems()
                                binding.textView8.setText("")
                                binding.textView3.setText("")
                                binding.textView2.setText("")
                                binding.Size1.setText("")
                                binding.textView8.setText("")
                                binding.sours1.setText("")
                                binding.counterEditTextm.setText("")
                                binding.spinKit.isVisible = false

                            }
                            is State.Error -> {
                                binding.spinKit.isVisible = false


                            }
                        }

                    }
                }

            }

            finish()
        }
    }
}