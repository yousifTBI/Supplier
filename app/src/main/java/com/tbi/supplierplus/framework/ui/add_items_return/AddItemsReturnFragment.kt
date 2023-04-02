package com.tbi.supplierplus.framework.ui.add_items_return

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.FragmentAdditemsReturnBinding
import com.tbi.supplierplus.databinding.FragmentCustomerReturnItemsBinding
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import com.tbi.supplierplus.framework.ui.sales.customer_profile.ItemsAdapter
import com.tbi.supplierplus.framework.ui.sales.customer_profile.ReturnsAdapter

class AddItemsReturnFragment : Fragment() {

    private lateinit var binding: FragmentAdditemsReturnBinding
    private val viewModel: AddItemsReturnViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdditemsReturnBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.items.observe(viewLifecycleOwner) {
            val adapter = ItemsAdapter(context!!, it)
            binding.spinner.adapter = adapter
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setCurrentItemID(
                    viewModel.items.value!![position].id,
                )

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }
        viewModel.currentItemID.observe(viewLifecycleOwner) { currentItemID ->
            try {
                if (currentItemID != null) {
                    viewModel.updateItem()
                    binding.spinner.setSelection(viewModel.items.value!!.indexOf(viewModel.items.value!!.filter {
                        currentItemID.equals(
                            it.id
                        )
                    }[0]))
                } else viewModel.getAllItems()
            } catch (e: Exception) {
            }
        }

        val returnsAdapter = ReturnsAdapter()
        viewModel.returns.observe(viewLifecycleOwner) {
            if (it!!.size > 0) {
                binding.returnsRecycler.adapter = returnsAdapter
            //    returnsAdapter.submitList(it)
                binding.mortg3atLayout.visibility = View.VISIBLE
            } else binding.mortg3atLayout.visibility = View.GONE
        }

        viewModel.navigateToExecute.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(AddItemsReturnFragmentDirections.actionAddItemsReturnFragmentToExecuteReturnItemsFragment())
                viewModel.onDoneNavigateToExecute()
            }
        }
        return binding.root
    }

}