package com.tbi.supplierplus.framework.ui.daily_closing.expensesAdapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.databinding.FragmentExpensesClosingBinding
import com.tbi.supplierplus.framework.ui.daily_closing.DailyClosingViewModel
import com.tbi.supplierplus.framework.ui.daily_closing.ItemsBIllActivity
import com.tbi.supplierplus.framework.ui.daily_closing.itemsReceived.AdapterItemsReceived
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.reports.ItemsReportAdapter
import com.tbi.supplierplus.framework.ui.reports.OnItemSettlementClickListener2
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import com.tbi.supplierplus.framework.ui2.availableitemsBB.StockRequestActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExpensesClosingFragment : Fragment() {
    private lateinit var binding:  FragmentExpensesClosingBinding
    val availableItemsViewModel: AvailableItemsViewModel by viewModels()

    private val viewModel: DailyClosingViewModel by  viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpensesClosingBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding. sales.setText(" المردودات ")

        val adabter=AdapterExpensesClosing()
        lifecycleScope.launch {
            availableItemsViewModel.getCurrentMortg3atofTheUser().collect {
                when (it) {

                    is State.Loading -> {}

                    is State.Success -> {

                        binding.ExpensesClosingrecyclerView.adapter = adabter
                        binding.ExpensesClosingrecyclerView.setHasFixedSize(true)
                        adabter.submitList(it.data.data)

                    }
                    is State.Error -> {
                    }
                }
            }
        }
        val adapter = ItemsReportAdapter(OnItemSettlementClickListener2{


             var ItemID = it.Item_ID
            val intent = Intent(activity, ItemsBIllActivity::class.java)
                .apply {
                    putExtra("ItemID  ", ItemID.toString())

                }
            startActivity(intent)
        })
      //  binding.spinKit.isVisible = false
        lifecycleScope.launch {
            availableItemsViewModel.getItemsReport().collect {
                when (it) {

                    is State.Loading -> {}
                        //binding.spinKit.isVisible = true

                    is State.Success -> {
                     //   binding.spinKit.isVisible = false

                        binding.purchaseClosingrecyclerView.adapter = adapter
                        adapter.submitList(it.data.data)


                    }
                    is State.Error -> {
                    //    binding.spinKit.isVisible = false
                    }
                }
            }
        }
     //   binding. sales.setText("المخزون والمردودات ")



        return binding.root
    }


}