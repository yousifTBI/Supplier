package com.tbi.supplierplus.framework.ui.itemsSettlement

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.settelment.SetItemsSettelment
import com.tbi.supplierplus.databinding.FragmentItemsSettlementBinding
import com.tbi.supplierplus.framework.ui.return_items.ReturnItemsAdapter
import com.tbi.supplierplus.framework.ui.return_items.ReturnItemsFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemsSettlementFragment : Fragment() {

    private lateinit var binding: FragmentItemsSettlementBinding
    val viewModel: ItemsSettlementViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemsSettlementBinding.inflate(inflater)
        binding.returnViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.setUser(ReturnItemsFragmentArgs.fromBundle(requireArguments()).user)
        viewModel. getItemsSettlementSuppliersFillSpinner()
        viewModel.itemsSettlementSuppliersLiveData.observe(viewLifecycleOwner) {
            Log.i("item", it.size.toString())
          val adapter = ReturnItemsAdapter(context!!, it)
          binding.itemsSettlementSpinner.adapter = adapter

        }

        binding.itemsSettlementSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                viewModel.getItemsSettlement(viewModel.itemsSettlementSuppliersLiveData.value!![position].Supplier_ID.toString())

                Log.i("supplierIdIn", viewModel.itemsSettlementSuppliersLiveData.value!![position].Supplier_ID.toString())
                viewModel.supplierId = viewModel.itemsSettlementSuppliersLiveData.value!![position].Supplier_ID.toString()


            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        val adapter = GetItemsSettlementAdapter(OnItemSettlementClickListener {

            val itemId:String = it.Item_ID.toString()
            val returnAmount:String = it.ReturnAmount.toString()
            val returnCount:String = it.ReturnSize.toString()
       //     viewModel.supplierId
           // Log.d("returnCount",viewModel.supplierId)


            val dialogView =
                LayoutInflater.from(requireContext()).inflate(R.layout.custom_items_settlement_alert_dialoge, null)
            val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setCancelable(false)
            val mAlertDialog = builder.show()

            dialogView.findViewById<Button>(R.id.cancel_button)
                .setOnClickListener(View.OnClickListener {
                    mAlertDialog.dismiss()
                    Toast.makeText(requireContext(), "canceled", Toast.LENGTH_SHORT).show()

                })


            dialogView.findViewById<Button>(R.id.save_items_settlement_button)
                .setOnClickListener {
                viewModel. Setitemssettelment(SetItemsSettelment(itemId,returnCount,"2",viewModel.supplierId))
                    viewModel.setItemsSettlement(itemId, viewModel.supplierId, returnAmount)
                    Log.i("itemIdSettlemnt", itemId)
                    Log.i("itemIdSettlemnt", viewModel.supplierId)
                    Log.i("itemIdSettlemnt", returnAmount)
                    viewModel.setItemsSettlementLiveData.observe(viewLifecycleOwner) {
                        com.tbi.supplierplus.business.utils.showSnackbar(activity!!, it.message)
                        Log.i("bbbbbbbbb", it.toString())

                    }
                    mAlertDialog.dismiss()
                }

        })
        viewModel.getItemsSettlementLiveData.observe(viewLifecycleOwner) {
            Log.i("ReturnsSize", it!!.size.toString())
            Log.i("ReturnsSize", it!!.toString())
            binding.getItemsSettlementRecycler.adapter = adapter
            adapter.submitList(it)
        }


        return  binding.root
    }


}