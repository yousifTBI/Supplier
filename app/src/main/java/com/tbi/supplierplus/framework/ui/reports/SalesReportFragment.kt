package com.tbi.supplierplus.framework.ui.reports

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tbi.supplierplus.business.utils.toJson
import com.tbi.supplierplus.databinding.FragmentSalesReportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalesReportFragment : Fragment() {
    private lateinit var binding: FragmentSalesReportBinding
    private val viewModel2: ReportsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSalesReportBinding.inflate(inflater)
        binding.viewModel = viewModel2
        binding.lifecycleOwner = this

        viewModel2.getselesRepor()
        viewModel2.selesReporLivedata.observe(viewLifecycleOwner){

        }


        return binding.root
    }

}