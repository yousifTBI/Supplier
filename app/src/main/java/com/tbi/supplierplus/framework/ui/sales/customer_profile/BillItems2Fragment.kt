package com.tbi.supplierplus.framework.ui.sales.customer_profile

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.Items
import com.tbi.supplierplus.business.pojo.billModels.ReturnBill
import com.tbi.supplierplus.business.pojo.billModels.SaleingBill
import com.tbi.supplierplus.databinding.FragmentBillItems2Binding
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Pattern


class BillItems2Fragment : Fragment() {
    private lateinit var binding: FragmentBillItems2Binding
    val viewModel: SalesViewModel by activityViewModels()
  //  var dialog: Dialog? = null
    var list= ArrayList<Items>()
    lateinit var barcode:String
    val QR_ACTION: String = "android.intent.ACTION_DECODE_DATA"
    val QR_EXTRA: String = "barcode_string"
    var listBill=ArrayList<SaleingBill>()
    var listReturns=ArrayList<ReturnBill>()
//    val  ids=arguments?.getString("id")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBillItems2Binding.inflate(inflater)
        val  ids=arguments?.getString("id")

        Toast.makeText(activity,ids.toString(),Toast.LENGTH_LONG).show()

      //  binding.viewModel = viewModel
      //  binding.lifecycleOwner = this
        // Inflate the layout for this fragment
    //    Toast.makeText(activity,"item",Toast.LENGTH_LONG).show()
     //  val  id=arguments?.getString("id")


         var adapte=SaleingBillAdpter()


      binding.textView23.setOnClickListener {
            // Initialize dialog
         SimpleSearchDialogCompat(activity,"ادخل اسم المنتج  "+"\n","search",null,
             inits(),SearchResultListener{
                 baseSearchDialogCompat, item, pos ->

                 val str = item.item
                 val delim = "-"


              val arr = Pattern.compile(delim).split(str)
               //  val list = str.split(delim)
              val name=arr.get(0).toString()
              val group=arr.get(1).toString()
              val sur=arr.get(2).toString()
              val size=arr.get(3).toString()

               val code1  =item.Item_ID.toString()
                 val dialog = activity?.let { it1 -> Dialog(it1) }
                 dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                 //بيعمل بلوك للباك جراوند
                //  dialog?.setCancelable(false)
                 dialog?.setContentView(R.layout.dilog_bill_row)
                 dialog?.getWindow()?.setLayout(600,1800)
                 dialog?.show()


                 lateinit var tv_name:TextView
                 tv_name= dialog?.findViewById(R.id.textView57)!!
                 tv_name.setText(arr.get(0).toString())

                 lateinit var tv_group:TextView
                tv_group= dialog?.findViewById(R.id.textView52)!!
                tv_group.setText(arr.get(1).toString())

                lateinit var code:TextView
               code= dialog?.findViewById(R.id.textView55)!!
               code.setText(item.Item_ID.toString())


                lateinit var tv_sur:TextView
               tv_sur= dialog?.findViewById(R.id.textView54)!!
               tv_sur.setText(arr.get(2).toString())

                lateinit var tv_size:TextView
               tv_size= dialog?.findViewById(R.id.textView56)!!
               tv_size.setText(arr.get(3).toString())

                 lateinit var buttonAddToSalingBill:Button
                 buttonAddToSalingBill=dialog.findViewById(R.id.button2b)

                 lateinit     var NumberOfUnits: TextInputEditText
                 NumberOfUnits=dialog?.findViewById(R.id.counterEditTextm)
               // NumberOfUnits.text.toString()

                 lateinit var discountEditTextk:EditText
                discountEditTextk=dialog.findViewById(R.id.discountEditTextk)
                var discount=   discountEditTextk.text.toString()

                 lateinit var buttonAddToReturnsBill:Button
                 buttonAddToReturnsBill=dialog.findViewById(R.id.buttonm)


                 buttonAddToSalingBill.setOnClickListener {


                 var price=item.Price_ID.toString()

                 var x:Int=Integer.parseInt(NumberOfUnits.text.toString())

                   var z=x.toDouble()


                   var TotalPrice: Double
                  TotalPrice=item.CustomerSellingPrice.toString() .toDouble()*z

                  listBill.add(SaleingBill(
                      1
                      ,
                      "1"
                      , name
                      , NumberOfUnits.text.toString()
                      ,size
                      ,item.CustomerSellingPrice.toString()
                      ,discountEditTextk.text.toString()
                      ,TotalPrice.toString()
                      , code1
                      ,item.Supplier_ID.toString()
                      ,item.Supply_Price.toString()
                      ,1.0
                      ,"منتج بيع"
                      ,""
                  ))

                     binding.billRecycler1.adapter = adapte
                     adapte.submitList(listBill)
                     dialog.dismiss()

                 }
                 buttonAddToReturnsBill.setOnClickListener {

                     var price=item.Price_ID.toString()

                     var x:Int=Integer.parseInt(NumberOfUnits.text.toString())

                     var z=x.toDouble()


                     var TotalPrice: Double
                     TotalPrice=price .toDouble()*z
                     listReturns.add(ReturnBill( "1"
                         , name
                         , NumberOfUnits.text.toString(),
                         TotalPrice.toString(),code1
                     )
                     )

                 }

               //    //بيعمل بلوك للباك جراوند
               //    //  dialog?.setCancelable(false)

                 baseSearchDialogCompat.dismiss()
             }).show()

      }



