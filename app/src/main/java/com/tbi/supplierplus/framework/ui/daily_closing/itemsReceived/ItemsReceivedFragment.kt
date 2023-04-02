package com.tbi.supplierplus.framework.ui.daily_closing.itemsReceived

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tbi.supplierplus.databinding.FragmentItemsReceivedBinding
import com.tbi.supplierplus.framework.ui.daily_closing.DailyClosingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemsReceivedFragment : Fragment() {
    private lateinit var binding: FragmentItemsReceivedBinding

    private val viewModel: DailyClosingViewModel by  activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemsReceivedBinding.inflate(inflater)
        //  binding.viewModel = viewModel
        binding.lifecycleOwner = this
//المنتحات المباعه
        Log.d(
            "getDail","sss"
        )
       // viewModel.    getDailyClosingPurchases()
        viewModel.   getDailyClosingSummaryItems()
       viewModel.itemsDailyClosingLiveData.observe(viewLifecycleOwner) {

              val adabter=AdapterItemsReceived()
              binding.ReceivdClosingrecyclerView.adapter = adabter
              binding.ReceivdClosingrecyclerView.setHasFixedSize(true)
              adabter.submitList(it)

       //   binding.sales.setText(  it.get(0).ReturnAmount.toString()+""+
       //           "\n"+it.get(0).Item_ID.toString()+
       //           "\n"+it.get(0).Name+
       //           "\n"+it.get(0).ReturnAmount.toString()+
       //           "\n"+it.get(0).Count.toString()+
       //           "\n"+it.get(0).Amount.toString()+
       //           "\n"+it.get(0).Total.toString())
       }

//       viewModel. purchasesLiveData.observe(viewLifecycleOwner) {

//           //  val adabter=AdapterExpensesClosing()
//           //  binding.ExpensesClosingrecyclerView.adapter = adabter
//           //  binding.ExpensesClosingrecyclerView.setHasFixedSize(true)
//           //  adabter.submitList(it)
//           Log.d(
//               "getDail",it.get(0).Name+"sss"
//           )
//    //    binding.sales.setText(  it.get(0).ReturnAmount.toString()+""+
//    //            "\n"+it.get(0).Item_ID.toString()+
//    //            "\n"+it.get(0).Name+
//    //            "\n"+it.get(0).ReturnAmount.toString()+
//    //            "\n"+it.get(0).Count.toString()+
//    //            "\n"+it.get(0).Amount.toString()+
//    //            "\n"+it.get(0).Total.toString())
//    //}
     //// Inflate the layout for this fragment
     return binding.root
      //  return inflater.inflate(R.layout.fragment_items_received, container, false)
    }


}