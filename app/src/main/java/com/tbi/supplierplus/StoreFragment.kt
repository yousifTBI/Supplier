package com.tbi.supplierplus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tbi.supplierplus.databinding.FragmentStoreBinding
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.framework.ui.MainFragmentDirections

class StoreFragment : Fragment() {
    private lateinit var binding: FragmentStoreBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStoreBinding.inflate(inflater)

        binding.reportsCard.setOnClickListener {
            findNavController().navigate(
                StoreFragmentDirections.actionStoreFragmentToAvailableItemsFragment(

                )
            )

        }



        binding.reportsEditSpecialPric.setOnClickListener {
            findNavController().navigate(
                StoreFragmentDirections.actionStoreFragmentToItemsSettlementFragment(

                    User("peter_tbi", "", "", "3", "", "2", "", "")

                )
            )

        }

        binding.reportsEditPric.setOnClickListener {
            findNavController().navigate(
                StoreFragmentDirections.actionStoreFragmentToItemsReportFragment(

                )
            )

        }




        return binding.root
    }

}