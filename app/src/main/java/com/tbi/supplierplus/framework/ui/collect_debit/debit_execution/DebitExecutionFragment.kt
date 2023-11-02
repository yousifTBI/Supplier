package com.tbi.supplierplus.framework.ui.collect_debit.debit_execution

import CTOS.CtPrint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.*
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.pojo.opening.AddCollection
import com.tbi.supplierplus.databinding.FragmentDebitExecutionBinding
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.collect_debit.CollectDebitFragmentDirections
import com.tbi.supplierplus.framework.ui.collect_debit.CollectDebitViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DebitExecutionFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentDebitExecutionBinding
    private val viewModel: CollectDebitViewModel by activityViewModels()
    var paymentMethods = arrayOf("كاش", "شيك", "تحويل بنكي", "محفظة الكترونيه")
    var paymentMethodPosition = 0
    var msgToPrint = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDebitExecutionBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val stringArg = arguments?.getString("stringArg")

        Log.d("stringArg", stringArg.toString())

        binding.cashEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var collection = 0.0f
                if (s!!.isNotEmpty())
                    collection = s.toString().toFloat()

                viewModel.calculateRemaining(collection)

            }

        })

        binding.customerSpinnerLayout2!!.setOnItemSelectedListener(this)

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, paymentMethods)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        binding.customerSpinnerLayout2!!.setAdapter(aa)


