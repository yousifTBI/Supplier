package com.tbi.supplierplus

import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.text.*
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tbi.supplierplus.business.pojo.billModels.SaleingBill
import com.tbi.supplierplus.business.pojo.bills.NewBill
import com.tbi.supplierplus.databinding.ActivityPaymentBinding
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import com.tbi.supplierplus.framework.utils.PrintPic
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*


@AndroidEntryPoint

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding

    private val barcode = StringBuffer()

    lateinit var viewModel: SalesViewModel
    var numberLists=ArrayList<SaleingBill>()
   // var listBill=ArrayList<SaleingBill>()
   var list=ArrayList<SaleingBill> ()
    var mBluetoothAdapter: BluetoothAdapter? = null
    var mmSocket: BluetoothSocket? = null
    var mmDevice: BluetoothDevice? = null

    var mmOutputStream: OutputStream? = null
    var mmInputStream: InputStream? = null
    var workerThread: Thread? = null

    lateinit var readBuffer: ByteArray
    var readBufferPosition = 0
    var counter = 0


    @Volatile
    var stopWorker = false

    var numberOfBill :Int = 0

    var msgToPrint = ""

     var CusID:String = ""
     var name:String = ""
    var Total= ""
    var TotalReturn=""
    var Unpaid_deferred=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        viewModel = ViewModelProvider(this).get(SalesViewModel::class.java)


        val TotalSalse = intent.getStringExtra("TotalSalse")
       Unpaid_deferred = intent.getStringExtra("Unpaid_deferred").toString()
        TotalReturn = intent.getStringExtra("TotalReturn").toString()
         Total = intent.getStringExtra("Total").toString()
         name = intent.getStringExtra("name").toString()
        CusID = intent.getStringExtra("CusID").toString()
     //  var list = intent.getStringExtra("data").toString()
      //  val myList = intent.getSerializableExtra("list") as ArrayList<SaleingBill>?
     //   var listPrivate: ArrayList<SaleingBill?>? = ArrayList()

        binding.cashEditText.setText("0.0")
        binding.deferredEditText.setText("0.0")
      // object : TypeToken<List<SaleingBill?>?>() {}.type
      // listPrivate = Gson().fromJson(intent.getStringExtra("list"), type)
       // Log.d("sgdgdffd",myList?.get(0)?.Items!!)
    //    val listName: List<SaleingBill> =
       //     Gson().fromJson<List<YourList>>("data", object : TypeToken<List<YourList?>?>() {}.type)

       // ArrayList<SaleingBill>=
         list = Gson().fromJson<List<SaleingBill>>(intent.getStringExtra("list"), object : TypeToken<List<SaleingBill?>?>() {}.type) as ArrayList<SaleingBill>

     //   binding.scanQra.setText(list.get(0).Items.toString())
//https://stackoverflow.com/questions/12092612/pass-list-of-objects-from-one-activity-to-other-activity-in-android

        //  val m= viewModel.v
   // Toast.makeText(baseContext, list, Toast.LENGTH_SHORT).show()

        // val challenge: ArrayList<SaleingBill> = intent.extras.getParcelableArrayList("Birds")!!
       // var numberLists=ArrayList<SaleingBill>()
