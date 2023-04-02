package com.tbi.supplierplus.framework.ui.expenses

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tbi.supplierplus.business.pojo.expenses.AddExpenses
import com.tbi.supplierplus.business.pojo.expenses.ExpensesSearch

import com.tbi.supplierplus.databinding.FragmentExpensesBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ExpensesFragment : Fragment() {

    private lateinit var binding: FragmentExpensesBinding
    val viewModel: ExpensesViewModel by viewModels()
    var expenses_ID:Int?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //val user = requireArguments().getString("user")!!.fromJson(User::class.java)
        // Inflate the layout for this fragment
        binding = FragmentExpensesBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

   //    viewModel.setUser(ExpensesFragmentArgs.fromBundle(requireArguments()).user)


   //    viewModel.liveData.observe(viewLifecycleOwner) {
   //        Log.i("List", it.size.toString())
   //        val adapter = ExpensesListAdapter(context!!, it)
   //        binding.lisOfExpenses.adapter = adapter
   //    }


        viewModel.getExpensesSearchType()
        viewModel. ExpensesItemsSearchList.observe(viewLifecycleOwner) {
            Log.i("List", it.size.toString())
            val adapter = ExpensesTypeAdapter(context!!, it)
            binding.expensesSpinner.adapter = adapter
        }

        viewModel.  getExpenses()
        viewModel.  returnExpensesList.observe(viewLifecycleOwner){
            val adapter = ExpensesListAdapter(context!!, it)
            binding.lisOfExpenses.adapter = adapter

        }

        binding.addExpensesButton.setOnClickListener {
       var Amount=    binding.addExpenseAmountEdittext .text.toString()
          var  Reason=  binding.addReasonEdittext.text.toString()
            viewModel.addExpenses(AddExpenses("2",Amount,Reason,expenses_ID.toString()))
        }
        //    binding.addExpensesButton.setOnClickListener {

   //        viewModel.addExpenses()
   //        binding.addExpenseAmountEdittext.text.clear()
   //        binding.addReasonEdittext.text.clear()

   //    }
        Log.d("expensesSpinner","p2.toString()")


   binding.expensesSpinner.onItemSelectedListener =
       object : AdapterView.OnItemSelectedListener {
           override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            //   viewModel.selectedItem.value = viewModel.expensesTypesLiveData.value!![p2]

           //    p1?.accessibilityClassName.toString()

           //    ExpensesSearch
           var x: ExpensesSearch
           x= p0?.getItemAtPosition(p2) as ExpensesSearch
               expenses_ID=x.Record_ID
               binding.addReasonEdittext2.setText(x.ExpenseType)
               Log.d("expensesSpinner",p2.toString())
               Log.d("expensesSpinner",p0.toString())
               Log.d("expensesSpinner",x.ExpenseType)
               Log.d("expensesSpinner",  p0?.getItemAtPosition(p2).toString())

                   // viewModel.addExpenses(AddExpenses(2,))
             //  Log.d("expensesSpinner",p0.toString())
              // Log.d("expensesSpinner",p3.toString())
           }

           override fun onNothingSelected(p0: AdapterView<*>?) {
               TODO("Not yet implemented")
           }

       }


        return binding.root
    }


}