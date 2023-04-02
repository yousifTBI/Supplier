package com.tbi.supplierplus.framework.ui.addNewItem

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.tbi.supplierplus.DialogBill
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.addItem.NewItem
import com.tbi.supplierplus.business.pojo.addItem.Supplier
import com.tbi.supplierplus.business.pojo.addItem.TypeOfcategory
import com.tbi.supplierplus.business.utils.LoadingDialog
import com.tbi.supplierplus.databinding.ActivityAddItemBinding
import com.tbi.supplierplus.framework.datasource.requests.State
import dagger.hilt.android.AndroidEntryPoint
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener

@AndroidEntryPoint
class AddItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemBinding
    lateinit var viewModel :ItemViewModel
    private val barcode = StringBuffer()
    var TypeOfcategory=ArrayList<TypeOfcategory>()
    var Supplier=ArrayList<Supplier>()
var Supplier_ID:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_add_item)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_item)
        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        viewModel. getItemGroupSpinner("2","7")
        viewModel.getSuppliers()
        val loading = LoadingDialog(this)

        viewModel.StateAdd.observe(this){
            when( it){

                is State.Loading ->  loading.startLoading()
                    //binding.progressBar2.visibility
                    //Log.d("","")
                // Toast.makeText(baseContext, it.toLoading(), Toast.LENGTH_SHORT).show()

                is State.Success ->
                    if (it.data.State==1){
                      //  binding.progressBar2.isGone
                        loading.isDismiss()

                        Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()

                    binding.nameOfItem.setText("")
                    binding.typ.setText("")

                    binding.size.setText("")
                    binding.sellingprice.setText("")
                    binding.Buyingprice.setText("")

                    binding.barcode.setText("")
                    binding.Ids.setText("")
                  //  Toast.makeText(this,it,Toast.LENGTH_SHORT)
                  //  Toast.makeText(applicationContext,it,Toast.LENGTH_SHORT)
                  //  Toast.makeText(applicationContext,it,Toast.LENGTH_SHORT)
                  //  Toast.makeText(baseContext, it, Toast.LENGTH_SHORT).show()

                    val dialog   = Dialog(this)

                    dialog.setContentView(R.layout.chos_items)

                    dialog.getWindow()?.setLayout(700, 2000)


                    dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    dialog.show()
                }else{
                    //    binding.progressBar2.isGone
                        loading.isDismiss()
                    Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()
                    Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()

                }
                is State.Error ->  if (it.toError().isNotEmpty()){

                    loading.isDismiss()
                    Toast.makeText(baseContext, it.messag, Toast.LENGTH_SHORT).show()
                }
                    //binding.progressBar2.isGone
                    //Toast.makeText(baseContext, it.messag, Toast.LENGTH_SHORT).show()

            }
         // if(it==1){
         // viewModel.massgeAdd.observe(this){
         //     binding.nameOfItem.setText("")
         //     binding.typ.setText("")

         //     binding.size.setText("")
         //     binding.sellingprice.setText("")
         //     binding.Buyingprice.setText("")

         //     binding.barcode.setText("")
         //     binding.Ids.setText("")
         //     Toast.makeText(this,it,Toast.LENGTH_SHORT)
         //     Toast.makeText(applicationContext,it,Toast.LENGTH_SHORT)
         //     Toast.makeText(applicationContext,it,Toast.LENGTH_SHORT)
         //     Toast.makeText(baseContext, it, Toast.LENGTH_SHORT).show()
         //     Log.d("nnooono", it)
         //     val dialog   = Dialog(this)

         //     dialog.setContentView(R.layout.chos_items)

         //     dialog.getWindow()?.setLayout(700, 2000)


         //     dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

         //     dialog.show()

         // }
         // }
        }

        viewModel.Suppliers.observe(this){
            Supplier= it as ArrayList<Supplier>
        }
        viewModel.Groups.observe(this){
            TypeOfcategory= it as ArrayList<TypeOfcategory>
            Log.d("sqsq", TypeOfcategory.get(2).ItemGroup_ID.toString())
            Log.d("sqsq", TypeOfcategory.get(2).CategoryName.toString())

        }

        binding.printbtn.setOnClickListener {


            if ( isvaled()==true){
                viewModel.addNewItem(
                    NewItem(
                        binding.nameOfItem.text.toString(),
                        binding.typ.text.toString(),
                        "2", Supplier_ID,
                        binding.size.text.toString(),
                        binding.sellingprice.text.toString(),
                        binding.Buyingprice.text.toString(),
                        "2",
                        binding.barcode.text.toString()
                    ))
            }
          //  Log.d("sqsq", TypeOfcategory.get(2).ItemGroup_ID.toString())



        }


        binding.cardView4.setOnClickListener {
            SimpleSearchDialogCompat(this,"ادخل الموزع  "+"\n","search",null,
                init(), SearchResultListener{
                        baseSearchDialogCompat, item, pos ->
                    binding.Ids.setText(item.CompanyName)
                    Log.d("sqsq", item.Supplier_ID.toString())

                    //  binding.Supl.setText(item.Supplier_ID)
                    Supplier_ID= item.Supplier_ID.toString()
                    baseSearchDialogCompat.dismiss()
                }).show()

        }
        binding.cardView5.setOnClickListener {
            SimpleSearchDialogCompat(this,"ادخل نوع المنج  "+"\n","search",null,
                inits(), SearchResultListener{
                        baseSearchDialogCompat, item, pos ->
                    binding.typeOfItem.setText(item.CategoryName)
                    Log.d("sqsq", item.ItemGroup_ID.toString())

                    binding.typ.setText(item.ItemGroup_ID.toString())
                    baseSearchDialogCompat.dismiss()
                }).show()

        }

    }
    private fun init(): ArrayList<Supplier>? {

        return Supplier
    }
    private fun inits(): ArrayList<TypeOfcategory>? {

        return TypeOfcategory
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {

        if (event?.action == KeyEvent.ACTION_DOWN) {
            val pressedKey = event.unicodeChar.toChar()
            barcode.append(pressedKey)
        }
        if (event?.action == KeyEvent.ACTION_DOWN && event?.keyCode == KeyEvent.KEYCODE_ENTER) {
            Toast.makeText(baseContext, barcode.toString(), Toast.LENGTH_SHORT).show()
            //  viewModel.getItemByBarcodeV1API("2",barcode.toString(),message)
          //  viewModel.getItemByBarcodeV1API("14",barcode.toString().trim(),"299")
            binding.barcode.setText("")
            binding.nameOfItem.setText("")
           // binding.typ.setText("")
           //
           // binding.size.setText("")
           // binding.sellingprice.setText("")
           // binding.Buyingprice.setText("")
           //
           // binding.barcode.setText("")
           // binding.editTextTextPersonName2.setText("")
            binding.barcode.setText(barcode.trim())

            barcode.delete(0, barcode.length)
        }

        return super.dispatchKeyEvent(event)
    }
    fun isvaled(): Boolean? {
        if ( binding.nameOfItem.text.toString().isEmpty()) {
            binding.nameOfItem.setError("Enter phone")
            return false
        }
        if ( binding.Ids.text.toString().isEmpty()) {
            binding.Ids.setError("Enter phone")
            Toast.makeText(baseContext, "اضف الموزع ", Toast.LENGTH_SHORT).show()

            return false
        }

        if ( binding.typ.text.toString().isEmpty()) {
          // Toast.makeText(this,"it1",Toast.LENGTH_SHORT)
          // Toast.makeText(applicationContext,"it2",Toast.LENGTH_SHORT)
          // Toast.makeText(applicationContext,"it3",Toast.LENGTH_SHORT)
           Toast.makeText(baseContext, "اضف نوع المنتج ", Toast.LENGTH_SHORT).show()
            binding.typ.setError("Enter pass")
            return false
        }
        if (  binding.size.text.toString().isEmpty()) {
            binding.size.setError("Enter pass")
            return false
        }
        if (  binding.sellingprice.text.toString().isEmpty()) {
            binding.sellingprice.setError("Enter pass")
            return false
        }
        if (  binding.Buyingprice.text.toString().isEmpty()) {
            binding.Buyingprice.setError("Enter pass")
            return false
        }
        if (      binding.barcode.text.toString().isEmpty()) {
            binding.barcode.setError("Enter pass")
            return false
        }
        run { return true }
    }
}