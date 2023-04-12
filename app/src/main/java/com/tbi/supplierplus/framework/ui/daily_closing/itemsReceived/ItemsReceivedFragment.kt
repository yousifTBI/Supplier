package com.tbi.supplierplus.framework.ui.daily_closing.itemsReceived

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tbi.supplierplus.databinding.FragmentItemsReceivedBinding
import com.tbi.supplierplus.framework.ui.daily_closing.DailyClosingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemsReceivedFragment : Fragment() {
    private lateinit var binding: FragmentItemsReceivedBinding

    private val viewModel: DailyClosingViewModel by   viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemsReceivedBinding.inflate(inflater)
        //  binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.   getDailyClosingSummaryItems()
       viewModel.itemsDailyClosingLiveData.observe(viewLifecycleOwner) {

              val adabter=AdapterItemsReceived()
              binding.ReceivdClosingrecyclerView.adapter = adabter
              binding.ReceivdClosingrecyclerView.setHasFixedSize(true)
              adabter.submitList(it)


       }


     return binding.root

    }


}