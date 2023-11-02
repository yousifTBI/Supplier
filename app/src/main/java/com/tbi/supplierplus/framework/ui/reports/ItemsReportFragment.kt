package com.tbi.supplierplus.framework.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.databinding.FragmentItemsReportBinding
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemsReportFragment : Fragment() {
    private lateinit var binding: FragmentItemsReportBinding

    val viewModel: AvailableItemsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentItemsReportBinding.inflate(inflater)
        //  binding.viewModel = viewModel
        binding.lifecycleOwner = this




        val adapter = ItemsReportAdapter(OnItemSettlementClickListener2{

        })
        binding.spinKit.isVisible = false
        lifecycleScope.launch {
            viewModel.getItemsReport().collect {
                when (it) {

                    is State.Loading -> binding.spinKit.isVisible = true

                    is State.Success -> {
                        binding.spinKit.isVisible = false

                        binding.recyclerView.adapter = adapter
                        adapter.submitList(it.data.data)

                        binding.recyclerView.adapter = adapter
                    }
                    is State.Error -> {
                        binding.spinKit.isVisible = false
                    }
                }
            }
        }


        return binding.root
    }

}