package com.tbi.supplierplus.framework.ui.reports

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.FragmentCustomerStatementDetailsBinding
import com.tbi.supplierplus.framework.ui.sales.customer_profile.BillAdapter
import com.tbi.supplierplus.framework.ui.sales.customer_profile.ReturnsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerStatementDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCustomerStatementDetailsBinding
    private val viewModel: ReportsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCustomerStatementDetailsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

       viewModel. selectedStatement.observe(viewLifecycleOwner){

       }
        val adapter1 = BillAdapter()
        viewModel.Sales.observe(viewLifecycleOwner){
           // it?.get(0)?.Items?.let { it1 -> Log.d("sjdhjhs", it1) }

            binding.billRecycler.adapter = adapter1
            adapter1.submitList(it)
        }

        val adapter2 = ReturnsAdapter()
         viewModel. Returns.observe(viewLifecycleOwner){

             binding.returnsRecycler.adapter = adapter2
             adapter2.submitList(it)
         }

      //  viewModel.billDetails.observe(viewLifecycleOwner) {
      //    if (it.bill.isEmpty())
      //        binding.billLayout.visibility = View.GONE
      //    else {
      //        binding.billLayout.visibility = View.VISIBLE
      //        val adapter = BillAdapter()
      //        binding.billRecycler.adapter = adapter
      //        adapter.submitList(it.bill)
      //    }
      //    if (it.returns.isEmpty())
      //        binding.mortg3atLayout.visibility = View.GONE
      //    else {
      //        binding.mortg3atLayout.visibility = View.VISIBLE
      //        val adapter = ReturnsAdapter()
      //        binding.returnsRecycler.adapter = adapter
      //        adapter.submitList(it.returns)
      //    }
      //}
        return binding.root
    }

}