package com.tbi.supplierplus.framework.ui.opening_balance.add_balance

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.utils.showSnackbar
import com.tbi.supplierplus.databinding.FragmentAddOpeningBalanceBinding
import com.tbi.supplierplus.databinding.FragmentOpeningBalancesBinding
import com.tbi.supplierplus.framework.ui.opening_balance.OpeningBalanceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddOpeningBalanceFragment : Fragment() {

    private lateinit var binding: FragmentAddOpeningBalanceBinding
    private val viewModel: OpeningBalanceViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddOpeningBalanceBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.msg.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                showSnackbar(activity!!, it)
                viewModel.onMsgShowDone()
            }
        }

        binding.printbtn.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("رسالة تأكيد")
            builder.setMessage(" هل انت متأكد من اضافة مبلغ ${viewModel.collection.value}")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton("ايوة") { dialog, which ->
                viewModel.onAdd()
            }

            builder.setNegativeButton("لا") { dialog, which ->
                dialog.cancel()
            }

            builder.show()
        }
        return binding.root
    }

}