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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectDebitFragment : Fragment() {
    private lateinit var binding: FragmentCollectDebitBinding
    private val viewModel: CollectDebitViewModel by activityViewModels()
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
        viewModel. getDebits()
        val adapter = CustomerDebitsAdapter(onClickListener = OnDebitClickListener {
            viewModel.setDebit(it)
            viewModel.navigateToExecution()
        })
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //  if (query.length > 1)
                //    adapte.filter.filter(query)
                //   adapte.notifyDataSetChanged()
                viewModel.filterCustomers(query)
                //   adapte.notifyDataSetChanged()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //  if (newText.length > 1)
                //       adapte.filter.filter(newText)


                viewModel.filterCustomers(newText)
                //  adapte.notifyDataSetChanged()
                return false
            }
        })

      binding.recyclerView.adapter = adapter
      viewModel.debits.observe(viewLifecycleOwner) {
          adapter.submitList(it)
          Log.i("size", it!!.size.toString())
      }

        viewModel.msg.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                showSnackbar(activity!!, it)
                viewModel.onMsgShowDone()
            }
        }

        viewModel.navToExecution.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(CollectDebitFragmentDirections.actionCollectDebitFragmentToDebitExecutionFragment())
                viewModel.onDoneNavigateToExecution()
            }
        }
        return binding.root
    }

}