package com.tbi.supplierplus

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.tbi.supplierplus.business.pojo.Items
import com.tbi.supplierplus.business.pojo.billModels.SaleingBill
import com.tbi.supplierplus.databinding.ActivityBill2Binding
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.datasource.requests.State
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import com.tbi.supplierplus.framework.ui.sales.customer_profile.SaleingBillAdpter
import dagger.hilt.android.AndroidEntryPoint
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@AndroidEntryPoint

class BillActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityBill2Binding
  //  val viewModel: SalesViewModel by viewModels()
//  lateinit var viewModel: SalesViewModel
    //  var dialog: Dialog? = null
  lateinit var viewModel:SalesViewModel
    var list= ArrayList<Items>()
   // lateinit var barcode:String

    var listBill=ArrayList<SaleingBill>()

  //  const val QR_ACTION: String = "android.intent.ACTION_DECODE_DATA"
  //  const val QR_EXTRA: String = "barcode_string"
    private val barcode = StringBuffer()
   // var  barcode:String=""
   lateinit var message:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill2)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_bill2);
          viewModel = ViewModelProvider(this).get(SalesViewModel::class.java)

        // val viewModel: SalesViewModel by viewModels()
     //   binding=ActivityBill2Binding.inflate(R.layout.activity_bill2)
       // viewModel = ViewModelProvider(this).get(SalesViewModel::class.java)
      // val  ids=arguments?.getString("id")
        message     = intent.getStringExtra("Customer_ID").toString()
        val message2      = intent.getStringExtra("Unpaid_deferred")
        val CompanyName = intent.getStringExtra("CompanyName")
        Log.d("messagee",message.toString())


        //  Toast.makeText(activity,ids.toString(), Toast.LENGTH_LONG).show()

        binding.textView77.setText(CompanyName+" / "+"مديونيه:  "+message2)
        supportActionBar?.hide()
        binding.Totalssz4.setText(message2)
        //  binding.viewModel = viewModel
        //  binding.lifecycleOwner = this
        // Inflate the layout for this fragment
        //    Toast.makeText(activity,"item",Toast.LENGTH_LONG).show()
        //  val  id=arguments?.getString("id")


        var adapte= SaleingBillAdpter()

       // binding.billRecycler1.adapter = adapte
        binding.billRecycler1.adapter = adapte

        binding.textView23.setOnClickListener {
            // Initialize dialog
            SimpleSearchDialogCompat(this,"ادخل اسم المنتج  "+"\n","search",null,
                inits(), SearchResultListener{
                        baseSearchDialogCompat, item, pos ->

                    val sizez=     item.size

                   val split= DialogBill(item.item)


                    val code1  =item.Item_ID.toString()
                    val dialog = this?.let { it1 -> Dialog(it1) }
                    dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    //بيعمل بلوك للباك جراوند
                    //  dialog?.setCancelable(false)
                    dialog?.setContentView(R.layout.dilog_bill_row)
                    dialog?.getWindow()?.setLayout(550,850)
                    dialog?.show()



                    lateinit var tv_name: TextView
                    tv_name= dialog?.findViewById(R.id.textView57)!!
                    tv_name.setText(split.name)

                    lateinit var tv_group: TextView
                    tv_group= dialog?.findViewById(R.id.textView52)!!
                    tv_group.setText(split.group)

                    lateinit var tv_sur: TextView
                    tv_sur= dialog?.findViewById(R.id.textView54)!!
                    tv_sur.setText(split.sur)

                    lateinit var tv_size: TextView
                    tv_size= dialog?.findViewById(R.id.textView56)!!
                    tv_size.setText(split.size)



                    lateinit var tv_price: TextView
                    tv_price= dialog?.findViewById(R.id.textView53)!!
                    tv_price.setText(item.CustomerSellingPrice.toString())

                    lateinit var code: TextView
                    code= dialog?.findViewById(R.id.textView55)!!
                    code.setText(item.Item_ID.toString())



                    lateinit     var NumberOfUnits: TextInputEditText
                    NumberOfUnits=dialog?.findViewById(R.id.counterEditTextm)

                    lateinit     var sizesEditText: TextInputEditText
                    sizesEditText=dialog?.findViewById(R.id.sizesEditTextm)
                    sizesEditText.setText(item.size.toString())
                    // NumberOfUnits.text.toString()

                    lateinit var discountEditTextk: EditText
                    discountEditTextk=dialog.findViewById(R.id.discountEditTextk)
                    var discount=   discountEditTextk.text.toString()
//////



                    lateinit var buttonAddToReturnsBill: Button
                    buttonAddToReturnsBill=dialog.findViewById(R.id.buttonm)

                    lateinit var buttonAddToSalingBill: Button
                    buttonAddToSalingBill=dialog.findViewById(R.id.button2b)

                    buttonAddToSalingBill.setOnClickListener {


                        var price=item.Price_ID.toString()
                        Log.d("roiuwk",item.Price_ID.toString())
                        Log.d("roiuwk",item.CustomerSellingPrice.toString())
                        Log.d("roiuwk",item.getPrice_ID())

                        var x:Int=Integer.parseInt(NumberOfUnits.text.toString())

                        var z=x.toDouble()


                        var TotalPrice: Double
                        TotalPrice=item.CustomerSellingPrice .toDouble()*z-discountEditTextk.text.toString().toDouble()


                        listBill.add(
                            SaleingBill(
                                1,
                            "1"
                            ,split.name
                            , NumberOfUnits.text.toString()
                            ,sizez.toString()
                            ,item.CustomerSellingPrice.toString()
                            ,discountEditTextk.text.toString()
                            ,TotalPrice.toString()
                            , code1
                            ,item.Supplier_ID.toString()
                            ,item.Supply_Price.toString()
                            ,1.0
                            ,"  بيع :"
                            ,""
                        )
                        )
                        var s=0.0
                        for (item in listBill){
                            if (item.TransactionType==1){
                                var z=       item.TotalPrice.toDouble()
                                s =s+ z
                            }


                          //  Log.d("qqqqqq", item.TotalPrice)

                            //s++
                        }
                       // Log.d("qqqqqq",s.toString())

                        binding.Totalss.setText(s.toString())


                        val totals= binding.Totalss.text.toString().toDouble()-
                                binding.Totalss2.text.toString().toDouble()+
                                binding.Totalssz4.text.toString().toDouble()
                              //  binding. billDiscountEditText.text.toString().toDouble()


                        // binding   billDiscountEditText?.text?.toString().toDouble()
                        binding.Totalss4.setText(
                            totals.toString()
                        )
                      //  val adabter = testAdepterR(this, listBill)

                      //binding.billRecycler1.adapter = adapte
                      adapte.submitList(listBill)
                        adapte.notifyItemInserted(listBill.size-1)
                   //   binding.TotalafterDiscaunt.setText(totals.toString())
                        dialog.dismiss()

                    }
                    buttonAddToReturnsBill.setOnClickListener {
                        Log.d("jhswjllq","ReturnsBill")


                        var price=item.CustomerSellingPrice.toString()
                        Log.d("price",price)

                        var x:Int=Integer.parseInt(NumberOfUnits.text.toString())
                        Log.d("price",x.toString())

                        var z=x.toDouble()
                        Log.d("price",z.toString())


                        var TotalPrice: Double
                        TotalPrice=price .toDouble()*z
                        Log.d("price",TotalPrice.toString())
                        Log.d("price",item.CustomerSellingPrice.toString())


                        var PriceAfterEditSize: Double
                        PriceAfterEditSize= sizesEditText.text.toString().toDouble()*TotalPrice.toString().toDouble()


                        var  TotalPriceAfterEditSize: Double
                        TotalPriceAfterEditSize= PriceAfterEditSize / item.size.toString().toDouble()
                        listBill.add(
                            SaleingBill(
                                0,
                                "1"
                                , split.name
                                , NumberOfUnits.text.toString()
                                , sizesEditText.text.toString()
                                ,item.CustomerSellingPrice.toString()
                                ,discountEditTextk.text.toString()
                                ,TotalPriceAfterEditSize.toString()
                                , code1
                                ,item.Supplier_ID.toString()
                                ,item.Supply_Price.toString()
                                ,1.0
                                ,""
                                ,"  مرتجع :   "
                            )
                        )
                        var TotalPrices=0.0
                        for (item in listBill){
                            if (item.TransactionType==0){
                                Log.d("qqqqqq", item.TotalPrice)
                                var z=       item.TotalPrice.toDouble()
                                TotalPrices =TotalPrices+ z
                            }
                        }
                            binding.Totalss2.setText(TotalPrices.toString())

                            val totals= binding.Totalss.text.toString().toDouble()-
                                    binding.Totalss2.text.toString().toDouble()+

                                   // binding. billDiscountEditText.text.toString().toDouble()

                                    binding.Totalssz4.text.toString().toDouble()
                                // binding   billDiscountEditText?.text?.toString().toDouble()
                            binding.Totalss4.setText(
                                totals.toString()
                            )
                         //   Log.d("qqqqqq", item.TotalPrice)

                            //s++
                        adapte.submitList(listBill)

                        adapte.notifyDataSetChanged()

                        adapte.notifyItemInserted(listBill.size-1)

                      //  binding.Totalss2.setText(s.toString())

                    //    binding.billRecycler1.adapter = adapte
                    //    adapte.submitList(listBill)
                        dialog.dismiss()
                    }


                    //        //بيعمل بلوك للباك جراوند
                    //        //  dialog?.setCancelable(false)
                    //        dialog?.setContentView(R.layout.dilog_bill_row)

                    baseSearchDialogCompat.dismiss()
                }).show()

        }



          viewModel.getAllItemss( message!!.toInt())
      // viewModel.getProducers  .observe(this){

      // }
        viewModel.getProducers  .observe(this){

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

     //  viewModel.print.observe(this) {
     //      if (it) {
     //          print()
     //          viewModel.print.value = false
     //      }
     //  }
      //  binding.Totalss4

       // viewModel.getItemByBarcodeV1AP("w","w","d")
     //  viewModel.getItemByBarcodeLiveData.observe(this){
     //
     //  }
        viewModel.getItemByBarcodeLiveData.observe(this) {

            when( it){

                is State.Loading -> Toast.makeText(baseContext, it.toLoading(), Toast.LENGTH_SHORT).show()

                //Log.d("makeText","Loading")

                is State.Success ->if (it.data.State==1){
                    Log.d("makeText", it.data.Item!!.item)
                    Log.d("makeText",it.data.Message)
                       it.data.Item
                      var item=it.data.Item
                      val split = DialogBill(it.data.Item.item)
                      val dialog = this?.let { it1 -> Dialog(it1) }
                      dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                      //بيعمل بلوك للباك جراوند
                      //  dialog?.setCancelable(false)
                      dialog?.setContentView(R.layout.dilog_bill_row)
                    dialog?.getWindow()?.setLayout(550,850)
                      dialog?.show()
                      val sizez=     item.size
                      lateinit var tv_group: TextView
                      tv_group= dialog?.findViewById(R.id.textView52)!!
                      tv_group.setText(split.group)

                      lateinit var tv_sur: TextView
                      tv_sur= dialog?.findViewById(R.id.textView54)!!
                      tv_sur.setText(split.sur)


                      lateinit var tv_name: TextView
                      tv_name = dialog?.findViewById(R.id.textView57)!!
                      tv_name.setText(split.name)
                      lateinit var tv_size: TextView
                      tv_size= dialog?.findViewById(R.id.textView56)!!

                      tv_size.setText(split.size)

                      tv_size = dialog?.findViewById(R.id.textView56)!!
                      tv_size.setText(it.data.Item.Price_ID.toString())
                      lateinit var tv_price: TextView
                      tv_price = dialog?.findViewById(R.id.textView53)!!
                      tv_price.setText(it.data.Item.CustomerSellingPrice.toString())

                      lateinit var buttonAddToReturnsBill: Button
                      buttonAddToReturnsBill = dialog.findViewById(R.id.buttonm)
                      lateinit     var NumberOfUnits: TextInputEditText
                      NumberOfUnits=dialog?.findViewById(R.id.counterEditTextm)
                      // NumberOfUnits.text.toString()

                      lateinit var discountEditTextk: EditText
                      discountEditTextk=dialog.findViewById(R.id.discountEditTextk)
                      var discount=   discountEditTextk.text.toString()

                      lateinit var buttonAddToSalingBill: Button
                      buttonAddToSalingBill = dialog.findViewById(R.id.button2b)

                      lateinit     var sizesEditText: TextInputEditText
                      sizesEditText=dialog?.findViewById(R.id.sizesEditTextm)
                      sizesEditText.setText(item.size.toString())

                      buttonAddToSalingBill.setOnClickListener {

                          var price=item.Price_ID.toString()
                          Log.d("roiuwk",item.Price_ID.toString())
                          Log.d("roiuwk",item.CustomerSellingPrice.toString())
                          Log.d("roiuwk",item.getPrice_ID())

                          var x:Int=Integer.parseInt(NumberOfUnits.text.toString())

                          var z=x.toDouble()


                          var TotalPrice: Double
                          TotalPrice=item.CustomerSellingPrice .toDouble()*z-discountEditTextk.text.toString().toDouble()


                          listBill.add(
                              SaleingBill(
                                  1,
                                  "1",
                                  split .name,
                                  NumberOfUnits.text.toString(),
                                  sizez.toString(),
                                  item.CustomerSellingPrice.toString(),
                                  discountEditTextk.text.toString(),
                                  TotalPrice.toString(),
                                  item.Item_ID.toString(),
                                  item.Supplier_ID.toString(),
                                  item.Supply_Price.toString(),
                                  1.0,
                                  " بيع :",
                                  "    "
                              )
                          )
                        // listBill.add(
                        //     SaleingBill(
                        //         1,
                        //         "1",
                        //         split.name,
                        //         NumberOfUnits.text.toString().trim(),
                        //         sizez.toString(),
                        //         item.CustomerSellingPrice.toString().trim(),
                        //         discountEditTextk.text.toString().trim(),
                        //         TotalPrice.toString().trim(),
                        //         item.Item_ID.toString().trim(),
                        //         item.Supplier_ID.toString().trim(),
                        //         item.Supply_Price.toString().trim(),
                        //         1.0,
                        //         "  بيع :",
                        //         ""
                        //     )
                        // )


                          var s = 0.0
                          for (item in listBill) {
                              if (item.TransactionType == 1) {
                                  var z = item.TotalPrice.toDouble()
                                  s = s + z
                              }


                              //  Log.d("qqqqqq", item.TotalPrice)

                              //s++
                          }
                          // Log.d("qqqqqq",s.toString())

                          binding.Totalss.setText(s.toString())


                          val totals = binding.Totalss.text.toString().toDouble() -
                                  binding.Totalss2.text.toString().toDouble()

                                  //  binding. billDiscountEditText.text.toString().toDouble()
                                  +
                                  binding.Totalssz4.text.toString().toDouble()
                          // binding   billDiscountEditText?.text?.toString().toDouble()
                          binding.Totalss4.setText(totals.toString())
                     //     binding.billRecycler1.adapter = adapte
                        //  adapte.submitList(listBill)
                          // binding.TotalafterDiscaunt.setText(totals.toString())

                          adapte.submitList(listBill)

                          adapte.notifyDataSetChanged()

                          adapte.notifyItemInserted(listBill.size-1)
                          dialog.dismiss()

                      }
                      buttonAddToReturnsBill.setOnClickListener {

                          Log.d("jhswjllq","ReturnsBill")
                          var price = item.CustomerSellingPrice.toString()
                          Log.d("price", price)

                          var x: Int = Integer.parseInt(NumberOfUnits.text.toString())
                          Log.d("price", x.toString())

                          var z = x.toDouble()
                          Log.d("price", z.toString())


                          var TotalPrice: Double
                          TotalPrice = price.toDouble() * z
                          Log.d("price", TotalPrice.toString())
                          Log.d("price", item.CustomerSellingPrice.toString())

                          // listReturns.add(
                          //     ReturnBill( "1"
                          //     , name
                          //     , NumberOfUnits.text.toString()
                          //     ,TotalPrice.toString()
                          //     ,code1
                          // )
                          // )

                          var PriceAfterEditSize: Double
                          PriceAfterEditSize= sizesEditText.text.toString().toDouble()*TotalPrice.toString().toDouble()


                          var  TotalPriceAfterEditSize: Double
                          TotalPriceAfterEditSize= PriceAfterEditSize / item.size.toString().toDouble()


                          listBill.add(
                              SaleingBill(
                                  0,
                                  "1",
                                  item.item,
                                  NumberOfUnits.text.toString(),
                                  sizesEditText.text.toString(),
                                  item.CustomerSellingPrice.toString(),
                                  discountEditTextk.text.toString(),
                                  TotalPriceAfterEditSize.toString(),
                                  item.Item_ID.toString(),
                                  item.Supplier_ID.toString(),
                                  item.Supply_Price.toString(),
                                  1.0,
                                  "",
                                  "  مرتجع :   "
                              )
                          )
                          var TotalPrices = 0.0
                          for (item in listBill) {
                              if (item.TransactionType == 0) {
                                  Log.d("qqqqqq", item.TotalPrice)
                                  var z = item.TotalPrice.toDouble()
                                  TotalPrices = TotalPrices + z
                              }
                          }
                          binding.Totalss2.setText(TotalPrices.toString())

                                  val totals= binding.Totalss.text.toString().toDouble()-
                                          binding.Totalss2.text.toString().toDouble()
                                          // binding. billDiscountEditText.text.toString().toDouble()
                                          +
                                          binding.Totalssz4.text.toString().toDouble()
                                  // binding   billDiscountEditText?.text?.toString().toDouble()
                                  binding.Totalss4.setText(
                                      totals.toString()
                                  )
                                  //   Log.d("qqqqqq", item.TotalPrice)

                                  //s++
                          adapte.submitList(listBill)

                          adapte.notifyDataSetChanged()

                          adapte.notifyItemInserted(listBill.size-1)
                                  //  binding.Totalss2.setText(s.toString())

                               //   binding.billRecycler1.adapter = adapte
                                //  adapte.submitList(listBill)
                                  dialog.dismiss()
                      }

                } else{
                    Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()
                    Log.d("makeText",it.data.Message)

                   // it.data.Message
                }
                //Log.d("makeText","Success")
                is State.Error -> Toast.makeText(baseContext, it.messag, Toast.LENGTH_SHORT).show()
                    //Log.d("makeText","Error")

            }

         //  Toast.makeText(baseContext, it.item, Toast.LENGTH_SHORT).show()

         //  var item=it
         //  val split = DialogBill(it.item)
         //  val dialog = this?.let { it1 -> Dialog(it1) }
         //  dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
         //  //بيعمل بلوك للباك جراوند
         //  //  dialog?.setCancelable(false)
         //  dialog?.setContentView(R.layout.dilog_bill_row)
         //  dialog?.getWindow()?.setLayout(700, 800)
         //  dialog?.show()
         //  val sizez=     item.size
         //  lateinit var tv_group: TextView
         //  tv_group= dialog?.findViewById(R.id.textView52)!!
         //  tv_group.setText(split.group)

         //  lateinit var tv_sur: TextView
         //  tv_sur= dialog?.findViewById(R.id.textView54)!!
         //  tv_sur.setText(split.sur)


         //  lateinit var tv_name: TextView
         //  tv_name = dialog?.findViewById(R.id.textView57)!!
         //  tv_name.setText(split.name)
         //  lateinit var tv_size: TextView
         //  tv_size= dialog?.findViewById(R.id.textView56)!!

         //  tv_size.setText(split.size)

         //  tv_size = dialog?.findViewById(R.id.textView56)!!
         //  tv_size.setText(it.Price_ID.toString())
         //  lateinit var tv_price: TextView
         //  tv_price = dialog?.findViewById(R.id.textView53)!!
         //  tv_price.setText(it.CustomerSellingPrice.toString())

         //  lateinit var buttonAddToReturnsBill: Button
         //  buttonAddToReturnsBill = dialog.findViewById(R.id.buttonm)
         //  lateinit     var NumberOfUnits: TextInputEditText
         //  NumberOfUnits=dialog?.findViewById(R.id.counterEditTextm)
         //  // NumberOfUnits.text.toString()

         //  lateinit var discountEditTextk: EditText
         //  discountEditTextk=dialog.findViewById(R.id.discountEditTextk)
         //  var discount=   discountEditTextk.text.toString()

         //  lateinit var buttonAddToSalingBill: Button
         //  buttonAddToSalingBill = dialog.findViewById(R.id.button2b)

         //  lateinit     var sizesEditText: TextInputEditText
         //  sizesEditText=dialog?.findViewById(R.id.sizesEditTextm)
         //  sizesEditText.setText(item.size.toString())
         //
         //  buttonAddToSalingBill.setOnClickListener {

         //      var price=item.Price_ID.toString()
         //      Log.d("roiuwk",item.Price_ID.toString())
         //      Log.d("roiuwk",item.CustomerSellingPrice.toString())
         //      Log.d("roiuwk",item.getPrice_ID())

         //      var x:Int=Integer.parseInt(NumberOfUnits.text.toString())

         //      var z=x.toDouble()


         //      var TotalPrice: Double
         //      TotalPrice=item.CustomerSellingPrice .toDouble()*z-discountEditTextk.text.toString().toDouble()


         //      listBill.add(
         //          SaleingBill(
         //              1,
         //              "1",
         //              split .name,
         //              NumberOfUnits.text.toString(),
         //              sizez.toString(),
         //              item.CustomerSellingPrice.toString(),
         //              discountEditTextk.text.toString(),
         //              TotalPrice.toString(),
         //              item.Item_ID.toString(),
         //              item.Supplier_ID.toString(),
         //              item.Supply_Price.toString(),
         //              1.0,
         //              " بيع :",
         //              "    "
         //          )
         //      )
         //    // listBill.add(
         //    //     SaleingBill(
         //    //         1,
         //    //         "1",
         //    //         split.name,
         //    //         NumberOfUnits.text.toString().trim(),
         //    //         sizez.toString(),
         //    //         item.CustomerSellingPrice.toString().trim(),
         //    //         discountEditTextk.text.toString().trim(),
         //    //         TotalPrice.toString().trim(),
         //    //         item.Item_ID.toString().trim(),
         //    //         item.Supplier_ID.toString().trim(),
         //    //         item.Supply_Price.toString().trim(),
         //    //         1.0,
         //    //         "  بيع :",
         //    //         ""
         //    //     )
         //    // )


         //      var s = 0.0
         //      for (item in listBill) {
         //          if (item.TransactionType == 1) {
         //              var z = item.TotalPrice.toDouble()
         //              s = s + z
         //          }


         //          //  Log.d("qqqqqq", item.TotalPrice)

         //          //s++
         //      }
         //      // Log.d("qqqqqq",s.toString())

         //      binding.Totalss.setText(s.toString())


         //      val totals = binding.Totalss.text.toString().toDouble() -
         //              binding.Totalss2.text.toString().toDouble()

         //              //  binding. billDiscountEditText.text.toString().toDouble()
         //              +
         //              binding.Totalssz4.text.toString().toDouble()
         //      // binding   billDiscountEditText?.text?.toString().toDouble()
         //      binding.Totalss4.setText(totals.toString())
         // //     binding.billRecycler1.adapter = adapte
         //    //  adapte.submitList(listBill)
         //      // binding.TotalafterDiscaunt.setText(totals.toString())

         //      adapte.submitList(listBill)

         //      adapte.notifyDataSetChanged()

         //      adapte.notifyItemInserted(listBill.size-1)
         //      dialog.dismiss()

         //  }
         //  buttonAddToReturnsBill.setOnClickListener {

         //      Log.d("jhswjllq","ReturnsBill")
         //      var price = item.CustomerSellingPrice.toString()
         //      Log.d("price", price)

         //      var x: Int = Integer.parseInt(NumberOfUnits.text.toString())
         //      Log.d("price", x.toString())

         //      var z = x.toDouble()
         //      Log.d("price", z.toString())


         //      var TotalPrice: Double
         //      TotalPrice = price.toDouble() * z
         //      Log.d("price", TotalPrice.toString())
         //      Log.d("price", item.CustomerSellingPrice.toString())

         //      // listReturns.add(
         //      //     ReturnBill( "1"
         //      //     , name
         //      //     , NumberOfUnits.text.toString()
         //      //     ,TotalPrice.toString()
         //      //     ,code1
         //      // )
         //      // )

         //      var PriceAfterEditSize: Double
         //      PriceAfterEditSize= sizesEditText.text.toString().toDouble()*TotalPrice.toString().toDouble()


         //      var  TotalPriceAfterEditSize: Double
         //      TotalPriceAfterEditSize= PriceAfterEditSize / item.size.toString().toDouble()


         //      listBill.add(
         //          SaleingBill(
         //              0,
         //              "1",
         //              item.item,
         //              NumberOfUnits.text.toString(),
         //              sizesEditText.text.toString(),
         //              item.CustomerSellingPrice.toString(),
         //              discountEditTextk.text.toString(),
         //              TotalPriceAfterEditSize.toString(),
         //              item.Item_ID.toString(),
         //              item.Supplier_ID.toString(),
         //              item.Supply_Price.toString(),
         //              1.0,
         //              "",
         //              "  مرتجع :   "
         //          )
         //      )
         //      var TotalPrices = 0.0
         //      for (item in listBill) {
         //          if (item.TransactionType == 0) {
         //              Log.d("qqqqqq", item.TotalPrice)
         //              var z = item.TotalPrice.toDouble()
         //              TotalPrices = TotalPrices + z
         //          }
         //      }
         //      binding.Totalss2.setText(TotalPrices.toString())

         //              val totals= binding.Totalss.text.toString().toDouble()-
         //                      binding.Totalss2.text.toString().toDouble()
         //                      // binding. billDiscountEditText.text.toString().toDouble()
         //                      +
         //                      binding.Totalssz4.text.toString().toDouble()
         //              // binding   billDiscountEditText?.text?.toString().toDouble()
         //              binding.Totalss4.setText(
         //                  totals.toString()
         //              )
         //              //   Log.d("qqqqqq", item.TotalPrice)

         //              //s++
         //      adapte.submitList(listBill)

         //      adapte.notifyDataSetChanged()

         //      adapte.notifyItemInserted(listBill.size-1)
         //              //  binding.Totalss2.setText(s.toString())

         //           //   binding.billRecycler1.adapter = adapte
         //            //  adapte.submitList(listBill)
         //              dialog.dismiss()
         //  }
        }

   // viewModel.getItemByBarcodeLiveData.observe(this){
   //      val item=it
   //      Log.d("messagee",it.item.toString())


   //      val dialog = this?.let { it1 -> Dialog(it1) }
   //      dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
   //      //بيعمل بلوك للباك جراوند
   //      //  dialog?.setCancelable(false)
   //      dialog?.setContentView(R.layout.dilog_bill_row)
   //      dialog?.getWindow()?.setLayout(1000,1800)
   //      dialog?.show()


   //      lateinit var tv_name: TextView
   //      tv_name= dialog?.findViewById(R.id.textView57)!!
   //      tv_name.setText(it.item)

   //      lateinit var tv_group: TextView
   //      tv_group= dialog?.findViewById(R.id.textView52)!!
   //      tv_group.setText(it.CustomerSellingPrice.toString())

   //      lateinit var tv_sur: TextView
   //      tv_sur= dialog?.findViewById(R.id.textView54)!!
   //      tv_sur.setText(it. Item_ID)

   //      lateinit var tv_size: TextView
   //      tv_size= dialog?.findViewById(R.id.textView56)!!
   //      tv_size.setText(item.Price_ID.toString())



   //      lateinit var tv_price: TextView
   //      tv_price= dialog?.findViewById(R.id.textView53)!!
   //      tv_price.setText(item.CustomerSellingPrice.toString())

   //      lateinit var code: TextView
   //      code= dialog?.findViewById(R.id.textView55)!!
   //      code.setText(item.Item_ID.toString())



   //      lateinit     var NumberOfUnits: TextInputEditText
   //      NumberOfUnits=dialog?.findViewById(R.id.counterEditTextm)
   //      // NumberOfUnits.text.toString()

   //      lateinit var discountEditTextk: EditText
   //      discountEditTextk=dialog.findViewById(R.id.discountEditTextk)
   //      var discount=   discountEditTextk.text.toString()



   //      lateinit var buttonAddToReturnsBill: Button
   //      buttonAddToReturnsBill=dialog.findViewById(R.id.buttonm)

   //      lateinit var buttonAddToSalingBill: Button
   //      buttonAddToSalingBill=dialog.findViewById(R.id.button2b)
   //      buttonAddToSalingBill.setOnClickListener {


   //          var price=item.Price_ID.toString()
   //          Log.d("roiuwk",item.Price_ID.toString())
   //          Log.d("roiuwk",item.CustomerSellingPrice.toString())
   //          Log.d("roiuwk",item.getPrice_ID())

   //          var x:Int=Integer.parseInt(NumberOfUnits.text.toString())

   //          var z=x.toDouble()


   //          var TotalPrice: Double
   //          TotalPrice=item.CustomerSellingPrice .toDouble()*z-discountEditTextk.text.toString().toDouble()


   //          listBill.add(
   //              SaleingBill(
   //                  1,
   //                  "1"
   //                  ,item.item
   //                  , NumberOfUnits.text.toString()
   //                  ,item.Price_ID.toString()
   //                  ,item.CustomerSellingPrice.toString()
   //                  ,discountEditTextk.text.toString()
   //                  ,TotalPrice.toString()
   //                  , item. Item_ID.toString()
   //                  ,item.Supplier_ID.toString()
   //                  ,item.Supply_Price.toString()
   //                  ,1.0
   //                  ,"منتج بيع"
   //                  ,""
   //              )
   //          )
   //          var s=0.0
   //          for (item in listBill){
   //              if (item.TransactionType==1){
   //                  var z=       item.TotalPrice.toDouble()
   //                  s =s+ z
   //              }


   //              //  Log.d("qqqqqq", item.TotalPrice)

   //              //s++
   //          }
   //          // Log.d("qqqqqq",s.toString())

   //          binding.Totalss.setText(s.toString())


   //          val totals= binding.Totalss.text.toString().toDouble()-
   //                  binding.Totalss2.text.toString().toDouble()-

   //                  //  binding. billDiscountEditText.text.toString().toDouble()
   //                  +
   //                  binding.Totalssz4.text.toString().toDouble()
   //          // binding   billDiscountEditText?.text?.toString().toDouble()
   //          binding.Totalss4.setText(
   //              totals.toString()
   //          )
   //          binding.billRecycler1.adapter = adapte
   //          adapte.submitList(listBill)
   //          // binding.TotalafterDiscaunt.setText(totals.toString())
   //          dialog.dismiss()

   //      }
   //      buttonAddToReturnsBill.setOnClickListener {

   //          var price=item.CustomerSellingPrice.toString()
   //          Log.d("price",price)

   //          var x:Int=Integer.parseInt(NumberOfUnits.text.toString())
   //          Log.d("price",x.toString())

   //          var z=x.toDouble()
   //          Log.d("price",z.toString())


   //          var TotalPrice: Double
   //          TotalPrice=price .toDouble()*z
   //          Log.d("price",TotalPrice.toString())
   //          Log.d("price",item.CustomerSellingPrice.toString())

   //          // listReturns.add(
   //          //     ReturnBill( "1"
   //          //     , name
   //          //     , NumberOfUnits.text.toString()
   //          //     ,TotalPrice.toString()
   //          //     ,code1
   //          // )
   //          // )

   //          listBill.add(
   //              SaleingBill(
   //                  0,
   //                  "1"
   //                  , item.item
   //                  , NumberOfUnits.text.toString()
   //                  ,item.Price_ID.toString()
   //                  ,item.CustomerSellingPrice.toString()
   //                  ,discountEditTextk.text.toString()
   //                  ,TotalPrice.toString()
   //                  , item. Item_ID.toString()
   //                  ,item.Supplier_ID.toString()
   //                  ,item.Supply_Price.toString()
   //                  ,1.0
   //                  ,""
   //                  ,"منتج مرتجع"
   //              )
   //          )
   //          var TotalPrices=0.0
   //          for (item in listBill){
   //              if (item.TransactionType==0){
   //                  Log.d("qqqqqq", item.TotalPrice)
   //                  var z=       item.TotalPrice.toDouble()
   //                  TotalPrices =TotalPrices+ z
   //              }
   //          }
   //          binding.Totalss2.setText(TotalPrices.toString())

   //          val totals= binding.Totalss.text.toString().toDouble()-
   //                  binding.Totalss2.text.toString().toDouble()-
   //                  // binding. billDiscountEditText.text.toString().toDouble()
   //                  +
   //                  binding.Totalssz4.text.toString().toDouble()
   //          // binding   billDiscountEditText?.text?.toString().toDouble()
   //          binding.Totalss4.setText(
   //              totals.toString()
   //          )
   //          //   Log.d("qqqqqq", item.TotalPrice)

   //          //s++

   //          //  binding.Totalss2.setText(s.toString())

   //          binding.billRecycler1.adapter = adapte
   //          adapte.submitList(listBill)
   //          dialog.dismiss()
   //      }

   //  }

       // binding.billDiscountEditText.
     //  viewModel.getItemByBarcodeV1API("14","6224008054610","299")
     //  viewModel.getItemByBarcodeLiveData.observe(this){
     //      Log.d("messagee",it.item)
     //      Toast.makeText(baseContext, it.item, Toast.LENGTH_SHORT).show()


     //  }
        binding.printbtn.setOnClickListener {
          //  viewModel.listBill.value=listBill

        //    viewModel.v=89
            val intent = Intent(this, PaymentActivity::class.java).apply {
                putExtra("TotalSalse"      ,  binding.Totalss.text.toString()  )
                putExtra("Unpaid_deferred",   binding.Totalssz4.text.toString()  )
                putExtra("TotalReturn"     ,  binding.Totalss2.text.toString()   )
                putExtra("Total"            , binding. Totalss4.text.toString()  )
                putExtra("name"            , CompanyName  )
                putExtra("CusID"            ,  message   )


              //  val yourListAsString = Gson().toJson(listBill)
              //  putExtra("data", yourListAsString)

              // val args = Bundle()
              // args.putParcelableArrayList("ARRAYLIST", listBill)
              // putExtra("BUNDLE", args)
                putExtra("list", Gson().toJson(listBill))

               // putExtra("list" , listBill )
                // putExtra("EXTRA_MESSAGE", s)
                //    putExtra("EXTRA_MESSAGE", s)
            }
            startActivity(intent)
            Log.e("PrintError1", "e.message.toString()")


            Toast.makeText(baseContext, "it.item", Toast.LENGTH_SHORT).show()
          //  registerBillAndPrint()
           // print()
        }
        //  binding.button3.setOnClickListener {  }

       // return binding.root

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_bill_items2, container, false)
    }

