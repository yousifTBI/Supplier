package com.tbi.supplierplus.framework.ui2.mortag3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.ItemDetailsModel
import com.tbi.supplierplus.business.pojo.AllCustomers
import com.tbi.supplierplus.databinding.ActivityBillMortag3Binding
import com.tbi.supplierplus.databinding.ActivityMortag3BillBinding
import com.tbi.supplierplus.databinding.ActivityMortg3Binding
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.reports.*
import com.tbi.supplierplus.framework.ui.sales.addCompany.Data
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class Mortag3BillActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMortag3BillBinding
    lateinit var viewModel: ReportsViewModel
    lateinit var availableItemsViewModel: AvailableItemsViewModel
    lateinit var allCustomers: AllCustomers
    lateinit var message: String
    var listItems = ArrayList<ItemDetailsModel>()
    var recordId =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mortag3_bill);
        viewModel = ViewModelProvider(this).get(ReportsViewModel::class.java)
        availableItemsViewModel = ViewModelProvider(this).get(AvailableItemsViewModel::class.java)

        viewModel. getCustomers(SharedPreferencesCom.getInstance().gerSharedUser_ID())
        message = intent.getStringExtra("Customer_ID").toString()
        val message2 = intent.getStringExtra("Unpaid_deferred")
        val CompanyName = intent.getStringExtra("CompanyName")


        viewModel.getCustomerStatement( message, recordId)
//        viewModel.customers.observe(this) {
//            if (it ==null){
//
//            }
//            else {
//                val adapter = CustomerSelectionAdapter(this, it)
////                binding.spinner.adapter = adapter
//            }
//        }

        //get all items name from api
        lifecycleScope.launch() {
            availableItemsViewModel.getAllItemsFromTable().collect {

                when (it) {
                    is State.Loading ->{

                    }
                    is State.Success -> {
                        if (it.data ==null){
                            Log.d("VisitBranchWithoutPay",it.data.Message)
                        }
                        else{

                            listItems= it.data.data as ArrayList<ItemDetailsModel>
                        }

                    }
                    is State.Error -> {
                        Log.d("VisitBranchWithoutPay",it.messag)
                    }
                }
            }

        }


        binding.itemCard.setOnClickListener {

            ItemsPopUp()
        }

        val  adapter1 =BillMortag3Adapter(OnDebitBillClickListeners1 {
            var BillNo = it.BillNo
            val intent = Intent(this, BillMortag3Activity::class.java)
                .apply {
                    //                   Log.d("Item_IDD", ItemID.toString())
                    putExtra("BillNo", BillNo.toString())
                    //      putExtra("itemname", itemname)

                    putExtra("Customer_ID", message)
                    putExtra("Unpaid_deferred", message2)
                    putExtra("CompanyName", CompanyName)

                    //   putExtra("ItemID", it.ItemID.toString())

                }
            startActivity(intent)
        })


//       val adapter1 = StatementAdapter(OnBillClickListener { statement ->
//
//
//            GlobalScope.launch(Dispatchers.Default){
//
//
//                withContext(Dispatchers.Default){
//
//
//                    viewModel.  getBillDetails(statement.BillNo.toString(),allCustomers.Branch_ID.toString())
//                }
//            }
//            viewModel.setSelectedStatement(statement)
//
//            findNavController().navigate(CustomerStatementFragmentDirections.actionCustomerStatementFragmentToCustomerStatementDetailsFragment())
//
//
//        })
        viewModel.statement.observe(this) {

            binding.recyclerView.adapter = adapter1
            adapter1.submitList(it)
        }



    }


    fun ItemsPopUp() {
        SimpleSearchDialogCompat(this, "ادخل اسم الشركه  " + "\n", "search", null,
            listItems, SearchResultListener { baseSearchDialogCompat, item, pos ->


                var itemName =""
                itemName = item.getTitle()
                recordId =item.Record_ID
                viewModel.getCustomerStatement(  message,recordId)
                Log.d("VisitBranchWithoutPay", recordId.toString())
                binding.textView21.setText(itemName)
                baseSearchDialogCompat.dismiss()
            }).show()
    }

}