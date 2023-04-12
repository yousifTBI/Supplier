package com.tbi.supplierplus.framework.ui.return_items

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tbi.supplierplus.business.pojo.returns.ReturnItems
import com.tbi.supplierplus.business.pojo.returns.Suppliers
import com.tbi.supplierplus.business.utils.showSnackbar
import com.tbi.supplierplus.databinding.FragmentReturnItemsBinding
import com.tbi.supplierplus.framework.ui.daily_closing.DailyClosingAdapter
import com.tbi.supplierplus.framework.ui.expenses.ExpensesFragmentArgs
import com.tbi.supplierplus.framework.ui.sales.customer_profile.ReturnsAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReturnItemsFragment : Fragment() {

    private lateinit var binding: FragmentReturnItemsBinding
    val viewModel: ReturnItemsViewModel by viewModels()

      var x: List<ReturnItems>? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentReturnItemsBinding.inflate(inflater)
        binding.returnViewModel = viewModel
        binding.lifecycleOwner = this
    //    viewModel.setUser(ReturnItemsFragmentArgs.fromBundle(requireArguments()).user)
       // viewModel.getReturnItems()
        x?.get(0)?.itemname=" لا يوجد"
        x?.get(0)?.Balance=0.0
        x?.get(0)?.Item_ID=0
        viewModel.getReturnItemsFillSpinner()


        viewModel.returnItemsLiveData.observe(viewLifecycleOwner) {
            val adapter = ReturnItemsAdapter(context!!, it)
            binding.returnItemSpinner.adapter = adapter
        }

        val adapter = GetReturnItemsSettelmentAdapter()
        viewModel.getReturnItemsSettelmentLiveData.observe(viewLifecycleOwner){
            if ( it.State==1){
                binding.getReturnItemsSettelmentRecycler.adapter = adapter
                adapter.submitList(it.data)
            }else if (it.State==2){
                binding.getReturnItemsSettelmentRecycler.adapter = adapter
                adapter.submitList(x)

            }

        }
        binding.returnItemSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long
            ) {
                var suppliers: Suppliers

                suppliers=  parentView?.getItemAtPosition(position)as Suppliers

                viewModel.getReturnItems(  suppliers.Supplier_ID.toString())
                adapter.notifyDataSetChanged()

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }




        viewModel.setReturnItemsLiveData.observe(viewLifecycleOwner) {
            showSnackbar(activity!!, it.message)
        }



        return binding.root
    }


}
