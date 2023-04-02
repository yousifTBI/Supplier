package com.tbi.supplierplus.framework.ui.reports

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.utils.fromJson
import com.tbi.supplierplus.databinding.FragmentCustomersBinding
import com.tbi.supplierplus.databinding.FragmentReportBinding
import com.tbi.supplierplus.databinding.FragmentReturnsBinding
import com.tbi.supplierplus.framework.ui.sales.customer_profile.BillsPager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment : Fragment() {
    private lateinit var binding: FragmentReportBinding

    val viewModel: ReportsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentReportBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
     //   viewModel.setUser(
     //       ReportFragmentArgs.fromBundle(requireArguments()).user
     //   )
        binding.viewPager.adapter = ReportsPager(parentFragmentManager)
        binding.tablayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }


}