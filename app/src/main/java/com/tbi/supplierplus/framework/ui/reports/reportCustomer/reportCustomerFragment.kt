package com.tbi.supplierplus.framework.ui.reports.reportCustomer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.FragmentReportCustomerBinding
import com.tbi.supplierplus.databinding.FragmentSelectCustomerBinding
import com.tbi.supplierplus.framework.ui.reports.ReportsViewModel
import dagger.hilt.android.AndroidEntryPoint



class reportCustomerFragment : Fragment() {
    private val viewModel: ReportsViewModel by activityViewModels()

    private lateinit var binding: FragmentReportCustomerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportCustomerBinding.inflate(inflater)
        binding.lifecycleOwner = this

        // Inflate the layout for this fragment
        return  binding.root
    }

}