//
//
        binding.TotalafterDiscaunt.setText( Total)
       // var     numberList = intent .getSerializableExtra( "list" )
               // as ArrayList<SaleingBill>
        viewModel.setNewBillTip.observe(this){
            binding.progressBar2.isGone=true
            Toast.makeText(baseContext, it.Message, Toast.LENGTH_SHORT).show()

            if (it.State==1){
               numberOfBill=it.State
               Toast.makeText(baseContext, it.Message, Toast.LENGTH_SHORT).show()
//
               GlobalScope.launch(Dispatchers.Default) {
                   withContext(Dispatchers.Default){
//
                       registerBillAndPrint(it.Message.toString())
                   }}
              // registerBillAndPrint()
               Toast.makeText(baseContext, it.Message, Toast.LENGTH_SHORT).show()

           }else if(it.State==0){
               Toast.makeText(baseContext, it.Message, Toast.LENGTH_SHORT).show()

           }

        }




        binding.Totalss.setText(TotalSalse)

        binding.Totalssz4.setText(Unpaid_deferred)
        binding.Totalss2. setText(TotalReturn)
        binding. Totalss4.setText(Total)





        binding.cancel1.setOnClickListener {

            val dialog2 = this?.let { it1 -> Dialog(it1) }
            dialog2?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            //بيعمل بلوك للباك جراوند
            //  dialog?.setCancelable(false)
            dialog2?.setContentView(R.layout.dilog_bill_row_discount)
            dialog2?.getWindow()?.setLayout(600, 800)
            dialog2?.show()


            lateinit var discount: TextInputEditText
            discount = dialog2?.findViewById(R.id.billDiscountEditText1)
            // NumberOfUnits.text.toString()

            lateinit var ok: Button
            ok = dialog2.findViewById(R.id.printbtn1)


        //   discount.addTextChangedListener(object : TextWatcher{
        //       override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //           TODO("Not yet implemented")
        //       }

        //       override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //           TODO("Not yet implemented")
        //       }

        //       override fun afterTextChanged(p0: Editable?) {

        //           var discount1 = discount.text.toString()
        //               .toDouble() + binding.billDiscountEditText.text.toString().toDouble()
        //           // binding.billDiscountEditText.setText(discount)

        //           //  var total1=binding.Totalss4.text.toString().toDouble()-discount1.toDouble()
        //           if (discount1 > binding.Totalss4.text.toString().toDouble()) {

        //               Toast.makeText(
        //                   applicationContext,
        //                   "الخصم اكبر من قيمه الفاتوره",
        //                   Toast.LENGTH_SHORT
        //               ).show()

        //           } else {
        //               //   var setbillDiscountEditText=discount1.toDouble()+ binding.billDiscountEditText.text.toString().toDouble()
        //               var total1 = binding.Totalss4.text.toString().toDouble() - discount1.toDouble()

        //               //   binding.billDiscountEditText.setText(setbillDiscountEditText.toString())
        //               binding.billDiscountEditText.setText(discount1.toString())

        //               binding.TotalafterDiscaunt.setText(total1.toString())
        //               dialog2.dismiss()
        //           }
        //       }

        //   })
            ok.setOnClickListener {


                var discount1 = discount.text.toString()
                    .toDouble() + binding.billDiscountEditText.text.toString().toDouble()
                // binding.billDiscountEditText.setText(discount)

                //  var total1=binding.Totalss4.text.toString().toDouble()-discount1.toDouble()
                if (discount1 > binding.Totalss4.text.toString().toDouble()) {

                    Toast.makeText(
                        applicationContext,
                        "الخصم اكبر من قيمه الفاتوره",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    //   var setbillDiscountEditText=discount1.toDouble()+ binding.billDiscountEditText.text.toString().toDouble()
                    var total1 = binding.Totalss4.text.toString().toDouble() - discount1.toDouble()

                    //   binding.billDiscountEditText.setText(setbillDiscountEditText.toString())
                    binding.billDiscountEditText.setText(discount1.toString())

                    binding.TotalafterDiscaunt.setText(total1.toString())
                    dialog2.dismiss()
                }

            }

            lateinit var cancel: Button
            cancel = dialog2.findViewById(R.id.cancel)

            cancel.setOnClickListener { dialog2.dismiss() }


        }

       // if(  binding.TotalafterDiscaunt.text.toString().toString().toDouble()<0) {
            binding.cashEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    Toast.makeText(applicationContext, p0.toString(), Toast.LENGTH_SHORT).show()
                    if (p0?.isEmpty() == true) {
                        binding.deferredEditText.setText("0.0")
                    } else {


                        var TotalafterDiscaunt1 =
                            binding.TotalafterDiscaunt.text.toString().toString().replace("-", "")


                        var deferd =
                            TotalafterDiscaunt1.toDouble() - binding.cashEditText.text.toString()
                                .toDouble()
                        binding.deferredEditText.setText(deferd.toString())
                    }


                }

            })

