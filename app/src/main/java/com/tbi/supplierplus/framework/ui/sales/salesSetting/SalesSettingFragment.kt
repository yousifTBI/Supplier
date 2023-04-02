package com.tbi.supplierplus.framework.ui.sales.salesSetting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.databinding.FragmentCustomerSettingBinding
import com.tbi.supplierplus.databinding.FragmentSalesSettingBinding
import com.tbi.supplierplus.framework.ui.MainFragmentDirections
import com.tbi.supplierplus.framework.ui.addNewItem.AddItemActivity
import com.tbi.supplierplus.framework.ui.purchase.PurchaseActivity


class SalesSettingFragment : Fragment() {

    private lateinit var binding: FragmentSalesSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSalesSettingBinding.inflate(inflater)
        binding.returnsForUserCard.setOnClickListener {
            Log.d("sdsdsf","sdsds")
            val    intent = Intent(activity, PurchaseActivity::class.java)
            startActivity(intent)
        }

        binding.reportsCard.setOnClickListener {
            val    intent = Intent(activity, AddItemActivity::class.java)
            startActivity(intent)
            //indNavController().navigate(
            //   MainFragmentDirections.actionMainFragmentToReportFragment(
            //    //   MainFragmentArgs.fromBundle(
            //    //       requireArguments()
            //    //   ).user
            //
            //   )
            //  )
        }

        binding.expensesCard.setOnClickListener {
            findNavController().navigate(
                SalesSettingFragmentDirections.actionSalesSettingFragmentToExpensesFragment(
                    //.actionMainFragmentToExpensesFragment(
                    //      MainFragmentArgs.fromBundle(
                    //          requireArguments()
                    //      ).user
                    User("peter_tbi", "", "", "3", "", "2", "", "")

                )
            )
        }
        binding.returnsCard.setOnClickListener {
            findNavController().navigate(
                SalesSettingFragmentDirections.actionSalesSettingFragmentToReturnItemsFragment(
                    //.actionMainFragmentToReturnItemsFragment(
                    //   MainFragmentArgs.fromBundle(
                    //       requireArguments()
                    //   ).user
                    User("peter_tbi", "", "", "3", "", "2", "", "")

                )
            )
        }
        binding.purchaseCard.setOnClickListener {
            findNavController().navigate(
                SalesSettingFragmentDirections.actionSalesSettingFragmentToPurchaseFragment(
                // MainFragmentDirections.actionMainFragmentToPurchaseFragment(
                //  //  MainFragmentArgs.fromBundle(
                //  //      requireArguments()
                //  //  ).user
                    User("peter_tbi", "", "", "3", "", "2", "", "")

                 )
                //  MainFragmentDirections.actionMainFragmentToPuarchaseFragment()
            )
        }
        // Inflate the layout for this fragment
        return binding.root
      //  return inflater.inflate(R.layout.fragment_sales_setting, container, false)
    }


}