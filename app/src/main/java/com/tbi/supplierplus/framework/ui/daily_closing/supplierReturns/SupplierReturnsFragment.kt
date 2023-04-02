package com.tbi.supplierplus.framework.ui.daily_closing.supplierReturns

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tbi.supplierplus.databinding.FragmentSupplierReturnsBinding
import com.tbi.supplierplus.framework.ui.daily_closing.DailyClosingViewModel
import com.tbi.supplierplus.framework.ui.daily_closing.purchasesclosings.AdapterPurchasesClosing
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SupplierReturnsFragment : Fragment() {
    private lateinit var binding: FragmentSupplierReturnsBinding

    private val viewModel: DailyClosingViewModel by  activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSupplierReturnsBinding.inflate(inflater)
        //  binding.viewModel = viewModel
        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
        viewModel.  getDailyClosingSummarySupplier()
         viewModel.summarySupplierLiveData.observe(viewLifecycleOwner) {
             val adabter=  AdapterSupplierReturns()
             binding.returnClosingrecyclerView.adapter = adabter
             binding.returnClosingrecyclerView.setHasFixedSize(true)
             adabter.submitList(it)
         }
     ///   returnClosingrecyclerView
        return binding.root
       // return inflater.inflate(R.layout.fragment_supplier_returns, container, false)
    }


}