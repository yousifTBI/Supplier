package com.tbi.supplierplus.framework.ui.closingLast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.utils.toJson
import com.tbi.supplierplus.databinding.ActivityBill2Binding
import com.tbi.supplierplus.databinding.ActivityClosingBinding
import com.tbi.supplierplus.framework.ui.daily_closing.DailyClosingAdapter
import com.tbi.supplierplus.framework.ui.daily_closing.DailyClosingViewModel
import com.tbi.supplierplus.framework.ui.daily_closing.OnDebitClickListeners
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import kotlinx.coroutines.launch
import com.tbi.supplierplus.framework.ui.login.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ClosingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClosingBinding
    lateinit var viewModel: SalesViewModel
    lateinit var DailyClosingviewModel: DailyClosingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_closing)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_closing);
        DailyClosingviewModel = ViewModelProvider(this).get(DailyClosingViewModel::class.java)

        supportActionBar!!.hide()

        val  adapter= DailyClosingAdapter(OnDebitClickListeners{
            //حط هنا
        })


        val  adapter2= DailyClosingAdapter(OnDebitClickListeners{
            //حط هنا
        })
        lifecycleScope.launch(){
            DailyClosingviewModel.   getCurrentMortg3atofTheUserAPI().collect {
                when (it) {
                    is State.Loading -> binding.spinKit.isVisible = true
                    is State.Success -> {
                        binding.spinKit.isVisible = false
                        adapter.submitList(it.data.data)
                        binding.ExpensesClosingrecyclerView.adapter = adapter
                    }
                    is State.Error -> {
                        binding.spinKit.isVisible = false

                        Log.d("getCurrentMortg3aterAPI",it.messag.toString())

                    }
                }
            }
        }

        binding.sales.setText("مردودات")

        lifecycleScope.launch(){
            DailyClosingviewModel.   GetPendingRequestsAPI().collect {
                when (it) {
                    is State.Loading -> binding.spinKit.isVisible = true
                    is State.Success -> {
                        binding.spinKit.isVisible = false
                        binding.purchaseClosingrecyclerView.adapter = adapter2
                        adapter2.submitList(it.data.data)

                        Log.d("getCurrentMortg3aterAPI1",it.data.data.toJson())
                    }
                    is State.Error -> {
                        binding.spinKit.isVisible = false

                        Log.d("getCurrentMortg3aterAPI",it.messag.toString())

                    }
                }
            }
        }


        binding.  button5.setOnClickListener {
            lifecycleScope.launch(){
                DailyClosingviewModel.   SubmitReturnMardodatVM().collect {
                    when (it) {
                        is State.Loading -> binding.spinKit.isVisible = true
                        is State.Success -> {
                            binding.spinKit.isVisible = false

                            DailyClosingviewModel.   SubmitReturnMardodatVM().collect {

                            }
                        }
                        is State.Error -> {
                            binding.spinKit.isVisible = false
                            //Toast.makeText(context, it.messag, Toast.LENGTH_SHORT).show()


                        }
                    }
                }
            }

        }
    }
}