//  fun addNewBill(){

//      for (item in listBill){
//          if (item.TransactionType==0){
//              var z=       item.TotalPrice.toDouble()
//              s =s+ z
//          }

//      message2
//      CompanyName

//      var CusID=message
//      var PillDiscount=binding.billDiscountEditText.text.toString()
//      var Editor="CompanyName"
//      var SalesID="2"
//      var TotalAmountBeforDiscount=
//      var TotalAmountAfterDiscount=
//      var Cash=
//      var Deferred=
//      var collection=
//      var ReturnAmount=
//      var BillDetails=

//      viewModel.setNewPill(NewBill())
//  }

    fun dilogItem(){


    }


    fun inits(): ArrayList<Items> {
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



 // private val receiver = object : BroadcastReceiver() {
 //     override fun onReceive(context: Context, intent: Intent) {
 //         try {
 //            // Timber.d("Get intent ${intent.action}")
 //             if (QR_ACTION == intent.action) {
 //                 if (intent.hasExtra(QR_EXTRA)) {
 //                     val code = intent.getStringExtra(QR_EXTRA)
 //                   //  Timber.d("New QR code $code")
 //                     // now you have qr code here
 //                 }
 //             }                    }
 //      catch (t: Throwable) {
 //         // handle errors
 //     }
 // }
//}
 override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
     return when (keyCode) {
         KeyEvent.KEYCODE_D -> {
            // moveShip(MOVE_LEFT)
             true
         }
         KeyEvent.KEYCODE_F -> {
         //    moveShip(MOVE_RIGHT)
             true
         }
         KeyEvent.KEYCODE_J -> {
           //  fireMachineGun()
             true
         }
         KeyEvent.KEYCODE_K -> {
           //  fireMissile()
             true
         }
         else -> super.onKeyUp(keyCode, event)
     }
 }
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {

        if (event?.action == KeyEvent.ACTION_DOWN) {
            val pressedKey = event.unicodeChar.toChar()
            barcode.append(pressedKey)
        }
        if (event?.action == KeyEvent.ACTION_DOWN && event?.keyCode == KeyEvent.KEYCODE_ENTER) {
            Toast.makeText(baseContext, barcode.toString(), Toast.LENGTH_SHORT).show()
          //  viewModel.getItemByBarcodeV1API("2",barcode.toString(),message)
            viewModel.getItemByBarcodeV1API("2",barcode.toString().trim(), message)
            barcode.delete(0, barcode.length)
        }

        return super.dispatchKeyEvent(event)
    }

  //  override   fun dispatchKeyEvent(event: KeyEvent?): Boolean {
