package com.tbi.supplierplus.framework.ui.sales.customers.product_selection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.FragmentChangeSpecialPriceBinding
import com.tbi.supplierplus.databinding.FragmentProductSelectionBinding
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel


class ChangeSpecialPriceFragment : Fragment() {
    private lateinit var binding:FragmentChangeSpecialPriceBinding
    val viewModel: SalesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeSpecialPriceBinding.inflate(inflater)
        // binding.viewModel = viewModel
        binding.lifecycleOwner = this
        // Inflate the layout for this fragment

      //  return inflater.inflate(R.layout.fragment_change_special_price, container, false)
        return binding.root
    }


}