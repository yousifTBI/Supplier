package com.tbi.supplierplus.framework.ui.daily_closing.itemsReceived

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
import com.tbi.supplierplus.databinding.FragmentItemsReceivedBinding
import com.tbi.supplierplus.framework.ui.daily_closing.DailyClosingViewModel
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemsReceivedFragment : Fragment() {
    private lateinit var binding: FragmentItemsReceivedBinding

    private val viewModel: DailyClosingViewModel by   viewModels()
    val availableItemsViewModel: AvailableItemsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemsReceivedBinding.inflate(inflater)
        //  binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.spinKit.isVisible =false



//        viewModel.   getDailyClosingSummaryItems()
//       viewModel.itemsDailyClosingLiveData.observe(viewLifecycleOwner) {
//
//              val adabter=AdapterItemsReceived()
//              binding.ReceivdClosingrecyclerView.adapter = adabter
//              binding.ReceivdClosingrecyclerView.setHasFixedSize(true)
//              adabter.submitList(it)
//
//
//       }






        lifecycleScope.launch {
            availableItemsViewModel.getPendingMortaga3at().collect {
                when (it) {

                    is State.Loading -> {}

                    is State.Success -> {
                        val adabter=AdapterItemsReceived()
                        binding.ReceivdClosingrecyclerView.adapter = adabter
                        binding.ReceivdClosingrecyclerView.setHasFixedSize(true)
                        adabter.submitList(it.data.data)
                    }
                    is State.Error -> {
                    }
                }
            }
            }


        binding.SubmitBtn.setOnClickListener {
            lifecycleScope.launch {
                availableItemsViewModel.getConfirmSalesrRequest().collect {
                    when (it) {

                        is State.Loading -> {
                            binding.spinKit.isVisible =true
                        }

                        is State.Success -> {

                            binding.spinKit.isVisible =false
                        //    Log.d("msgtxt","it.data.message")
                         //   binding.msgTxt.setText(it.data.Message)
                            Toast.makeText(context, it.data.Message, Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, it.data.Message, Toast.LENGTH_SHORT).show()

                        }
                        is State.Error -> {
                            binding.spinKit.isVisible = false
                            Toast.makeText(context, it.messag, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }




     return binding.root

    }


}