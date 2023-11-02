package com.tbi.supplierplus

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.databinding.FragmentClientOperationBinding
import com.tbi.supplierplus.framework.ui.MainFragmentDirections
import com.tbi.supplierplus.framework.ui.sales.addBranch.AddBranchActivity
import com.tbi.supplierplus.framework.ui.sales.add_customer.AddCustomerActivity


class ClientOperationFragment : Fragment() {


    private lateinit var binding : FragmentClientOperationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentClientOperationBinding.inflate(inflater)

        binding.returnsForUserCard.setOnClickListener {
            val    intent = Intent(activity, AddCustomerActivity::class.java)
            startActivity(intent)
        }

          binding.returnsCard.setOnClickListener {

              findNavController().navigate(

                     ClientOperationFragmentDirections.actionClientOperationFragmentToAddCompanyFragment()
                  )
        }


          binding.reportsCard.setOnClickListener {

              findNavController().navigate(

                     ClientOperationFragmentDirections.actionClientOperationFragmentToAddBranchFragment()
                  )
//              val    intent = Intent(activity, AddBranchActivity::class.java)
//              startActivity(intent)
        }



          binding.editBranchId.setOnClickListener {

              findNavController().navigate(

                     ClientOperationFragmentDirections.actionClientOperationFragmentToEditBranchFragment()
                  )
//              val    intent = Intent(activity, AddBranchActivity::class.java)
//              startActivity(intent)
        }



        return binding.root
    }
}