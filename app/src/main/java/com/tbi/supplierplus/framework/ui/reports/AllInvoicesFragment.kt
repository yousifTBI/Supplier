package com.tbi.supplierplus.framework.ui.reports

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.VisitBranchWithoutPayModel
import com.tbi.supplierplus.business.pojo.AllCustomers
import com.tbi.supplierplus.databinding.FragmentAllInvoicesBinding
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@AndroidEntryPoint
class AllInvoicesFragment : Fragment() {

    private lateinit var binding: FragmentAllInvoicesBinding
    private val viewModel: ReportsViewModel by activityViewModels()
    private val availableItemsViewModel: AvailableItemsViewModel by viewModels()

    lateinit var allCustomers: AllCustomers
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllInvoicesBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

    //    viewModel. getCustomers(SharedPreferencesCom.getInstance().gerSharedUser_ID())

     //  viewModel.customers.observe(viewLifecycleOwner) {
     //      if (it ==null){

     //      }
     //      else {
     //          val adapter = CustomerSelectionAdapter(context!!, it)
     //          binding.spinner.adapter = adapter
     //      }
     //  }


    //  binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
    //      override fun onItemSelected(
    //          parentView: AdapterView<*>?,
    //          selectedItemView: View?,
    //          position: Int,
    //          id: Long
    //      ) {

    //          allCustomers=  parentView?.getItemAtPosition( position) as AllCustomers
    //          // Log.d("onItemSelectedListener",allCustomers.toJson())
    //          viewModel.getCustomerStatement(  allCustomers.Branch_ID.toString())

    //          // viewModel.setCustomerID(viewModel.customers.value!![position].Customer_ID.toString())
    //      }

    //      override fun onNothingSelected(parentView: AdapterView<*>?) {
    //      }
    //  }

        val adapter1 = StatementAdapter(OnBillClickListener { statement ->


            GlobalScope.launch(Dispatchers.Default){


                withContext(Dispatchers.Default){


                    viewModel.  getBillDetails(statement.BillNo.toString(),"0")
                }
            }
            viewModel.setSelectedStatement(statement)

          //  findNavController().navigate(CustomerStatementFragmentDirections.actionCustomerStatementFragmentToCustomerStatementDetailsFragment())
        })

      // viewModel.statement.observe(viewLifecycleOwner) {

      //     binding.recyclerView.adapter = adapter1
      //     adapter1.submitList(it)

      // }

        lifecycleScope.launch() {
            availableItemsViewModel.getUserBillsByDay().collect {

                when (it) {
                    is State.Loading ->{

                    }
                    is State.Success -> {
                        binding.recyclerView.adapter = adapter1
                        adapter1.submitList(it.data.data)
                        Log.d("VisitBranchWithoutPay", "Success")
                        // Log.d("VisitBranchWithoutPay", message+"BranchID")
                        // Log.d("VisitBranchWithoutPay", loc!!.longitude.toString()+"longitude")
                        // Log.d("VisitBranchWithoutPay",  loc!!.latitude.toString()+"latitude")
                        // Log.d("VisitBranchWithoutPay",  SharedPreferencesCom.getInstance().gerSharedUser_ID().toString()+"User_ID")
                        // it.data.message


                    }
                    is State.Error -> {
                        Log.d("VisitBranchWithoutPay","Error")
                    }
                }
            }

        }




        return binding.root
    }

}