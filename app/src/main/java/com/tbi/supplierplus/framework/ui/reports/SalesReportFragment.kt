package com.tbi.supplierplus.framework.ui.reports

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.databinding.FragmentSalesReportBinding
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SalesReportFragment : Fragment() {
    private lateinit var binding: FragmentSalesReportBinding
    private val viewModel2: ReportsViewModel by viewModels()
    val availableItemsViewModel: AvailableItemsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSalesReportBinding.inflate(inflater)
        binding.viewModel = viewModel2
        binding.lifecycleOwner = this

        viewModel2.getselesRepor()
        viewModel2.selesReporLivedata.observe(viewLifecycleOwner) {

        }


        lifecycleScope.launch {
            availableItemsViewModel.getCloseSalesDayDatafForUser().collect {
                when (it) {

                    is State.Loading -> {}

                    is State.Success -> {
                        if (it.data.data == null) {
                        } else {


                            binding.actualAmounttxt.setText(it.data.item.actual_amount.toString())
                            binding.requerAmounttxt.setText(it.data.item.required_amount.toString())
                        }
                    }
                    is State.Error -> {
                        Log.d("jlklkl", it.messag)
                    }
                }
            }
        }

        binding.SubmitBtn.setOnClickListener {

            lifecycleScope.launch {
                availableItemsViewModel.getConfirmCompleteAccount().collect {
                    when (it) {

                        is State.Loading -> {}

                        is State.Success -> {
                            if (it == null) {
                            } else {
                                binding.msgTxt.setText(it.data.Message)
                                Toast.makeText(context, it.data.Message, Toast.LENGTH_SHORT)
                                Toast.makeText(context, it.data.Message, Toast.LENGTH_SHORT)

                            }
                        }
                        is State.Error -> {
                            Log.d("jlklkssl", it.messag)
                        }
                    }
                }
            }
        }


        return binding.root
    }

}