package com.tbi.supplierplus.framework.ui.add_items_return

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.FragmentCustomerReturnItemsBinding
import com.tbi.supplierplus.databinding.FragmentCustomerReturnItemsBindingImpl
import com.tbi.supplierplus.databinding.FragmentCustomersBinding
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import com.tbi.supplierplus.framework.ui.sales.customers.CustomersAdapter
import com.tbi.supplierplus.framework.ui.sales.customers.CustomersFragmentArgs
import com.tbi.supplierplus.framework.ui.sales.customers.CustomersFragmentDirections
import com.tbi.supplierplus.framework.ui.sales.customers.OnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerReturnItemsFragment : Fragment() {

    private lateinit var binding: FragmentCustomerReturnItemsBinding
    private val viewModel: AddItemsReturnViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding = FragmentCustomerReturnItemsBinding.inflate(inflater)
        binding.data = viewModel
        binding.lifecycleOwner = this


        val adapter = CustomersAdapter(OnClickListener {
            viewModel.setCustomerID(it.customerID)
            findNavController().navigate(
                CustomerReturnItemsFragmentDirections.actionCustomerReturnItemsFragmentToAddItemsReturnFragment(
                    CustomerReturnItemsFragmentArgs.fromBundle(requireArguments()).user
                )
            )
        })

        viewModel.setUser(
            CustomersFragmentArgs.fromBundle(requireArguments()).user
        )

        viewModel.customers.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = adapter
            adapter.submitList(viewModel.customers.value)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length > 1)
                    viewModel.filterCustomers(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length > 1)
                    viewModel.filterCustomers(newText)
                return true
            }
        })

        binding.btnQr.setOnClickListener {
            findNavController().navigate(
                CustomersFragmentDirections.actionCustomersFragmentToCustomerQRCodeScannerFragment(
                    "customer"
                )
            )

        }





        return binding.root
    }

}