//
  //        if (event?.action == KeyEvent.ACTION_DOWN) {
  //            val pressedKey = event.unicodeChar.toChar()
  //            barcode.append(pressedKey)
  //        }
  //        if (event?.action == KeyEvent.ACTION_DOWN && event?.keyCode == KeyEvent.KEYCODE_ENTER) {
  //            Toast.makeText(activity, barcode.toString(), Toast.LENGTH_SHORT).show()
  //            barcode.delete(0, barcode.length)
  //        }
//
  //        return dispatchKeyEvent(event)
  //     }



//
   // private val receiver = object : BroadcastReceiver() {
   //     override fun onReceive(context: Context, intent: Intent) {
   //         try {
   //             //  Timber.d("Get intent ${intent.action}")
   //             if (QR_ACTION == intent.action) {
   //                 if (intent.hasExtra(QR_EXTRA)) {
   //                     val code = intent.getStringExtra(QR_EXTRA)
   //                     // Timber.d("New QR code $code")
   //                     // now you have qr code here
   //                     Log.d("zzzzzzzz",code.toString())
   //               //      Toast.makeText(activity, barcode.toString(), Toast.LENGTH_SHORT).show()
//
   //                 }
   //             }
   //         } catch (t: Throwable) {
   //             // handle errors
   //         }
   //     }
   // }
   // //  fun dispatchKeyEvent(e: KeyEvent): Boolean {
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


