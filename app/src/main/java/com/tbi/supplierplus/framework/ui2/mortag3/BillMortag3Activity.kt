package com.tbi.supplierplus.framework.ui2.mortag3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.tbi.supplierplus.DialogBill
import com.tbi.supplierplus.PaymentActivity
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.Items
import com.tbi.supplierplus.business.pojo.billModels.SaleingBill
import com.tbi.supplierplus.databinding.ActivityBill2Binding
import com.tbi.supplierplus.databinding.ActivityBillMortag3Binding
import com.tbi.supplierplus.framework.datasource.requests.State
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.closingLast.Mortg3atActivity
import com.tbi.supplierplus.framework.ui.collect_debit.CollectDebitViewModel
import com.tbi.supplierplus.framework.ui.reports.ReportsViewModel
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import com.tbi.supplierplus.framework.ui.sales.add_customer.AddCustomerViewModel
import com.tbi.supplierplus.framework.ui.sales.customer_profile.BillAdapter
import com.tbi.supplierplus.framework.ui.sales.customer_profile.ReturnsAdapter
import com.tbi.supplierplus.framework.ui.sales.customer_profile.SaleingBillAdpter
import com.tbi.supplierplus.framework.ui.scanner.capture
import dagger.hilt.android.AndroidEntryPoint
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

@AndroidEntryPoint
class BillMortag3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityBillMortag3Binding
    lateinit var viewModel: ReportsViewModel
    var list = ArrayList<Items>()
   // var adapte = SaleingBillAdpter()
    lateinit var adapte: SaleingBillAdpter
    var listBill = ArrayList<SaleingBill>()
    private val barcode = StringBuffer()
    lateinit var message: String

    private lateinit var viewModel2: CollectDebitViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_mortag3)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bill_mortag3);
        viewModel = ViewModelProvider(this).get(ReportsViewModel::class.java)
        viewModel2 = ViewModelProvider(this).get(CollectDebitViewModel::class.java)

        message = intent.getStringExtra("Customer_ID").toString()
        val message2 = intent.getStringExtra("Unpaid_deferred")
        val CompanyName = intent.getStringExtra("CompanyName")
        val BillNo = intent.getStringExtra("BillNo")


        val adapter1 = Mortag3Adapter(OnDebitBillClickListeners {
            //حط هنا
            var size = it.size
            var itemname = it.Items
            var NumberOfUnits =it.size.toDouble()* it.NumberOfUnits.toDouble()
            var TotalPrice = it.UnitPrice
            var ItemID = it.ItemID
            Log.d("Item_IDD", NumberOfUnits.toString())
            val intent = Intent(this, EdirMortag3Activity::class.java)
                .apply {
                    //                   Log.d("Item_IDD", ItemID.toString())
                    putExtra("ITEM_ID", size.toString())
                    putExtra("itemname", itemname)
                    putExtra("NumberOfUnits", NumberOfUnits.toString())
                    putExtra("Customer_ID", message)
                    putExtra("Unpaid_deferred", message2)
                    putExtra("CompanyName", CompanyName)
                    putExtra("TotalPrice", TotalPrice.toString())
                    putExtra("ItemID", it.ItemID.toString())
                    putExtra("BillNo", BillNo.toString())

                }
            startActivity(intent)
        })
        viewModel.Sales.observe(this){
            // it?.get(0)?.Items?.let { it1 -> Log.d("sjdhjhs", it1) }
          //  Log.d("SubmittttttID",it.)
            if (it==null){}
            else{

            binding.billRecycler.adapter = adapter1
            adapter1.submitList(it)


 //               Log.d("SubmittttttID",it.get(0).Items)
//                val intent = Intent(activity, SellOrMortg3Activity::class.java)
//                    .apply {
//                        putExtra("Customer_ID", Customer_ID)
//                        putExtra("Unpaid_deferred", Unpaid_deferred)
//                        putExtra("CompanyName", CompanyName)
//
//                    }
//                startActivity(intent)


        }
        }


            GlobalScope.launch(Dispatchers.Default){



                  //  Log.d("SubmittttttID",message+"mmmm")
                  //  Log.d("BillNo",BillNo.toString()+"bill")

                viewModel.getBillDetails(BillNo.toString(),message)


            }






    }
}