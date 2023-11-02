package com.tbi.supplierplus.framework.ui.reports

import CTOS.CtPrint
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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.billModels.SaleingBill
import com.tbi.supplierplus.business.pojo.reports.Returns
import com.tbi.supplierplus.business.pojo.reports.Sales
import com.tbi.supplierplus.databinding.FragmentCustomerStatementDetailsBinding
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.sales.customer_profile.BillAdapter
import com.tbi.supplierplus.framework.ui.sales.customer_profile.ReturnsAdapter
import com.tbi.supplierplus.framework.ui2.availableitemsBB.AvailableItemsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class CustomerStatementDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCustomerStatementDetailsBinding
    private val viewModel: ReportsViewModel by activityViewModels()
    private val availableItemsViewModel: AvailableItemsViewModel by viewModels()
    var list=ArrayList<Sales> ()
    var listReturns=ArrayList<Returns> ()
    var msgToPrint = ""
    var returnflag = 0
    var stringArg =""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCustomerStatementDetailsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        stringArg = arguments?.getString("stringArg").toString()
      //  Log.d("FragmentCustomerStatemen",stringArg.toString())
       viewModel. selectedStatement.observe(viewLifecycleOwner){

       }
        val adapter1 = BillAdapter()
        viewModel.Sales.observe(viewLifecycleOwner){
           // it?.get(0)?.Items?.let { it1 -> Log.d("sjdhjhs", it1) }

            list= it as ArrayList<Sales>
            binding.billRecycler.adapter = adapter1
            adapter1.submitList(it)
        }

        val adapter2 = ReturnsAdapter()
         viewModel. Returns.observe(viewLifecycleOwner){
             if (it==null){
                 binding.reprintreturnBTN.isVisible=false
             }else{
                 binding.reprintreturnBTN.isVisible=true
                 listReturns = it as ArrayList<Returns>
                 binding.returnsRecycler.adapter = adapter2
                 adapter2.submitList(it)
             }

         }

      //  viewModel.billDetails.observe(viewLifecycleOwner) {
      //    if (it.bill.isEmpty())
      //        binding.billLayout.visibility = View.GONE
      //    else {
      //        binding.billLayout.visibility = View.VISIBLE
      //        val adapter = BillAdapter()
      //        binding.billRecycler.adapter = adapter
      //        adapter.submitList(it.bill)
      //    }
      //    if (it.returns.isEmpty())
      //        binding.mortg3atLayout.visibility = View.GONE
      //    else {
      //        binding.mortg3atLayout.visibility = View.VISIBLE
      //        val adapter = ReturnsAdapter()
      //        binding.returnsRecycler.adapter = adapter
      //        adapter.submitList(it.returns)
      //    }
      //}

        binding.reprintBTN.setOnClickListener {
            returnflag = 0
            msgToPrint = ""
            lifecycleScope.launch {
                if (binding.billnumTXT.text != null) {
                    availableItemsViewModel.GetBillQRCode(binding.billnumTXT.text.toString()).collect {
                        when (it) {
                            is State.Loading -> {}
                            is State.Success -> {

                                CastlesPrinter( it.data.item.toString(),binding.billnumTXT.text.toString())

                            }
                            is State.Error -> {
                                Log.d("billNumToCreateQR","Error")

                            }
                        }
                    }
                }
            }
        }
        binding.reprintreturnBTN.setOnClickListener {

            if (listReturns.isEmpty())
            {
                Toast.makeText(requireContext(),"لا يوجد مرتجعات",Toast.LENGTH_SHORT).show()
            }else{
                returnflag = 1
                msgToPrint = ""
                lifecycleScope.launch {
                    if (binding.billnumTXT.text != null) {
                        availableItemsViewModel.GetBillQRCode(binding.billnumTXT.text.toString()).collect {
                            when (it) {
                                is State.Loading -> {}
                                is State.Success -> {

                                    CastlesPrinter( it.data.item.toString(),binding.billnumTXT.text.toString())

                                }
                                is State.Error -> {
                                    Log.d("billNumToCreateQR","Error")

                                }
                            }

                        }
                    }
                }
            }

        }

        return binding.root
    }


    fun CastlesPrinter(QR: String?,billNum:String) {
        var print_font: String
        val print_x = 45
        val print_y = 36
        val Currently_high = 20
        var ret = 0
        val print = CtPrint()


        if (returnflag ==0){
            print.initPage((list.size*165)+300)
            print.drawImage(textAsBitmap2(registerBillAndPrint2(billNum), 350, 25), 9, 30)

        }else{
            print.initPage((listReturns.size*170)+300)
            print.drawImage(textAsBitmap2(registerBillAndPrint(billNum), 330, 24), 4, 30)
        }

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

      //  this.  msgToPrint = msgToPrint.plus("التاريخ :").plus(currentDate.toString())
        this.  msgToPrint = msgToPrint.plus("التاريخ :").plus(binding.error.text)
        this.  msgToPrint = msgToPrint.plus("   ")
        this.  msgToPrint.plus("\n")
//        this.  msgToPrint =
//            msgToPrint.plus("رقم الموبيل:  ").plus("01112272015 ")
//                .plus("\n")


        //  msgToPrint = msgToPrint.plus("\n")
        //  val split= DialogBill(name)
        //   val
       // Log.d("stringArg",stringArg.toString())
       this.  msgToPrint =
           msgToPrint.plus("اسم العميل :").plus(stringArg)
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

                this.     msgToPrint = msgToPrint.plus(" الصنف : " +listX.Items)
                this.      msgToPrint = msgToPrint.plus("   ")
//                this.      msgToPrint.plus("\n")
//                this.     msgToPrint = msgToPrint.plus("\n")
//                this.      msgToPrint = msgToPrint.plus(":" + listX.sals +listX.returns)
//                this.     msgToPrint = msgToPrint.plus("\n")

                this.   msgToPrint.plus("\n")
                this.  msgToPrint = msgToPrint.plus("\n")

//                this.  msgToPrint = msgToPrint.plus("سعر الكيلو :   " + binding.branchcode.text.toString())
//                this.  msgToPrint = msgToPrint.plus("\n")
//
//                this.  msgToPrint.plus("\n")


                this.  msgToPrint = msgToPrint.plus("العدد:   " + listX.NumberOfUnits)
                this.  msgToPrint.plus("\n")
                this.  msgToPrint = msgToPrint.plus("\n")


                this.  msgToPrint = msgToPrint.plus("الاجمالى :  " +listX.TotalPrice)
                this.  msgToPrint.plus("\n")
                this.  msgToPrint = msgToPrint.plus("\n")
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

        if (binding.currentDefferd.text ==null){}
        else{
            this. msgToPrint = msgToPrint.plus("مديونيه سابقه :   " +binding.currentDefferd.text)
            this. msgToPrint.plus("\n")
            this. msgToPrint = msgToPrint.plus("\n")
        }


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


    fun registerBillAndPrint(numerofBill:String):String {
        //  Log.e("PrintError1", "e.message.toString()")


        //  val    msgToPrint=""
      //  this.msgToPrint = msgToPrint.plus("رقم الفاتورة ").plus(numerofBill).plus("\n")

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

        //  this.  msgToPrint = msgToPrint.plus("التاريخ :").plus(currentDate.toString())
        this.  msgToPrint = msgToPrint.plus("التاريخ :").plus(binding.error.text).plus("\n")


        this.  msgToPrint = msgToPrint.plus("   ")
        this.  msgToPrint.plus("\n")
//        this.  msgToPrint =
//            msgToPrint.plus("رقم الموبيل:  ").plus("01112272015 ").plus("\n")


        //  msgToPrint = msgToPrint.plus("\n")
        //  val split= DialogBill(name)

        this.msgToPrint = msgToPrint.plus("اسم العميل :").plus(stringArg)
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

            for (listX in listReturns) {
                //  if (listX.TransactionType==1) {

                this.     msgToPrint = msgToPrint.plus(" الصنف : " +listX.ItemName)
                this.      msgToPrint = msgToPrint.plus("   ")
//                this.      msgToPrint.plus("\n")
//                this.     msgToPrint = msgToPrint.plus("\n")
//                this.      msgToPrint = msgToPrint.plus(":" + listX.sals +listX.returns)
//                this.     msgToPrint = msgToPrint.plus("\n")

                this.   msgToPrint.plus("\n")
                this.  msgToPrint = msgToPrint.plus("\n")

//                this.  msgToPrint = msgToPrint.plus("سعر الكيلو :   " + binding.branchcode.text.toString())
//                this.  msgToPrint = msgToPrint.plus("\n")
//
//                this.  msgToPrint.plus("\n")


                this.  msgToPrint = msgToPrint.plus("وزن المرتجع من المنتج:   " + listX.size)
                this.  msgToPrint.plus("\n")
                this.  msgToPrint = msgToPrint.plus("\n")


                this.  msgToPrint = msgToPrint.plus("الاجمالى :  " +listX.Amount)
                this.  msgToPrint.plus("\n")
                this.  msgToPrint = msgToPrint.plus("\n")
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

        if (binding.currentDefferd.text ==null){}
        else{
            this. msgToPrint = msgToPrint.plus("مديونيه سابقه :   " +binding.currentDefferd.text)
            this. msgToPrint.plus("\n")
            this. msgToPrint = msgToPrint.plus("\n")
        }


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