//   override fun dispatchKeyEvent(event: KeyEvent?): Boolean {

//       if (event?.action == KeyEvent.ACTION_DOWN) {
//           val pressedKey = event.unicodeChar.toChar()
//           barcode.append(pressedKey)
//       }
//       if (event?.action == KeyEvent.ACTION_DOWN && event?.keyCode == KeyEvent.KEYCODE_ENTER) {
//           Toast.makeText(baseContext, barcode.toString(), Toast.LENGTH_SHORT).show()
//           barcode.delete(0, barcode.length)
//       }

//       return super.dispatchKeyEvent(event)
//   }
//   @Throws(IOException::class)
//   fun registerBillAndPrint() {
//       Log.e("PrintError1", "e.message.toString()")

//      // viewModelScope.launch {
//          // if (oldDeferred.value!! > collection.value!!.toFloat())
//           //    billCash.value = 0.0f

//          // if (oldDeferred.value!! <= collection.value!!.toFloat())
//         //      billCash.value =
//         //          collection.value!!.toFloat().minus(customer.value!!.deferred.toFloat())
///
//      //  salesRepository.saveBill(
//      //      bill = _bill.value!!.toList(),
//      //      customerID = customer.value!!.customerID,
//      //      billDiscount = totalItemsDiscount.value.toString(),
//      //      editor = user.value!!.userName,
//      //      salesID = user.value!!.userID,
//      //      total = totalBill.value.toString(),
//      //      cash = billCash.value.toString(),
//      //      deferred = toBeDeferredPayment.value!!,
//      //      returnAmount = totalReturns.value.toString(),
//      //      returns = _returns.value!!.toList(),
//      //      oldDeferred = customer.value!!.deferred,
//      //  ).collect {
//      //      message.value = it.message
//      //      billNo = it.billNumber
//      //  }
//           msgToPrint = "اسم العميل ".plus("customer.value!!.customerName").plus("\n")

