package com.tbi.supplierplus.framework.ui.reports

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.business.pojo.AllCustomers
import com.tbi.supplierplus.databinding.FragmentAllInvoicesBinding
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
    private var startDate = "X"
    private var endDate = "X"

    lateinit var allCustomers: AllCustomers
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllInvoicesBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter1 = AllBillsToday(OnBillClickListener { statement ->


            GlobalScope.launch(Dispatchers.Default){


                withContext(Dispatchers.Default){


                    viewModel.getBillDetails(statement.BillNo.toString(),"0")
                }
            }
            viewModel.setSelectedStatement(statement)

//              findNavController().navigate(CustomerStatementFragmentDirections.actionCustomerStatementFragmentToCustomerStatementDetailsFragment(""))
            GlobalScope.launch(Dispatchers.Default){


                withContext(Dispatchers.Default){
                    //  Log.d("CompanyName",allCustomers.CompanyName.toString() +"")
                  //  Log.d("CompanyName",allCustomers.item.toString() +"")

                    viewModel.getBillDetails(statement.BillNo.toString(),"0")
                }
            }
          //  viewModel.setSelectedStatement(statement)
//            stringArg =allCustomers.item
            // Log.d("stringArg",stringArg.toString())
            findNavController().navigate(AllInvoicesFragmentDirections.actionAllInvoicesFragmentToCustomerStatementDetailsFragment(""))
        })

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
                    availableItemsViewModel.getUserBillsByDay(startDate ,endDate).collect {

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

            }
        }


        lifecycleScope.launch() {
            availableItemsViewModel.getUserBillsByDay(startDate ,endDate).collect {

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