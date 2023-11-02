package com.tbi.supplierplus.framework.ui2.mortag3

import CTOS.CtPrint
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.MainActivity
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.ReturnItemsModel
import com.tbi.supplierplus.business.pojo.billModels.SaleingBill
import com.tbi.supplierplus.business.pojo.returns.ReturnItems
import com.tbi.supplierplus.databinding.ActivityEdirMortag3Binding
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class EdirMortag3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityEdirMortag3Binding
    private val availableItemsViewModel: AvailableItemsViewModel by viewModels()
    lateinit var message: String
    var msgToPrint = ""
    var lastDebit = ""
    var CompanyName =""
    var list=ArrayList<ReturnItemsModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edir_mortag3);
        binding.spinKit.isVisible = false
        message = intent.getStringExtra("Customer_ID").toString()
        val Unpaid_deferred = intent.getStringExtra("Unpaid_deferred")
          CompanyName = intent.getStringExtra("CompanyName").toString()
        val size = intent.getStringExtra("ITEM_ID")
        val itemname = intent.getStringExtra("itemname")
        val NumberOfUnits = intent.getStringExtra("NumberOfUnits")
        val TotalPrice = intent.getStringExtra("TotalPrice")
        val ItemID = intent.getStringExtra("ItemID")
        val BillNo = intent.getStringExtra("BillNo")
        lastDebit= Unpaid_deferred.toString()
     //   Log.d("SubmittttttID", message.toString())
     //   Log.d("SubmittttttID", ItemID.toString())


        binding.com.setText(itemname)
        binding.numOfRecord.setText((0.0).toString())
        binding.comCode.setText(NumberOfUnits)
        binding.branchcode.setText("0.0")

        binding.Editweight.setOnClickListener {
            val dialog = this?.let { it1 -> Dialog(it1) }
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            //بيعمل بلوك للباك جراوند
            //  dialog?.setCancelable(false)
            dialog?.setContentView(R.layout.dialog_edit_weight)
            dialog?.getWindow()?.setLayout(700, 800)
            dialog?.show()
            lateinit var tv_name: TextView
            tv_name = dialog?.findViewById(R.id.billDiscountEditText1)!!

            lateinit var EditweightDailog: Button
            EditweightDailog = dialog?.findViewById(R.id.EditweightDailog)!!

            EditweightDailog.setOnClickListener {
                if (tv_name.text.isNullOrEmpty()){}
                else{
                    if (tv_name.text.toString().toDouble() >NumberOfUnits.toString().toDouble()*size.toString().toDouble())
                    {
                        Toast.makeText(baseContext,"لا يمكن قبول ذلك الوزن", Toast.LENGTH_SHORT).show()
                    }
                    else{

                            if (size != null) {
                                if (TotalPrice != null) {

                                    val number:Double = (tv_name.text.toString().toDouble()/size.toDouble()) *TotalPrice.toDouble()
                                    val number3digits:Double = String.format("%.3f", number).toDouble()
                                    val number2digits:Double = String.format("%.2f", number3digits).toDouble()
                                    val solution:Double = String.format("%.1f", number2digits).toDouble()
                                    binding.branchcode.setText (number2digits.toString())
                                }
                            }

                            binding.numOfRecord.setText(tv_name.text)
                    dialog.dismiss()


            }

        }


        binding.SubmittttttID.setOnClickListener {

                    lifecycleScope.launch() {
                        if (ItemID != null) {
                            availableItemsViewModel.ReturnItems(
                                ReturnItemsModel(
                                    ItemID.toInt(),
                                    message.toInt(),
                                    SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt(),
                                    binding.numOfRecord.text.toString().toDouble(),
                                    binding.branchcode.text.toString().toDouble(),
                                    "",
                                    BillNo
                                )
                            ).collect {

                                when (it) {
                                    is State.Loading -> {

                                    }
                                    is State.Success -> {
                                        Log.d("billNumToCreateQR", "Success")
                                        list.add(
                                            ReturnItemsModel(
                                                ItemID.toInt(),
                                                message.toInt(),
                                                SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt(),
                                                binding.numOfRecord.text.toString().toDouble(),
                                                binding.branchcode.text.toString().toDouble(),
                                                "",
                                                BillNo
                                            )
                                        )

                                        Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()

                                        lifecycleScope.launch {
                                            if (BillNo != null) {
                                                availableItemsViewModel.GetBillQRCode(BillNo).collect {
                                                    when (it) {
                                                        is State.Loading -> {}
                                                        is State.Success -> {

                                                            CastlesPrinter( it.data.item.toString(),BillNo)

                                                        }
                                                        is State.Error -> {
                                                            Log.d("billNumToCreateQR","Error")

                                                        }
                                                    }

                                                }
                                            }
                                        }

                                        val i =Intent(this@EdirMortag3Activity,MainActivity::class.java)
                                        startActivity(i)
                                        finish()





                                    }
                                    is State.Error -> {
                                        Log.d("billNumToCreateQR", "Error")
                                    }
                                }
                            }
                        }

                    }
                }
            }

        }
    }
    private fun CheckAllFields(): Boolean {
       if (binding.numOfRecord.length() == 0) {
            binding.numOfRecord.setError("This field is required")
            return false
        }


        // after all validation return true.
        return true
    }

    fun CastlesPrinter(QR: String?,billNum:String) {
        var print_font: String
        val print_x = 45
        val print_y = 36
        val Currently_high = 20
        var ret = 0
        val print = CtPrint()
        print.initPage((list.size*280)+300)
        print.drawImage(textAsBitmap2(registerBillAndPrint2(billNum), 320, 27), 4, 30)
        print.printPage()
        var bitmap: Bitmap? = null
        print.initPage(370)
        bitmap = print.encodeToBitmap(QR, CtPrint.QR_CODE, 350, 320)
        print.drawImage(bitmap, 0, 0)
        print.printPage()
        ret = print.roll(10)
        // Log.d(TAG, String.format("Roll ret = %d", ret));
        ret = print.status()
        //   Log.d(TAG, String.format("status ret = %d", ret));
        print.heatLevel = 2


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
        c.drawPaint(paint)

        // Draw text
        c.save()
        c.translate(0f, 0f)
        mTextLayout.draw(c)
        c.restore()
        return b
    }

    fun registerBillAndPrint2(numerofBill:String):String {
        //  Log.e("PrintError1", "e.message.toString()")


        //  val    msgToPrint=""
        this.msgToPrint = msgToPrint.plus("رقم الفاتورة ").plus(numerofBill).plus("\n")

        this.  msgToPrint =
            msgToPrint.plus("بواسطة : ").plus("شركه البان الدوار  ")
                .plus("\n")

        this. msgToPrint =
            msgToPrint.plus("س.ت  ").plus(" 173847 ")
                .plus("\n")
        this. msgToPrint =
            msgToPrint.plus("ت.ض : ").plus(" 679/427/597 ")
                .plus("\n")

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        this.  msgToPrint = msgToPrint.plus("التاريخ :").plus(currentDate.toString())


        this.  msgToPrint = msgToPrint.plus("   ")
        this.  msgToPrint.plus("\n")
//        this.  msgToPrint =
//            msgToPrint.plus("رقم الموبيل:  ").plus("01112272015 ")
//                .plus("\n")


        //  msgToPrint = msgToPrint.plus("\n")
        //  val split= DialogBill(name)
        //   val
        this.  msgToPrint =
            msgToPrint.plus("اسم العميل ").plus(CompanyName)
                .plus("\n")
        //  msgToPrint = "اسم العميل ".plus(name).plus("\n")
        // msgToPrint =
        //     msgToPrint.plus("التوقيت ").plus(SimpleDateFormat("dd/M/yyyy").format(Date()))
        //         .plus(" ").plus(SimpleDateFormat("hh:mm:ss").format(Date())).plus("\n")

        //   if (_bill.value!!.isNotEmpty()) {
        this.   msgToPrint = msgToPrint.plus("________ الفاتورة _________")
        this.   msgToPrint = msgToPrint.plus("\n")
        //  repeat(_bill.value!!.size) {


        if (list.isEmpty()) {
        } else {

            for (listX in list) {
                //  if (listX.TransactionType==1) {

                this.     msgToPrint = msgToPrint.plus(" اسم المنتج : " +binding.com.text.toString())
                this.      msgToPrint = msgToPrint.plus("   ")
//                this.      msgToPrint.plus("\n")
//                this.     msgToPrint = msgToPrint.plus("\n")
//                this.      msgToPrint = msgToPrint.plus(":" + listX.sals +listX.returns)
//                this.     msgToPrint = msgToPrint.plus("\n")

                this.   msgToPrint.plus("\n")


//                this.  msgToPrint = msgToPrint.plus("سعر الكيلو :   " + binding.branchcode.text.toString())
//                this.  msgToPrint = msgToPrint.plus("\n")
//
//                this.  msgToPrint.plus("\n")


                this.  msgToPrint = msgToPrint.plus("وزن المرتجع من المنتج:   " + binding.numOfRecord.text.toString())
                this.  msgToPrint.plus("\n")
                this.  msgToPrint = msgToPrint.plus("\n")


                this.  msgToPrint = msgToPrint.plus("الاجمالى :  " +binding.branchcode.text.toString())
                this.  msgToPrint.plus("\n")
                this.  msgToPrint = msgToPrint.plus("\n")
              //  this.  msgToPrint = msgToPrint.plus("________________________")

            }
        }

//        this. msgToPrint = msgToPrint.plus("\n")
//        this. msgToPrint = msgToPrint.plus("اجمالي المبيعات :   " )
//        this. msgToPrint = msgToPrint.plus("   ")
//        this. msgToPrint.plus("\n")
//        this. msgToPrint = msgToPrint.plus("\n")

        //    msgToPrint = msgToPrint.plus("الوزن " +" _bill.value!![it].size")
//        this. msgToPrint = msgToPrint.plus("المديونيه السابقه :   " )
//        this. msgToPrint = msgToPrint.plus("\n")

        this. msgToPrint.plus("\n")

//        this. msgToPrint = msgToPrint.plus("اجمالى المرتجعات :   " )
//        this. msgToPrint.plus("\n")
//        this. msgToPrint = msgToPrint.plus("\n")
//        this. msgToPrint = msgToPrint.plus("\n")

        this. msgToPrint = msgToPrint.plus("مديونيه سابقه :   " +lastDebit)
        this. msgToPrint.plus("\n")
        this. msgToPrint = msgToPrint.plus("\n")

//        this. msgToPrint = msgToPrint.plus("الاجمالى قبل الخصم:   " )
//        this. msgToPrint.plus("\n")
//        this. msgToPrint = msgToPrint.plus("\n")

//        this. msgToPrint =
//            this.     msgToPrint.plus("الخصم :               " )


//        this. msgToPrint = msgToPrint.plus("\n")
//
//        this. msgToPrint.plus("\n")
//        this. msgToPrint =
//            this.     msgToPrint.plus("المطلوب دفعه :       ")
//        this. msgToPrint = msgToPrint.plus("\n")
//
//        this. msgToPrint.plus("\n")
//        this. msgToPrint =
//            this.     msgToPrint.plus("المدفوع:              " )
//        this. msgToPrint = msgToPrint.plus("  ")
//        this. msgToPrint = msgToPrint.plus("\n")

//        this. msgToPrint.plus("\n")
//        this. msgToPrint =
//            this.     msgToPrint.plus("الباقى :      " )
        this. msgToPrint = msgToPrint.plus("  ")
        this. msgToPrint = msgToPrint.plus("\n")


        Log.e("PrintError1",this. msgToPrint)
        return this. msgToPrint

    }
}