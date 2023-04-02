package com.tbi.supplierplus.framework.ui.daily_closing.purchasesclosings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tbi.supplierplus.databinding.FragmentPurchasesClosingBinding
import com.tbi.supplierplus.framework.ui.daily_closing.DailyClosingViewModel
import com.tbi.supplierplus.framework.ui.daily_closing.itemsReceived.AdapterItemsReceived
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PurchasesClosingFragment : Fragment() {

    private lateinit var binding: FragmentPurchasesClosingBinding

    private val viewModel: DailyClosingViewModel by  activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPurchasesClosingBinding.inflate(inflater)
        //  binding.viewModel = viewModel
        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
      viewModel.   getDailyClosingPurchases()
        viewModel.purchasesLiveData.observe(viewLifecycleOwner) {
           // purchaseClosingrecyclerView
            val adabter= AdapterPurchasesClosing()
            binding.purchaseClosingrecyclerView.adapter = adabter
            binding.purchaseClosingrecyclerView.setHasFixedSize(true)
            adabter.submitList(it)

            binding. Purchases.setText(

             //   it.get(0).ReturnAmount.toString()+
                        "مشتريات"
                                //+
                  //  "\n"+it.get(0).Item_ID.toString()+
                  //  "\n"+it.get(0).Name+
                  //  "\n"+it.get(0).ReturnAmount.toString()+
                  //  "\n"+it.get(0).Count.toString()+
                  //  "\n"+it.get(0).Amount.toString()+
                  //  "\n"+it.get(0).Total.toString()

            )
                //.text(it.get(0).ReturnAmount.toString())
        }
        return binding.root
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_purchases_closing, container, false)
    }


}