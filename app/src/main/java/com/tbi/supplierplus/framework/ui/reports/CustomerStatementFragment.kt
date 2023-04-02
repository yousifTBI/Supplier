package com.tbi.supplierplus.framework.ui.reports

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.business.pojo.AllCustomers
import com.tbi.supplierplus.business.pojo.expenses.ExpensesSearch
import com.tbi.supplierplus.databinding.FragmentCustomerStatementBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CustomerStatementFragment : Fragment() {
    private lateinit var binding: FragmentCustomerStatementBinding
    private val viewModel: ReportsViewModel by activityViewModels()
    lateinit var x: AllCustomers
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCustomerStatementBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel. getCustomers("2")

        viewModel.customers.observe(viewLifecycleOwner) {
            val adapter = CustomerSelectionAdapter(context!!, it)
            binding.spinner.adapter = adapter
        }


        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {

             //   var x: AllCustomers
                x=  parentView?.getItemAtPosition( position) as AllCustomers
                viewModel.getCustomerStatement(  x.Customer_ID.toString())
   // viewModel.setCustomerID(viewModel.customers.value!![position].Customer_ID.toString())
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }
       // viewModel.Returns.observe(viewLifecycleOwner){
//
       // }
       // viewModel.getBillDetails(x.Customer_ID.toString(),"5672")

      //  viewModel.Sales.observe(viewLifecycleOwner){}
//
      //  viewModel.Returns.observe(viewLifecycleOwner){}

        val adapter1 = StatementAdapter(OnBillClickListener { statement ->
          //  Log.d("List<Returns>",x.Customer_ID.toString()+"+"+statement.BillNo.toString())

            GlobalScope.launch(Dispatchers.Default){


                withContext(Dispatchers.Default){


                    viewModel.  getBillDetails(x.Customer_ID.toString(),statement.BillNo.toString())
                }
            }
            viewModel.setSelectedStatement(statement)
            //customerStatementDetailsFragment
            //CustomerStatementFragment
            findNavController().navigate(CustomerStatementFragmentDirections.actionCustomerStatementFragmentToCustomerStatementDetailsFragment())
        })
        viewModel.statement.observe(viewLifecycleOwner) {

            binding.recyclerView.adapter = adapter1
            adapter1.submitList(it)
        }




        return binding.root
    }

}