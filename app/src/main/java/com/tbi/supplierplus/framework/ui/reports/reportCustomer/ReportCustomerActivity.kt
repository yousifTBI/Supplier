package com.tbi.supplierplus.framework.ui.reports.reportCustomer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.ActivityBill2Binding
import com.tbi.supplierplus.databinding.ActivityReportCustomerBinding
import com.tbi.supplierplus.framework.ui.reports.ReportsViewModel
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class ReportCustomerActivity : AppCompatActivity() {
    lateinit var viewModel: ReportsViewModel
    private lateinit var binding: ActivityReportCustomerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_report_customer)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_report_customer);
        viewModel = ViewModelProvider(this).get(ReportsViewModel::class.java)

        val Customer_ID     = intent.getStringExtra("Customer_ID").toString()

        val CompanyName = intent.getStringExtra("CompanyName")
        viewModel.reportSpecificCustomer( Customer_ID )
        viewModel. reporLivedata.observe(this){

            binding.countv.setText(it.TotalPil .toString())
            binding.countOfProduct.setText(it.TotalItemsQount .toString())
            binding.Discount.setText(it.TotalPilDiscount.toString())
            binding.TotalPillBeforDiscount.setText(it.TotalPillBeforDiscount.toString())
            binding.TotalPilDiscount.setText(it.TotalPilDiscount.toString())
            binding.ReturnAmount.setText(it.ReturnAmount.toString())
            binding.TotalPilAfterDiscount.setText(it.TotalPilAfterDiscount.toString())
            binding.TotalPilCach.setText(it.TotalPilCach.toString())
            binding.TotalPilAgel  .setText(it.TotalPilAgel  .toString())
            binding.NumOfBranches  .setText(it.NumOfBranches  .toString())
            binding.TotalPilNet  .setText(it.TotalPilNet  .toString())
        }


    }
}