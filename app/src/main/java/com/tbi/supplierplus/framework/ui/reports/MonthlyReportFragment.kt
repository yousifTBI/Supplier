package com.tbi.supplierplus.framework.ui.reports

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.FragmentDateReportBinding
import com.tbi.supplierplus.databinding.FragmentMonthlyReportBinding
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MonthlyReportFragment : Fragment() {
    private lateinit var binding: FragmentMonthlyReportBinding
    val availableItemsViewModel: AvailableItemsViewModel by viewModels()
    private var startDate = ""
    private var endDate = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentMonthlyReportBinding.inflate(inflater)

        lifecycleScope.launch() {
            availableItemsViewModel.GetAllDayDataForDistributors("*" ,"*").collect {

                when (it) {
                    is State.Loading ->{

                    }
                    is State.Success -> {
                        if (it.data.item ==null){
                            Toast.makeText(requireContext(),"لا يوجد تقارير لتلك الفترة", Toast.LENGTH_SHORT).show()
                        }
                        else{

                            Log.d("VisitBranchWithoutPay",it.data.item.SalesName)
                            binding.colection.setText(it.data.item.TotalPilAfterDiscount.toString())

                        }

                    }
                    is State.Error -> {
                        Log.d("VisitBranchWithoutPay",it.messag)
                    }
                }
            }

        }

        return binding.root

    }
}