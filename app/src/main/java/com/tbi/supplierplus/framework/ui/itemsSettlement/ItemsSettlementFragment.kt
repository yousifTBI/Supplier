package com.tbi.supplierplus.framework.ui.itemsSettlement

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.InvoicRequest
import com.tbi.supplierplus.business.pojo.settelment.SetItemsSettelment
import com.tbi.supplierplus.databinding.FragmentItemsSettlementBinding
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.return_items.ReturnItemsAdapter
import com.tbi.supplierplus.framework.ui.return_items.ReturnItemsFragmentArgs
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.ArrayList

@AndroidEntryPoint
class ItemsSettlementFragment : Fragment() {

    private lateinit var binding: FragmentItemsSettlementBinding
    val viewModel: AvailableItemsViewModel by viewModels()
    lateinit var adapter: GetItemsSettlementAdapter
     var RecordID: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemsSettlementBinding.inflate(inflater)
        binding.lifecycleOwner = this




          adapter = GetItemsSettlementAdapter(OnItemSettlementClickListener {
//            //  Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
//              RecordID=it.RecordID
//            // set value in array list
//
//            var arrayList = ArrayList<Int>()
//
//            // set value in array list
//            arrayList.add(1)
//            arrayList.add(2)
//            arrayList.add(3)
//            arrayList.add(4)
//            arrayList.add(5)
//            arrayList.add(6)
//            arrayList.add(7)
//            arrayList.add(8)
//            arrayList.add(9)
//            arrayList.add(10)
//            arrayList.add(11)
//            arrayList.add(12)
//            arrayList.add(13)
//            arrayList.add(14)
//            arrayList.add(15)
//
//            // Initialize dialog
//            val dialog = Dialog(requireContext())
//
//            // set custom dialog
//            dialog.setContentView(android.R.layout.list_content)
//
//            // set custom height and width
//            dialog.window!!.setLayout(350, 500)
//
//            // set transparent background
//            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
//
//            // show dialog
//            dialog.show()
//
//            // Initialize and assign variable
//            // EditText editText=dialog.findViewById(R.id.edit_text);
//            val listView = dialog.findViewById<ListView>(android.R.id.list)
//
//            // Initialize array adapter
//            val adapterlist: ArrayAdapter<Int> =
//                ArrayAdapter<Int>(context!!, android.R.layout.simple_list_item_1, arrayList)
//
//            // set adapter
//            listView.adapter = adapterlist
//
//            listView.onItemClickListener =
//                AdapterView.OnItemClickListener { parent, view, position, id ->
//
//                    lifecycleScope.launch {
//                        viewModel.getSubmitChangeQuantityVMI(adapterlist.getItem(position).toString().toDouble(),RecordID).collect{
//                            when (it) {
//
//                                is State.Loading -> {}
//
//                                is State.Success -> {
//
//                                    binding.spinKit.isVisible = false
//                                    viewModel.GetPendingRequestsMV().collect {
//                                        when (it) {
//
//                                            is State.Loading -> {}
//
//                                            is State.Success -> {
//                                                adapter.submitList(it.data.data)
//                                                binding.recyclerView.adapter = adapter
//                                               adapter.notifyDataSetChanged()
//
//                                            }
//
//                                            is State.Error -> {
//                                                binding.spinKit.isVisible = false
//                                            }
//                                        }
//
//                                    }
//                                }
//                                is State.Error -> {
//                                    binding.spinKit.isVisible = false
//
//                                }
//                            }
//                        }
//                    }
//
//
//
//                 //   Log.d("GetItemsSettlementAdapter",RecordID.toString())
//
//
//
//                   // Toast.makeText(context, adapterlist.getItem(position).toString(), Toast.LENGTH_SHORT).show()
//                    adapter.notifyDataSetChanged()
//
//                    dialog.dismiss()
//
//                }
        })

        binding.button4.setOnClickListener {


            lifecycleScope.launch {
                viewModel.ConfirmSalesrRequestApi().collect {
                    when (it) {

                        is State.Loading -> {}

                        is State.Success -> {
                            binding.spinKit.isVisible = false
                           // adapter.submitList(it.data.data)
                            lifecycleScope.launch {
                                viewModel.GetPendingRequestsMV().collect {
                                    when (it) {

                                        is State.Loading -> {}

                                        is State.Success -> {
                                            binding.spinKit.isVisible = false
                                            adapter.submitList(it.data.data)

                                            binding.recyclerView.adapter = adapter
                                        }
                                        is State.Error -> {
                                            binding.spinKit.isVisible = false
                                        }
                                    }

                                }
                            }
                            binding.recyclerView.adapter = adapter
                        }
                        is State.Error -> {
                            binding.spinKit.isVisible = false
                        }
                    }

                }
            }
        }
        lifecycleScope.launch {
            viewModel.GetPendingRequestsMV().collect {
                when (it) {


                    is State.Loading -> {}

                    is State.Success -> {
                        binding.spinKit.isVisible = false
                        binding.messageStateId.setText(it.data.message)

                      //  Log.d("GetPendingRequestsMV",it.data.message.toString())
                        adapter.submitList(it.data.data)

                        binding.recyclerView.adapter = adapter
                    }
                    is State.Error -> {
                        binding.spinKit.isVisible = false
                    }
                }

            }
        }
        return  binding.root
    }


}