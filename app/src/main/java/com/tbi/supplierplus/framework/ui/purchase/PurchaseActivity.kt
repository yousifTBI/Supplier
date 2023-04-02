package com.tbi.supplierplus.framework.ui.purchase

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.tbi.supplierplus.DialogBill
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.puarchase.AddBurchace
import com.tbi.supplierplus.business.pojo.puarchase.ItemsPRCh
import com.tbi.supplierplus.business.utils.LoadingDialog
import com.tbi.supplierplus.databinding.ActivityPurchaseBinding
import com.tbi.supplierplus.framework.datasource.requests.State
import dagger.hilt.android.AndroidEntryPoint
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import java.util.regex.Pattern

@AndroidEntryPoint

class PurchaseActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPurchaseBinding
    lateinit var viewModel:PurchaseViewModel
    private val barcode = StringBuffer()
     var list = ArrayList<ItemsPRCh>()
    lateinit var item_id:String
    lateinit var supllyid:String
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
     //  setContentView(R.layout.activity_purchase)

       binding= DataBindingUtil.setContentView(this,R.layout.activity_purchase)
     //  LayoutInflater.from(this).inflate(R.layout.activity_purchase,parent,false);
       viewModel = ViewModelProvider(this).get(PurchaseViewModel::class.java)

       val loading = LoadingDialog(this)


       viewModel.getPurchases()


       supportActionBar?.hide()
       //   viewModel. getItemByBarcodeV1API()
     // viewModel.  NotFoundItemByBarcodefulsLiveData.observe(this){
     //      Toast.makeText(applicationContext,it,Toast.LENGTH_SHORT)

     // }
    //   binding.button2b.setOnClickListener {
