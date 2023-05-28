package com.tbi.supplierplus.framework.ui.reports.reportCustomer

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.FragmentItemsSettlementBinding
import com.tbi.supplierplus.databinding.FragmentSelectCustomerBinding
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.itemsSettlement.ItemsSettlementViewModel
import com.tbi.supplierplus.framework.ui.reports.ReportsFragmentDirections
import com.tbi.supplierplus.framework.ui.reports.ReportsViewModel
import com.tbi.supplierplus.framework.ui.sales.customers.product_selection.ChangeSpecialPriceActivity
import com.tbi.supplierplus.testAdabters.AllCustomersAdapter
import com.tbi.supplierplus.testAdabters.OnClickListenerss
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class SelectCustomerFragment : Fragment() {
    private val viewModel: ReportsViewModel by activityViewModels()
    private lateinit var binding:FragmentSelectCustomerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectCustomerBinding.inflate(inflater)

        binding.lifecycleOwner = this

        val  adapte= AllCustomersAdapter(OnClickListenerss {


            val Customer_ID       =it.Customer_ID.toString()
            val Unpaid_deferred=it.Unpaid_deferred.toString()
            val CompanyName       =it.item.toString()
            viewModel.setCustomer( Customer_ID ,CompanyName  )

             val intent = Intent(activity, ReportCustomerActivity::class.java)
                 .apply {
                     putExtra("Customer_ID", Customer_ID)

                      putExtra("CompanyName", CompanyName)

                 }
             startActivity(intent)


        })

        viewModel.getCustomers(SharedPreferencesCom.getInstance().gerSharedUser_ID())
        viewModel.customers.observe(viewLifecycleOwner){
            binding.recyclerView.adapter = adapte
            adapte.submitList(it)
        }

        return  binding.root
    }


}