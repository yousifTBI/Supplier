package com.tbi.supplierplus.framework.ui.daily_closing.summaryReport

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.FragmentSummeryReportKBinding
import com.tbi.supplierplus.databinding.FragmentSupplierReturnsBinding
import com.tbi.supplierplus.framework.ui.daily_closing.DailyClosingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class SummeryReportKFragment : Fragment() {
    private lateinit var binding: FragmentSummeryReportKBinding

    private val viewModel: DailyClosingViewModel by  activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummeryReportKBinding.inflate(inflater)
        //  binding.viewModel = viewModel
        binding.lifecycleOwner = this
        // Inflate the layout for this fragment

     // viewModel.getDailyClosingPurchases()
     // viewModel.getData()
     // viewModel.getDailyClosingSummarySupplier()
     // viewModel.getDailyClosingSummaryItems()
        viewModel.summarySupplierLiveData.observe(viewLifecycleOwner) {
            var sum=0.0
            for (Supplier in it){
                sum=sum+ Supplier.Total

            }
           // binding.returns.setText(sum.toString()+"     اجمالى المرتجعات ")

        }
        viewModel.purchasesLiveData.observe(viewLifecycleOwner) {
            var sum=0.0
            for (Supplier in it){
                sum=sum+ Supplier.Total

            }
          // binding.supllay
          //     .setText(sum.toString()+"     اجمالى المشتريات ")

        }
        var sumReturn=0.0
        var sumSale=0.0
        viewModel.itemsDailyClosingLiveData.observe(viewLifecycleOwner) {

            for (Supplier in it){
                sumReturn=sumReturn+ Supplier.ReturnAmount
                sumSale=sumSale+ Supplier.Amount

            }
            binding.sal.setText( "   اجمالى المبيعات:    "
           //         +sumSale.toString()
           // +"\n"
           //         +"\n"
           //         +"  اجمالى المرتجعات:    "+sumReturn
//
            )

            binding.textView83.setText(sumReturn.toString())

            binding.textView82.setText(sumSale.toString())
            var total=0.0
            for (sumReturn in it){
                total=total+ sumReturn.
                Amount

            }

        }


     //   }
        var sum1 = 0.0
        viewModel.closingExpensesLiveData.observe(viewLifecycleOwner) {
        var sum1 = 0.0
        for (Supplier in it) {
            sum1 = sum1 + Supplier.Amount

        }
        binding.expenses.setText("  اجمالى المصروفات:    ")
            binding.textView84.setText(sum1.toString())

            var total=0.0
            var totalx=0.0
            total=sumReturn+sum1
            totalx=sumSale-total
            binding.textView73.setText(totalx.toString())

        }
//cashEditText
        binding.cashEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
              //  Toast.makeText(applicationContext, p0.toString(), Toast.LENGTH_SHORT).show()
                if (p0?.isEmpty() == true) {
                    binding.cashEditText.setText("0.0")
                } else {

                  var m2waya=  binding.textView73.text.toString().toDouble() *  binding.cashEditText.text.toString().toDouble()
                  var m2waya2=  m2waya /100
                    binding.deferredEditText.setText(m2waya2.toString())
                }


            }

        })
      //  var total=0.0
      //  var totalx=0.0
      //  total=sumReturn+sum1
      //  totalx=sumSale-total
      //  binding.textView73.setText(totalx.toString())
        return binding.root
    }


}