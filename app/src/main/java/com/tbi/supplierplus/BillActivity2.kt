package com.tbi.supplierplus

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.tbi.supplierplus.business.pojo.Items
import com.tbi.supplierplus.business.pojo.billModels.SaleingBill
import com.tbi.supplierplus.databinding.ActivityBill2Binding
import com.tbi.supplierplus.framework.datasource.requests.State
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.collect_debit.CollectDebitViewModel
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import com.tbi.supplierplus.framework.ui.sales.add_customer.AddCustomerViewModel
import com.tbi.supplierplus.framework.ui.sales.customer_profile.OnCategoryClickListener
import com.tbi.supplierplus.framework.ui.sales.customer_profile.SaleingBillAdpter
import com.tbi.supplierplus.framework.ui.scanner.capture
import dagger.hilt.android.AndroidEntryPoint
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import kotlinx.android.synthetic.main.dilog_bill_row_discount.*
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint

class BillActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityBill2Binding
    lateinit var viewModel: SalesViewModel
    lateinit var addCustomerviewModel: AddCustomerViewModel
    var list = ArrayList<Items>()

    // var adapte = SaleingBillAdpter
    lateinit var adapte: SaleingBillAdpter
    var listBill = ArrayList<SaleingBill>()
    private val barcode = StringBuffer()
    lateinit var message: String
    var s = 0.0
    var itemBalance = 0.0
    var changeItemRowBalance = 0.0

    private lateinit var viewModel2: CollectDebitViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill2)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bill2);
        viewModel = ViewModelProvider(this).get(SalesViewModel::class.java)
        viewModel2 = ViewModelProvider(this).get(CollectDebitViewModel::class.java)
        addCustomerviewModel = ViewModelProvider(this).get(AddCustomerViewModel::class.java)

        message = intent.getStringExtra("Customer_ID").toString()
        val oldRemainningAmount = intent.getStringExtra("Unpaid_deferred")
        val CompanyName = intent.getStringExtra("CompanyName")
        //   Log.d("messagee", message.toString())

        //put count in recyclview row
        adapte = SaleingBillAdpter(onCategoryClickListener = OnCategoryClickListener {
            //  Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()

            // set value in array list
            var oldChangeItemRowBalance = 0.0
            oldChangeItemRowBalance = listBill[it].NumberOfUnits.toDouble()
            Log.d("oldChangeItemRowBalance", oldChangeItemRowBalance.toString())
            var arrayList = java.util.ArrayList<Int>()

            // set value in array list
            arrayList.add(1)
            arrayList.add(2)
            arrayList.add(3)
            arrayList.add(4)
            arrayList.add(5)
            arrayList.add(6)
            arrayList.add(7)
            arrayList.add(8)
            arrayList.add(9)
            arrayList.add(10)
            arrayList.add(11)
            arrayList.add(12)
            arrayList.add(13)
            arrayList.add(14)
            arrayList.add(15)

            // Initialize dialog
            val dialog = Dialog(this)

            // set custom dialog
            dialog.setContentView(android.R.layout.list_content)

            // set custom height and width
            dialog.window!!.setLayout(300, 300)

            // set transparent background
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))

            // show dialog
            dialog.show()

            // Initialize and assign variable
            // EditText editText=dialog.findViewById(R.id.edit_text);
            val listView = dialog.findViewById<ListView>(android.R.id.list)

            // Initialize array adapter
            val adapterlist: ArrayAdapter<Int> =
                ArrayAdapter<Int>(this, android.R.layout.simple_list_item_1, arrayList)

            // set adapter
            listView.adapter = adapterlist

            listView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->

                    Toast.makeText(
                        this,
                        adapterlist.getItem(position).toString(),
                        Toast.LENGTH_SHORT
                    ).show()


                    listBill[it].NumberOfUnits = adapterlist.getItem(position).toString()!!
                    listBill[it].TotalPrice = (adapterlist.getItem(position)!!
                        .toDouble() * listBill[it].UnitPrice.toDouble()).toString()!!

                    changeItemRowBalance += listBill[it].NumberOfUnits.toDouble() - oldChangeItemRowBalance.toDouble()
                    calculateSum(listBill)
                    adapte.submitList(listBill)
                    adapte.notifyDataSetChanged()

                    dialog.dismiss()

                }
        })


        binding.textView77.setText(CompanyName + " / " + "مديونيه:  " + oldRemainningAmount)
        supportActionBar?.hide()
        binding.oldRemainningAmount.setText(oldRemainningAmount)



        addCustomerviewModel.getProductByBarcodeLivedata.observe(this) {
            when (it) {

                is com.tbi.supplierplus.framework.ui.login.State.Loading -> {}
                is com.tbi.supplierplus.framework.ui.login.State.Success -> {
                    Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()
                    Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()



                    if (it.data.State == 1) {
                        val split = DialogBill(it.data.Item!!.item)
                        val dialog = this?.let { it1 -> Dialog(it1) }
                        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        //بيعمل بلوك للباك جراوند
                        //  dialog?.setCancelable(false)
                        dialog?.setContentView(R.layout.dilog_bill_row)
                        dialog?.getWindow()?.setLayout(750, 1150)
                        dialog?.show()

                        var size = it.data.Item.size
                        var CustomerSellingPrice = it.data.Item.CustomerSellingPrice

                        lateinit var tv_name: TextView
                        tv_name = dialog?.findViewById(R.id.textView57)!!
                        tv_name.setText(split.name)

                        var itemName = it.data.Item?.item

                        lateinit var tv_group: TextView
                        tv_group = dialog?.findViewById(R.id.textView52)!!
                        tv_group.setText(it.data.Item!!.Item_ID.toString())

                        lateinit var tv_sur: TextView
                        tv_sur = dialog?.findViewById(R.id.textView54)!!
                        tv_sur.setText("")

                        lateinit var tv_size: TextView
                        tv_size = dialog?.findViewById(R.id.textView56)!!
                        tv_size.setText(it.data.Item.size.toString())

                        var itemSiza = it.data.Item.size.toString()


//                        Log.d(
//                            "findViewByIdfindViewById",
//                            it.data.Item.size.toString() + "" + it.data.Item.CustomerSellingPrice.toString()
//                        )
                        lateinit var tv_price: TextView
                        tv_price = dialog?.findViewById(R.id.textView53)!!
                        tv_price.setText(it.data.Item.CustomerSellingPrice.toString())
                        var sellingPriceItem = it.data.Item.CustomerSellingPrice.toString()

                        lateinit var code: TextView
                        code = dialog?.findViewById(R.id.textView55)!!
                        code.setText(it.data.Item.Item_ID.toString())


                        var supplierId = it.data.Item.Supplier_ID
                        var supplierPrice = it.data.Item.Supply_Price
                        var itemID = it.data.Item.Item_ID

                        lateinit var NumberOfUnits: TextInputEditText
                        NumberOfUnits = dialog?.findViewById(R.id.counterEditTextm)

                        lateinit var sizesEditText: TextInputEditText
                        sizesEditText = dialog?.findViewById(R.id.sizesEditTextm)
                        sizesEditText.setText(it.data.Item.size.toString())


                        lateinit var discountEditTextk: EditText
                        discountEditTextk = dialog.findViewById(R.id.discountEditTextk)
                        var discount = discountEditTextk.text.toString()


                        lateinit var buttonAddToReturnsBill: Button
                        buttonAddToReturnsBill = dialog.findViewById(R.id.buttonm)

                        lateinit var buttonAddToSalingBill: Button
                        buttonAddToSalingBill = dialog.findViewById(R.id.button2b)

                        /////
                        lateinit var textView98: TextView
                        textView98 = dialog.findViewById(R.id.textView98)

                        lateinit var textView99: TextView
                        textView99 = dialog.findViewById(R.id.textView99)

                        lateinit var textView100: TextView
                        textView100 = dialog.findViewById(R.id.textView100)

                        /////
                        lateinit var reason: Button
                        reason = dialog.findViewById(R.id.reason)
                        reason.setOnClickListener {
                            val dialog2 = this?.let { it1 -> Dialog(it1) }
                            dialog2?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            //بيعمل بلوك للباك جراوند
                            //  dialog?.setCancelable(false)
                            dialog2?.setContentView(R.layout.dilog_bill_row_discount)
                            dialog2?.getWindow()?.setLayout(720, 1100)
                            dialog2?.show()


                            lateinit var cancel: Button
                            cancel = dialog2.findViewById(R.id.cancel)
                            cancel.setOnClickListener { dialog2.dismiss() }

                            lateinit var ok: Button
                            ok = dialog2.findViewById(R.id.printbtn1)

                            var collection = 0.0f

                            lateinit var discount: TextInputEditText
                            discount = dialog2?.findViewById(R.id.billDiscountEditText1)


                            lateinit var discount2: TextInputLayout
                            discount2 = dialog2?.findViewById(R.id.textinput_billDiscount)
                            //  discount2.visibility==0
                            discount2.setVisibility(View.GONE);
                            lateinit var billDiscountEditText9: TextInputEditText
                            billDiscountEditText9 =
                                dialog2?.findViewById(R.id.billDiscountEditText9)

                            discount.addTextChangedListener(object : TextWatcher {
                                override fun afterTextChanged(s: Editable?) {
                                }

                                override fun beforeTextChanged(
                                    s: CharSequence?,
                                    start: Int,
                                    count: Int,
                                    after: Int
                                ) {
                                }

                                override fun onTextChanged(
                                    s: CharSequence?,
                                    start: Int,
                                    before: Int,
                                    count: Int
                                ) {


                                    if (s!!.isNotEmpty())

                                        try {
                                            if (s.toString().toFloat() > size) {
                                                Toast.makeText(
                                                    this@BillActivity2,
                                                    "الوزن كبير", Toast.LENGTH_SHORT
                                                ).show()

                                                Toast.makeText(
                                                    this@BillActivity2,
                                                    "الوزن كبير", Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                collection = (s.toString()
                                                    .toFloat() * CustomerSellingPrice / size).toFloat()
                                                billDiscountEditText9.setText(collection.toString())

                                            }

                                        } catch (e: java.lang.Exception) {
                                            billDiscountEditText9.setText("0")
                                        }
                                }

                            })
                            lateinit var discribtionText: TextInputEditText
                            discribtionText = dialog2?.findViewById(R.id.discribtionText)
                            discribtionText.setText("")
                            lateinit var spinner: Spinner
                            spinner = dialog2?.findViewById(R.id.spinner)

                            val languages = resources.getStringArray(R.array.Languages)
                            if (spinner != null) {
                                val adapter = ArrayAdapter(
                                    this,
                                    android.R.layout.simple_spinner_item, languages
                                )
                                spinner.adapter = adapter

                                spinner.onItemSelectedListener = object :
                                    AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        parent: AdapterView<*>,
                                        view: View, position: Int, id: Long
                                    ) {
                                        textView100.setText(languages[position])
                                        if (languages[position].equals("فرق وزن")) {
                                            discount2.setVisibility(View.VISIBLE);

                                        } else {
                                            discount2.setVisibility(View.GONE);

                                        }
                                        // discount.setText(languages[position].toString())
                                        //  discount.hint ="ادخل القيمة"
                                        Toast.makeText(
                                            this@BillActivity2,
                                            languages[position], Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    override fun onNothingSelected(p0: AdapterView<*>?) {
                                        //           Log.d("sdkshf", p0.toString())
                                    }

                                }
                            }
                            ok.setOnClickListener {

                                discountEditTextk.setText(billDiscountEditText9.text.toString())
                                textView98.setText(discribtionText.text.toString())
                                textView99.setText(discount.text.toString())
                                dialog2.dismiss()


                            }

                        }
                        buttonAddToSalingBill.setOnClickListener {


                            var x: Int = Integer.parseInt(NumberOfUnits.text.toString())

                            var z = x.toDouble()


                            var TotalPrice: Double
                            TotalPrice =
                                sellingPriceItem.toDouble() * z - discountEditTextk.text.toString()
                                    .toDouble()


                            listBill.add(
                                SaleingBill(
                                    1,
                                    "1",
                                    itemName.toString(),
                                    NumberOfUnits.text.toString(),
                                    itemSiza,
                                    sellingPriceItem,
                                    discountEditTextk.text.toString(),
                                    TotalPrice.toString(),
                                    itemID.toString(),
                                    supplierId.toString(),
                                    supplierPrice.toString(),
                                    1.0,
                                    "  بيع :",
                                    "",
                                    textView98.text.toString(),
                                    textView100.text.toString(),
                                    textView99.text.toString().toDouble()
                                )
                            )
                            s = 0.0

                            calculateSum(listBill)
                            adapte.submitList(listBill)
                            adapte.notifyItemInserted(listBill.size - 1)
                            dialog.dismiss()

                        }

                        buttonAddToReturnsBill.setOnClickListener {
                            //               Log.d("jhswjllq", "ReturnsBill")


                            var price = sellingPriceItem.toString()
                            //             Log.d("price", price)

                            var x: Int = Integer.parseInt(NumberOfUnits.text.toString())
                            //              Log.d("price", x.toString())

                            var z = x.toDouble()
                            //            Log.d("price", z.toString())


                            var TotalPrice: Double
                            TotalPrice = price.toDouble() * z


                            var PriceAfterEditSize: Double
                            PriceAfterEditSize =
                                sizesEditText.text.toString().toDouble() * TotalPrice.toString()
                                    .toDouble()


                            var TotalPriceAfterEditSize: Double
                            TotalPriceAfterEditSize =
                                PriceAfterEditSize / itemSiza.toString().toDouble()
                            listBill.add(
                                SaleingBill(
                                    0,
                                    "1",
                                    itemName,
                                    NumberOfUnits.text.toString(),
                                    sizesEditText.text.toString(),
                                    sellingPriceItem.toString(),
                                    discountEditTextk.text.toString(),
                                    TotalPriceAfterEditSize.toString(),
                                    itemID.toString(),
                                    supplierId.toString(),
                                    sellingPriceItem.toString(),
                                    1.0,
                                    "",
                                    "  مرتجع :   ",
                                    "",
                                    "", 0.0
                                )
                            )
                            var TotalPrices = 0.0
                            for (item in listBill) {
                                if (item.TransactionType == 0) {

                                    var z = item.TotalPrice.toDouble()
                                    TotalPrices = TotalPrices + z
                                }
                            }
                            binding.Totalss2.setText(TotalPrices.toString())

                            val totals = binding.Totalss.text.toString().toDouble()
//                            -binding.Totalss2.text.toString().toDouble()
//                            +binding.Totalssz4.text.toString().toDouble()

                            binding.Totalss4.setText(
                                totals.toString()
                            )

                            adapte.submitList(listBill)

                            adapte.notifyDataSetChanged()

                            adapte.notifyItemInserted(listBill.size - 1)


                            dialog.dismiss()

                        }
                    }

                }
                is com.tbi.supplierplus.framework.ui.login.State.Error -> {

                }
            }
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //  Toast.makeText(ItemsActivity.this, "Swipe to delete", Toast.LENGTH_SHORT).show();
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Snackbar.make(binding.billRecycler1, "Deleted item", Snackbar.LENGTH_SHORT)
                    .setAction(listBill[viewHolder.adapterPosition].Items,
                        View.OnClickListener { }).show()

                itemBalance += listBill[viewHolder.adapterPosition].NumberOfUnits.toString()
                    .toDouble()
                Log.d("oldChangeItemRowBalance", itemBalance.toString())
                listBill.removeAt(viewHolder.adapterPosition)

                d()
                mortaga3()
                adapte.notifyDataSetChanged()
            }
        }).attachToRecyclerView(binding.billRecycler1)



        binding.billRecycler1.adapter = adapte

        binding.textView22.setOnClickListener {


            val intentIntegrator = IntentIntegrator(this@BillActivity2)


            intentIntegrator.setPrompt("For flash use Volump up key")
            intentIntegrator.setBeepEnabled(true)
            intentIntegrator.setOrientationLocked(true)
            intentIntegrator.setCaptureActivity(capture::class.java)
            intentIntegrator.initiateScan()

        }


        binding.imageView9.setOnClickListener {
            SimpleSearchDialogCompat(this, "ادخل اسم المنتج  " + "\n", "search", null,
                inits(), SearchResultListener { baseSearchDialogCompat, item, pos ->

                    //       Log.d("buttonAddToSalingBisll", pos.toString())
                    //   val bal =it.bal
                    val sizez = item.size

                    val bal = item.bal

                    val cunt = item.count
                    val split = DialogBill(item.item)

                    val code1 = item.Item_ID.toString()

                    val dialog = this?.let { it1 -> Dialog(it1) }
                    dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    //بيعمل بلوك للباك جراوند
                    //  dialog?.setCancelable(false)
                    dialog?.setContentView(R.layout.dilog_bill_row)
                    dialog?.getWindow()?.setLayout(750, 1150)
                    dialog?.show()


                    lateinit var tv_name: TextView
                    tv_name = dialog?.findViewById(R.id.textView57)!!
                    tv_name.setText(split.name)
                    lateinit var availabelItemCount: TextView
                    availabelItemCount = dialog?.findViewById(R.id.availabelItemCount)!!
                    tv_name.setText(split.name)

                    Log.d(
                        "oldChangeItemRowBalance",
                        bal.toString() + "bal  " + cunt.toString() + "cunt " + itemBalance.toString() + "itembal " + changeItemRowBalance.toString() + "  ch"
                    )
                    availabelItemCount.setText(
                        (bal.toString().toDouble() - cunt.toString()
                            .toDouble() + itemBalance - changeItemRowBalance).toString()
                    )

                    lateinit var tv_group: TextView
                    tv_group = dialog?.findViewById(R.id.textView52)!!
                    tv_group.setText(split.group)

                    lateinit var tv_sur: TextView
                    tv_sur = dialog?.findViewById(R.id.textView54)!!
                    tv_sur.setText(split.sur)

                    lateinit var tv_size: TextView
                    tv_size = dialog?.findViewById(R.id.textView56)!!
                    tv_size.setText(split.size)


                    lateinit var tv_price: TextView
                    tv_price = dialog?.findViewById(R.id.textView53)!!
                    tv_price.setText(item.CustomerSellingPrice.toString())

                    lateinit var code: TextView
                    code = dialog?.findViewById(R.id.textView55)!!
                    code.setText(item.Item_ID.toString())


                    lateinit var NumberOfUnits: TextInputEditText
                    NumberOfUnits = dialog?.findViewById(R.id.counterEditTextm)


                    lateinit var sizesEditText: TextInputEditText
                    sizesEditText = dialog?.findViewById(R.id.sizesEditTextm)
                    sizesEditText.setText(item.size.toString())


                    lateinit var discountEditTextk: EditText
                    discountEditTextk = dialog.findViewById(R.id.discountEditTextk)
                    var discount = discountEditTextk.text.toString()


                    lateinit var buttonAddToReturnsBill: Button
                    buttonAddToReturnsBill = dialog.findViewById(R.id.buttonm)

                    lateinit var textView98: TextView
                    textView98 = dialog.findViewById(R.id.textView98)

                    lateinit var textView99: TextView
                    textView99 = dialog.findViewById(R.id.textView99)
                    textView99.setText("0.0")

                    lateinit var textView100: TextView
                    textView100 = dialog.findViewById(R.id.textView100)

                    var valid = NumberOfUnits.text.toString().toDouble() * item.CustomerSellingPrice


                    lateinit var reason: Button
                    reason = dialog.findViewById(R.id.reason)
                    reason.setOnClickListener {
                        val dialog2 = this?.let { it1 -> Dialog(it1) }
                        dialog2?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        //بيعمل بلوك للباك جراوند
                        //  dialog?.setCancelable(false)
                        dialog2?.setContentView(R.layout.dilog_bill_row_discount)
                        dialog2?.getWindow()?.setLayout(720, 1100)
                        dialog2?.show()


                        lateinit var cancel: Button
                        cancel = dialog2.findViewById(R.id.cancel)
                        cancel.setOnClickListener { dialog2.dismiss() }

                        lateinit var ok: Button
                        ok = dialog2.findViewById(R.id.printbtn1)

                        lateinit var confirmPricee: Button
                        confirmPricee = dialog2?.findViewById(R.id.confirmPrice)

                        lateinit var confirmWieght: Button
                        confirmWieght = dialog2?.findViewById(R.id.confirmWieght)

                        var collection = 0.0f

                        lateinit var discount: TextInputEditText
                        discount = dialog2?.findViewById(R.id.billDiscountEditText1)

                        lateinit var discount2: TextInputLayout
                        discount2 = dialog2?.findViewById(R.id.textinput_billDiscount)
                        //  discount2.visibility==0
                        discount2.setVisibility(View.GONE);
                        lateinit var billDiscountEditText9: TextInputEditText
                        billDiscountEditText9 = dialog2?.findViewById(R.id.billDiscountEditText9)

                        discount.addTextChangedListener(object : TextWatcher {
                            override fun afterTextChanged(s: Editable?) {
                            }

                            override fun beforeTextChanged(
                                s: CharSequence?,
                                start: Int,
                                count: Int,
                                after: Int
                            ) {
                            }

                            override fun onTextChanged(
                                s: CharSequence?,
                                start: Int,
                                before: Int,
                                count: Int
                            ) {


                                if (s!!.isNotEmpty())

                                    try {
                                        if (s.toString().toFloat() > item.size) {
                                            Toast.makeText(
                                                this@BillActivity2,
                                                "الوزن كبير",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            Toast.makeText(
                                                this@BillActivity2,
                                                "الوزن كبير",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            val arabicLocale = Locale("ar")
                                            collection = (discount.text.toString()
                                                .toFloat() * item.CustomerSellingPrice / item.size).toFloat()
                                            val number3digits =
                                                String.format(arabicLocale, "%.2f", collection)
                                            val englishNumber =
                                                convertToEnglishDigits(number3digits.toString())
                                            //  val number3digits: Double = String.format("%.2f", collection).toDouble()
                                            billDiscountEditText9.setText(englishNumber.toString())

                                        }

                                    } catch (e: java.lang.Exception) {
                                        billDiscountEditText9.setText("0")
                                    }
                            }

                        })
                        /*               //wieght listener
                                     confirmWieght.setOnClickListener {
                                         val arabicLocale = Locale("ar")
                                      //  var englishFormattedNumber =convertToEnglishDigits(discount.text.toString())

                                       //  Log.d("englishFormattedNumber",englishFormattedNumber.toString())
                                         if (discount.text.isNullOrEmpty()) {
                                             billDiscountEditText9.setText("0.0")
                                         } else {
                                                 if (discount.text.toString().toFloat() > item.size) {
                                                     Toast.makeText(
                                                         this@BillActivity2,
                                                         "الوزن كبير",
                                                         Toast.LENGTH_SHORT
                                                     ).show()
                                                     Toast.makeText(
                                                         this@BillActivity2,
                                                         "الوزن كبير",
                                                         Toast.LENGTH_SHORT
                                                     ).show()
                                                 } else {
                                                     collection = (discount.text.toString()
                                                         .toFloat() * item.CustomerSellingPrice / item.size).toFloat()
                                                     val number3digits = String.format(arabicLocale, "%.2f", collection)
                                                     val englishNumber = convertToEnglishDigits(number3digits.toString())
                                                   //  val number3digits: Double = String.format("%.2f", collection).toDouble()
                                                     billDiscountEditText9.setText(englishNumber.toString())

                                                 }

                                             }
                                     }*/

                        //price listener
                        confirmPricee.setOnClickListener {
                            val arabicLocale = Locale("ar")
                            //   val formattedNumber = String.format(arabicLocale, "%.3f", collection)
                            //   val englishFormattedNumber = convertToEnglishDigits(billDiscountEditText9.text.toString())

                            if (billDiscountEditText9.text.isNullOrEmpty()) {
                                billDiscountEditText9.setText("0.0")
                            } else {
                                if (billDiscountEditText9.text.toString()
                                        .toFloat() > item.CustomerSellingPrice
                                ) {
                                    Toast.makeText(
                                        this@BillActivity2,
                                        "الوزن كبير",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Toast.makeText(
                                        this@BillActivity2,
                                        "الوزن كبير",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    collection = (billDiscountEditText9.text.toString()
                                        .toFloat() / item.CustomerSellingPrice * item.size).toFloat()
                                    val number3digits =
                                        String.format(arabicLocale, "%.2f", collection)
                                    val englishNumber =
                                        convertToEnglishDigits(number3digits.toString())
                                    //  val englishNumber = convertToEnglishDigits(number3digits.replace(',', '.'))

                                    //       val number3digits: Double =
                                    //           String.format("%.3f", collection).toDouble()
                                    discount.setText(englishNumber.replace(',', '.').toString())

                                }
                            }
                        }


                        lateinit var discribtionText: TextInputEditText
                        discribtionText = dialog2?.findViewById(R.id.discribtionText)

                        lateinit var spinner: Spinner
                        spinner = dialog2?.findViewById(R.id.spinner)

                        val languages = resources.getStringArray(R.array.Languages)
                        if (spinner != null) {
                            val adapter = ArrayAdapter(
                                this,
                                android.R.layout.simple_spinner_item, languages
                            )
                            spinner.adapter = adapter

                            spinner.onItemSelectedListener = object :
                                AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>,
                                    view: View, position: Int, id: Long
                                ) {
                                    textView100.setText(languages[position])
                                    if (languages[position].equals("فرق وزن")) {
                                        discount2.setVisibility(View.VISIBLE);

                                    } else {
                                        discount2.setVisibility(View.GONE);

                                    }
                                    // discount.setText(languages[position].toString())
                                    //  discount.hint ="ادخل القيمة"
                                    Toast.makeText(
                                        this@BillActivity2,
                                        languages[position], Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onNothingSelected(p0: AdapterView<*>?) {
                                    //          Log.d("sdkshf", p0.toString())
                                }

                            }
                        }
                        ok.setOnClickListener {

                            if (discribtionText.text.isNullOrEmpty() || discribtionText.text.toString() == " ") {
                                discribtionText.setError("This field is required")
                            } else {

                                if (valid > billDiscountEditText9.text.toString().toDouble()) {
                                    discountEditTextk.setText(billDiscountEditText9.text.toString())
                                    textView98.setText(discribtionText.text.toString())
                                    textView99.setText(discount.text.toString())
                                    dialog2.dismiss()
                                } else {
                                    Toast.makeText(
                                        this@BillActivity2,
                                        "الخصم اكبر من المسموح", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }

                    }


                    lateinit var buttonAddToSalingBill: Button
                    buttonAddToSalingBill = dialog.findViewById(R.id.button2b)


                    buttonAddToSalingBill.setOnClickListener {


                        if (bal - cunt - NumberOfUnits.text.toString().toDouble() < 0.0) {

                            Toast.makeText(this, "العدد المطلوب اكبر من المتاح", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            list[pos].count += NumberOfUnits.text.toString().toDouble()
                            var price = item.Price_ID.toString()
                            //          Log.d("roiuwk", item.Price_ID.toString())
                            //          Log.d("roiuwk", item.CustomerSellingPrice.toString())
                            //          Log.d("roiuwk", item.getPrice_ID())

                            var x: Double = NumberOfUnits.text.toString().toDouble()

                            var z = x.toDouble()


                            var TotalPrice: Double
                            TotalPrice =
                                item.CustomerSellingPrice.toDouble() * z - discountEditTextk.text.toString()
                                    .toDouble()


                            listBill.add(
                                SaleingBill(
                                    1,
                                    "1",
                                    split.name,
                                    NumberOfUnits.text.toString(),
                                    sizez.toString(),
                                    item.CustomerSellingPrice.toString(),
                                    discountEditTextk.text.toString(),
                                    TotalPrice.toString(),
                                    code1,
                                    item.Supplier_ID.toString(),
                                    item.Supply_Price.toString(),
                                    1.0,
                                    "  بيع :",
                                    "",
                                    textView98.text.toString(),
                                    textView100.text.toString(),
                                    textView99.text.toString().toDouble()


                                )
                            )
                            var s = 0.0
                            for (item in listBill) {
                                if (item.TransactionType == 1) {
                                    var z = item.TotalPrice.toDouble()
                                    s = s + z
                                }


                            }


                            binding.Totalss.setText(s.toString())


                            val totals = binding.Totalss.text.toString().toDouble()
//                             -
//                                     binding.Totalss2.text.toString().toDouble() +
//                                     binding.Totalssz4.text.toString().toDouble()


                            binding.Totalss4.setText(
                                totals.toString()
                            )

                            adapte.submitList(listBill)
                            adapte.notifyItemInserted(listBill.size - 1)

                            dialog.dismiss()

                            //    Toast.makeText(this, "العدد المطلوب اكبر من المتاح", Toast.LENGTH_SHORT).show()
                        }


                    }
                    //          Log.d("buttonAddToSalingBill", NumberOfUnits.text.toString() + "...")
                    //          Log.d("buttonAddToSalingBill", bal.toString() + "...1")


                    buttonAddToReturnsBill.setOnClickListener {
                        //       Log.d("jhswjllq", "ReturnsBill")


                        var price = item.CustomerSellingPrice.toString()
                        //          Log.d("price", price)

                        var x: Int = Integer.parseInt(NumberOfUnits.text.toString())
                        //          Log.d("price", x.toString())

                        var z = x.toDouble()
                        //          Log.d("price", z.toString())


                        var TotalPrice: Double
                        TotalPrice = price.toDouble() * z
                        //        Log.d("price", TotalPrice.toString())
                        //        Log.d("price", item.CustomerSellingPrice.toString())


                        var PriceAfterEditSize: Double
                        PriceAfterEditSize =
                            sizesEditText.text.toString().toDouble() * TotalPrice.toString()
                                .toDouble()


                        var TotalPriceAfterEditSize: Double
                        TotalPriceAfterEditSize =
                            PriceAfterEditSize / item.size.toString().toDouble()
                        listBill.add(
                            SaleingBill(
                                0,
                                "1",
                                split.name,
                                NumberOfUnits.text.toString(),
                                sizesEditText.text.toString(),
                                item.CustomerSellingPrice.toString(),
                                discountEditTextk.text.toString(),
                                TotalPriceAfterEditSize.toString(),
                                code1,
                                item.Supplier_ID.toString(),
                                item.Supply_Price.toString(),
                                1.0,
                                "",
                                "  مرتجع :   ",
                                textView98.text.toString(),
                                textView100.text.toString(),
                                textView99.text.toString().toDouble()
                            )
                        )
                        var TotalPrices = 0.0
                        for (item in listBill) {
                            if (item.TransactionType == 0) {

                                var z = item.TotalPrice.toDouble()
                                TotalPrices = TotalPrices + z
                            }
                        }
                        binding.Totalss2.setText(TotalPrices.toString())

                        val totals = binding.Totalss.text.toString().toDouble()
//                        -
//                                binding.Totalss2.text.toString().toDouble() +
//                                binding.Totalssz4.text.toString().toDouble()

                        binding.Totalss4.setText(
                            totals.toString()
                        )

                        adapte.submitList(listBill)

                        adapte.notifyDataSetChanged()

                        adapte.notifyItemInserted(listBill.size - 1)

                        dialog.dismiss()
                    }


                    //        //بيعمل بلوك للباك جراوند
                    //        //  dialog?.setCancelable(false)
                    //        dialog?.setContentView(R.layout.dilog_bill_row)

                    baseSearchDialogCompat.dismiss()
                }).show()

        }



        viewModel.getAllItemss(message!!.toInt())

        viewModel.getProducers.observe(this) {


            if (it.isNullOrEmpty()) {
            } else {
                list = it as ArrayList<Items>
            }


        }


        viewModel.getItemByBarcodeLiveData.observe(this) {

            when (it) {

                is State.Loading -> Toast.makeText(baseContext, it.toLoading(), Toast.LENGTH_SHORT)
                    .show()

                //Log.d("makeText","Loading")

                is State.Success -> if (it.data.State == 1) {
                    Log.d("makcvfvfeText", it.data.Item!!.item)
                    //         Log.d("makeText", it.data.Message)
                    it.data.Item
                    var item = it.data.Item
                    val split = DialogBill(it.data.Item.item)
                    val dialog = this?.let { it1 -> Dialog(it1) }
                    dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    //بيعمل بلوك للباك جراوند
                    //  dialog?.setCancelable(false)
                    dialog?.setContentView(R.layout.dilog_bill_row)
                    dialog?.getWindow()?.setLayout(550, 850)
                    dialog?.show()
                    val sizez = item.size
                    lateinit var tv_group: TextView
                    tv_group = dialog?.findViewById(R.id.textView52)!!
                    tv_group.setText(split.group)

                    lateinit var tv_sur: TextView
                    tv_sur = dialog?.findViewById(R.id.textView54)!!
                    tv_sur.setText(split.sur)


                    lateinit var tv_name: TextView
                    tv_name = dialog?.findViewById(R.id.textView57)!!
                    tv_name.setText(split.name)
                    lateinit var tv_size: TextView
                    tv_size = dialog?.findViewById(R.id.textView56)!!

                    tv_size.setText(split.size)

                    tv_size = dialog?.findViewById(R.id.textView56)!!
                    tv_size.setText(it.data.Item.Price_ID.toString())
                    lateinit var tv_price: TextView
                    tv_price = dialog?.findViewById(R.id.textView53)!!
                    tv_price.setText(it.data.Item.CustomerSellingPrice.toString())

                    lateinit var buttonAddToReturnsBill: Button
                    buttonAddToReturnsBill = dialog.findViewById(R.id.buttonm)
                    lateinit var NumberOfUnits: TextInputEditText
                    NumberOfUnits = dialog?.findViewById(R.id.counterEditTextm)
                    // NumberOfUnits.text.toString()

                    lateinit var discountEditTextk: EditText
                    discountEditTextk = dialog.findViewById(R.id.discountEditTextk)
                    var discount = discountEditTextk.text.toString()

                    lateinit var buttonAddToSalingBill: Button
                    buttonAddToSalingBill = dialog.findViewById(R.id.button2b)

                    lateinit var sizesEditText: TextInputEditText
                    sizesEditText = dialog?.findViewById(R.id.sizesEditTextm)
                    sizesEditText.setText(item.size.toString())

                    buttonAddToSalingBill.setOnClickListener {

                        var price = item.Price_ID.toString()


                        var x: Int = Integer.parseInt(NumberOfUnits.text.toString())

                        var z = x.toDouble()


                        var TotalPrice: Double
                        TotalPrice =
                            item.CustomerSellingPrice.toDouble() * z - discountEditTextk.text.toString()
                                .toDouble()


                        listBill.add(
                            SaleingBill(
                                1,
                                "1",
                                split.name,
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
                                "    ",
                                "",
                                "", 0.0
                            )
                        )


                        var s = 0.0
                        for (item in listBill) {
                            if (item.TransactionType == 1) {
                                var z = item.TotalPrice.toDouble()
                                s = s + z
                            }


                        }


                        binding.Totalss.setText(s.toString())


                        val totals = binding.Totalss.text.toString().toDouble()
//                        -
//                                binding.Totalss2.text.toString().toDouble()
//
//                        +
//                        binding.Totalssz4.text.toString().toDouble()

                        binding.Totalss4.setText(totals.toString())


                        adapte.submitList(listBill)

                        adapte.notifyDataSetChanged()

                        adapte.notifyItemInserted(listBill.size - 1)
                        dialog.dismiss()

                    }
                    buttonAddToReturnsBill.setOnClickListener {

                        //      Log.d("jhswjllq", "ReturnsBill")
                        var price = item.CustomerSellingPrice.toString()
                        //      Log.d("price", price)

                        var x: Int = Integer.parseInt(NumberOfUnits.text.toString())
                        //      Log.d("price", x.toString())

                        var z = x.toDouble()
                        //       Log.d("price", z.toString())


                        var TotalPrice: Double
                        TotalPrice = price.toDouble() * z
                        //      Log.d("price", TotalPrice.toString())
                        //      Log.d("price", item.CustomerSellingPrice.toString())


                        var PriceAfterEditSize: Double
                        PriceAfterEditSize =
                            sizesEditText.text.toString().toDouble() * TotalPrice.toString()
                                .toDouble()

                        var TotalPriceAfterEditSize: Double
                        TotalPriceAfterEditSize =
                            PriceAfterEditSize / item.size.toString().toDouble()

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
                                "  مرتجع :   ",
                                "",
                                "", 0.0
                            )
                        )
                        var TotalPrices = 0.0
                        for (item in listBill) {
                            if (item.TransactionType == 0) {
                                //       Log.d("qqqqqq", item.TotalPrice)
                                var z = item.TotalPrice.toDouble()
                                TotalPrices = TotalPrices + z
                            }
                        }
                        binding.Totalss2.setText(TotalPrices.toString())

                        val totals = binding.Totalss.text.toString().toDouble()
//                        -
//                                binding.Totalss2.text.toString().toDouble()
//                        // binding. billDiscountEditText.text.toString().toDouble()
//                        +
//                        binding.Totalssz4.text.toString().toDouble()
                        // binding   billDiscountEditText?.text?.toString().toDouble()
                        binding.Totalss4.setText(totals.toString())



                        adapte.submitList(listBill)

                        adapte.notifyDataSetChanged()

                        adapte.notifyItemInserted(listBill.size - 1)

                        dialog.dismiss()
                    }

                } else {
                    Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()
                    //    Log.d("makeText", it.data.Message)

                }

                is State.Error -> Toast.makeText(baseContext, it.messag, Toast.LENGTH_SHORT).show()

            }

        }





        binding.printbtn.setOnClickListener {
            if (  binding.Totalss4.text.toString() =="0.0")
            {
                Toast.makeText(baseContext, "اختر الصنف اولا ", Toast.LENGTH_SHORT).show()
            }else{
            val intent = Intent(this, PaymentActivity::class.java).apply {
                putExtra("TotalSalse", binding.Totalss.text.toString())
                putExtra("Unpaid_deferred", binding.oldRemainningAmount.text.toString())
                putExtra("TotalReturn", binding.Totalss2.text.toString())
                putExtra("TotalAfterDescound", binding.Totalss4.text.toString())
                putExtra("name", CompanyName)
                putExtra("CusID", message)
                putExtra("list", Gson().toJson(listBill))
            }
                startActivity(intent)
            }

            // Log.e("PrintError1", "e.message.toString()")


            //    Toast.makeText(baseContext, "it.item", Toast.LENGTH_SHORT).show()

        }

    }


    fun inits(): ArrayList<Items> {
        return list

    }


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
            //   viewModel.getItemByBarcodeV1API("2", barcode.toString().trim(), message)
            barcode.delete(0, barcode.length)
        }

        return super.dispatchKeyEvent(event)
    }


    fun d() {
        var s = 0.0
        for (item in listBill) {
            if (item.TransactionType == 1) {
                var z = item.TotalPrice.toDouble()
                s = s + z
            }

        }


        binding.Totalss.setText(s.toString())

        val totals = binding.Totalss.text.toString().toDouble()
//        -
//                binding.Totalss2.text.toString().toDouble() +
//                binding.Totalssz4.text.toString().toDouble()

        binding.Totalss4.setText(
            totals.toString()
        )
    }


    fun mortaga3() {
        var TotalPrices = 0.0
        for (item in listBill) {
            if (item.TransactionType == 0) {
                Log.d("qqqqqq", item.TotalPrice)
                var z = item.TotalPrice.toDouble()
                TotalPrices = TotalPrices + z
            }
        }
        binding.Totalss2.setText(TotalPrices.toString())

        val totals = binding.Totalss.text.toString().toDouble()
//        -
//                binding.Totalss2.text.toString().toDouble() +
//                binding.Totalssz4.text.toString().toDouble()
        binding.Totalss4.setText(totals.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {

            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result.contents == null) {
                Toast.makeText(baseContext, "Cancelled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(baseContext, "Success", Toast.LENGTH_SHORT).show()

                addCustomerviewModel.GetProductByBarcodeVM(
                    SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt(),
                    result.contents, message
                )

            }

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun livedataFun() {
        addCustomerviewModel.GetProductByBarcodeVM(
            SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt(),
            binding.textView23.text.toString(), message
        )

        addCustomerviewModel.getProductByBarcodeLivedata.observe(this) {
            when (it) {

                is com.tbi.supplierplus.framework.ui.login.State.Loading -> {}
                is com.tbi.supplierplus.framework.ui.login.State.Success -> {


                    Log.d("selling", it.data.Item!!.Supply_Price.toString())
                    val split = DialogBill(it.data.Item!!.item)

                    Toast.makeText(baseContext, it.data.Message, Toast.LENGTH_SHORT).show()

                    val dialog = this?.let { it1 -> Dialog(it1) }
                    dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    //بيعمل بلوك للباك جراوند
                    //  dialog?.setCancelable(false)
                    dialog?.setContentView(R.layout.dilog_bill_row)
                    dialog?.getWindow()?.setLayout(740, 1000)
                    dialog?.show()


                    lateinit var tv_name: TextView
                    tv_name = dialog?.findViewById(R.id.textView57)!!
                    tv_name.setText(split.name)

                    var itemName = it.data.Item?.item

                    lateinit var tv_group: TextView
                    tv_group = dialog?.findViewById(R.id.textView52)!!
                    tv_group.setText(it.data.Item!!.Item_ID.toString())

                    lateinit var tv_sur: TextView
                    tv_sur = dialog?.findViewById(R.id.textView54)!!
                    tv_sur.setText("")

                    lateinit var tv_size: TextView
                    tv_size = dialog?.findViewById(R.id.textView56)!!
                    tv_size.setText(it.data.Item.size.toString())

                    var itemSiza = it.data.Item.size.toString()


                    lateinit var tv_price: TextView
                    tv_price = dialog?.findViewById(R.id.textView53)!!
                    tv_price.setText(it.data.Item.CustomerSellingPrice.toString())
                    var sellingPriceItem = it.data.Item.CustomerSellingPrice.toString()

                    lateinit var code: TextView
                    code = dialog?.findViewById(R.id.textView55)!!
                    code.setText(it.data.Item.Item_ID.toString())


                    var supplierId = it.data.Item.Supplier_ID
                    var supplierPrice = it.data.Item.Supply_Price
                    var itemID = it.data.Item.Item_ID

                    lateinit var NumberOfUnits: TextInputEditText
                    NumberOfUnits = dialog?.findViewById(R.id.counterEditTextm)

                    lateinit var sizesEditText: TextInputEditText
                    sizesEditText = dialog?.findViewById(R.id.sizesEditTextm)
                    sizesEditText.setText(it.data.Item.size.toString())
                    // NumberOfUnits.text.toString()

                    lateinit var discountEditTextk: EditText
                    discountEditTextk = dialog.findViewById(R.id.discountEditTextk)
                    var discount = discountEditTextk.text.toString()


                    lateinit var buttonAddToReturnsBill: Button
                    buttonAddToReturnsBill = dialog.findViewById(R.id.buttonm)

                    lateinit var buttonAddToSalingBill: Button
                    buttonAddToSalingBill = dialog.findViewById(R.id.button2b)

                    buttonAddToSalingBill.setOnClickListener {


                        var x: Int = Integer.parseInt(NumberOfUnits.text.toString())

                        var z = x.toDouble()


                        var TotalPrice: Double
                        TotalPrice =
                            sellingPriceItem.toDouble() * z - discountEditTextk.text.toString()
                                .toDouble()



                        listBill.add(
                            SaleingBill(
                                1,
                                "1",
                                itemName.toString(),
                                NumberOfUnits.text.toString(),
                                itemSiza,
                                sellingPriceItem,
                                discountEditTextk.text.toString(),
                                TotalPrice.toString(),
                                itemID.toString(),
                                supplierId.toString(),
                                supplierPrice.toString(),
                                1.0,
                                "  بيع :",
                                "",
                                "",
                                "", 0.0
                            )
                        )
                        calculateSum(listBill)
                        //  val adabter = testAdepterR(this, listBill)
                        //binding.billRecycler1.adapter = adapte
                        adapte.submitList(listBill)
                        adapte.notifyItemInserted(listBill.size - 1)
                        //   binding.TotalafterDiscaunt.setText(totals.toString())
                        dialog.dismiss()

                    }

                    buttonAddToReturnsBill.setOnClickListener {
                        Log.d("jhswjllq", "ReturnsBill")


                        var price = sellingPriceItem.toString()
                        Log.d("price", price)

                        var x: Int = Integer.parseInt(NumberOfUnits.text.toString())
                        Log.d("price", x.toString())

                        var z = x.toDouble()
                        Log.d("price", z.toString())


                        var TotalPrice: Double
                        TotalPrice = price.toDouble() * z
//                                Log.d("price",TotalPrice.toString())
//                                Log.d("price",item.CustomerSellingPrice.toString())


                        var PriceAfterEditSize: Double
                        PriceAfterEditSize =
                            sizesEditText.text.toString().toDouble() * TotalPrice.toString()
                                .toDouble()


                        var TotalPriceAfterEditSize: Double
                        TotalPriceAfterEditSize =
                            PriceAfterEditSize / itemSiza.toString().toDouble()
                        listBill.add(
                            SaleingBill(
                                0,
                                "1",
                                itemName,
                                NumberOfUnits.text.toString(),
                                sizesEditText.text.toString(),
                                sellingPriceItem.toString(),
                                discountEditTextk.text.toString(),
                                TotalPriceAfterEditSize.toString(),
                                itemID.toString(),
                                supplierId.toString(),
                                sellingPriceItem.toString(),
                                1.0,
                                "",
                                "  مرتجع :   ",
                                "",
                                "", 0.0
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

                        val totals = binding.Totalss.text.toString().toDouble()
                        //    -binding.Totalss2.text.toString().toDouble() +

                        // binding. billDiscountEditText.text.toString().toDouble()

                        //  binding.Totalssz4.text.toString().toDouble()
                        // binding   billDiscountEditText?.text?.toString().toDouble()
                        binding.Totalss4.setText(totals.toString())
                        //   Log.d("qqqqqq", item.TotalPrice)

                        //s++
                        adapte.submitList(listBill)

                        adapte.notifyDataSetChanged()

                        adapte.notifyItemInserted(listBill.size - 1)

                        //  binding.Totalss2.setText(s.toString())
                        //    binding.billRecycler1.adapter = adapte
                        //    adapte.submitList(listBill)
                        dialog.dismiss()

                    }


                }
                is com.tbi.supplierplus.framework.ui.login.State.Error -> {

                }
            }
        }

    }


//    private fun CheckAllFields(): Boolean {
//        if (binding.discribtionText.length() == 0) {
//            binding.discribtionText.setError("This field is required")
//            return false
//        }
//        return true
//    }

    private fun calculateSum(listBill: java.util.ArrayList<SaleingBill>) {
        s = 0.0
        for (item in listBill) {
            if (item.TransactionType == 1) {
                var z = item.TotalPrice.toDouble()
                s = s + z
            }
        }
        binding.Totalss.setText(s.toString())
        val totals = binding.Totalss.text.toString().toDouble()
//     - binding.Totalss2.text.toString().toDouble() +
//       binding.Totalssz4.text.toString().toDouble()
        binding.Totalss4.setText(totals.toString())
    }


    fun convertToEnglishDigits(arabicNumber: String): String {
        val arabicDigits = charArrayOf(',', '٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
        val englishDigits = charArrayOf('.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        var convertedNumber = arabicNumber
        for (i in arabicDigits.indices) {
            convertedNumber = convertedNumber.replace(arabicDigits[i], englishDigits[i])

        }

        // Replace Arabic decimal separator '٫' with English decimal separator '.'
        convertedNumber = convertedNumber.replace('٫', '.')
        return convertedNumber
    }

}




