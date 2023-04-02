package com.tbi.supplierplus.framework.ui.reports

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.FragmentRegisterBinding
import com.tbi.supplierplus.databinding.FragmentReportsBinding
import com.tbi.supplierplus.framework.ui.MainFragmentDirections

class ReportsFragment : Fragment() {
    private lateinit var binding:FragmentReportsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportsBinding.inflate(layoutInflater)


        binding.invoiceCard.setOnClickListener {
            findNavController().navigate(
                ReportsFragmentDirections.actionReportsFragmentToCustomerStatementFragment()
////
            )
        }
        binding.dailyReportID.setOnClickListener {
            findNavController().navigate(
                ReportsFragmentDirections.actionReportsFragmentToSalesReportFragment()
////
            )
        }
        binding.Accountstatement.setOnClickListener {
            findNavController().navigate(
                ReportsFragmentDirections.actionReportsFragmentToItemsReportFragment()
////
            )
        }
        binding.CusReportID.setOnClickListener {
            findNavController().navigate(
                ReportsFragmentDirections.actionReportsFragmentToSelectCustomerFragment()
////
            )
        }

        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_reports, container, false)
        return binding.root
    }

}