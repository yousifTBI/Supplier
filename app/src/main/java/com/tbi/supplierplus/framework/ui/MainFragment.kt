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

       //binding.purchaseCard.setVisibility(View.INVISIBLE)
       //binding.reportsCard.setVisibility(View.INVISIBLE)
       //binding.expensesCard.setVisibility(View.INVISIBLE)
       //binding.returnsCard.setVisibility(View.INVISIBLE)
       //binding.collectCard.setVisibility(View.INVISIBLE)
       //binding.returnsForUserCard.setVisibility(View.INVISIBLE)
       //binding.itemsSettlementCard.setVisibility(View.INVISIBLE)
       //binding.closingDayCard.setVisibility(View.INVISIBLE)
       //binding.balanceCard.setVisibility(View.INVISIBLE)
      // binding.  EditProductpriceCard.setVisibility(View.INVISIBLE)
      // binding.  ProductSelectionCard.setVisibility(View.INVISIBLE)

        binding.salesCard.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToCustomersFragment(
                    User("peter_tbi", "", "", "3", "", "2", "", "")
                   // MainFragmentArgs.fromBundle(requireArguments()).user
                )

            )

        }

        binding.purchaseCard.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionGlobalSalesSettingFragment()
             // MainFragmentDirections.actionMainFragmentToPurchaseFragment(
             //  //  MainFragmentArgs.fromBundle(
             //  //      requireArguments()
             //  //  ).user
             //     User("peter_tbi", "", "", "3", "", "2", "", "")

             // )
              //  MainFragmentDirections.actionMainFragmentToPuarchaseFragment()
            )
        }


        binding.addCustomerCard.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionGlobalSettingsFragment()
                //  MainFragmentDirections.actionMainFragmentToPuarchaseFragment()
            )
        }


      // binding.reportsCard.setOnClickListener {
      //     val    intent = Intent(activity, AddItemActivity::class.java)
      //     startActivity(intent)
      //    //indNavController().navigate(
      //    //   MainFragmentDirections.actionMainFragmentToReportFragment(
      //    //    //   MainFragmentArgs.fromBundle(
      //    //    //       requireArguments()
      //    //    //   ).user
      //    //
      //    //   )
      //   //  )
      // }

   //  binding.expensesCard.setOnClickListener {
   //      findNavController().navigate(
   //          MainFragmentDirections.actionMainFragmentToExpensesFragment(
   //        //      MainFragmentArgs.fromBundle(
   //        //          requireArguments()
   //        //      ).user
   //          User("peter_tbi", "", "", "3", "", "2", "", "")

   //    }

        binding.closingDayCard.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDailyClosingFragment(
                    User("peter_tbi", "", "", "3", "", "2", "", "")

                    // MainFragmentArgs.fromBundle(
                   //     requireArguments()
                   // ).user
                )
            )
        }

        binding.collectCard.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionGlobalCustomerSettingFragment(
                  //  MainFragmentArgs.fromBundle(
                  //      requireArguments()
                  //  ).user
                 //   User("peter_tbi", "", "", "3", "", "2", "", "")

                )
            )
        }

   //    binding.balanceCard.setOnClickListener {
   //        findNavController().navigate(
   //           // MainFragmentDirections.actionMainFragmentToOpeningCustomerSettingFragment(
   //            MainFragmentDirections.actionGlobalCustomerSettingFragment(
   //              // MainFragmentArgs.fromBundle(
   //              //     requireArguments()
   //              // ).user
   //               // User("peter_tbi", "", "", "3", "", "2", "", "")

   //            )
   //        )
   //    }

      binding.itemsSettlementCard.setOnClickListener {
          findNavController().navigate(
              MainFragmentDirections.actionMainFragmentToItemsSettlementFragment(
                //  MainFragmentArgs.fromBundle(requireArguments()).user
                  User("peter_tbi", "", "", "3", "", "2", "", "")

              )

          )
      }
      //  binding.returnsForUserCard.setOnClickListener {
      //      val    intent = Intent(activity, PurchaseActivity::class.java)
      //      startActivity(intent)
      //  }

          binding.invoiceCard.setOnClickListener {
              findNavController().navigate(
                  MainFragmentDirections.actionGlobalReportsFragment()
////
              )
          }
    //  binding.ProductSelectionCard.setOnClickListener {
    //      findNavController().navigate(
    //          MainFragmentDirections.actionGlobalProductSelectionFragment()
////
    //      )
    //  }
//EditProductprice_card
      // binding.EditProductpriceCard.setOnClickListener {
      //     val    intent = Intent(activity, ChangePriceActivity::class.java)
      //     startActivity(intent)
      //    // findNavController().navigate(
      //    //     MainFragmentDirections.actionGlobalProductSelectionFragment()
//////
      //    // )
      // }

      //  invoice_card
//Accountstatement
        return binding.root
    }
}