//           msgToPrint = msgToPrint.plus("رقم الفاتورة ").plus("billNo").plus("\n")
//           msgToPrint =
//               msgToPrint.plus("بواسطة ").plus("user.value!!.userName" + "-" + "user.value!!.userID")
//                   .plus("\n")
//          // msgToPrint =
//          //     msgToPrint.plus("التوقيت ").plus(SimpleDateFormat("dd/M/yyyy").format(Date()))
//          //         .plus(" ").plus(SimpleDateFormat("hh:mm:ss").format(Date())).plus("\n")

//        //   if (_bill.value!!.isNotEmpty()) {
//               msgToPrint = msgToPrint.plus("==========الفاتورة==========")
//               msgToPrint = msgToPrint.plus("\n")
//             //  repeat(_bill.value!!.size) {
//                   msgToPrint = msgToPrint.plus("الصنف " + "_bill.value!![it].name")
//                   msgToPrint = msgToPrint.plus("   ")
//                   msgToPrint = msgToPrint.plus(
//                       "الكمية " + "_bill.value!![it].quantity"
//                   )
//                   msgToPrint = msgToPrint.plus("   ")
//                   msgToPrint = msgToPrint.plus("الوزن " +" _bill.value!![it].size")
//                   msgToPrint = msgToPrint.plus("   ")
//                   msgToPrint.plus("\n")
//                   msgToPrint = msgToPrint.plus(
//                       "السعر " + "_bill.value!![it].sellingPrice"
//                   )
//                   msgToPrint = msgToPrint.plus("   ")

