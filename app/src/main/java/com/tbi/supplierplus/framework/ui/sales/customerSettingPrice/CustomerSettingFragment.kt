package com.tbi.supplierplus.framework.ui.sales.customerSettingPrice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.databinding.FragmentCustomerSettingBinding
import com.tbi.supplierplus.databinding.FragmentCustomersBinding
import com.tbi.supplierplus.framework.ui.MainFragmentDirections

class CustomerSettingFragment : Fragment() {

    private lateinit var binding: FragmentCustomerSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerSettingBinding.inflate(inflater)
        binding.balanceCard.setOnClickListener {
            findNavController().navigate(
                CustomerSettingFragmentDirections.actionCustomerSettingFragmentToOpeningBalancesFragment(
                    // MainFragmentArgs.fromBundle(
                    //     requireArguments()
                    // ).user
                    User("peter_tbi", "", "", "3", "", "2", "", "")

                )
            )
        }

        binding.collectCard.setOnClickListener {
            findNavController().navigate(
                CustomerSettingFragmentDirections.actionCustomerSettingFragmentToCollectDebitFragment(
                    User("peter_tbi", "", "", "3", "", "2", "", "")
                )
                    //.actionMainFragmentToCollectDebitFragment(
                    //  MainFragmentArgs.fromBundle(
                    //      requireArguments()
                    //  ).user
                //

             //   )
            )
        }
        // Inflate the layout for this fragment
        return binding.root
    //  return inflater.inflate(R.layout.fragment_customer_setting, container, false)
    }


}