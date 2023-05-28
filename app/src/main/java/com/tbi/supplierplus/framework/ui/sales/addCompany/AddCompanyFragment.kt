package com.tbi.supplierplus.framework.ui.sales.addCompany

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tbi.supplierplus.business.models.Branch
import com.tbi.supplierplus.databinding.FragmentAddCompanyBinding
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.sales.add_customer.AddCustomerViewModel
import dagger.hilt.android.AndroidEntryPoint
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener

@AndroidEntryPoint
class AddCompanyFragment : Fragment() {
    lateinit var viewModel: AddCustomerViewModel
    private lateinit var binding: FragmentAddCompanyBinding
    var listCom = ArrayList<Data>()
    var listBranches =ArrayList<Branch>()
    var ComName = ""
    var Idd: Int = 0
    var branchId: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddCompanyBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(AddCustomerViewModel::class.java)
        binding.spinKit.isVisible = false
        viewModel.getBranchesDetailsLiveData.observe(viewLifecycleOwner){
            when (it) {

                is State.Loading -> binding.spinKit.isVisible = true
                is State.Success -> {
                    binding.spinKit.isVisible = false



                }
                is State.Error -> binding.spinKit.isVisible = false

            }

        }

        viewModel.getAllCompaniesLiveData.observe(viewLifecycleOwner){

            when (it) {

                is State.Loading -> binding.spinKit.isVisible = true
                is State.Success -> {
                    binding.spinKit.isVisible = false



                    viewModel.GetAllBranchDetailsVM(Idd)
                    viewModel.getBranchesDetailsLiveData.observe(viewLifecycleOwner){
                        when (it) {


                            is State.Loading -> binding.spinKit.isVisible = true
                            is State.Success -> {

                                if (it.data.Details == null) {
                                } else {
                                    binding.spinKit.isVisible = false
                                    if (listBranches.size == 1) {
                                        binding.branchCard.isVisible = false
                                        //  Log.d("ddd",it.data.Details.BranchName)
                                        binding.com.setText(it.data.Details.BranchName.toString() + " ")
                                        binding.numOfRecord.setText(it.data.Details.ContactName.toString() + " ")
                                        binding.comCode.setText(it.data.Details.Telephone1.toString() + " ")
                                        binding.branchcode.setText(it.data.Details.Telephone2.toString() + " ")
                                        binding.BuildingNumber.setText(it.data.Details.DistributorID.toString() + " ")
                                        binding.streat.setText(it.data.Details.Email.toString() + " ")

                                    } else {

                                    }
                                }
                            }
                            is State.Error -> binding.spinKit.isVisible = false

                        }


                    }
                }
                is State.Error -> binding.spinKit.isVisible = false

            }
        }


        viewModel.getAllCompaniesLiveData.observe(viewLifecycleOwner) {

            when (it) {

                is State.Loading -> binding.spinKit.isVisible = true
                is State.Success -> {
                    binding.spinKit.isVisible = false
                  //  listCom = it.data.Data


                }
                is State.Error -> binding.spinKit.isVisible = false

            }
        }

        binding.companyCard.setOnClickListener {

            popUp()
            binding.branchCard.isVisible =true
            binding.textView22.setText("")
            binding.com.setText(" ")
            binding.numOfRecord.setText(" ")
            binding.comCode.setText(" ")
            binding.branchcode.setText(" ")
            binding.BuildingNumber.setText(" ")
            binding.streat.setText( " ")


        }



        viewModel.getAllBranchesLiveData.observe(viewLifecycleOwner){
            when (it) {

                is State.Loading -> binding.spinKit.isVisible = true
                is State.Success -> {
                    binding.spinKit.isVisible = false

                    listBranches=it.data.Data


                }
                is State.Error -> binding.spinKit.isVisible = false

            }

        }


        viewModel.getBranchesDetailsLiveData.observe(viewLifecycleOwner){
            when (it) {

                is State.Loading -> binding.spinKit.isVisible = true
                is State.Success -> {
                    binding.spinKit.isVisible = false

                    //  Log.d("ddd",it.data.Details.BranchName)

                    //  Log.d("ddd",it.data.Details.BranchName)
                    if (it.data.Details == null) {
                    } else {
                        if (it.data.Details.BranchName.isNullOrEmpty() || it.data.Details.BranchName.toString()
                                .isNullOrEmpty()
                        ) {

                        } else {
                            binding.com.setText(it.data.Details.BranchName.toString() + " ")
                        }

                        if (it.data.Details.ContactName.isNullOrEmpty()) {

                        } else {
                            binding.numOfRecord.setText(it.data.Details.ContactName.toString() + " ")
                        }
                        if (it.data.Details.Telephone1.isNullOrEmpty()) {

                        } else {
                            binding.comCode.setText(it.data.Details.Telephone1.toString() + " ")
                        }
                        if (it.data.Details.Telephone2.isNullOrEmpty()) {

                        } else {
                            binding.branchcode.setText(it.data.Details.Telephone2.toString() + " ")
                        }
                        if (it.data.Details.DistributorID.toString().isNullOrEmpty()) {

                        } else {
                            binding.BuildingNumber.setText(it.data.Details.DistributorID.toString() + " ")
                        }
                        if (it.data.Details.Email.isNullOrEmpty()) {

                        } else {
                            binding.streat.setText(it.data.Details.Email.toString() + " ")
                        }


                    }

                }
                is State.Error -> binding.spinKit.isVisible = false

            }

        }


