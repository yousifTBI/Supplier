package com.tbi.supplierplus.framework.ui.sales.customers.product_selection

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.tbi.supplierplus.DialogBill
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.Items
import com.tbi.supplierplus.business.pojo.price.EditProductprice
import com.tbi.supplierplus.business.pojo.price.SpecialPrice
import com.tbi.supplierplus.business.utils.LoadingDialog
import com.tbi.supplierplus.databinding.ActivityChangePriceBinding
import com.tbi.supplierplus.databinding.ActivityChangeSpecialPriceBinding
import com.tbi.supplierplus.framework.datasource.requests.State
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import dagger.hilt.android.AndroidEntryPoint
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
@AndroidEntryPoint

class ChangePriceActivity : AppCompatActivity() {
    lateinit var viewModel: SalesViewModel
    private lateinit var binding:ActivityChangePriceBinding
   // lateinit var message:String
    var list= ArrayList<Items>()
   // val loading = LoadingDialog( this)
    private val barcode = StringBuffer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_change_price)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_change_price)
        viewModel = ViewModelProvider(this).get(SalesViewModel::class.java)

        supportActionBar?.hide()
      //  viewModel.getAllItemss( message!!.toInt())
        viewModel.getProducers  .observe(this){

            list= it as ArrayList<Items>

        }
        // binding.linearLayout2

       viewModel. editProductpricemass.observe(this){
           Toast.makeText(baseContext, it.toString(), Toast.LENGTH_SHORT).show()

       }

        //binding.

     //   viewModel.SetSpeci.observe(this){
     //       Log.d("skowxal",it)
     //   }
        binding.button2b.setOnClickListener {
            Toast.makeText(baseContext,  binding.textView3.text.toString().trim(), Toast.LENGTH_SHORT).show()

            if (isvaled()==true){
                viewModel.editItemByBarcode( EditProductprice(  binding.textView3.text.toString().trim(),"2",
                    binding.textView2.text.toString().toDouble(),
                    binding.counterEditTextm.text.toString().toDouble()
                ))
            }



        }

        //   viewModel.  SetSpeci.observe(this){
        //       Toast.makeText(baseContext, it.toString()+"1dr", Toast.LENGTH_SHORT).show()
//
        //   }
        viewModel.   editProductpriceState.observe(this){

            if (it==1){
                binding.textView8.setText("")

                binding.textView2.setText("")
                binding.textView3.setText("")
                binding. editTextTextPersonName3.setText("")
               // editTextTextPersonName3

                binding.counterEditTextm.setText("")
                Toast.makeText(baseContext, it.toString(), Toast.LENGTH_SHORT).show()
                val dialog   = Dialog(this)

                dialog.setContentView(R.layout.chos_items)

                dialog.getWindow()?.setLayout(700, 800)


                dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dialog.show()

            }

        }
       // loading.startLoading()
        viewModel.getItemByBarcodeLiveData.observe(this) {
            when( it){

                is State.Loading ->
                    //loading.startLoading()
               Log.d("","")
                is State.Success ->if (it.data.State==1){
                   // loading.isDismiss()
                   // Log.d("makeText", it.data.Item!!.item)
                   // Log.d("makeText",it.data.Message)
                    binding.textView8.setText(it.data.Item!!.item)
                    //binding.textView3.setText(barcode.toString())
                    binding.textView2.setText(it.data.Item!!.CustomerSellingPrice.toString())
                        //.CustomerSellingPrice.toString())
                    //   it.data.Item
                    Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()

                } else{
                   // loading.isDismiss()
                    Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()

                    Log.d("makeText",it.data.Message)

                    // it.data.Message
                }
                //Log.d("makeText","Success")
                is State.Error ->  // if (it.toError().isNotEmpty()){

                  //  loading.isDismiss()
                    Toast.makeText(baseContext, it.messag, Toast.LENGTH_SHORT).show()
              //  }
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
            binding.counterEditTextm.setText("")
            binding.textView3.setText(barcode.toString())

            viewModel.getItemByBarcodeV1API("2",barcode.toString().trim(), "0")

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