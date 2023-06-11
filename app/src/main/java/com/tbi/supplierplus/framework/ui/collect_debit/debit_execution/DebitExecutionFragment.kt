package com.tbi.supplierplus.framework.ui.collect_debit.debit_execution

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

@AndroidEntryPoint
class DebitExecutionFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentDebitExecutionBinding
    private val viewModel: CollectDebitViewModel by activityViewModels()
    var paymentMethods = arrayOf("كاش", "شيك", "تحويل بنكي","محفظة الكترونيه")
    var paymentMethodPosition =0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDebitExecutionBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this



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

//
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
            if (CheckAllFields()){
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
                        activity!!.onBackPressed()

                    }



                }

                builder.setNegativeButton("لا") { dialog, which ->
                    dialog.cancel()
                }

                builder.show()
            }
            else{
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

        paymentMethodPosition =position+1
      //  Log.d("setOnItemSelectedListener",paymentMethodPosition.toString())

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
}

//private fun DialogInterface.OnClickListener.setOnItemSelectedListener(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
//   Log.d("setOnItemSelectedListener",languages[position])
//}

