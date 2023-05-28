package com.tbi.supplierplus.framework.ui

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.databinding.FragmentMainBinding
import com.tbi.supplierplus.framework.ui.addNewItem.AddItemActivity
import com.tbi.supplierplus.framework.ui.purchase.PurchaseActivity
import com.tbi.supplierplus.framework.ui.sales.customers.product_selection.ChangePriceActivity
import dagger.hilt.android.AndroidEntryPoint


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this



        binding.stock.setOnClickListener {

            findNavController().navigate(
                MainFragmentDirections.actionGlobalStoreFragment(

                )
            )



        }

        binding.sales.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToCustomersFragment(
                    User("peter_tbi", "", "", "3", "", "2", "", "")
                )

            )

        }

        binding.saless.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionGlobalSalesSettingFragment()

            )
        }


        binding.car.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionGlobalSettingsFragment()

            )
        }



        binding.sales2.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDailyClosingFragment(
                    User("peter_tbi", "", "", "3", "", "2", "", "")

                )
            )
        }

        binding.saless2.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionGlobalCustomerSettingFragment(

                )
            )
        }



      binding.car2.setOnClickListener {
          findNavController().navigate(
              MainFragmentDirections.actionMainFragmentToItemsSettlementFragment(

                  User("peter_tbi", "", "", "3", "", "2", "", "")

              )

          )
      }

          binding.sales3.setOnClickListener {
              findNavController().navigate(
                  MainFragmentDirections.actionGlobalReportsFragment()

              )
          }

        return binding.root
    }
}


