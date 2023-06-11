package com.tbi.supplierplus.framework.ui.reports

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.databinding.FragmentSalesReportBinding
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
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
        binding.spinKit.isVisible = false

        binding.actualAmounttxt.isVisible =true
        binding.requerAmounttxt.isVisible =true
        binding.requarAmounttitle.isVisible =true
        binding.actualAmounttitle.isVisible =true

        viewModel2.getselesRepor()
        viewModel2.selesReporLivedata.observe(viewLifecycleOwner) {

        }
      //  Log.d("gggggg", SharedPreferencesCom.getInstance().gerSharedUser_ID())


        lifecycleScope.launch {
            availableItemsViewModel.getCloseSalesDayDatafForUser().collect {
                when (it) {

                    is State.Loading -> {}

                    is State.Success -> {



                        if (it.data.item ==null){
                            binding.actualAmounttxt.isVisible =false
                            binding.requerAmounttxt.isVisible =false
                            binding.requarAmounttitle.isVisible =false
                            binding.actualAmounttitle.isVisible =false
                        }
                        else {
                                binding.actualAmounttxt.setText(it.data.item.actual_amount.toString())
                                binding.requerAmounttxt.setText(it.data.item.required_amount.toString())

                        }
                    }
                    is State.Error -> {
     //                   Log.d("actualAmounttxt","actualAmounttxtError")
                    }
                }
            }
        }

        binding.SubmitBtn.setOnClickListener {

            lifecycleScope.launch {
                availableItemsViewModel.getConfirmCompleteAccount().collect {
                    when (it) {

                        is State.Loading -> { binding.spinKit.isVisible = true}

                        is State.Success -> {

                            binding.SubmitBtn.setText("تم التاكيد")
                            availableItemsViewModel.getCloseSalesDayDatafForUser().collect {}

                            viewModel2.getselesRepor()
                            if (it == null) {
                            } else {
                                binding.spinKit.isVisible = false
                             //   binding.msgTxt.setText(it.data.Message)
                                Toast.makeText(context, it.data.Message, Toast.LENGTH_SHORT)
                                Toast.makeText(context, it.data.Message, Toast.LENGTH_SHORT)

                            }
                        }
                        is State.Error -> {
                            binding.spinKit.isVisible = false
                            Toast.makeText(context, it.messag, Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }


        return binding.root
    }

}