//                   msgToPrint = msgToPrint.plus("الخصم " + "_bill.value!![it].discount")
//                   msgToPrint = msgToPrint.plus("   ")

//                   msgToPrint =
//                       msgToPrint.plus("الإجمالي " + "_bill.value!![it].totalSale().toString()")
//                   msgToPrint = msgToPrint.plus("\n")
//                   msgToPrint = msgToPrint.plus("##########")
//                   msgToPrint = msgToPrint.plus("\n")
//         //      }
//         //  }

//         // if (_returns.value!!.isNotEmpty()) {
//         //     msgToPrint = msgToPrint.plus("=========المرتجعات==========")
//         //     msgToPrint = msgToPrint.plus("\n")
//         //     repeat(_returns.value!!.size) {
//         //         msgToPrint = msgToPrint.plus("الصنف ${_returns.value!![it].name}")
//         //         msgToPrint = msgToPrint.plus("   ")

//         //         msgToPrint = msgToPrint.plus("الوزن ${_returns.value!![it].size}")
//         //         msgToPrint = msgToPrint.plus("   ")

//         //         msgToPrint = msgToPrint.plus("الإجمالي ${_returns.value!![it].totalReturn()}")
//         //         msgToPrint = msgToPrint.plus("\n")
//         //         msgToPrint = msgToPrint.plus("##########")
//         //         msgToPrint = msgToPrint.plus("\n")
//         //     }
//         // }

