package com.tbi.supplierplus.framework.ui.dateReport

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.databinding.FragmentDateReportBinding
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.reports.DatePickerFragment
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DateReportFragment : Fragment() {

    private lateinit var binding:FragmentDateReportBinding
    val availableItemsViewModel: AvailableItemsViewModel by viewModels()
    private var startDate = ""
    private var endDate = ""
    private val ComID = "6"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDateReportBinding.inflate(inflater)


        binding.startDateCard.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            val supportFragmentManager = requireActivity().supportFragmentManager

            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    val date = bundle.getString("SELECTED_DATE")

                    startDate = date.toString()
                    startDate = startDate.replace("-", "/")
                    //Log.d("REQUEST_KEY", startDate.replace("-","/"))
                    Log.d("REQUEST_KEY", startDate + " " + endDate)
                    binding.startDateTV.text = startDate
                }
            }
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }


        binding.endDateCard.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            val supportFragmentManager = requireActivity().supportFragmentManager

            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {

                    val date = bundle.getString("SELECTED_DATE")
                    endDate = date.toString()
                    endDate = endDate.replace("-", "/")
                    Log.d("REQUEST_KEY", endDate)
                    binding.endDateTV.text = endDate
                }
            }
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }



        binding.reportbtn.setOnClickListener {
            if (checkDate()){

                Log.d("REQUEST_KEY", endDate)
                lifecycleScope.launch() {
                    availableItemsViewModel.GetAllDayDataForDistributors(startDate ,endDate).collect {

                        when (it) {
                            is State.Loading ->{

                            }
                            is State.Success -> {
                                if (it.data.item ==null){
                                    Log.d("VisitBranchWithoutPay",it.data.item.SalesName)
                                    Toast.makeText(requireContext(),"لا يوجد تقارير لتلك الفترة",Toast.LENGTH_SHORT).show()
                                }
                                else{

                                    Log.d("VisitBranchWithoutPay",it.data.item.SalesName)
                                   binding.actualAmounttxt.setText(it.data.item.TotalPil.toString())
                                   binding.requerAmounttxt.setText(it.data.item.CashShortFall.toString())
                                   binding.paperPayId.setText(it.data.item.CollectionPaperCountShortFall.toString())
                                   binding.paperPayIdAmount.setText(it.data.item.CollectionPaperAmountShortFall.toString())
                                   binding.ActualDeferredMoneyPaperAmount.setText(it.data.item.MoneyPaperAmountShortFall.toString())
                                   binding.ActualDeferredMoneyPaperCount.setText(it.data.item.MoneyPaperCountShortFall.toString())
                                   binding.paymentPaperCountRequired.setText(it.data.item.MoneyReceiptPapers_count.toString())
                                   binding.paymentPaperAmount.setText(it.data.item.MoneyReceiptPapers_Amount.toString())
                                   binding.colectionPaperCountRequired.setText(it.data.item.DeferredMoneyPaper_count.toString())
                                   binding.colectionPaperAmount.setText(it.data.item.DeferredMoneyPaper_Amount.toString())
                                   binding.colection.setText(it.data.item.Collection.toString())

                                }

                            }
                            is State.Error -> {
                                Log.d("VisitBranchWithoutPay",it.messag)
                            }
                        }
                    }

                }
            }
        }

//        lifecycleScope.launch() {
//            availableItemsViewModel.GetAllDayDataForDistributors(startDate ,endDate).collect {
//
//                when (it) {
//                    is State.Loading ->{
//
//                    }
//                    is State.Success -> {
//                        Log.d("VisitBranchWithoutPay",it.data.Message)
//
//                    }
//                    is State.Error -> {
//                        Log.d("VisitBranchWithoutPay","Error")
//                    }
//                }
//            }
//
//        }



        return binding.root
    }

    private fun checkDate(): Boolean {
        if (startDate.isEmpty() || startDate == null || startDate === "") {

            Toast.makeText(context, "ادخل تاريخ البداية بطريقة صحيحة", Toast.LENGTH_SHORT).show()
            return false
        }
        if (endDate.isEmpty() || endDate == null || endDate === "") {

            Toast.makeText(context, "ادخل تاريخ النهاية بطريقة صحيحة", Toast.LENGTH_SHORT).show()
            return false
        }

        return startDate.isNotEmpty()

    }
    }
