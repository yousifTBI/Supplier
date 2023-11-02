package com.tbi.supplierplus.framework.ui.roadLine

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.FragmentRoadLineBinding
import com.tbi.supplierplus.framework.ui.itemsSettlement.GetItemsSettlementAdapter
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
@AndroidEntryPoint
class RoadLineFragment : Fragment() {

   lateinit var binding :FragmentRoadLineBinding
    val viewModel: AvailableItemsViewModel by viewModels()
    lateinit var adapter: RoadLineAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRoadLineBinding.inflate(inflater)

        adapter = RoadLineAdapter()
        lifecycleScope.launch() {
           viewModel.GetSalesCurrentDayRoadMap().collect {

                when (it) {
                    is State.Loading ->{

                    }
                    is State.Success -> {
                        binding.spinKit.isVisible = false
                        binding.textView9v.setText(it.data.message)
                        if (it.data.data ==null){
                            Toast.makeText(requireContext(),it.data.message, Toast.LENGTH_SHORT).show()
                        }
                        else{
                            adapter.submitList(it.data.data)
                            binding.recyclerView.adapter = adapter

                        }

                    }
                    is State.Error -> {
                        Log.d("VisitBranchWithoutPay",it.messag)
                    }
                }
            }

        }
        return binding.root
    }

}