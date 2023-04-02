package com.tbi.supplierplus.framework.ui.purchase

import android.os.Bundle
import android.util.Log

import android.app.Dialog

import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.pojo.puarchase.AddBurchace
import com.tbi.supplierplus.business.pojo.puarchase.ItemsPRCh
import com.tbi.supplierplus.business.utils.fromJson
import com.tbi.supplierplus.business.utils.showSnackbar
import com.tbi.supplierplus.databinding.FragmentPurchaseBinding
import com.tbi.supplierplus.framework.ui.sales.customer_profile.ItemsAdapter
import dagger.hilt.android.AndroidEntryPoint
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import java.util.regex.Pattern

@AndroidEntryPoint
class PurchaseFragment : Fragment() {
    private lateinit var binding: FragmentPurchaseBinding
    private val viewModel: PurchaseViewModel by viewModels()
    var list = ArrayList<ItemsPRCh>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPurchaseBinding.inflate(inflater)
       //a binding.textView23.setOnClickListener { pupup() }

        val adabter = PurchasedItemsAdapter()
      //  viewModel.getPurchases()
        viewModel.getitemPurchase()

     //  viewModel.getitemss.observe(viewLifecycleOwner) {
     //      list = it as ArrayList<ItemsPRCh>
     //      Log.d("nnooono", it.get(0).ItemName)
     //  }

        viewModel.getPuarchaseItem.observe(viewLifecycleOwner){
            binding.textView51.setText(it.itemCount.toString())
            binding.textView60.setText(it.Total.toString())

        }

        viewModel.getPuarchase.observe(viewLifecycleOwner) {
            //   list= it as ArrayList<ItemsPRCh>
          //  Log.d("nnooono", it.get(0).ItemName)
            //    adabter.
            binding.PuarchaseRecycler1.adapter = adabter
            adabter.submitList(it)
        }


        return binding.root
    }

    fun pupup() {
        SimpleSearchDialogCompat(activity, "ادخل اسم المنتج  " + "\n", "search", null,
            inits(), SearchResultListener { baseSearchDialogCompat, item, pos ->

                val str = item.ItemName
                val delim = "-"

                val dialog = activity?.let { it1 -> Dialog(it1) }

                val arr = Pattern.compile(delim).split(str)
                //  val list = str.split(delim)
                val name = arr.get(0).toString()
                val group = arr.get(1).toString()
                val sur = arr.get(2).toString()
                val size = arr.get(3).toString()

                //  val code1  =item.Item_ID.toString()
                // val dialog = activity?.let { it1 -> Dialog(it1) }
                dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                //بيعمل بلوك للباك جراوند
                //  dialog?.setCancelable(false)
                dialog?.setContentView(R.layout.dilog_bill_row)
                dialog?.getWindow()?.setLayout(800, 1800)
                dialog?.show()


                lateinit var tv_name: TextView
                tv_name = dialog?.findViewById(R.id.textView57)!!
                tv_name.setText(arr.get(0).toString())

                lateinit var tv_group: TextView
                tv_group = dialog?.findViewById(R.id.textView52)!!
                tv_group.setText(arr.get(1).toString())

                lateinit var code: TextView
                code = dialog?.findViewById(R.id.textView55)!!
                code.setText(item.Item_ID.toString())


                lateinit var tv_sur: TextView
                tv_sur = dialog?.findViewById(R.id.textView54)!!
                tv_sur.setText(arr.get(2).toString())

                lateinit var tv_size: TextView
                tv_size = dialog?.findViewById(R.id.textView56)!!
                tv_size.setText(arr.get(3).toString())

                lateinit var buttonAddToSalingBill: Button
                buttonAddToSalingBill = dialog.findViewById(R.id.button2b)

                lateinit var NumberOfUnits: TextInputEditText
                NumberOfUnits = dialog?.findViewById(R.id.counterEditTextm)
                // NumberOfUnits.text.toString()

                lateinit var discountEditTextk: EditText
                discountEditTextk = dialog.findViewById(R.id.discountEditTextk)
                var discount = discountEditTextk.text.toString()

                lateinit var buttonAddToReturnsBill: Button
                buttonAddToReturnsBill = dialog.findViewById(R.id.buttonm)
               var  id=item.Item_ID
               var suplierID= item.Supplier_ID

                buttonAddToSalingBill.setOnClickListener {
             val Item_Count=NumberOfUnits.text.toString()
                    viewModel.addNewPurchases(   AddBurchace(id.toString(),Item_Count,"2", suplierID.toString()))



                    dialog.dismiss()
                }


                baseSearchDialogCompat.dismiss()
            }).show()


    }


    private fun inits(): ArrayList<ItemsPRCh>? {

        return list
    }
}   //   binding.viewModel = viewModel
//   binding.lifecycleOwner = this
//   viewModel.setUser(
//       PurchaseFragmentArgs.fromBundle(requireArguments()).user
//   )
//   viewModel.items.observe(viewLifecycleOwner) {
//       val adapter = ItemsAdapter(context!!, it)
//       binding.spinner.adapter = adapter
//   }

//   val purchasedItemsAdapter = PurchasedItemsAdapter()
//   viewModel.purchasedItems.observe(viewLifecycleOwner) {
//       binding.recyclerView.adapter = purchasedItemsAdapter
//       purchasedItemsAdapter.submitList(it)
//       if (it!![0].message.isNotEmpty())
//           showSnackbar(activity!!, it[0].message)
//   }

//   binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//       override fun onItemSelected(
//           parentView: AdapterView<*>?,
//           selectedItemView: View,
//           position: Int,
//           id: Long
//       ) {
//           viewModel.setCurrentItem(viewModel.items.value!![position].id)
//       }

//       override fun onNothingSelected(parentView: AdapterView<*>?) {
//       }
//   }

//   binding.btnQr.setOnClickListener {
//       findNavController().navigate(
//           PurchaseFragmentDirections.actionPurchaseFragmentToQRCodeScannerFragment(
//               "purchase_item"
//           )
//       )
//   }