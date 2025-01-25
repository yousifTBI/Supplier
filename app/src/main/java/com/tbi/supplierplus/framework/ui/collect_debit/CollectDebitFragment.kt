package com.tbi.supplierplus.framework.ui.collect_debit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.utils.showSnackbar
import com.tbi.supplierplus.databinding.FragmentCollectDebitBinding
import com.tbi.supplierplus.framework.ui.collect_debit.CollectDebitFragmentDirections.actionCollectDebitFragmentToDebitExecutionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectDebitFragment : Fragment() {
    private lateinit var binding: FragmentCollectDebitBinding
    private val viewModel: CollectDebitViewModel by activityViewModels()

    // Fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCollectDebitBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.setUser(
            CollectDebitFragmentArgs.fromBundle(requireArguments()).user
        )

        val adapter = CustomerDebitsAdapter(onClickListener = OnDebitClickListener {
            viewModel.setDebit(it)  
            viewModel.navigateToExecution()
        })

        binding.recyclerView.adapter = adapter

        // Observe changes to the filtered list in the ViewModel
        viewModel.debits.observe(viewLifecycleOwner) { debits ->
            Log.d("CollectDebitFragment", "Received updated debits list: $debits")

            adapter.submitList(debits)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("newText", newText.toString())
                // Check if newText is not null before filtering customers
                if (!newText.isNullOrEmpty()) {
                    Log.d("newText", "in if $newText")
                    viewModel.filterCustomers(newText)
                } else {
                    Log.d("newText", "in else $newText")
                    // If the query is null or empty, clear the filter
                    viewModel.clearFilter()
                }
                return true
            }
        })

        viewModel.msg.observe(viewLifecycleOwner) { msg ->
            if (msg.isNotEmpty()) {
                showSnackbar(requireActivity(), msg)
                viewModel.onMsgShowDone()
            }
        }

        viewModel.navToExecution.observe(viewLifecycleOwner) { nav ->
            if (nav) {
                findNavController().navigate(actionCollectDebitFragmentToDebitExecutionFragment("Hello, this is the string to pass!"))
                viewModel.onDoneNavigateToExecution()
            }
        }

        return binding.root
    }

}

//class CollectDebitFragment : Fragment() {
//    private lateinit var binding: FragmentCollectDebitBinding
//    private val viewModel: CollectDebitViewModel by activityViewModels()
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        binding = FragmentCollectDebitBinding.inflate(inflater)
//        binding.viewModel = viewModel
//        binding.lifecycleOwner = this
//        viewModel.setUser(
//            CollectDebitFragmentArgs.fromBundle(requireArguments()).user
//        )
//
//        viewModel. getDebits()
//        var adapter = CustomerDebitsAdapter(onClickListener = OnDebitClickListener {
//            viewModel.setDebit(it)
//            viewModel.navigateToExecution()
//        })
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    return false
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    // Check if newText is not null before calling filterCustomers
//                    if (!newText.isNullOrEmpty()) {
//                        viewModel.filterCustomers(newText)
//
//                    }
//                    return true
//                }
//            })
//
//
//            var stringArg =""
//      binding.recyclerView.adapter = adapter
//      viewModel.debits.observe(viewLifecycleOwner) {
//          adapter.submitList(it)
//
//      }
//
//        viewModel.msg.observe(viewLifecycleOwner) {
//            if (it.isNotEmpty()) {
//                showSnackbar(activity!!, it)
//                viewModel.onMsgShowDone()
//            }
//        }
//
//        viewModel.navToExecution.observe(viewLifecycleOwner) {
//            if (it) {
//                findNavController().navigate(actionCollectDebitFragmentToDebitExecutionFragment("Hello, this is the string to pass!"))
//                viewModel.onDoneNavigateToExecution()
//            }
//        }
//        return binding.root
//    }
//
//}