//       //    msgToPrint = msgToPrint.plus("اجمالي الفاتورة قبل الخصم و المرتجعات ")
//       //        .plus(sales.value.toString())
//       //        .plus("\n")
//       //    msgToPrint = msgToPrint.plus("اجمالي الفاتورة بعد الخصم و المرتجعات ")
//       //        .plus(totalBill.value.toString())
//       //        .plus("\n")
//       //    msgToPrint = msgToPrint.plus("اجمالي المرتجعات ").plus(totalReturns.value).plus("\n")
//       //    msgToPrint = msgToPrint.plus("اجمالي خصم الفاتورة ")
//       //        .plus(totalItemsDiscount.value!!.plus(appliedDiscount.value!!.toFloat())).plus("\n")
//       //    msgToPrint = msgToPrint.plus("المطلوب دفعه ").plus(requiredAmount.value).plus("\n")
//       //    msgToPrint = msgToPrint.plus("الباقي ").plus(toBeDeferredPayment.value).plus("\n")
//       //    if (billCash.value!!.toFloat() != 0.0f)
//       //        msgToPrint = msgToPrint.plus("كاش ").plus(billCash.value).plus("\n")
//       //    msgToPrint = msgToPrint.plus("المدفوع ").plus(collection.value).plus("\n")
///

