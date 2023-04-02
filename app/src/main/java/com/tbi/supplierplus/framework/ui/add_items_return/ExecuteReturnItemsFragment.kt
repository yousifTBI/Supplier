package com.tbi.supplierplus.framework.ui.add_items_return

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.utils.showSnackbar
import com.tbi.supplierplus.databinding.FragmentExecuteReturnItemsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExecuteReturnItemsFragment : Fragment() {
    private lateinit var binding: FragmentExecuteReturnItemsBinding
    private val viewModel: AddItemsReturnViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExecuteReturnItemsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.cashEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s!!.isNotEmpty()) {
                    binding.deferredEditText.setText(
                        viewModel.calculateNewDeferred(
                            s.toString().toFloat()
                        ).toString()
                    )
                }
            }
        })

        viewModel.requiredAmount.observe(viewLifecycleOwner) {
            binding.deferredEditText.setText(
                viewModel.calculateNewDeferred(viewModel.collection.value!!.toFloat()).toString()
            )
        }
        viewModel.message.observe(viewLifecycleOwner) {
            if (it != null) {
                showSnackbar(activity!!, it)
                viewModel.onDoneMsg()
                findNavController().popBackStack()
            }
        }

        binding.printbtn.setOnClickListener(View.OnClickListener {
            viewModel.registerBillAndPrint()
        })
        return binding.root
    }


}