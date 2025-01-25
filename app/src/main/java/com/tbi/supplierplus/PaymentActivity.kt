package com.tbi.supplierplus

import CTOS.CtPrint
import android.app.AlertDialog
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.icu.text.SimpleDateFormat
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.text.*
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tbi.supplierplus.business.pojo.billModels.SaleingBill
import com.tbi.supplierplus.business.pojo.bills.NewBill
import com.tbi.supplierplus.business.utils.LoadingDialog
import com.tbi.supplierplus.business.utils.toJson
import com.tbi.supplierplus.databinding.ActivityPaymentBinding
import com.tbi.supplierplus.framework.getLocation.MyLocation
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import com.tbi.supplierplus.framework.utils.PrintPic
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
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
    var numberLists = ArrayList<SaleingBill>()

    // var listBill=ArrayList<SaleingBill>()
    var list = ArrayList<SaleingBill>()
    var mBluetoothAdapter: BluetoothAdapter? = null
    var mmSocket: BluetoothSocket? = null
    var mmDevice: BluetoothDevice? = null

    var mmOutputStream: OutputStream? = null
    var mmInputStream: InputStream? = null
    var workerThread: Thread? = null

    lateinit var readBuffer: ByteArray
    var readBufferPosition = 0
    var counter = 0
    var billNumToCreateQR = ""

    @Volatile
    var stopWorker = false

    var numberOfBill: Int = 0

    var msgToPrint = ""

    var CusID: String = ""
    var name: String = ""
    var TotalAfterDescound = ""
    var TotalReturn = ""
    var Unpaid_deferred = ""
    val loading = LoadingDialog(this)
    var loc: Location? = null
    var lat = 0.0
    var lon = 0.0
    lateinit var availableItemsViewModel: AvailableItemsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        viewModel = ViewModelProvider(this).get(SalesViewModel::class.java)
        availableItemsViewModel = ViewModelProvider(this).get(AvailableItemsViewModel::class.java)

        val locationResult: MyLocation.LocationResult = object : MyLocation.LocationResult() {
            override fun gotLocation(location: Location) {
                loc = location
                //   System.out.println("allah: " + loc!!.latitude)
                // System.out.println("allah: " + loc!!.longitude)
                lon = loc!!.longitude
                lat = loc!!.latitude

            }
        }

        val myLocation =
            MyLocation()
        myLocation.getLocation(this, locationResult)

        val TotalSalse = intent.getStringExtra("TotalSalse")
        Unpaid_deferred = intent.getStringExtra("Unpaid_deferred").toString()
        TotalReturn = intent.getStringExtra("TotalReturn").toString()
        TotalAfterDescound = intent.getStringExtra("TotalAfterDescound").toString()
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


        list = Gson().fromJson<List<SaleingBill>>(
            intent.getStringExtra("list"),
            object : TypeToken<List<SaleingBill?>?>() {}.type
        ) as ArrayList<SaleingBill>


        //   binding.scanQra.setText(list.get(0).Items.toString())
