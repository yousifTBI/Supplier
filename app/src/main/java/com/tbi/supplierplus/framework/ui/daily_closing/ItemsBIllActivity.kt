package com.tbi.supplierplus.framework.ui.daily_closing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.utils.toJson
import com.tbi.supplierplus.databinding.ActivityItemsBillBinding
import com.tbi.supplierplus.databinding.ActivityItemsBillBindingImpl
import com.tbi.supplierplus.framework.ui.closingLast.ClosingActivity
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.reports.CustomerStatementFragmentDirections
import com.tbi.supplierplus.framework.ui.reports.OnBillClickListener
import com.tbi.supplierplus.framework.ui.reports.StatementAdapter
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@AndroidEntryPoint
class ItemsBIllActivity : AppCompatActivity() {
    lateinit var viewModel: AvailableItemsViewModel
    private lateinit var binding: ActivityItemsBillBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_items_bill)
        viewModel = ViewModelProvider(this).get(AvailableItemsViewModel::class.java)
        supportActionBar!!.hide()
        val ItemID = intent.getStringExtra("ITEM_ID").toString()
        val itemname = intent.getStringExtra("itemname").toString()
 //        Log.d("ItemID",ItemID.toString())

        binding.textLA.setText( "اسم المنتج "+":"+itemname)
        val adapter1 = ItemsBillAdapet(com.tbi.supplierplus.framework.ui.daily_closing.OnBillClickListener {  })





        lifecycleScope.launch {
            viewModel.getItemsBills(ItemID.toInt()).collect {

                when (it) {
                    is State.Loading ->{}
                    is State.Success -> {

  //                      Log.d("getItemsBills",it.data.data.toJson())
                        binding.recyclerView.adapter = adapter1
                        binding.recyclerView.setHasFixedSize(true)
                        adapter1.submitList(it.data.data)

                    }
                    is State.Error -> {


                    }
                }
            }
        }




    }
}