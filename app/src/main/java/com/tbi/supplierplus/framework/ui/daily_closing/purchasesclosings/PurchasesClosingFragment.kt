package com.tbi.supplierplus.framework.ui.daily_closing.purchasesclosings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.databinding.FragmentPurchasesClosingBinding
import com.tbi.supplierplus.framework.ui.daily_closing.DailyClosingViewModel
import com.tbi.supplierplus.framework.ui.daily_closing.itemsReceived.AdapterItemsReceived
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.reports.ItemsReportAdapter
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PurchasesClosingFragment : Fragment() {

    private lateinit var binding: FragmentPurchasesClosingBinding
    val viewModel: AvailableItemsViewModel by viewModels()
   // private val viewModel: DailyClosingViewModel by  viewModels()
   private val DailyClosingviewModel: DailyClosingViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPurchasesClosingBinding.inflate(inflater)

        binding.lifecycleOwner = this


      binding.  button5.setOnClickListener {
          lifecycleScope.launch(){
              DailyClosingviewModel.   SubmitReturnMardodatVM().collect {
                  when (it) {
                      is State.Loading -> binding.spinKit.isVisible = true
                      is State.Success -> {
                          binding.spinKit.isVisible = false
                          Log.d("SubmitReturnMardodatVM","1")
                          DailyClosingviewModel.   SubmitReturnMardodatVM().collect {

                          }
                      }
                      is State.Error -> {
                          binding.spinKit.isVisible = false
                          Toast.makeText(context, it.messag, Toast.LENGTH_SHORT).show()
                          Log.d("SubmitReturnMardodatVM","2")

                      }
                  }
              }
          }

      }

       val adapter = ItemsReportAdapter()
       binding.spinKit.isVisible = false
       lifecycleScope.launch {
           viewModel.getItemsReport().collect {
               when (it) {

                   is State.Loading -> binding.spinKit.isVisible = true

                   is State.Success -> {
                       binding.spinKit.isVisible = false

                       binding.purchaseClosingrecyclerView.adapter = adapter
                       adapter.submitList(it.data.data)

                       binding.purchaseClosingrecyclerView.adapter = adapter
                   }
                   is State.Error -> {
                       binding.spinKit.isVisible = false
                   }
               }
           }
       }
       binding. Purchases.setText("المخزون والمردودات ")


//       viewModel.   getDailyClosingPurchases()
//        viewModel.purchasesLiveData.observe(viewLifecycleOwner) {
//
//            val adabter= AdapterPurchasesClosing()
//            binding.purchaseClosingrecyclerView.adapter = adabter
//            binding.purchaseClosingrecyclerView.setHasFixedSize(true)
//            adabter.submitList(it)
//
//            binding. Purchases.setText("المخزون والمردودات ")
//
//        }
        return binding.root

    }


}