//
    //   }
       binding.btnPopup.setOnClickListener {
           Log.d("linearLayout2","sh")

           Toast.makeText(applicationContext,"isEmpty",Toast.LENGTH_SHORT)

           pupup() }

       viewModel.getitemss.observe(this) {
           list = it as ArrayList<ItemsPRCh>
           Log.d("nnooono", it.get(0).ItemName)
       }
       viewModel.SucssaddNewPurchases.observe(this){
           Toast.makeText(this,it,Toast.LENGTH_SHORT)
           Toast.makeText(applicationContext,it,Toast.LENGTH_SHORT)
           Toast.makeText(applicationContext,it,Toast.LENGTH_SHORT)
           Toast.makeText(baseContext, it, Toast.LENGTH_SHORT).show()
           Log.d("nnooono", it)
           val dialog   = Dialog(this)
           // set custom dialog
           // set custom dialog
           dialog.setContentView(R.layout.chos_items)
           //  dialog.setContentView(R.layout.chos_itemse);

           // set custom height and width
           //  dialog.setContentView(R.layout.chos_itemse);

           // set custom height and width
           dialog.getWindow()?.setLayout(550, 700)

           // set transparent background

           // set transparent background
           dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
           // ImageView imageView2=dialog.findViewById(R.id.imageView2);

           // show dialog
           // ImageView imageView2=dialog.findViewById(R.id.imageView2);

           // show dialog
           dialog.show()


       }

      binding.button2b.setOnClickListener {
          if ( isvaled()==true){
          //    viewModel.addNewPurchases(   AddBurchace(binding.ItemId.text.toString(),binding.counterEditTextm.toString(),"2", "7"))
              viewModel.addNewPurchases(   AddBurchace( item_id.toString(),binding.counterEditTextm.text.toString(),"2", supllyid.toString()))
          }

        //  viewModel.addNewPurchases(
         //     AddBurchace(id.toString(),binding.counterEditTextm.toString(),"2", suplierID.toString()))

         // viewModel.addNewPurchases(
         //     AddBurchace("binding.oString()",
         //         binding.counterEditTextm.toString(),

         //         "2", "7"))
        //  binding.textView80.setText(item_id.toString()+"\n"+binding.counterEditTextm.toString()+"\n"+"2"+"\n"+supllyid.toString())
          Log.d("",binding.ItemId.text.toString()+""+binding.counterEditTextm.toString())

       //   viewModel.addNewPurchases(   AddBurchace( item_id.toString(),binding.counterEditTextm.text.toString(),"2", supllyid.toString()))

    //     if ( binding.textView8.text.isEmpty()&&binding.counterEditTextm.text.isEmpty()
    //         &&binding.counterEditTextm.text.length==0&& binding.textView8.text.length==0){
    //          Toast.makeText(baseContext,"isEmpty",Toast.LENGTH_SHORT)

    //         binding.counterEditTextm.setError("lpl")
    //     }else{
    //         viewModel.addNewPurchases(   AddBurchace(binding.ItemId.text.toString(),binding.counterEditTextm.toString(),"2", "7"))

    //     }


          //pupup()
      }
       viewModel.NotFoundItemByBarcodefulsLiveData.observe(this){
           Log.d("applicationContext",it)
           Toast.makeText(applicationContext,it,Toast.LENGTH_SHORT)

       }
       viewModel.getItemByBarcodeLiveData.observe(this) {
          // var item=DialogBill("j")
           when( it){

               is State.Loading -> loading.startLoading()
               // Toast.makeText(baseContext, it.toLoading(), Toast.LENGTH_SHORT).show()

               is State.Success ->if (it.data.State==1){
                   loading.isDismiss()
                   var split=DialogBill(it.data.Item!!.item)
                   binding.textView8.setText(split.name)
                   binding.Size1.setText(split.size)
                   binding.sours1.setText(split.sur)
                  // binding.textView2.setText(it.Supply_Price.toString())
                   binding.textView2.setText(it.data.Item.Supply_Price.toString())
                   item_id= it.data.Item.Item_ID.toString()
                   supllyid=it.data.Item.getSupplier_ID()
               }else{
                   loading.isDismiss()
                   Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()
                   Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()

               }
               is State.Error -> if (it.toError().isNotEmpty()){

                   loading.isDismiss()
                       Toast.makeText(baseContext, it.messag, Toast.LENGTH_SHORT).show()
               }


           }

       }
   }
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {

        if (event?.action == KeyEvent.ACTION_DOWN) {
            val pressedKey = event.unicodeChar.toChar()
            barcode.append(pressedKey)
        }
        if (event?.action == KeyEvent.ACTION_DOWN && event?.keyCode == KeyEvent.KEYCODE_ENTER) {
          //  Toast.makeText(baseContext, barcode.toString(), Toast.LENGTH_SHORT).show()
            //  viewModel.getItemByBarcodeV1API("2",barcode.toString(),message)

           binding.textView8.setText("")
           binding.Size1.setText("")

           binding.sours1.setText("")
           binding.textView2.setText("")
           binding.textView3.setText("")
           binding.ItemId.setText("")

            binding.  counterEditTextm.setText("")
            binding.  counterEditTextm.clearFocus()
            Toast.makeText(applicationContext,"isEmpty",Toast.LENGTH_SHORT)


            binding.textView3.setText(barcode.toString().trim())
            viewModel.getItemByBarcodeV1API("2",barcode.toString().trim(),"0")
            binding.editTextTextPersonName5.setText("")
            binding.editTextTextPersonName5.clearFocus()
            barcode.delete(0, barcode.length)
        }

        return super.dispatchKeyEvent(event)
    }

   fun pupup() {
       SimpleSearchDialogCompat(this, "ادخل اسم المنتج  " + "\n", "search", null,
           inits(), SearchResultListener { baseSearchDialogCompat, item, pos ->

               val str = item.ItemName
               val delim = "-"

               val dialog = this?.let { it1 -> Dialog(it1) }

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
               dialog.getWindow()?.setLayout(550, 500)
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
    fun isvaled(): Boolean? {
        if ( binding.counterEditTextm.text.toString().isEmpty()) {
            binding.counterEditTextm.setError("Enter phone")
            return false
        }
        if ( binding.textView8.text.toString().isEmpty()) {
            binding.textView8.setError("Enter phone")
            Toast.makeText(baseContext, "اضف الموزع ", Toast.LENGTH_SHORT).show()

            return false
        }


        run { return true }
    }
}