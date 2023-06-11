package com.tbi.supplierplus.framework.ui.daily_closing.expensesAdapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.databinding.FragmentExpensesClosingBinding
import com.tbi.supplierplus.framework.ui.closingLast.ClosingActivity
import com.tbi.supplierplus.framework.ui.daily_closing.DailyClosingViewModel
import com.tbi.supplierplus.framework.ui.daily_closing.ItemsBIllActivity
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.reports.ItemsReportAdapter
import com.tbi.supplierplus.framework.ui.reports.OnItemSettlementClickListener2
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExpensesClosingFragment : Fragment() {
    private lateinit var binding: FragmentExpensesClosingBinding
    val availableItemsViewModel: AvailableItemsViewModel by viewModels()

    private val viewModel: DailyClosingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpensesClosingBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.spinKit.isVisible = false
        binding.closingDayButton.setOnClickListener {

            lifecycleScope.launch() {
                viewModel.SubmitReturnMardodatVM().collect {
                    when (it) {
                        is State.Loading -> binding.spinKit.isVisible = true
                        is State.Success -> {

                            binding.spinKit.isVisible = false
                            viewModel.SubmitReturnMardodatVM().collect {}


                            Toast.makeText(context, it.data.message, Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, it.data.message, Toast.LENGTH_SHORT).show()
//                            if (it.data.data.isNullOrEmpty()) {
//                                Log.d("SubmitReturnMardodatVMisNullOrEmpty1", it.data.message)
//                            } else {
//                                val intent = Intent(activity, ClosingActivity::class.java)
//
//                                startActivity(intent)
//                            }
                            val intent = Intent(activity, ClosingActivity::class.java)

                            startActivity(intent)

//                            val intent = Intent(activity, ClosingActivity::class.java)
//
//                            startActivity(intent)

//                            DailyClosingviewModel.   SubmitReturnMardodatVM().collect {
//
//                            }
                        }
                        is State.Error -> {
                            binding.spinKit.isVisible = false
                            Toast.makeText(context, it.messag, Toast.LENGTH_SHORT).show()


                        }
                    }
                }
            }


        }
        binding.sales.setText(" المردودات ")

        val adabter = AdapterExpensesClosing()
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
        val adapter = ItemsReportAdapter(OnItemSettlementClickListener2 {


            var ItemID = it.ITEM_ID
            var itemname = it.itemname
            val intent = Intent(activity, ItemsBIllActivity::class.java)
                .apply {
 //                   Log.d("Item_ID", ItemID.toString())
                    putExtra("ITEM_ID", ItemID.toString())
                    putExtra("itemname", itemname)

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