//https://stackoverflow.com/questions/12092612/pass-list-of-objects-from-one-activity-to-other-activity-in-android

        //  val m= viewModel.v
        // Toast.makeText(baseContext, list, Toast.LENGTH_SHORT).show()

        // val challenge: ArrayList<SaleingBill> = intent.extras.getParcelableArrayList("Birds")!!
        // var numberLists=ArrayList<SaleingBill>()


        binding.TotalafterDiscaunt.setText(
            (Unpaid_deferred.toDouble() + TotalSalse.toString().toDouble()).toString()
        )
        // var     numberList = intent .getSerializableExtra( "list" )
        // as ArrayList<SaleingBill>
        viewModel.setNewBillTip.observe(this) {
            loading.isDismiss()
            binding.progressBar2.isGone = true
            Toast.makeText(baseContext, it.Message, Toast.LENGTH_SHORT).show()

            if (it.State == 1) {
                Log.d("billNumToCreateQR", it.Message + "msg")

                var msgnum = it.Message


                val dialog = Dialog(this)

                dialog.setContentView(R.layout.chos_items)

                dialog.getWindow()?.setLayout(700, 2000)


                dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dialog.show()

                numberOfBill = it.State


                Toast.makeText(baseContext, it.Message, Toast.LENGTH_SHORT).show()
//
                GlobalScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.Main) {

                          Log.d("billNumToCreateQR",billNumToCreateQR)

                        //   Log.d("billNumToCreateQR",billNumToCreateQR)

                        lifecycleScope.launch {
                            availableItemsViewModel.GetBillQRCode(it.Message).collect {
                                when (it) {
                                    is State.Loading -> {}
                                    is State.Success -> {

                                        Log.d("billNumToCreateQR", it.data.item.toString())
                                        Log.d("billNumToCreateQR", "it.data.item.toString()")

                                        billNumToCreateQR = it.data.item.toString()
                                        if (binding.deferredEditText.text.toString()
                                                .toDouble() == 0.0
                                        ) {
                                            CastlesPrinter(it.data.item.toString(), msgnum)
                                        } else {
                                            CastlesPrinter(it.data.item.toString(), msgnum)
                                            CastlesPrinter(it.data.item.toString(), msgnum)
                                        }
                                    }
                                    is State.Error -> {
                                        Log.d("billNumToCreateQR", "Error")

                                    }
                                }

                            }
                        }


                        //   registerBillAndPrint(it.Message.toString())
                    }
                }
                // registerBillAndPrint()
                //  Toast.makeText(baseContext, it.Message, Toast.LENGTH_SHORT).show()

                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            } else if (it.State == 0) {
                Toast.makeText(baseContext, it.Message, Toast.LENGTH_SHORT).show()

            }

        }




        binding.Totalss.setText(TotalSalse)

        binding.Totalssz4.setText(Unpaid_deferred)
        binding.Totalss2.setText(TotalReturn)
        binding.Totalss4.setText(TotalAfterDescound)





        binding.cancel1.setOnClickListener {

            val dialog2 = this?.let { it1 -> Dialog(it1) }
            dialog2?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            //بيعمل بلوك للباك جراوند
            //  dialog?.setCancelable(false)
            dialog2?.setContentView(R.layout.dilog_bill_row_discount)
            dialog2?.getWindow()?.setLayout(700, 900)
            dialog2?.show()


            lateinit var discount: TextInputEditText
            discount = dialog2?.findViewById(R.id.billDiscountEditText1)

             lateinit var discribtionText: TextInputEditText
            discribtionText = dialog2?.findViewById(R.id.discribtionText)


            // NumberOfUnits.text.toString()
            discount.setVisibility(View.GONE)
            lateinit var billDiscountEditText9: TextInputEditText
            billDiscountEditText9 =
                dialog2?.findViewById(R.id.billDiscountEditText9)

            lateinit var ok: Button
            ok = dialog2.findViewById(R.id.printbtn1)

            lateinit var confirmPrice: Button
            confirmPrice = dialog2.findViewById(R.id.confirmPrice)
            confirmPrice.isVisible=false


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
            //               Toast.makeText( applicationContext, "الخصم اكبر من قيمه الفاتوره", Toast.LENGTH_SHORT ).show()
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

                var discount1 = billDiscountEditText9.text.toString().toDouble()
                //+ binding.billDiscountEditText.text.toString().toDouble()
                // binding.billDiscountEditText.setText(discount)
                //  var total1=binding.Totalss4.text.toString().toDouble()-discount1.toDouble()
                if (discount1 > binding.Totalss4.text.toString().toDouble()) {

                    Toast.makeText(applicationContext, "الخصم اكبر من قيمه الفاتوره", Toast.LENGTH_SHORT).show()

                } else {
                    if (discribtionText.text.isNullOrEmpty()||discribtionText.text.toString()==" "){
                        discribtionText.setError("field is require")

                    }else{


                    //   var setbillDiscountEditText=discount1.toDouble()+ binding.billDiscountEditText.text.toString().toDouble()
                    //   var total1 = binding.Totalss4.text.toString().toDouble() - discount1.toDouble()
                    var total1 = (Unpaid_deferred.toDouble() + TotalSalse.toString()
                        .toDouble()) - discount1.toDouble()

                    //   binding.billDiscountEditText.setText(setbillDiscountEditText.toString())
                    binding.billDiscountEditText.setText(discount1.toString())

                    binding.TotalafterDiscaunt.setText(total1.toString())
                    dialog2.dismiss()
                }
                }
                binding.deferredEditText.setText((binding.TotalafterDiscaunt.text.toString()))

            }

            lateinit var cancel: Button
            cancel = dialog2.findViewById(R.id.cancel)

            cancel.setOnClickListener { dialog2.dismiss() }


        }
        binding.deferredEditText.setText(binding.TotalafterDiscaunt.text)

        // if(  binding.TotalafterDiscaunt.text.toString().toString().toDouble()<0) {
        binding.cashEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // binding.deferredEditText.setText(binding.TotalafterDiscaunt.text)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //   binding.deferredEditText.setText(binding.TotalafterDiscaunt.text)
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

                //  binding.deferredEditText.setText(binding.TotalafterDiscaunt.text)
            }

        })

        binding.progressBar2.isGone = true
        binding.printbtn.setOnClickListener {
            if (binding.TotalafterDiscaunt.text.toString().toDouble() < 0) {
                showDefaultDialog(this)
            } else {
                if (binding.cashEditText.text.toString()
                        .toDouble() > binding.TotalafterDiscaunt.text.toString().toDouble()
                ) {
                    Toast.makeText(
                        applicationContext,
                        "ادخل المبلغ بطريقة صحيحة",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    showDefaultDialog(this)
                }
            }


        }
    }

    @Throws(IOException::class)
    fun registerBillAndPrint(numerofBill: String) {
        //  Log.e("PrintError1", "e.message.toString()")


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
            //    Log.d("listBill",list.toJson())

            for (listX in list) {
                //  if (listX.TransactionType==1) {

                msgToPrint = msgToPrint.plus(" اسم المنتج : " + listX.Items)
                msgToPrint = msgToPrint.plus("   ")
                msgToPrint.plus("\n")
                msgToPrint = msgToPrint.plus("\n")
                msgToPrint = msgToPrint.plus("النوع :   " + listX.sals + listX.returns)
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


                msgToPrint = msgToPrint.plus("الاجمالى :  " + listX.TotalPrice)
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

        msgToPrint = msgToPrint.plus("الاجمالى قبل الخصم:   " + TotalAfterDescound)
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
        // Log.i("MSG_TO_PRINT", msgToPrint)
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

    private fun showDefaultDialog(context: Context) {
        val alertDialog = AlertDialog.Builder(context)

        alertDialog.apply {
            //setIcon(R.drawable.ic_hello)
            setTitle("مرحبا")
            setMessage("هل متاكد من دفع مبلغ " + binding.cashEditText.text + "\n" + "والمتبقي " + binding.deferredEditText.text)
            setPositiveButton("نعم ") { _: DialogInterface?, _: Int ->
                loading.startLoading()
                binding.progressBar2.isVisible = true
                SharedPreferencesCom.getInstance().gerSharedUser_ID()


                  Log.d("showDefaultDialog", SharedPreferencesCom.getInstance().gerSharedUser_ID().toString())
                 // Log.d("showDefaultDialog",loc!!.longitude.toString())
                viewModel.setNewPill(
                    NewBill(
                        CusID.trim(),
                        binding.billDiscountEditText.text.toString().trim(),
                        "1",
                        SharedPreferencesCom.getInstance().gerSharedUser_ID().toString(),
                        binding.Totalss4.text.toString().trim(),
                        binding.TotalafterDiscaunt.text.toString().trim(),
                        binding.cashEditText.text.toString().trim(),
                        binding.deferredEditText.text.toString().trim(),
                        Unpaid_deferred,
                        binding.Totalss2.text.toString().trim(),
                        list,
                        lon,
                        lat,
                        binding.com.text.toString(),
                        binding.clientName.text.toString()
                    )
                )

                Log.d("Unpaid_deferred", Unpaid_deferred.toString())
                Log.e(
                    "PrintError13", NewBill(
                        CusID.trim(),
                        binding.billDiscountEditText.text.toString().trim(),
                        "1",
                        SharedPreferencesCom.getInstance().gerSharedUser_ID().toString(),
                        binding.Totalss4.text.toString().trim(),
                        binding.TotalafterDiscaunt.text.toString().trim(),
                        binding.cashEditText.text.toString().trim(),
                        binding.deferredEditText.text.toString().trim(),
                        Unpaid_deferred,
                        binding.Totalss2.text.toString().trim(),
                        list,
                        lon,
                        lat,
                        binding.com.text.toString(),
                        binding.clientName.text.toString()
                    ).toJson().toString()
                )
                //registerBillAndPrint()
                // print()
            }
            setNegativeButton("لا") { _, _ ->
                Toast.makeText(context, "لا", Toast.LENGTH_SHORT).show()

            }
            setNeutralButton("Neutral") { _, _ ->
                Toast.makeText(context, "Neutral", Toast.LENGTH_SHORT).show()
            }
        }.create().show()
    }

    private fun showDefaultDialog2(context: Context) {
        val alertDialog = AlertDialog.Builder(context)

        alertDialog.apply {
            //setIcon(R.drawable.ic_hello)
            setTitle("Hello")
            setMessage("I just wanted to greet you. I hope you are doing great!")
            setPositiveButton("Positive") { _: DialogInterface?, _: Int ->
                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("Negative") { _, _ ->
                Toast.makeText(context, "Negative", Toast.LENGTH_SHORT).show()
            }
            setNeutralButton("Neutral") { _, _ ->
                Toast.makeText(context, "Neutral", Toast.LENGTH_SHORT).show()
            }
        }.create().show()
    }

    fun CastlesPrinter(QR: String?, billNum: String) {
        var print_font: String
        val print_x = 45
        val print_y = 36
        val Currently_high = 20
        var ret = 0
        val print = CtPrint()
        //   print.initPage((list.size*280)+1140)
        print.initPage((list.size * 280) + 500)
        print.drawImage(textAsBitmap2(registerBillAndPrint2(billNum), 330, 24), 4, 30)
        print.printPage()
        var bitmap: Bitmap? = null
        print.initPage(370)
        bitmap = print.encodeToBitmap(QR, CtPrint.QR_CODE, 350, 320)



        bitmap = print.encodeToBitmap(QR, CtPrint.QR_CODE, 350, 320)
        print.drawImage(bitmap, 0, 0)
        print.printPage()
        ret = print.roll(10)
        // Log.d(TAG, String.format("Roll ret = %d", ret));
        ret = print.status()
        //   Log.d(TAG, String.format("status ret = %d", ret));
        print.heatLevel = 2

        //  print.initPage(100);

        //   print.drawText(0, print_y + Currently_high, String.valueOf(sb), print_y, 4, 0xFF000000, true, (float) 0, true,
        //           false, Typeface.create("sans-serif-thin", Typeface.BOLD));
        //   Currently_high += print_y;
        //   print.drawText(0, print_y + Currently_high, print_font, print_y / 2, print_y, 5, 0xFF000000, true, (float) 0, false,
        //           false, Typeface.create("sans-serif-thin", Typeface.NORMAL));
        //   Currently_high += print_y;
        //   print.drawText(0, print_y + Currently_high, print_font, print_y * 2, print_y, 6, 0xFF000000, true, (float) 0, false,
        //           false, Typeface.create("sans-serif-thin", Typeface.NORMAL));
        //   String json_printname = "";
        //   String json_printip = "";
        //   String json_printport = "";
        // try {
        //     Root r=new Root();

        //     json_obj = json_arr.getJSONObject(0);
        //     json_printname = json_obj.getString("PrinterName");
        //     json_printip = json_obj.getString("PrinterIP");
        //     json_printport = json_obj.getString("PrinterPort");
        //   print.  printerSearch() throws Exception
        //     print.printerSelect(json_obj);
        // } catch (JSONException e) {
        //     e.printStackTrace();
        // }
        // try{
        //     print.printerSelect(json_obj);
        // }catch(Exception e){
        //     Log.d(TAG, e.getMessage());
        //     textView1.setText(e.getMessage());
        // }
        //  public int lineEx();


        //   print.printPage();
    }


    fun textAsBitmap2(text: String?, textWidth: Int, textSize: Int): Bitmap? {
        // Get text dimensions
        val textPaint = TextPaint(
            Paint.ANTI_ALIAS_FLAG
                    or Paint.LINEAR_TEXT_FLAG
        )
        textPaint.style = Paint.Style.FILL
        textPaint.color = Color.BLACK
        textPaint.textSize = textSize.toFloat()
        val mTextLayout = StaticLayout(
            text, textPaint,
            textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
        )
        // Set the typeface to bold
        textPaint.typeface = Typeface.DEFAULT_BOLD

        // Create bitmap and canvas to draw to
        val b = Bitmap.createBitmap(textWidth, mTextLayout.height, Bitmap.Config.RGB_565)
        val c = Canvas(b)

        // Draw background
        val paint = Paint(
            (Paint.ANTI_ALIAS_FLAG or Paint.LINEAR_TEXT_FLAG)
        )
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        c.drawPaint(paint)

// Draw text
        c.save()
        c.translate(0f, 0f)
        mTextLayout.draw(c)
        c.restore()
        return b
    }


//    fun setpill(): String? {
//        val today = Date()
//        val format_date =
//            SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")
//        val print_date = format_date.format(today)
//        val sb = StringBuffer()
//        // ArrayList<Items> itemsList
//        var cunt = 0
//        var totprice = 0.0
//        var totquantity = 0
//        //  List<ItemsModels> models
//        // List<ItemsModels> models
//        for (s in list) {
//            //  if (s.ItemName.length()>15){
//            //    int startRest= s.ItemName.indexOf(" ",15);
//            //    String restOfItemName=s.ItemName.substring(startRest);
//            //    s.ItemName=s.ItemName.substring(0,startRest);
//
//            //      sb.append("  "+s.ItemName+"       "+s.contaty+"           "+s.balanc+"           "+"\n" +
//            //            " "+restOfItemName+"\n" +"\n");
//            //  }else {
//            sb.append("\n")
//            sb.append("   " + s.Price + "")
//            sb.append("         " + s.Quantity + "    ")
//            sb.append("        n" + s.ItemName + "")
//            sb.append("\n")
//            // sb.append(""+s.getQuantity()+"");
//            cunt++
//            totprice += java.lang.Double.valueOf(s.Price * s.Quantity)
//            totquantity += s.Quantity
//
//            //  " " + s.getPrice() + "  " + "s.getTotal()" + "\n");
//
//            //   }
//            //  sb.append("  "+s.ItemName+"           "+s.contaty+"           "+s.balanc +"\n");
//        }
//        sb.append("\n")
//        sb.append("___________")
//        sb.append("\n")
//        sb.append("\n")
//        sb.append(" Total Items                        $cunt")
//        sb.append("\n")
//        sb.append(" Discount     " + "                      " + "0.0")
//        sb.append("\n")
//        sb.append(" Cash Discount  " + "                " + "0.0")
//        sb.append("\n")
//        sb.append("___________")
//        sb.append("\n")
//        sb.append(" Total Item Price       $totprice")
//        sb.append("\n")
//        sb.append(" Total Quantity          $totquantity")
//        sb.append("\n")
//        sb.append(" Credit Cards  " + "                 " + "0.0")
//        sb.append("\n")
//        sb.append("___________")
//        sb.append("\n")
//        sb.append("                      Administrator")
//        sb.append("\n")
//        sb.append("        $print_date")
//        sb.append("\n")
//        sb.append("        " + "                        pos 1")
//        sb.append("\n")
//        sb.append("___________")
//        val n4 = "____  BILL DETAILS ___"
//        val n1 = "                Bavaria "
//        val n2 = "                             "
//        val n3 = "                     "
//        val msg2 =  //n0+"\n"+"\n"+ "\n" +
//            """
//            $n1
//            $n2
//            $n3
//
//
//            """.trimIndent()
//        return """
//            $msg2$n4
//
//            $sb
//            """.trimIndent()
//    }

    //  @Throws(IOException::class)
    fun registerBillAndPrint2(numerofBill: String): String {
        //  Log.e("PrintError1", "e.message.toString()")


        msgToPrint = ""
        this.msgToPrint = msgToPrint.plus("رقم الفاتورة ").plus(numerofBill).plus("\n")

        this.msgToPrint =
            msgToPrint.plus("بواسطة : ").plus("شركه البان الدوار  ").plus("\n")

        this.msgToPrint =
            msgToPrint.plus("س.ت  ").plus(" 173847 ").plus("\n")

        this.msgToPrint =
            msgToPrint.plus("ت.ض : ").plus(" 679/427/597 ").plus("\n")

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        this.msgToPrint = msgToPrint.plus("التاريخ :").plus(currentDate.toString())


        this.msgToPrint = msgToPrint.plus("   ")
        this.msgToPrint.plus("\n")
//        this.  msgToPrint =
//            msgToPrint.plus("رقم الموبيل:  ").plus("01112272015 ")
//                .plus("\n")


        //  msgToPrint = msgToPrint.plus("\n")
        //  val split= DialogBill(name)
        //   val
        this.msgToPrint =
            msgToPrint.plus("اسم العميل ").plus(name)
                .plus("\n")
        //  msgToPrint = "اسم العميل ".plus(name).plus("\n")
        // msgToPrint =
        //     msgToPrint.plus("التوقيت ").plus(SimpleDateFormat("dd/M/yyyy").format(Date()))
        //         .plus(" ").plus(SimpleDateFormat("hh:mm:ss").format(Date())).plus("\n")

        //   if (_bill.value!!.isNotEmpty()) {
        this.msgToPrint = msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("________ الفاتورة _________")
        this.msgToPrint = msgToPrint.plus("\n")
        //  repeat(_bill.value!!.size) {


        if (list.isEmpty()) {
        } else {

            for (listX in list) {
                //  if (listX.TransactionType==1) {

                this.msgToPrint = msgToPrint.plus(" المنتج : " + listX.Items)
                this.msgToPrint = msgToPrint.plus("   ")
                this.msgToPrint.plus("\n")
                this.msgToPrint = msgToPrint.plus("\n")

                this.msgToPrint =
                    msgToPrint.plus("العدد               السعر           الاجمالي ").plus("\n")
                //  this.      msgToPrint.plus("العدد                 السعر             الاجمالي ")
                this.msgToPrint.plus("\n")
                this.msgToPrint = msgToPrint.plus("\n")

                this.msgToPrint =
                    msgToPrint.plus("    " + listX.TotalPrice + "          " + listX.UnitPrice + "                " + listX.NumberOfUnits)
                // this.      msgToPrint = msgToPrint.plus(":" + listX.sals +listX.returns)
                this.msgToPrint = msgToPrint.plus("\n")

                this.msgToPrint.plus("\n")


                //   this.  msgToPrint = msgToPrint.plus("سعر الوحده :                 " + listX.UnitPrice)
                //   this.  msgToPrint = msgToPrint.plus("\n")
                //   this.  msgToPrint.plus("\n")

                if (listX.Discount.toDouble() == 0.0) {

                } else {
                    this.msgToPrint = msgToPrint.plus("الخصم على هذا المنج :   " + listX.Discount)
                    this.msgToPrint.plus("\n")
                    this.msgToPrint = msgToPrint.plus("\n")
                }


                //   this.  msgToPrint = msgToPrint.plus("عدد المباع من المنتج:     " + listX.NumberOfUnits)
                //   this.  msgToPrint.plus("\n")
                //   this.  msgToPrint = msgToPrint.plus("\n")


                // this.  msgToPrint = msgToPrint.plus("الاجمالى :                   " +listX.TotalPrice)
                // this.  msgToPrint.plus("\n")
                // this.  msgToPrint = msgToPrint.plus("\n")
                this.msgToPrint = msgToPrint.plus("___________________________")
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

        this.msgToPrint = msgToPrint.plus("\n")
        this.msgToPrint =
            msgToPrint.plus("اجمالي المبيعات :         " + binding.Totalss.text.toString())
        this.msgToPrint = msgToPrint.plus("   ")
        this.msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("\n")

        //    msgToPrint = msgToPrint.plus("الوزن " +" _bill.value!![it].size")
        this.msgToPrint = msgToPrint.plus("المديونيه السابقه :          " + Unpaid_deferred)
        this.msgToPrint = msgToPrint.plus("\n")

        this.msgToPrint.plus("\n")

//        this. msgToPrint = msgToPrint.plus("اجمالى المرتجعات :   " + TotalReturn)
//        this. msgToPrint.plus("\n")
//        this. msgToPrint = msgToPrint.plus("\n")
//        this. msgToPrint = msgToPrint.plus("\n")

//        this. msgToPrint = msgToPrint.plus("مديونيه سابقه :   " + Unpaid_deferred)
//        this. msgToPrint.plus("\n")
//        this. msgToPrint = msgToPrint.plus("\n")

        if (binding.billDiscountEditText.text.toString().toDouble() == 0.0) {

        } else {
            this.msgToPrint = msgToPrint.plus("الاجمالى قبل الخصم:    " + TotalAfterDescound)
            this.msgToPrint.plus("\n")
            this.msgToPrint = msgToPrint.plus("\n")

            this.msgToPrint =
                this.msgToPrint.plus("الخصم :                        " + binding.billDiscountEditText.text.toString())


            this.msgToPrint = msgToPrint.plus("\n")

            this.msgToPrint.plus("\n")
        }

        this.msgToPrint =
            this.msgToPrint.plus("المطلوب دفعه :             " + binding.TotalafterDiscaunt.text.toString())
        this.msgToPrint = msgToPrint.plus("\n")

        this.msgToPrint.plus("\n")
        this.msgToPrint =
            this.msgToPrint.plus("المدفوع:                        " + binding.cashEditText.text.toString())
        this.msgToPrint = msgToPrint.plus("  ")
        this.msgToPrint = msgToPrint.plus("\n")

        this.msgToPrint.plus("\n")
        this.msgToPrint =
            this.msgToPrint.plus("الباقى :                         " + binding.deferredEditText.text.toString())
        this.msgToPrint = msgToPrint.plus("  ")
        this.msgToPrint = msgToPrint.plus("\n")

        this.msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("  ")
        this.msgToPrint = msgToPrint.plus("\n")

        // this. msgToPrint = msgToPrint.plus("\n")
        //  this. msgToPrint = msgToPrint.plus("##########")
        //  this. msgToPrint = msgToPrint.plus("\n")
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
        //  this.    msgToPrint = msgToPrint.plus("\n")
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
        this.msgToPrint = msgToPrint.plus("******************************")
        this.msgToPrint = msgToPrint.plus("\n")
        // this.   msgToPrint = msgToPrint.plus("                 شش")
        // this.   msgToPrint = msgToPrint.plus("\n ")
        // this.   msgToPrint = msgToPrint.plus("\n ")
        // this.   msgToPrint = msgToPrint.plus("استلمت انا المذكور اعلاه البضاعه الموضحة وصفا وقيمة وملتزم بسداد الثمن خلال مدة اقصاها او ارجاعها ")
        // this.   msgToPrint = msgToPrint.plus("\n")
        // this.   msgToPrint = msgToPrint.plus("\n")
        // this.   msgToPrint = msgToPrint.plus("بحالتها التي سلمت عليها ولا تعتبر ذمتي خالية الا باستلام الفاتورة الموقعه مني ")
        // this.   msgToPrint = msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("       المستلم   ")

        this.msgToPrint = msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("******************************")
        this.msgToPrint = msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("Powered by")
        this.msgToPrint = msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("Technology & Business Integration")
        //    this.   msgToPrint = msgToPrint.plus("\n")
        //  }
        //  }
        //  print()
        // Log.i("MSG_TO_PRINT", msgToPrint)
        //  print.value = true
        //   }
        Log.e("PrintError1", this.msgToPrint)
        return this.msgToPrint

    }
}