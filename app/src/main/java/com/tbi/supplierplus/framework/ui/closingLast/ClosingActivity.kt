package com.tbi.supplierplus.framework.ui.closingLast

import android.content.Intent
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
import com.tbi.supplierplus.framework.ui.daily_closing.ItemsBIllActivity
import com.tbi.supplierplus.framework.ui.daily_closing.OnDebitClickListeners
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import kotlinx.coroutines.launch
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ClosingActivity : AppCompatActivity() {
    lateinit var adapter2: DailyClosingAdapter
    lateinit var  adapter: DailyClosingAdapter
    private lateinit var binding: ActivityClosingBinding
    lateinit var viewModel: SalesViewModel
    lateinit var DailyClosingviewModel: DailyClosingViewModel
    lateinit var availableItemsViewModel: AvailableItemsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_closing)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_closing);
        DailyClosingviewModel = ViewModelProvider(this).get(DailyClosingViewModel::class.java)
        availableItemsViewModel = ViewModelProvider(this).get(AvailableItemsViewModel::class.java)

        supportActionBar!!.hide()

          adapter= DailyClosingAdapter(OnDebitClickListeners{
            //حط هنا
            var ItemID = it.RecordID
            var itemname = it.Itemname
            val intent = Intent(this, Mortg3atActivity::class.java)
                .apply {
 //                   Log.d("Item_IDD", ItemID.toString())
                    putExtra("ITEM_ID", ItemID.toString())
                    putExtra("itemname", itemname)

                }
            startActivity(intent)


        })


          adapter2= DailyClosingAdapter(OnDebitClickListeners{
            //حط هنا
            var ItemID = it.RecordID
            var itemname = it.Itemname
            val intent = Intent(this, Mortg3Activity::class.java)
                .apply {
//                    Log.d("Item_IDD", ItemID.toString())
                    putExtra("ITEM_ID", ItemID.toString())
                    putExtra("itemname", itemname)

                }
            startActivity(intent)


        })
        lifecycleScope.launch(){
            availableItemsViewModel. getPendingMortaga3at().collect {
                when (it) {
                    is State.Loading -> binding.spinKit.isVisible = true
                    is State.Success -> {
                        binding.spinKit.isVisible = false
                        adapter.submitList(it.data.data)
                        binding.ExpensesClosingrecyclerView.adapter = adapter
                    }
                    is State.Error -> {
                        binding.spinKit.isVisible = false

 //                       Log.d("getCurrentMortg3aterAPI",it.messag.toString())

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

 //                       Log.d("getCurrentMortg3aterAPI1",it.data.data.toJson())
                    }
                    is State.Error -> {
                        binding.spinKit.isVisible = false

 //                       Log.d("getCurrentMortg3aterAPI",it.messag.toString())

                    }
                }
            }
        }


        binding.  button5.setOnClickListener {



                lifecycleScope.launch {
                    availableItemsViewModel.getConfirmSalesrRequest().collect {
                        when (it) {

                            is State.Loading -> {
                                binding.spinKit.isVisible =true
                            }

                            is State.Success -> {

                                DailyClosingviewModel.   GetPendingRequestsAPI().collect {}
                                availableItemsViewModel. getPendingMortaga3at().collect {}
                                binding.spinKit.isVisible =false
                                //    Log.d("msgtxt","it.data.message")
                                //   binding.msgTxt.setText(it.data.Message)
                                Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()
                                Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()
                                Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()

                            }
                            is State.Error -> {
                                binding.spinKit.isVisible = false
//                                Toast.makeText(context, it.messag, Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }

//                DailyClosingviewModel.   SubmitReturnMardodatVM().collect {
//                    when (it) {
//                        is State.Loading -> binding.spinKit.isVisible = true
//                        is State.Success -> {
//                            binding.spinKit.isVisible = false
//                            Log.d("getCurrentMortsg3aterAPI",it.data.message)
//                            Log.d("getCurrentMortsg3aterAPI",it.data.State.toString())
//
//                        }
//                        is State.Error -> {
//                            binding.spinKit.isVisible = false
//                            //Toast.makeText(context, it.messag, Toast.LENGTH_SHORT).show()
//
//
//                        }
//                    }
//                }


        }
    }

    override fun onResume() {
        super.onResume()

        APIsCalls()

    }


    fun APIsCalls()
    {
        lifecycleScope.launch(){
            DailyClosingviewModel.   GetPendingRequestsAPI().collect {
                when (it) {
                    is State.Loading -> binding.spinKit.isVisible = true
                    is State.Success -> {
                        binding.spinKit.isVisible = false
                        binding.purchaseClosingrecyclerView.adapter = adapter2
                        adapter2.submitList(it.data.data)

 //                       Log.d("getCurrentMortg3aterAPI1",it.data.data.toJson())
                    }
                    is State.Error -> {
                        binding.spinKit.isVisible = false

  //                      Log.d("getCurrentMortg3aterAPI",it.messag.toString())

                    }
                }
            }
        }



        lifecycleScope.launch(){
            availableItemsViewModel. getPendingMortaga3at().collect {
                when (it) {
                    is State.Loading -> binding.spinKit.isVisible = true
                    is State.Success -> {
                        binding.spinKit.isVisible = false
                        adapter.submitList(it.data.data)
                        binding.ExpensesClosingrecyclerView.adapter = adapter
                    }
                    is State.Error -> {
                        binding.spinKit.isVisible = false

//                        Log.d("getCurrentMortg3aterAPI",it.messag.toString())

                    }
                }
            }
        }
    }


}