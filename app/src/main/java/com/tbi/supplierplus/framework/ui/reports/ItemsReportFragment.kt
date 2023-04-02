package com.tbi.supplierplus.framework.ui.reports

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.utils.toJson
import com.tbi.supplierplus.databinding.FragmentItemsReportBinding
import com.tbi.supplierplus.databinding.FragmentSalesReportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemsReportFragment : Fragment() {
    private lateinit var binding: FragmentItemsReportBinding
    private val viewModel: ReportsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentItemsReportBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val adapter = ItemsReportAdapter()

        viewModel.getItemsReport()
        viewModel.ItemsReportLivedata.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = adapter
            adapter.submitList(it!!.toList())
        }

     //   viewModel.itemsSummary.observe(viewLifecycleOwner) {
     //       binding.recyclerView.adapter = adapter
     //       adapter.submitList(it!!.toList())
     //       Log.i("ItemsSummary",it.toJson())
     //   }
        return binding.root
    }

}