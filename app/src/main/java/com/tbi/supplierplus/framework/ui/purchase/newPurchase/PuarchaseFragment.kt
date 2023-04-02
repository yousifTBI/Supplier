package com.tbi.supplierplus.framework.ui.purchase.newPurchase

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.Items
import com.tbi.supplierplus.business.pojo.billModels.ReturnBill
import com.tbi.supplierplus.business.pojo.billModels.SaleingBill
import com.tbi.supplierplus.business.pojo.puarchase.ItemsPRCh
import com.tbi.supplierplus.databinding.FragmentPuarchaseBinding
import com.tbi.supplierplus.databinding.FragmentPurchaseBinding
import com.tbi.supplierplus.framework.ui.purchase.PurchaseViewModel
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import java.util.regex.Pattern

class PuarchaseFragment : Fragment() {

    private lateinit var binding: FragmentPuarchaseBinding
    private val viewModel: PurachaseViewModel by viewModels()
   // var list= ArrayList<ItemsPRCh>()
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NewApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPuarchaseBinding.inflate(inflater)
 //       viewModel = ViewModelProviders.of(this).get(PurchaseViewModel::class.java)
       // binding.viewModel = viewModel
      //  binding.lifecycleOwner = this


        binding.lifecycleOwner = viewLifecycleOwner
        // Inflate the layout for this fragment
     //   return inflater.inflate(R.layout.fragment_puarchase, container, false)
      //  viewModel.itemPurchase()
      viewModel.getitemss.observe(viewLifecycleOwner){

         // list= it as ArrayList<ItemsPRCh>

      }

     //  binding. linearLayout2.setOnClickListener { pupup() }
        return binding.root
    }
 //fun pupup(){
 //  SimpleSearchDialogCompat(activity,"ادخل اسم المنتج  "+"\n","search",null,
 //      inits(), SearchResultListener{
 //              baseSearchDialogCompat, item, pos ->

 //          val str = item.ItemName
 //          val delim = "-"


 //          val arr = Pattern.compile(delim).split(str)
 //          //  val list = str.split(delim)
 //          val name=arr.get(0).toString()
 //          val group=arr.get(1).toString()
 //          val sur=arr.get(2).toString()
 //          val size=arr.get(3).toString()

 //          val code1  =item.Item_ID.toString()
 //          val dialog = activity?.let { it1 -> Dialog(it1) }
 //          dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
 //          //بيعمل بلوك للباك جراوند
 //          //  dialog?.setCancelable(false)
 //          dialog?.setContentView(R.layout.dilog_bill_row)
 //          dialog?.getWindow()?.setLayout(600,1800)
 //          dialog?.show()


 //          lateinit var tv_name: TextView
 //          tv_name= dialog?.findViewById(R.id.textView57)!!
 //          tv_name.setText(arr.get(0).toString())

 //          lateinit var tv_group: TextView
 //          tv_group= dialog?.findViewById(R.id.textView52)!!
 //          tv_group.setText(arr.get(1).toString())

 //          lateinit var code: TextView
 //          code= dialog?.findViewById(R.id.textView55)!!
 //          code.setText(item.Item_ID.toString())


 //          lateinit var tv_sur: TextView
 //          tv_sur= dialog?.findViewById(R.id.textView54)!!
 //          tv_sur.setText(arr.get(2).toString())

 //          lateinit var tv_size: TextView
 //          tv_size= dialog?.findViewById(R.id.textView56)!!
 //          tv_size.setText(arr.get(3).toString())

 //          lateinit var buttonAddToSalingBill: Button
 //          buttonAddToSalingBill=dialog.findViewById(R.id.button2b)

 //          lateinit     var NumberOfUnits: TextInputEditText
 //          NumberOfUnits=dialog?.findViewById(R.id.counterEditTextm)
 //          // NumberOfUnits.text.toString()

 //          lateinit var discountEditTextk: EditText
 //          discountEditTextk=dialog.findViewById(R.id.discountEditTextk)
 //          var discount=   discountEditTextk.text.toString()

 //          lateinit var buttonAddToReturnsBill: Button
 //          buttonAddToReturnsBill=dialog.findViewById(R.id.buttonm)


 //   //      buttonAddToSalingBill.setOnClickListener {


 //   //          var price=item.Price_ID.toString()

 //   //          var x:Int=Integer.parseInt(NumberOfUnits.text.toString())

 //   //          var z=x.toDouble()


 //   //          var TotalPrice: Double
 //   //          TotalPrice=item.CustomerSellingPrice.toString() .toDouble()*z

 //   //          listBill.add(
 //   //              SaleingBill(
 //   //              "1"
 //   //              , name
 //   //              , NumberOfUnits.text.toString()
 //   //              ,size
 //   //              ,item.CustomerSellingPrice.toString()
 //   //              ,discountEditTextk.text.toString()
 //   //              ,TotalPrice.toString()
 //   //              , code1
 //   //              ,item.Supplier_ID.toString()
 //   //              ,item.Supply_Price.toString()
 //   //              ,1.0
 //   //          )
 //   //          )

 //   //          binding.billRecycler1.adapter = adapte
 //   //          adapte.submitList(listBill)
 //   //

// })
//  }
  //  private fun inits(): ArrayList<ItemsPRCh> {
 //  //     return list

   // }
}