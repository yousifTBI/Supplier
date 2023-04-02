package com.tbi.supplierplus.framework.ui.daily_closing.expensesAdapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tbi.supplierplus.databinding.FragmentExpensesClosingBinding
import com.tbi.supplierplus.framework.ui.daily_closing.DailyClosingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpensesClosingFragment : Fragment() {
    private lateinit var binding: FragmentExpensesClosingBinding

    private val viewModel: DailyClosingViewModel by  activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpensesClosingBinding.inflate(inflater)
      //  binding.viewModel = viewModel
        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
       /// val adabter=AdapterExpensesClosing()
        viewModel.    getData()

        viewModel.closingExpensesLiveData.observe(viewLifecycleOwner) {
             val adabter=AdapterExpensesClosing()
             binding.ExpensesClosingrecyclerView.adapter = adabter
             binding.ExpensesClosingrecyclerView.setHasFixedSize(true)
             adabter.submitList(it)
          //  binding.expen.setText( "مصروفات"+it.get(0).ReturnAmount.toString()+""+
          //          "\n"+it.get(0).Item_ID.toString()+
          //          "\n"+it.get(0).Name+
          //          "\n"+it.get(0).ReturnAmount.toString()+
          //          "\n"+it.get(0).Count.toString()+
          //          "\n"+it.get(0).Amount.toString()+
          //          "\n"+it.get(0).Total.toString())
//
        }
        return binding.root
    }


}