        binding.branchCard.setOnClickListener {

            viewModel.GetAllBranchesVM(Idd)
            viewModel.getAllBranchesLiveData.observe(viewLifecycleOwner) {

                when (it) {

                    is State.Loading -> binding.spinKit.isVisible = true
                    is State.Success -> {
                        binding.spinKit.isVisible = false
                        listBranches  = it.data.Data


                    }
                    is State.Error -> binding.spinKit.isVisible = false

                }
            }
            popUp2()
        }

        viewModel.GetAllCompaniesVM()
        viewModel.  getAllCompaniesLiveData.observe(viewLifecycleOwner) {
            when (it) {

                is State.Loading -> binding.spinKit.isVisible = true
                is State.Success -> {
                    listCom= it.data.Data as ArrayList<Data>
                   // popUp(it.data.Data as ArrayList<Data>)
                    Log.d("getAllCompaniesLiveData",it.data.Data[0].title)

                }
                is State.Error ->
                {
                    Log.d("getAllCompaniesLiveData","Error")
                    binding.spinKit.isVisible = false
                }

            }
        }

        return binding.root
    }


    fun popUp() {
        SimpleSearchDialogCompat(context, "ادخل اسم الشركه  " + "\n", "search", null,
            listCom, SearchResultListener { baseSearchDialogCompat, item, pos ->

                binding.branchCard.isVisible =true
                binding.com.setText(" ")
                binding.numOfRecord.setText(" ")
                binding.comCode.setText(" ")
                binding.branchcode.setText(" ")
                binding.BuildingNumber.setText(" ")
                binding.streat.setText( " ")

                Idd = item.ID()
                Log.d("ddd",Idd.toString())
                ComName = item.getTitle()
                binding.textView21.setText(ComName)

                viewModel.GetAllCompaniesVM()
                viewModel.GetAllBranchDetailsVM(Idd)
                callApi()


                baseSearchDialogCompat.dismiss()
            }).show()

//                viewModel.getAllCompaniesLiveData.observe(viewLifecycleOwner) {
//
//                    when (it) {
//
//                        is State.Loading -> binding.spinKit.isVisible = true
//                        is State.Success -> {
//                            binding.spinKit.isVisible = false
//                         //   listCom = it.data.Data
//
//
//                        }
//                        is State.Error -> binding.spinKit.isVisible = false
//
//                    }
//                }


//                viewModel.getBranchesDetailsLiveData.observe(viewLifecycleOwner){
//                    when (it) {
//
//                        is State.Loading -> binding.spinKit.isVisible = true
//                        is State.Success -> {
//                            binding.spinKit.isVisible = false
//
//
//
//                        }
//                        is State.Error -> binding.spinKit.isVisible = false
//
//                    }
//
//                }

//                viewModel.getAllCompaniesLiveData.observe(viewLifecycleOwner){
//
//                    when (it) {
//
//                        is State.Loading -> binding.spinKit.isVisible = true
//                        is State.Success -> {
//                            binding.spinKit.isVisible = false
//
//
//
//                                viewModel.GetAllBranchDetailsVM(Idd)
//                                viewModel.getBranchesDetailsLiveData.observe(viewLifecycleOwner){
//                                    when (it) {
//
//
//                                        is State.Loading -> binding.spinKit.isVisible = true
//                                        is State.Success -> {
//
//                                            if (it.data.Details == null) {
//                                            } else {
//                                                binding.spinKit.isVisible = false
//                                                if (listBranches.size == 1) {
//                                                    binding.branchCard.isVisible = false
//                                                    //  Log.d("ddd",it.data.Details.BranchName)
//                                                    if (it.data.Details.BranchName.isNullOrEmpty()) {
//                                                        binding.com.setText("    ")
//                                                    }
//                                                    if (it.data.Details.ContactName.isNullOrEmpty()) {
//                                                        binding.numOfRecord.setText("    ")
//                                                    }
//                                                    if (it.data.Details.Telephone1.isNullOrEmpty()) {
//                                                        binding.comCode.setText("    ")
//                                                    }
//                                                    if (it.data.Details.Telephone2.isNullOrEmpty()) {
//                                                        binding.branchcode.setText("    ")
//                                                    }
//                                                    if (it.data.Details.DistributorID.toString()
//                                                            .isNullOrEmpty()
//                                                    ) {
//                                                        binding.BuildingNumber.setText("    ")
//                                                    }
//                                                    if (it.data.Details.Email.isNullOrEmpty()) {
//                                                        binding.streat.setText("    ")
//                                                    }
//
//                                                    binding.com.setText(it.data.Details.BranchName.toString() + " ")
//                                                    binding.numOfRecord.setText(it.data.Details.ContactName.toString() + " ")
//                                                    binding.comCode.setText(it.data.Details.Telephone1.toString() + " ")
//                                                    binding.branchcode.setText(it.data.Details.Telephone2.toString() + " ")
//                                                    binding.BuildingNumber.setText(it.data.Details.DistributorID.toString() + " ")
//                                                    binding.streat.setText(it.data.Details.Email.toString() + " ")
//
//                                                } else {
//
//                                                }
//                                            }
//                                        }
//                                        is State.Error -> binding.spinKit.isVisible = false
//
//                                    }
//
//
//                            }
//                        }
//                        is State.Error -> binding.spinKit.isVisible = false
//
//                    }
//                }




    }

    fun popUp2() {
        SimpleSearchDialogCompat(context, "ادخل اسم الفرع  " + "\n", "search", null,
            listBranches, SearchResultListener { baseSearchDialogCompat, item, pos ->


                branchId=item.ID()
               viewModel.GetAllBranchDetailsVM(branchId)
                ComName = item.getTitle()
                binding.textView22.setText(ComName)
                baseSearchDialogCompat.dismiss()
            }).show()
    }


    fun callApi(){
        listBranches.clear()
        viewModel.GetAllBranchesVM(Idd)

    }

}