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
        // Inflate the layout for this fragment

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
          //  Log.i("List", it.size.toString())
            Log.d("getReturn",it.get(0).Supplier_ID.toString())

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
                //  getReturnItemsSettelmentLiveData1.value?.get(0)?.itemname="gh"
                //  getReturnItemsSettelmentLiveData1.value?.get(0)?.Balance=0.0
                //  getReturnItemsSettelmentLiveData.value= getReturnItemsSettelmentLiveData1.value
            }
           // Log.d("getReturnItems",it.get(0).itemname)

        }
        binding.returnItemSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long
            ) {
                var suppliers: Suppliers

                suppliers=  parentView?.getItemAtPosition(position)as Suppliers

                viewModel.getReturnItems(  suppliers.Supplier_ID.toString())
                adapter.notifyDataSetChanged()
                Log.d("returnItemSpinner", suppliers.CompanyName)
                Log.d("returnItemSpinner", suppliers.Supplier_ID.toString())
               // viewModel.getReturnItems(viewModel.returnItemsLiveData.value!![position].Supplier_ID.toString())
//
               // viewModel.supplierId = viewModel.returnItemsLiveData.value!![position].Supplier_ID.toString()

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }


   //    val adapter = GetReturnItemsSettelmentAdapter()
   //    viewModel.getReturnItemsSettelmentLiveData.observe(viewLifecycleOwner) {
   //        Log.i("ReturnsSize", it!!.size.toString())
   //        binding.getReturnItemsSettelmentRecycler.adapter = adapter
   //        adapter.submitList(it)
   //    }




   //   binding.saveButton.setOnClickListener(View.OnClickListener {
   //       Log.i("supplierId", viewModel.supplierId)

   //       viewModel.getReturnItemsSettelmentLiveData.value!!.forEach {
   //           viewModel.setReturnItems("2", viewModel.supplierId, it.id, it.quantity)
   //       }

   //       viewModel.setReturnItemsLiveData.observe(viewLifecycleOwner) {
   //           showSnackbar(activity!!, it.message)
   //       }
   //   })

        viewModel.setReturnItemsLiveData.observe(viewLifecycleOwner) {
            showSnackbar(activity!!, it.message)
        }



        return binding.root
    }


}