binding.progressBar2.isGone=true
        binding.printbtn.setOnClickListener {
            binding.progressBar2.isVisible=true

      //    Log.d("haaaaaaaager",   CusID
      //     +"  --" + binding.billDiscountEditText.text.toString()
      //     +"  --" + "1"
      //     +"  --" + "2"
      //     +"  --" + binding.Totalss4.text.toString()
      //     +"  --" + binding.TotalafterDiscaunt.text.toString()
      //     +"  --" + binding.cashEditText.text.toString()
      //     +"  --" + binding.deferredEditText.text.toString()
      //     +"  --" +
      //           "1"
      //     +"  --" + binding.Totalss2.text.toString())
      //    for (x in list){
      //      //  binding.textView79.setText(  " TransactionType"+"-"+x. TransactionType+"\n"
      //      //          +"\n" +" SR_No"+"-"+x.   SR_No
      //      //          +"\n"  +" Items"+"-"+x.   Items
      //      //          +"\n"  +"NumberOfUnits"+"-"+x.   NumberOfUnits
      //      //          +"\n"  +" size"+"-"+x.   size
      //      //          +"\n"  +"UnitPrice"+"-"+x.   UnitPrice
      //      //          +"\n"  +" Discount"+"-"+x.   Discount
      //      //          +"\n"  +" TotalPrice"+"-"+x.   TotalPrice
      //      //          +"\n" +"ItemID"+"-"+x.   ItemID
      //      //          +"\n" +"Suppier_id"+"-"+x.   Suppier_id
      //      //          +"\n"  +" Supply_Price"+"-"+x.   Supply_Price
      //      //          +"\n" +" itemcount"+"-"+x.   itemcount
      //      //          +"\n"  +" sals"+"-"+x.   sals
      //      //          +"\n"  +" returns"+"-"+x.   returns   )
      //        Log.d("haaaaaaaager","" +
      //                " TransactionType"+x. TransactionType+"--"
      //          +" SR_No"+x.   SR_No
      //          +" Items"+x.   Items
      //          +"NumberOfUnits"+x.   NumberOfUnits
      //          +" size"+x.   size
      //          +"UnitPrice"+x.   UnitPrice
      //          +" Discount"+x.   Discount
      //          +" TotalPrice"+x.   TotalPrice
      //          +"ItemID"+x.   ItemID
      //          +"Suppier_id"+x.   Suppier_id
      //          +" Supply_Price"+x.   Supply_Price
      //          +" itemcount"+x.   itemcount
      //          +" sals"+x.   sals
      //          +" returns"+x.   returns          )

      //    }

         //  GlobalScope.launch(Dispatchers.Default) {
         //      withContext(Dispatchers.Default){

         //          registerBillAndPrint()
         //      }}

         //  GlobalScope.launch(Dispatchers.Default) {
         //      withContext(Dispatchers.Default){

         //          registerBillAndPrint()
         //      }}
          viewModel.setNewPill(NewBill(
              CusID.trim(),
              binding.billDiscountEditText.text.toString().trim(),
              "1",
              "2",
              binding.Totalss4.text.toString().trim(),
              binding.TotalafterDiscaunt.text.toString().trim(),
              binding.cashEditText.text.toString().trim(),
              binding.deferredEditText.text.toString().trim(),
              Unpaid_deferred,
              binding.Totalss2.text.toString().trim(),
              list))

            Log.e("PrintError1", "e.message.toString()")
           //registerBillAndPrint()
            // print()
        }
    }
    @Throws(IOException::class)
    fun registerBillAndPrint(numerofBill:String) {
        Log.e("PrintError1", "e.message.toString()")



        msgToPrint = msgToPrint.plus("رقم الفاتورة ").plus(numerofBill).plus("\n")

            msgToPrint =
                msgToPrint.plus("بواسطة : ").plus("شركه البان الدوار  ")
                    .plus("\n")

        msgToPrint =
            msgToPrint.plus("س.ت  ").plus(" 173847 ")
                .plus("\n")
        msgToPrint =
            msgToPrint.plus("ت.ض : ").plus(" 679/427/597 ")
                .plus("\n")
        msgToPrint = msgToPrint.plus("التاريخ :").plus("9-10-2022 ")
        msgToPrint = msgToPrint.plus("   ")
        msgToPrint.plus("\n")
        msgToPrint =
            msgToPrint.plus("رقم الموبيل:  ").plus("01112272015 ")
                .plus("\n")

        //
      //  msgToPrint = msgToPrint.plus("\n")
      //  val split= DialogBill(name)
        //   val
        msgToPrint =
            msgToPrint.plus("اسم العميل ").plus(name)
                .plus("\n")
     //  msgToPrint = "اسم العميل ".plus(name).plus("\n")
            // msgToPrint =
            //     msgToPrint.plus("التوقيت ").plus(SimpleDateFormat("dd/M/yyyy").format(Date()))
            //         .plus(" ").plus(SimpleDateFormat("hh:mm:ss").format(Date())).plus("\n")

            //   if (_bill.value!!.isNotEmpty()) {
            msgToPrint = msgToPrint.plus("==========الفاتورة==========")
            msgToPrint = msgToPrint.plus("\n")
            //  repeat(_bill.value!!.size) {


            if (list.isEmpty()) {
            } else {

                for (listX in list) {
                    //  if (listX.TransactionType==1) {

                    msgToPrint = msgToPrint.plus(" اسم المنتج : " + listX.Items)
                    msgToPrint = msgToPrint.plus("   ")
                    msgToPrint.plus("\n")
                    msgToPrint = msgToPrint.plus("\n")
                    msgToPrint = msgToPrint.plus("النوع :   " + listX.sals +listX.returns)
                    msgToPrint = msgToPrint.plus("\n")

                    msgToPrint.plus("\n")


                    msgToPrint = msgToPrint.plus("سعر الوحده :   " + listX.UnitPrice)
                    msgToPrint = msgToPrint.plus("\n")

                    msgToPrint.plus("\n")

                    msgToPrint = msgToPrint.plus("الخصم على هذا المنج :   " + listX.Discount)
                    msgToPrint.plus("\n")
                    msgToPrint = msgToPrint.plus("\n")

                    msgToPrint = msgToPrint.plus("عدد المباع من المنتج:   " + listX.NumberOfUnits)
                    msgToPrint.plus("\n")
                    msgToPrint = msgToPrint.plus("\n")


                    msgToPrint = msgToPrint.plus("الاجمالى :  " +listX.TotalPrice)
                    msgToPrint.plus("\n")
                    msgToPrint = msgToPrint.plus("\n")
                    msgToPrint = msgToPrint.plus("________________________")
                    // }else{
                    //     msgToPrint = msgToPrint.plus("\n")
                    //     //  repeat(_bill.value!!.size) {
                    //     msgToPrint = msgToPrint.plus("الصنف " + "")
                    //     msgToPrint = msgToPrint.plus("منتجات بقاله ")
                    //     msgToPrint = msgToPrint.plus("")
                    //     //"الكمية " + "--"
                    //     msgToPrint = msgToPrint.plus("\n")

                    //     msgToPrint = msgToPrint.plus("\n")
                    //     msgToPrint = msgToPrint.plus("منتج مرتجع ")
                    //     msgToPrint = msgToPrint.plus("")
                    //     //"الكمية " + "--"
                    //     msgToPrint = msgToPrint.plus("\n")

                    //     msgToPrint = msgToPrint.plus("\n")

                    //     msgToPrint = msgToPrint.plus(" اسم المنتج :   " + listX.Items)
                    //     msgToPrint = msgToPrint.plus("   ")
                    //     msgToPrint.plus("\n")
                    //     msgToPrint = msgToPrint.plus("\n")
                    //     msgToPrint.plus("\n")
                    //     msgToPrint = msgToPrint.plus("\n")


                    //     //    msgToPrint = msgToPrint.plus("الوزن " +" _bill.value!![it].size")
                    //     msgToPrint = msgToPrint.plus("سعر الوحده :   " + listX.UnitPrice)
                    //     msgToPrint = msgToPrint.plus("\n")

                    //     msgToPrint.plus("\n")

                    //     msgToPrint = msgToPrint.plus("الخصم على هذا المنج :   " + listX.Discount)
                    //     msgToPrint.plus("\n")
                    //     msgToPrint = msgToPrint.plus("\n")

                    //     msgToPrint = msgToPrint.plus("عدد المباع من المنتج:   " + listX.NumberOfUnits)
                    //     msgToPrint.plus("\n")
                    //     msgToPrint = msgToPrint.plus("\n")


                    //     msgToPrint = msgToPrint.plus("الاجمالى من المنتج:   " + listX.TotalPrice)
                    //     msgToPrint.plus("\n")
                    //     msgToPrint = msgToPrint.plus("\n")
                    //     msgToPrint.plus("\n")
                    //     msgToPrint = msgToPrint.plus("\n")
                    //     msgToPrint.plus("\n")
                    //     msgToPrint = msgToPrint.plus("________________________")
                    // }
                }
            }

            msgToPrint = msgToPrint.plus("\n")
            msgToPrint = msgToPrint.plus("اجمالي المبيعات :   " + binding.Totalss.text.toString())
            msgToPrint = msgToPrint.plus("   ")
            msgToPrint.plus("\n")
            msgToPrint = msgToPrint.plus("\n")

            //    msgToPrint = msgToPrint.plus("الوزن " +" _bill.value!![it].size")
            msgToPrint = msgToPrint.plus("المديونيه السابقه :   " + Unpaid_deferred)
            msgToPrint = msgToPrint.plus("\n")

            msgToPrint.plus("\n")

            msgToPrint = msgToPrint.plus("اجمالى المرتجعات :   " + TotalReturn)
            msgToPrint.plus("\n")
            msgToPrint = msgToPrint.plus("\n")
            msgToPrint = msgToPrint.plus("\n")

            msgToPrint = msgToPrint.plus("مديونيه سابقه :   " + Unpaid_deferred)
            msgToPrint.plus("\n")
            msgToPrint = msgToPrint.plus("\n")

            msgToPrint = msgToPrint.plus("الاجمالى قبل الخصم:   " + Total)
            msgToPrint.plus("\n")
            msgToPrint = msgToPrint.plus("\n")

            msgToPrint =
                msgToPrint.plus("الخصم :               " + binding.billDiscountEditText.text.toString())


            msgToPrint = msgToPrint.plus("\n")

            msgToPrint.plus("\n")
            msgToPrint =
                msgToPrint.plus("المطلوب دفعه :       " + binding.TotalafterDiscaunt.text.toString())
            msgToPrint = msgToPrint.plus("\n")

            msgToPrint.plus("\n")
            msgToPrint =
                msgToPrint.plus("المدفوع:              " + binding.cashEditText.text.toString())
            msgToPrint = msgToPrint.plus("  ")
            msgToPrint = msgToPrint.plus("\n")

            msgToPrint.plus("\n")
            msgToPrint =
                msgToPrint.plus("الباقى :      " + binding.deferredEditText.text.toString())
            msgToPrint = msgToPrint.plus("  ")
            msgToPrint = msgToPrint.plus("\n")

            msgToPrint.plus("\n")
            msgToPrint = msgToPrint.plus("  ")
            msgToPrint = msgToPrint.plus("\n")

            msgToPrint = msgToPrint.plus("\n")
            msgToPrint = msgToPrint.plus("##########")
            msgToPrint = msgToPrint.plus("\n")
            //      }
            //  }

            // if (_returns.value!!.isNotEmpty()) {
            //     msgToPrint = msgToPrint.plus("=========المرتجعات==========")
            //     msgToPrint = msgToPrint.plus("\n")
            //     repeat(_returns.value!!.size) {
            //         msgToPrint = msgToPrint.plus("الصنف ${_returns.value!![it].name}")
            //         msgToPrint = msgToPrint.plus("   ")

            //         msgToPrint = msgToPrint.plus("الوزن ${_returns.value!![it].size}")
            //         msgToPrint = msgToPrint.plus("   ")

            //    msgToPrint = msgToPrint.plus("اجمالى المبيعات${Total}")
            msgToPrint = msgToPrint.plus("\n")
            //         msgToPrint = msgToPrint.plus("##########")
            //         msgToPrint = msgToPrint.plus("\n")
            //     }
            // }

            //    msgToPrint = msgToPrint.plus("اجمالي الفاتورة قبل الخصم و المرتجعات ")
            //        .plus(sales.value.toString())
            //        .plus("\n")
            //    msgToPrint = msgToPrint.plus("اجمالي الفاتورة بعد الخصم و المرتجعات ")
            //        .plus(totalBill.value.toString())
            //        .plus("\n")
            //    msgToPrint = msgToPrint.plus("اجمالي المرتجعات ").plus(totalReturns.value).plus("\n")
            //    msgToPrint = msgToPrint.plus("اجمالي خصم الفاتورة ")
            //        .plus(totalItemsDiscount.value!!.plus(appliedDiscount.value!!.toFloat())).plus("\n")
            //    msgToPrint = msgToPrint.plus("المطلوب دفعه ").plus(requiredAmount.value).plus("\n")
            //    msgToPrint = msgToPrint.plus("الباقي ").plus(toBeDeferredPayment.value).plus("\n")
            //    if (billCash.value!!.toFloat() != 0.0f)
            //        msgToPrint = msgToPrint.plus("كاش ").plus(billCash.value).plus("\n")
            //    msgToPrint = msgToPrint.plus("المدفوع ").plus(collection.value).plus("\n")
//

            msgToPrint = msgToPrint.plus("\n")
            msgToPrint = msgToPrint.plus("\n")
            msgToPrint = msgToPrint.plus("Powered by")
            msgToPrint = msgToPrint.plus("\n")
            msgToPrint = msgToPrint.plus("Technology & Business Integration")
            msgToPrint = msgToPrint.plus("\n")
     //  }
     //  }
        print()
        Log.i("MSG_TO_PRINT", msgToPrint)
        //  print.value = true
        //   }
    }
    fun print() {
        findBT()
        openBT()

        sendData()

    }

    fun findBT() {
        Log.e("PrintError1", "e.message.toString()")

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            if (mBluetoothAdapter == null) {
                viewModel.message.value = "No bluetooth adapter available"
            }
            if (mBluetoothAdapter?.isEnabled == false) {
                val enableBluetooth = Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE
                )
                startActivityForResult(enableBluetooth, 0)
            }
            val pairedDevices: Set<BluetoothDevice> = mBluetoothAdapter?.bondedDevices!!
            if (pairedDevices.isNotEmpty()) {
                for (device in pairedDevices) {

                    // MP300 is the name of the bluetooth printer device
                    if (device.name == "InnerPrinter") {
                        mmDevice = device
                        break
                    }
                }
            }
            viewModel.message.value = "Bluetooth Device Found"
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Tries to open a connection to the bluetooth printer device
    @Throws(IOException::class)
    fun openBT() {
        try {
            // Standard SerialPortService ID
            val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
            mmSocket = mmDevice!!.createRfcommSocketToServiceRecord(uuid)
            mmSocket?.connect()
            mmOutputStream = mmSocket?.outputStream
            mmInputStream = mmSocket?.inputStream
            beginListenForData()
            viewModel.message.value = "Bluetooth Opened"
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // After opening a connection to bluetooth printer device,
    // we have to listen and check if a data were sent to be printed.
    fun beginListenForData() {
        try {
            val handler = Handler()

            // This is the ASCII code for a newline character
            val delimiter: Byte = 10
            stopWorker = false
            readBufferPosition = 0
            readBuffer = ByteArray(2000)
            workerThread = Thread {
                while (!Thread.currentThread().isInterrupted
                    && !stopWorker
                ) {
                    try {
                        val bytesAvailable: Int = mmInputStream!!.available()
                        if (bytesAvailable > 0) {
                            val packetBytes = ByteArray(bytesAvailable)
                            mmInputStream?.read(packetBytes)
                            for (i in 0 until bytesAvailable) {
                                val b = packetBytes[i]
                                if (b == delimiter) {
                                    val encodedBytes = ByteArray(readBufferPosition)
                                    System.arraycopy(
                                        readBuffer, 0,
                                        encodedBytes, 0,
                                        encodedBytes.size
                                    )
                                    val data = String(
                                        encodedBytes
                                    )
                                    readBufferPosition = 0
                                } else {
                                    readBuffer[readBufferPosition.inc()] = b
                                }
                            }
                        }
                    } catch (ex: IOException) {
                        stopWorker = true
                    }
                }
            }
            workerThread?.start()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*
 * This will send data to be printed by the bluetooth printer
 */
    @Throws(IOException::class)
    fun sendData() {
        try {

            // the text typed by the user

            var printPic = PrintPic.getInstance()
            printPic.init(
                textAsBitmap(
                    context = this!!,
                    textSize = 18,
                    textWidth = 370
                )
                // viewModel.textAsBitmap(
                //     context = this!!,
                //     textSize = 18,
                //     textWidth = 370
                // )
            )

            var draw = printPic.printDraw()

            mmOutputStream!!.write(draw)
            // tell the user data were sent
        } catch (e: NullPointerException) {
            e.printStackTrace()
            Log.e("PrintError1", e.message.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("PrintErro2r", e.message.toString())

        }
        closeBT()
    }

    // Close the connection to bluetooth printer.
    @Throws(IOException::class)
    fun closeBT() {
        try {
            stopWorker = true
            mmOutputStream?.close()
            mmInputStream?.close()
            mmSocket?.close()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun textAsBitmap(textWidth: Int, textSize: Int, context: Context): Bitmap? {

        // Get text dimensions
        val textPaint = TextPaint(
            Paint.ANTI_ALIAS_FLAG
                    or Paint.LINEAR_TEXT_FLAG
        )
        textPaint.style = Paint.Style.FILL_AND_STROKE
        val typeface = ResourcesCompat.getFont(context, R.font.kawkabregular)
        textPaint.typeface = typeface
        textPaint.color = Color.BLACK
        textPaint.textSize = textSize.toFloat()
        val mTextLayout = StaticLayout(
            msgToPrint, textPaint,
            textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
        )

        // Create bitmap and canvas to draw to
        val b = Bitmap.createBitmap(textWidth, mTextLayout.height, Bitmap.Config.RGB_565)
        val c = Canvas(b)

        // Draw background
        val paint = Paint(
            (Paint.ANTI_ALIAS_FLAG
                    or Paint.LINEAR_TEXT_FLAG)
        )
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        paint.typeface = typeface
        c.drawPaint(paint)

// Draw text
        c.save()
        c.translate(0f, 0f)
        mTextLayout.draw(c)
        c.restore()
        return b
    }

}