package com.tbi.supplierplus.framework.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.databinding.FragmentAdditemsReturnBinding
import com.tbi.supplierplus.databinding.FragmentSettingsBinding
import com.tbi.supplierplus.framework.ui.MainFragmentDirections
import com.tbi.supplierplus.framework.ui.addNewItem.AddItemActivity
import com.tbi.supplierplus.framework.ui.sales.customers.product_selection.ChangePriceActivity


class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        //  binding.viewModel = viewModel
        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
        binding.reportsCard.setOnClickListener {
            val intent = Intent(getActivity(), AddItemActivity::class.java)
            getActivity()?.startActivity(intent)
        }
        binding.editPublicPrice.setOnClickListener {
            val intent = Intent(getActivity(), ChangePriceActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        binding.reportsEditPric.setOnClickListener {
            findNavController().navigate(
                SettingsFragmentDirections.actionSettingsFragmentToProductSelectionFragment(

                )
            )
        }
        return binding.root
        //inflater.inflate(R.layout.fragment_settings, container, false)
    }

}