//        val customerAutoTV2: AutoCompleteTextView = binding.customerTextView2
//
//        // create list of customer
//        var customerList2 = ArrayList<String>()
//        customerList2 = getCustomerList2()!!
//
//        //Create adapter
//        val adapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, customerList2)
//
//        //Set adapter
//        customerAutoTV2.setAdapter(adapter2)


        binding.printbtn.setOnClickListener {
            if (CheckAllFields()) {
                if (binding.deferredEditText.text.toString().toDouble() < 0
                    || binding.cashEditText.text.toString().toDouble() == 0.0
                ) {

                    Toast.makeText(
                        requireContext(),
                        "لا يمكن تحصيل هذا المبلغ ",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("رسالة تأكيد")
                    builder.setMessage(" هل انت متأكد من اضافة مبلغ ${viewModel.collection.value}")

                    builder.setPositiveButton("ايوة") { dialog, which ->
                        viewModel.debit.observe(viewLifecycleOwner) {

                            viewModel.onCollect(
                                AddCollection(
                                    SharedPreferencesCom.getInstance().gerSharedUser_ID(),
                                    binding.cashEditText.text.toString(),
                                    it?.cus_id.toString(),
                                    binding.deferredEditText.text.toString(),
                                    paymentMethodPosition.toInt()
                                )
                            )

//                    findNavController().navigate(DebitExecutionFragmentDirections.actionDebitExecutionFragmentToCollectDebitFragment(
//                        User("peter_tbi", "", "", "3", "", "2", "", "")
//                    ))
                            CastlesPrinter("qrlink", "1")
                            activity!!.onBackPressed()
                        }

                    }

                    builder.setNegativeButton("لا") { dialog, which ->
                        dialog.cancel()
                    }
                    builder.show()
                }

            } else {
                Toast.makeText(context, "ادخل المبلغ المطلوب", Toast.LENGTH_SHORT)
            }

        }

        return binding.root
    }


    private fun getCustomerList2(): ArrayList<String>? {
        val customers = ArrayList<String>()
        customers.add("KGM")
        customers.add("EA")
        return customers
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        paymentMethodPosition = position + 1
        Log.d("setOnItemSelectedListener", paymentMethodPosition.toString())

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


    private fun CheckAllFields(): Boolean {
        if (binding.cashEditText.length() == 0) {
            binding.cashEditText.setError("This field is required")
            return false
        }
        // after all validation return true.
        return true
    }


    fun CastlesPrinter(QR: String?, billNum: String) {
        var print_font: String
        val print_x = 45
        val print_y = 36
        val Currently_high = 20
        var ret = 0
        val print = CtPrint()


        print.initPage(680)
        print.drawImage(textAsBitmap2(registerBillAndPrint2(billNum), 350, 25), 9, 30)

        print.printPage()
        //  var bitmap: Bitmap? = null
        print.initPage(20)
        //  bitmap = print.encodeToBitmap(QR, CtPrint.QR_CODE, 350, 320)
        //   print.drawImage(bitmap, 0, 0)
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

    fun registerBillAndPrint2(numerofBill: String): String {
        //  Log.e("PrintError1", "e.message.toString()")
        //  val    msgToPrint=""
        // this.msgToPrint = msgToPrint.plus("رقم الفاتورة ").plus(numerofBill).plus("\n")

        this.msgToPrint =
            msgToPrint.plus("بواسطة : ").plus("شركه البان الدوار  ")
                .plus("\n")

        this.msgToPrint =
            msgToPrint.plus("س.ت  ").plus(" 173847 ")
                .plus("\n")
        this.msgToPrint =
            msgToPrint.plus("ت.ض : ").plus(" 679/427/597 ")
                .plus("\n")

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        this.msgToPrint = msgToPrint.plus("التاريخ :").plus(currentDate.toString()).plus("\n")
        this.msgToPrint = msgToPrint.plus("   ")
        this.msgToPrint.plus("\n")
        this.msgToPrint.plus("\n")
//        this.  msgToPrint =
//            msgToPrint.plus("رقم الموبيل:  ").plus("01112272015 ")
//                .plus("\n")


        //  msgToPrint = msgToPrint.plus("\n")
        //  val split= DialogBill(name)
        //   val
        this.msgToPrint =
            msgToPrint.plus("اسم العميل ").plus(binding.BranchnameId.text.toString()).plus("\n")

        // msgToPrint =
        //     msgToPrint.plus("التوقيت ").plus(SimpleDateFormat("dd/M/yyyy").format(Date()))
        //         .plus(" ").plus(SimpleDateFormat("hh:mm:ss").format(Date())).plus("\n")

        //   if (_bill.value!!.isNotEmpty()) {
        this.msgToPrint = msgToPrint.plus("____________ تحصيل ____________")
        this.msgToPrint = msgToPrint.plus("\n")
        //  repeat(_bill.value!!.size) {


        //   if (list.isEmpty()) {
        //   } else {

        //        for (listX in list) {
        //  if (listX.TransactionType==1) {

        //   this.     msgToPrint = msgToPrint.plus(" الصنف : " +listX.Items)
        //   this.      msgToPrint = msgToPrint.plus("   ")
//                this.      msgToPrint.plus("\n")
//                this.     msgToPrint = msgToPrint.plus("\n")
//                this.      msgToPrint = msgToPrint.plus(":" + listX.sals +listX.returns)
//                this.     msgToPrint = msgToPrint.plus("\n")

        //    this.   msgToPrint.plus("\n")
        //    this.  msgToPrint = msgToPrint.plus("\n")

//                this.  msgToPrint = msgToPrint.plus("سعر الكيلو :   " + binding.branchcode.text.toString())
//                this.  msgToPrint = msgToPrint.plus("\n")
//
//                this.  msgToPrint.plus("\n")


        //    this.  msgToPrint = msgToPrint.plus("العدد:   " + listX.NumberOfUnits)
        //    this.  msgToPrint.plus("\n")
        //    this.  msgToPrint = msgToPrint.plus("\n")


        this.msgToPrint = msgToPrint.plus("المدفوع :  " + binding.cashEditText.text.toString())
        this.msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("\n")


        this.msgToPrint = msgToPrint.plus("الباقي :  " + binding.deferredEditText.text.toString())
        this.msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("\n")

        this.msgToPrint = msgToPrint.plus(
            "طريقة الدفع :  " + paymentMethods.get(paymentMethodPosition - 1).toString()
        )
        this.msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("\n")

        this.msgToPrint = msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("******************************")
        this.msgToPrint = msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("       المستلم   ")

        this.msgToPrint = msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("******************************")
        this.msgToPrint = msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("Powered by")
        this.msgToPrint = msgToPrint.plus("\n")
        this.msgToPrint = msgToPrint.plus("Technology & Business Integration")
        //  this.  msgToPrint = msgToPrint.plus("________________________")

        //       }
        //  }

//        this. msgToPrint = msgToPrint.plus("\n")
//        this. msgToPrint = msgToPrint.plus("اجمالي المبيعات :   " )
//        this. msgToPrint = msgToPrint.plus("   ")
//        this. msgToPrint.plus("\n")
//        this. msgToPrint = msgToPrint.plus("\n")

        //    msgToPrint = msgToPrint.plus("الوزن " +" _bill.value!![it].size")
//        this. msgToPrint = msgToPrint.plus("المديونيه السابقه :   " )
//        this. msgToPrint = msgToPrint.plus("\n")

        //  this. msgToPrint.plus("\n")

//        this. msgToPrint = msgToPrint.plus("اجمالى المرتجعات :   " )
//        this. msgToPrint.plus("\n")
//        this. msgToPrint = msgToPrint.plus("\n")
//        this. msgToPrint = msgToPrint.plus("\n")

        //   if (binding.currentDefferd.text ==null){}
        //   else{
        //       this. msgToPrint = msgToPrint.plus("مديونيه سابقه :   " +binding.currentDefferd.text)
        //       this. msgToPrint.plus("\n")
        //       this. msgToPrint = msgToPrint.plus("\n")
        //   }


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
        this.msgToPrint = msgToPrint.plus("  ")
        this.msgToPrint = msgToPrint.plus("\n")


        Log.e("PrintError1", this.msgToPrint)
        return this.msgToPrint

    }


}

//private fun DialogInterface.OnClickListener.setOnItemSelectedListener(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
//   Log.d("setOnItemSelectedListener",languages[position])
//}

