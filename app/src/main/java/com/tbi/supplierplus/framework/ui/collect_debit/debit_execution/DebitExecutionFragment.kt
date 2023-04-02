package com.tbi.supplierplus.framework.ui.collect_debit.debit_execution

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.opening.AddCollection
import com.tbi.supplierplus.databinding.FragmentDebitExecutionBinding
import com.tbi.supplierplus.framework.ui.collect_debit.CollectDebitViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DebitExecutionFragment : Fragment() {
    private lateinit var binding: FragmentDebitExecutionBinding
    private val viewModel: CollectDebitViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDebitExecutionBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

       // viewModel.debit.observe(viewLifecycleOwner){
       //
       // }

    //  viewModel.onCollect(AddCollection("2",
    //      "10"
    //      ,"1"
    //      ,"60"))
      //  viewModel.debit.observe(viewLifecycleOwner){
      //
      //  }

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
        binding.printbtn.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("رسالة تأكيد")
            builder.setMessage(" هل انت متأكد من اضافة مبلغ ${viewModel.collection.value}")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton("ايوة") { dialog, which ->
                Log.e("AddOpenings", it.toString()+"btnn")


            //   viewModel.onCollect(AddCollection(it?.cus_id.toString(),
            //       "2"
            //       ,binding.cashEditText.text.toString()
            //       ,binding.deferredEditText.toString()))

                viewModel.debit.observe(viewLifecycleOwner){
                    Log.e("AddOpenings", it?.cusName+"btnn")

                     viewModel.onCollect(AddCollection("2"

                         ,binding.cashEditText.text.toString()
                         ,  it?.cus_id.toString()
                         ,binding.deferredEditText.text.toString()))




                }

          //     viewModel.onCollect(AddCollection("2"
          //         ,binding.cashEditText.text.toString()
          //         ,""))
            }

            builder.setNegativeButton("لا") { dialog, which ->
                dialog.cancel()
            }

            builder.show()
        }

        return binding.root
    }

}