        viewModel.getAllItemss(ids!!.toInt())
        viewModel.getProducers  .observe(viewLifecycleOwner){

                list= it as ArrayList<Items>
           Log.d("ddddd",list.get(0).getID())
         //   list.get(0).a
                //  binding.recyclerView.adapter = adapte
                //  adapte.submitList(it)
            }

    //   viewModel.getAllCustomers()
    //   viewModel.getAllCustomersLiveData.observe(viewLifecycleOwner){
    //       //   Toast.
    //       list= it as ArrayList<AllCustomers>
    //       //  binding.recyclerView.adapter = adapte
    //       //  adapte.submitList(it)
    //   }


      //  binding.button3.setOnClickListener {  }

        return binding.root

        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_bill_items2, container, false)
    }


     private fun inits(): ArrayList<Items> {
     return list

   }

    fun getInstances(): SupplierAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
           // .addCallAdapterFactory(CoroutineCallAdapterFactory())
            //  .baseUrl(TBI_BASE_URL)
            .baseUrl("http://scopos-rotana.online/")

            .build()
            .create(SupplierAPI::class.java)

    }

  // fun dispatchKeyEvent(event: KeyEvent?): Boolean {

  //    if (event?.action == KeyEvent.ACTION_DOWN) {
  //        val pressedKey = event.unicodeChar.toChar()
  //     //   barcode.append(pressedKey)
  //    }
  //    if (event?.action == KeyEvent.ACTION_DOWN && event?.keyCode == KeyEvent.KEYCODE_ENTER) {
  //        Toast.makeText(activity, barcode.toString(), Toast.LENGTH_SHORT).show()
  //     //   barcode.delete(0, barcode.length)
  //    }

  //    return dispatchKeyEvent(event)
   // }




    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            try {
                //  Timber.d("Get intent ${intent.action}")
                if (QR_ACTION == intent.action) {
                    if (intent.hasExtra(QR_EXTRA)) {
                        val code = intent.getStringExtra(QR_EXTRA)
                        // Timber.d("New QR code $code")
                        // now you have qr code here
                        Log.d("zzzzzzzz",code.toString())
                   Toast.makeText(activity, barcode.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } catch (t: Throwable) {
                // handle errors
            }
        }
    }

//  override fun minus(textData: View, position: Int) {

//      TODO("Not yet implemented")
//  }

//  override fun add(textData: View, position: Int) {
//      TODO("Not yet implemented")
//  }

//  override fun SaleingBillAdpter(): SaleingBillAdpter {
//      TODO("Not yet implemented")
//  }
    //  fun dispatchKeyEvent(e: KeyEvent): Boolean {
  //      if (e.action == KeyEvent.ACTION_DOWN) {
  //          // getItem(e.toString().trim());
  //          //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
  //          Log.i("TAG", "dispatchKeyEvent: $e")
  //          val pressedKey = e.unicodeChar.toChar()
  //          barcode += pressedKey
  //      }
  //      if (e.action == KeyEvent.ACTION_DOWN && e.keyCode == KeyEvent.KEYCODE_ENTER) {
  //          // Toast.makeText(getApplicationContext(),
  //          //                 "barcode--->>>" + barcode, Toast.LENGTH_LONG)
  //          //         .show();
  //        //  Toast.makeText(this, barcode.trim { it <= ' ' }, Toast.LENGTH_SHORT).show()
  //          //   textview.setText(barcode.trim());
  //          Log.d("TAG", barcode.trim { it <= ' ' })
  //       //   getItem(barcode.trim { it <= ' ' })
  //          barcode = ""
  //          //String curText =  textview.getText();
  //        //  textview.setText("")
  //          //textview.clearFocus()
  //      }
  //      return super.dispatchKeyEvent(e)
  //  }


}