//           msgToPrint = msgToPrint.plus("\n")
//           msgToPrint = msgToPrint.plus("\n")
//           msgToPrint = msgToPrint.plus("Powered by")
//           msgToPrint = msgToPrint.plus("\n")
//           msgToPrint = msgToPrint.plus("\n")
//           msgToPrint = msgToPrint.plus("Technology & Business Integration")
//           msgToPrint = msgToPrint.plus("\n")
//           msgToPrint = msgToPrint.plus("\n")
//       print()
//           Log.i("MSG_TO_PRINT", msgToPrint)
//         //  print.value = true
//    //   }
//   }
//   fun print() {
//       findBT()
//       openBT()

//       sendData()

//   }

//   fun findBT() {
//       Log.e("PrintError1", "e.message.toString()")

//       try {
//           mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//           if (mBluetoothAdapter == null) {
//               viewModel.message.value = "No bluetooth adapter available"
//           }
//           if (mBluetoothAdapter?.isEnabled == false) {
//               val enableBluetooth = Intent(
//                   BluetoothAdapter.ACTION_REQUEST_ENABLE
//               )
//               startActivityForResult(enableBluetooth, 0)
//           }
//           val pairedDevices: Set<BluetoothDevice> = mBluetoothAdapter?.bondedDevices!!
//           if (pairedDevices.isNotEmpty()) {
//               for (device in pairedDevices) {

//                   // MP300 is the name of the bluetooth printer device
//                   if (device.name == "InnerPrinter") {
//                       mmDevice = device
//                       break
//                   }
//               }
//           }
//           viewModel.message.value = "Bluetooth Device Found"
//       } catch (e: NullPointerException) {
//           e.printStackTrace()
//       } catch (e: Exception) {
//           e.printStackTrace()
//       }
//   }

//   // Tries to open a connection to the bluetooth printer device
//   @Throws(IOException::class)
//   fun openBT() {
//       try {
//           // Standard SerialPortService ID
//           val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
//           mmSocket = mmDevice!!.createRfcommSocketToServiceRecord(uuid)
//           mmSocket?.connect()
//           mmOutputStream = mmSocket?.outputStream
//           mmInputStream = mmSocket?.inputStream
//           beginListenForData()
//           viewModel.message.value = "Bluetooth Opened"
//       } catch (e: NullPointerException) {
//           e.printStackTrace()
//       } catch (e: Exception) {
//           e.printStackTrace()
//       }
//   }

//   // After opening a connection to bluetooth printer device,
//   // we have to listen and check if a data were sent to be printed.
//   fun beginListenForData() {
//       try {
//           val handler = Handler()

//           // This is the ASCII code for a newline character
//           val delimiter: Byte = 10
//           stopWorker = false
//           readBufferPosition = 0
//           readBuffer = ByteArray(1024)
//           workerThread = Thread {
//               while (!Thread.currentThread().isInterrupted
//                   && !stopWorker
//               ) {
//                   try {
//                       val bytesAvailable: Int = mmInputStream!!.available()
//                       if (bytesAvailable > 0) {
//                           val packetBytes = ByteArray(bytesAvailable)
//                           mmInputStream?.read(packetBytes)
//                           for (i in 0 until bytesAvailable) {
//                               val b = packetBytes[i]
//                               if (b == delimiter) {
//                                   val encodedBytes = ByteArray(readBufferPosition)
//                                   System.arraycopy(
//                                       readBuffer, 0,
//                                       encodedBytes, 0,
//                                       encodedBytes.size
//                                   )
//                                   val data = String(
//                                       encodedBytes
//                                   )
//                                   readBufferPosition = 0
//                               } else {
//                                   readBuffer[readBufferPosition.inc()] = b
//                               }
//                           }
//                       }
//                   } catch (ex: IOException) {
//                       stopWorker = true
//                   }
//               }
//           }
//           workerThread?.start()
//       } catch (e: NullPointerException) {
//           e.printStackTrace()
//       } catch (e: Exception) {
//           e.printStackTrace()
//       }
//   }

//   /*
//* This will send data to be printed by the bluetooth printer
//*/
//   @Throws(IOException::class)
//   fun sendData() {
//       try {

//           // the text typed by the user

//           var printPic = PrintPic.getInstance()
//           printPic.init(
//               textAsBitmap(
//                   context = this!!,
//                   textSize = 18,
//                   textWidth = 370
//               )
//              // viewModel.textAsBitmap(
//              //     context = this!!,
//              //     textSize = 18,
//              //     textWidth = 370
//              // )
//           )

//           var draw = printPic.printDraw()

//           mmOutputStream!!.write(draw)
//           // tell the user data were sent
//       } catch (e: NullPointerException) {
//           e.printStackTrace()
//           Log.e("PrintError1", e.message.toString())
//       } catch (e: Exception) {
//           e.printStackTrace()
//           Log.e("PrintErro2r", e.message.toString())

//       }
//       closeBT()
//   }

//   // Close the connection to bluetooth printer.
//   @Throws(IOException::class)
//   fun closeBT() {
//       try {
//           stopWorker = true
//           mmOutputStream?.close()
//           mmInputStream?.close()
//           mmSocket?.close()
//       } catch (e: NullPointerException) {
//           e.printStackTrace()
//       } catch (e: Exception) {
//           e.printStackTrace()
//       }
//   }
//   fun textAsBitmap(textWidth: Int, textSize: Int, context: Context): Bitmap? {

//       // Get text dimensions
//       val textPaint = TextPaint(
//           Paint.ANTI_ALIAS_FLAG
//                   or Paint.LINEAR_TEXT_FLAG
//       )
//       textPaint.style = Paint.Style.FILL_AND_STROKE
//       val typeface = ResourcesCompat.getFont(context, R.font.kawkabregular)
//       textPaint.typeface = typeface
//       textPaint.color = Color.BLACK
//       textPaint.textSize = textSize.toFloat()
//       val mTextLayout = StaticLayout(
//           msgToPrint, textPaint,
//           textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
//       )

//       // Create bitmap and canvas to draw to
//       val b = Bitmap.createBitmap(textWidth, mTextLayout.height, Bitmap.Config.RGB_565)
//       val c = Canvas(b)

//       // Draw background
//       val paint = Paint(
//           (Paint.ANTI_ALIAS_FLAG
//                   or Paint.LINEAR_TEXT_FLAG)
//       )
//       paint.style = Paint.Style.FILL
//       paint.color = Color.WHITE
//       paint.typeface = typeface
//       c.drawPaint(paint)

/// Draw text
//       c.save()
//       c.translate(0f, 0f)
//       mTextLayout.draw(c)
//       c.restore()
//       return b
//   }
}




