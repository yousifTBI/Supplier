package com.tbi.supplierplus.framework.ui.sales.customer_profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.FragmentBillBinding
import com.tbi.supplierplus.databinding.FragmentReturnsBinding
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReturnsFragment : Fragment() {
    private lateinit var binding: FragmentReturnsBinding
    private val viewModel: SalesViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReturnsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.returnsRecycler.adapter = ReturnsAdapter()
        val adapter = ReturnsAdapter()
     //   viewModel.returns.observe(viewLifecycleOwner) {
     //       Log.i("ReturnsSize", it!!.size.toString())
     //       binding.returnsRecycler.adapter = adapter
     //       binding.returnsRecycler.setHasFixedSize(true)
     //       adapter.submitList(it)
     //   }
        return binding.root
    }

}