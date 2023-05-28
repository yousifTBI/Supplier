package com.tbi.supplierplus.framework.ui.daily_closing.purchasesclosings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tbi.supplierplus.databinding.FragmentPurchasesClosingBinding
import com.tbi.supplierplus.framework.ui.daily_closing.DailyClosingViewModel
import com.tbi.supplierplus.framework.ui.daily_closing.itemsReceived.AdapterItemsReceived
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PurchasesClosingFragment : Fragment() {

    private lateinit var binding: FragmentPurchasesClosingBinding

    private val viewModel: DailyClosingViewModel by  viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPurchasesClosingBinding.inflate(inflater)

        binding.lifecycleOwner = this

      viewModel.   getDailyClosingPurchases()
        viewModel.purchasesLiveData.observe(viewLifecycleOwner) {

            val adabter= AdapterPurchasesClosing()
            binding.purchaseClosingrecyclerView.adapter = adabter
            binding.purchaseClosingrecyclerView.setHasFixedSize(true)
            adabter.submitList(it)

            binding. Purchases.setText("مشتريات")

        }
        return binding.root

    }


}