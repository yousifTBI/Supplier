package com.tbi.supplierplus.framework.ui.sales.customers.product_selection

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.tbi.supplierplus.DialogBill
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.Items
import com.tbi.supplierplus.business.pojo.price.SpecialPrice
import com.tbi.supplierplus.databinding.ActivityChangeSpecialPriceBinding
import com.tbi.supplierplus.framework.datasource.requests.State
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import dagger.hilt.android.AndroidEntryPoint
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener

@AndroidEntryPoint

class ChangeSpecialPriceActivity : AppCompatActivity() {
    lateinit var viewModel:SalesViewModel
    private lateinit var binding:ActivityChangeSpecialPriceBinding
    lateinit var message:String
    var list= ArrayList<Items>()

    private val barcode = StringBuffer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_change_special_price)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_change_special_price)
       viewModel = ViewModelProvider(this).get(SalesViewModel::class.java)
      //  viewModel = ViewModelProvider(this).get(SalesViewModel::class.java)
        supportActionBar?.hide()
        message     = intent.getStringExtra("Customer_ID").toString()
      var CompanyName     = intent.getStringExtra("CompanyName").toString()

        binding.textView22.setText(CompanyName)
        viewModel.getAllItemss( message!!.toInt())
         viewModel.getProducers  .observe(this){

             list= it as ArrayList<Items>

         }
       // binding.linearLayout2
//\

        viewModel.SetSpecialItemPriceLiveData.observe(this){
            Toast.makeText(baseContext, it.Message, Toast.LENGTH_SHORT).show()

            if (it.State==1){
                binding.textView8.setText("")
                binding.textView2.setText("")
                binding.textView3.setText("")
                binding.counterEditTextm.setText("")
                Toast.makeText(baseContext, it.Message, Toast.LENGTH_SHORT).show()
                val dialog   = Dialog(this)
                dialog.setContentView(R.layout.chos_items)
                dialog.getWindow()?.setLayout(700, 800)
                dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
            }else{
                Toast.makeText(baseContext, it.Message, Toast.LENGTH_SHORT).show()
                binding.textView80.setText(it.Message+""+it.State.toString())

            }
            binding.textView80.setText(it.Message+""+it.State.toString())

        }
        //binding.
        binding.btnPopup.setOnClickListener {
            // Initialize dialog
            SimpleSearchDialogCompat(this,"ادخل اسم المنتج  "+"\n","search",null,
                inits(), SearchResultListener{
                        baseSearchDialogCompat, item, pos ->
              //      binding.
                    val split= DialogBill(item.item)
                    binding.textView8.setText(split.name)
                    binding.textView3.setText(item. Item_ID.toString())
                    binding.textView2.setText(item.CustomerSellingPrice.toString())


                    baseSearchDialogCompat.dismiss()
                }).show()
        }
       // viewModel.SetSpeci.observe(this){
       //     Log.d("skowxal",it)
       // }


        binding.button2b.setOnClickListener {
         //  Log.d("makeText", "it.data.Item!!.item")
         //  binding.textView80.setText( binding.textView3.text.toString()+"  - "
         //          +message+"  -"+binding.counterEditTextm.text.toString()+" - ")
            if (isvaled()==true){
                binding.textView80.setText( binding.textView3.text.toString()+""
                    +message+""+binding.counterEditTextm.text.toString()+"")
                //binding.textView8.text.toString()

                binding.textView2.text.toString()
                viewModel.SetSpecialItemPrice(SpecialPrice("2", binding.textView3.text.toString().trim(),
                    message,binding.counterEditTextm.text.toString().trim()
                ))
            }

        }

     //   viewModel.  SetSpeci.observe(this){
     //       Toast.makeText(baseContext, it.toString()+"1dr", Toast.LENGTH_SHORT).show()
//
     //   }
   // viewModel.   SetStat.observe(this){

   //     if (it==1){
   //         binding.textView8.setText("")

   //         binding.textView2.setText("")
   //      binding.textView3.setText("")

   //         binding.counterEditTextm.setText("")
   //         Toast.makeText(baseContext, it.toString(), Toast.LENGTH_SHORT).show()
   //         val dialog   = Dialog(this)

   //         dialog.setContentView(R.layout.chos_items)

   //         dialog.getWindow()?.setLayout(700, 700)


   //         dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

   //         dialog.show()

   //     }else{

   //     }

   // }
    //    val loading = LoadingDialog(this)

        viewModel.getItemByBarcodeLiveData.observe(this) {
            when( it){

                is State.Loading ->    Log.d("makeText", "it.data.Item!!.item")

                //loading.startLoading()
                is State.Success ->if (it.data.State==1){
                   // loading.isDismiss()
                    Log.d("makeText", it.data.Item!!.item)
                    Log.d("makeText",it.data.Message)
                    binding.textView8.setText(it.data.Item.item)
                   binding.textView3.setText(it.data.Item. Item_ID.toString())
                    binding.textView2.setText(it.data.Item.CustomerSellingPrice.toString())
                    //.CustomerSellingPrice.toString())
                    //   it.data.Item
                    Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()

                } else{
                  //  loading.isDismiss()
                    Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()

                    Log.d("makeText",it.data.Message)

                    // it.data.Message
                }
                //Log.d("makeText","Success")
                is State.Error ->   if (it.toError().isNotEmpty()){

                //    loading.isDismiss()
                    Toast.makeText(baseContext, it.messag, Toast.LENGTH_SHORT).show()
                }
            }



        }

    }


    fun inits(): ArrayList<Items> {
        return list

    }
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {

        if (event?.action == KeyEvent.ACTION_DOWN) {
            val pressedKey = event.unicodeChar.toChar()
            barcode.append(pressedKey)
        }
        if (event?.action == KeyEvent.ACTION_DOWN && event?.keyCode == KeyEvent.KEYCODE_ENTER) {
            Toast.makeText(baseContext, barcode.toString(), Toast.LENGTH_SHORT).show()
             binding.textView8.setText("")
             binding.textView3.setText("")
             binding.textView2.setText("")
            binding.editTextTextPersonName3.setText("")
            binding.editTextTextPersonName3. clearFocus ()
           // binding.textView3.setText(barcode.toString().trim())
            viewModel.getItemByBarcodeV1API("2",barcode.toString().trim(), message)

            barcode.delete(0, barcode.length)
        }

        return super.dispatchKeyEvent(event)

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
        if ( binding.textView2.text.toString().isEmpty()) {
            binding.textView2.setError("Enter phone")
            Toast.makeText(baseContext, "اضف الموزع ", Toast.LENGTH_SHORT).show()

            return false
        }
//textView2

        run { return true }
    }
}