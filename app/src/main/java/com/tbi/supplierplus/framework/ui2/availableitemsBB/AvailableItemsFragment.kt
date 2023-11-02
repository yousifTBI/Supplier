package com.tbi.supplierplus.framework.ui2.availableitemsBB

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.BillActivity2
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.AvailableItems
import com.tbi.supplierplus.databinding.FragmentAvailableItemsBinding
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.sales.addCompany.Data
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Locale


class AvailableItemsFragment : Fragment() {


    lateinit var binding: FragmentAvailableItemsBinding
    val availableItemsViewModel: AvailableItemsViewModel by activityViewModels()
    lateinit var adapter: AvailableItemsAdapter
    var AvailableItemsItemslist = ArrayList<AvailableItems>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAvailableItemsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        // Inflate the layout for this fragment



            adapter = AvailableItemsAdapter(AvailableItemsItemslist,OnAvailableItemsClickListener {
            val ItemID = it.ItemID
            val ItemName = it.ItemName
            val Barcode = it.Barcode
            val Supply_Price = it.Supply_Price
            val Selling_Price = it.Selling_Price
            val availableCount = it.availableCount
            val Supplier_Id = it.Supplier_Id


            val intent = Intent(activity, StockRequestActivity::class.java)
                .apply {
                    putExtra("ItemID  ", ItemID.toString())
                    putExtra("ItemName", ItemName)
                    putExtra("Barcode ", Barcode.toString())
                    putExtra("Supply_Price  ", Supply_Price.toString())
                    putExtra("Selling_Price ", Selling_Price.toString())
                    putExtra("availableCount", availableCount.toString())
                    putExtra("Supplier_Id", Supplier_Id.toString())

                }
            startActivity(intent)

        })


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
           //    filterCustomers(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
              filterCustomers(newText)
                return true
            }
        })

//        viewLifecycleOwner.lifecycleScope.launch {
//            availableItemsViewModel.GetBillQRCode("70").collect {
//                when (it) {
//                    is State.Loading -> {}
//                    is State.Success -> {
//
//                        Log.d("billNumToCreateQR", it.data.item.toString())
//                        Log.d("billNumToCreateQR","it.data.item.toString()")
//
//                    }
//                    is State.Error -> {
//                        Log.d("billNumToCreateQR","Error")
//
//                    }
//                }
//
//            }
//        }




        viewLifecycleOwner.lifecycleScope.launch {
            availableItemsViewModel.getAvailableItems().collect {
                when (it) {

                    is State.Loading -> {}
                    //binding.spinKit.isVisible = true
                    is State.Success -> {
                        binding.spinKit.isVisible = false
   //                     Log.d("testSot", it.data.data[0].ItemName)
                        AvailableItemsItemslist =it.data.data
                        adapter.submitList(it.data.data)
                        binding.recyclerView.adapter = adapter


                    }
                    is State.Error -> {
  //                      Log.d("testSot", "2")
                        binding.spinKit.isVisible = false
                        Toast.makeText(context, it.messag, Toast.LENGTH_SHORT).show()
                    }
                }

            }


        }




        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewLifecycleOwner.lifecycleScope.launch {
            availableItemsViewModel.getAvailableItems().collect {
                when (it) {

                    is State.Loading -> {}
                    //binding.spinKit.isVisible = true
                    is State.Success -> {
                        binding.spinKit.isVisible = false
                        AvailableItemsItemslist =it.data.data
     //                   Log.d("testSot", it.data.data[0].ItemName)
                        adapter.submitList(it.data.data)
                        binding.recyclerView.adapter = adapter


                    }
                    is State.Error -> {
     //                   Log.d("testSot", "2")
                        binding.spinKit.isVisible = false
                        Toast.makeText(context, it.messag, Toast.LENGTH_SHORT).show()
                    }
                }

            }


        }
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch {
            availableItemsViewModel.getAvailableItems().collect {
                when (it) {

                    is State.Loading -> {}
                    //binding.spinKit.isVisible = true
                    is State.Success -> {
                        binding.spinKit.isVisible = false
                        //                   Log.d("testSot", it.data.data[0].ItemName)
                        adapter.submitList(it.data.data)
                        binding.recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()

                    }
                    is State.Error -> {
                        //                   Log.d("testSot", "2")
                        binding.spinKit.isVisible = false
                        Toast.makeText(context, it.messag, Toast.LENGTH_SHORT).show()
                    }
                }

            }


        }
    }


    fun filterCustomers(query: String) {
        if (query != null) {
            val filteredList =ArrayList<AvailableItems>()
          //  AvailableItemsItemslist = AvailableItemsItemslist!!.filter { query in it.ItemName } as ArrayList<AvailableItems>

            for (i in AvailableItemsItemslist){
                if (i.ItemName.toLowerCase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                    Log.d("AvailableItemsItemslist",AvailableItemsItemslist[0].ItemName)
                }
            }
            if (filteredList.isEmpty())
            {
                Toast.makeText(requireContext(), "No Data found",Toast.LENGTH_SHORT).show()
            }else{

              //  adapter.setFilteredList(filteredList)
               adapter.submitList(filteredList)
                adapter.notifyDataSetChanged()

            }
        }
    }
}