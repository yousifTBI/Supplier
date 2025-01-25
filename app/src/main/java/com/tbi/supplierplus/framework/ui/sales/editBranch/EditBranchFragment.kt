package com.tbi.supplierplus.framework.ui.sales.editBranch

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.Branch
import com.tbi.supplierplus.business.pojo.addCustomer.Ranges
import com.tbi.supplierplus.business.pojo.addCustomer.Regions
import com.tbi.supplierplus.databinding.FragmentEditBranchBinding
import com.tbi.supplierplus.framework.getLocation.MyLocation
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import com.tbi.supplierplus.framework.ui.sales.addBranch.AddBranchModel
import com.tbi.supplierplus.framework.ui.sales.addCompany.Data
import com.tbi.supplierplus.framework.ui.sales.add_customer.AddCustomerViewModel
import dagger.hilt.android.AndroidEntryPoint
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class EditBranchFragment : Fragment() {

    private lateinit var binding:FragmentEditBranchBinding
    lateinit var viewModel: AddCustomerViewModel
    private val salesViewModel: SalesViewModel by activityViewModels()

    var listCom = ArrayList<Data>()
    var listBranches =ArrayList<Branch>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var ComName = ""
    var Idd: Int = 0
    var branchId: Int = 0
    var RangeList = ArrayList<Ranges>()
    var RegionList = ArrayList<Regions>()
    var RangeName = ""
    var RegionName = ""
    var RegionId = -333
    var loc: Location? = null
    var long = 0.0
    var lat = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditBranchBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(AddCustomerViewModel::class.java)
        binding.spinKit.isVisible = false
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)

        val locationResult: MyLocation.LocationResult = object : MyLocation.LocationResult() {
            override fun gotLocation(location: Location) {
                loc = location
                lat = loc!!.latitude
                long = loc!!.longitude
                //   System.out.println("allah: " + loc!!.latitude)
                //   System.out.println("allah: " + loc!!.longitude)
                 //    Log.d("allahAkbr",  loc!!.latitude.toString())
                //  Log.d("allahAkbr",  loc!!.longitude.toString())
            }
        }

        val myLocation = MyLocation()
        myLocation.getLocation(context, locationResult)



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



                  //  viewModel.GetAllBranchDetailsVM(Idd)
                    viewModel.getBranchesDetailsLiveData.observe(viewLifecycleOwner){
                        when (it) {


                            is State.Loading -> binding.spinKit.isVisible = true
                            is State.Success -> {

                                if (it.data.Details == null) {
                                } else {
                                    binding.spinKit.isVisible = false
//                                    if (listBranches.size == 1) {
//                                       // binding.branchCard.isVisible = false
//                                        //  Log.d("ddd",it.data.Details.BranchName)
//                                        binding.com.setText(it.data.Details.BranchName.toString() + " ")
//                                        binding.numOfRecord.setText(it.data.Details.ContactName.toString() + " ")
//                                        binding.comCode.setText(it.data.Details.Telephone1.toString() + " ")
//                                        binding.branchcode.setText(it.data.Details.Telephone2.toString() + " ")
//                                        binding.BuildingNumber.setText(it.data.Details.DistributorID.toString() + " ")
//                                        binding.streat.setText(it.data.Details.Email.toString() + " ")
//
//                                    } else {
//
//                                    }
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
            binding.textView272.setText("")
            binding.textView23.setText("")
            binding.com.setText(" ")
            binding.numOfRecord.setText(" ")
            binding.comCode.setText(" ")
            binding.branchcode.setText(" ")
            binding.BuildingNumber.setText(" ")
            binding.streat.setText( " ")


        }

        salesViewModel.GetRange( SharedPreferencesCom.getInstance().gerSharedUser_ID())
        salesViewModel.RangesLiveData.observe(viewLifecycleOwner){

            if (it.isNullOrEmpty()){
            } else{
                RangeList= it as ArrayList<Ranges>

            }
        }


        salesViewModel.RegionsLiveData.observe(viewLifecycleOwner){
            if (it.isNullOrEmpty()){}
            else{
                RegionList = it as ArrayList<Regions>
            }
        }


        binding.RangeCard.setOnClickListener {
            RangepopUp()
        }
        binding.RegionCard.setOnClickListener {
            RegionpopUp()
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

                    is State.Loading ->{
                        binding.spinKit.isVisible = true
                    }

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
                    //                  Log.d("getAllCompaniesLiveData",it.data.Data[0].title)

                }
                is State.Error ->
                {
                    //                Log.d("getAllCompaniesLiveData","Error")
                    binding.spinKit.isVisible = false
                }

            }
        }



        val date = Date()
        binding.SubmittttttID.setOnClickListener {

       //     Log.d("allahAkbr", loc?.latitude.toString())
         if ( CheckAllFields()){
             viewModel.EditBranchDetailsVM(
                 EditBranchDetailsModel(
                     branchId,
                     binding.com.text.toString(),
                     binding.numOfRecord.text.toString(),
                     binding.comCode.text.toString(),
                     binding.branchcode.text.toString(),
                     binding.streat.text.toString(),
                     binding.textView22.text.toString(),
                     "",
                     "",
                     date,
                     binding.comCode.text.toString(),
                     SharedPreferencesCom.getInstance().gerSharedDistributor_ID().toInt(),
                     RegionId,
                     Idd,
                     lat,
                     long,
                     "",
                     SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt()
                 )
             )
            }


        }
        viewModel.EditBranchDetailsLiveData.observe(viewLifecycleOwner){
            when (it) {

                is State.Loading -> binding.spinKit.isVisible = true
                is State.Success -> {
                    binding.spinKit.isVisible = false
                    Toast.makeText(context,it.data.Message.toString(),Toast.LENGTH_SHORT).show()
                //  Log.d("toastmessage",it.data.Message)
                }
                is State.Error ->
                {
                    //                Log.d("getAllCompaniesLiveData","Error")
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
                //              Log.d("ddd",Idd.toString())
                ComName = item.getTitle()
                binding.textView21.setText(ComName)

                viewModel.GetAllCompaniesVM()
              //  viewModel.GetAllBranchDetailsVM(Idd)
                callApi()


                baseSearchDialogCompat.dismiss()
            }).show()





    }

    fun popUp2() {
        SimpleSearchDialogCompat(context, "ادخل اسم الفرع  " + "\n", "search", null,
            listBranches, SearchResultListener { baseSearchDialogCompat, item, pos ->


                branchId=item.ID()
                viewModel.GetAllBranchDetailsVM(branchId)
                ComName = item.getTitle()
                binding.textView272.setText(ComName)
                baseSearchDialogCompat.dismiss()
            }).show()
    }


    fun callApi(){
        listBranches.clear()
        viewModel.GetAllBranchesVM(Idd)

    }

    fun RangepopUp() {
        SimpleSearchDialogCompat(context, "ادخل العنوان  " + "\n", "search", null,
            RangeList, SearchResultListener { baseSearchDialogCompat, item, pos ->


                RegionId = -333
                binding.textView23.setText("")

                RegionList.clear()
                RangeName = item.getTitle()
                binding.textView22.setText(RangeName)
                salesViewModel.getRegions(SharedPreferencesCom.getInstance().gerSharedUser_ID(),item.RecordIDs())
                //               Log.d("RegionName", item.RecordIDs())
                baseSearchDialogCompat.dismiss()
            }).show()
    }
    fun RegionpopUp() {
        SimpleSearchDialogCompat(context, "ادخل اسم المنطقة  " + "\n", "search", null,
            RegionList, SearchResultListener { baseSearchDialogCompat, item, pos ->




                RegionId=item.RecordIDs().toInt()
                RegionName = item.getTitle()
                binding.textView23.setText(RegionName)
                baseSearchDialogCompat.dismiss()
            }).show()
    }

    private fun CheckAllFields(): Boolean {
        if (binding.com.length() == 0) {
            binding.com.setError("This field is required")
            return false
        }
        if (binding.numOfRecord.length() == 0) {
            binding.numOfRecord.setError("This field is required")
            return false
        } else if (binding.comCode.length() == 0) {
            binding.comCode.setError("This field is required")
            return false
        }
//        else if (binding.branchcode.length() == 0) {
//            binding.branchcode.setError("This field is required")
//            return false
//        }
//        else if (binding.streat.length() == 0) {
//            binding.streat.setError("This field is required")
//            return false
//        }
        else if (binding.BuildingNumber.length() == 0) {
            binding.BuildingNumber.setError("This field is required")
            return false
        }

        else if (binding.textView21.length() == 0||binding.textView21.text==" اسم الشركة") {
            binding.textView21.setError("This field is required")
            return false
        }

        else if (binding.textView272.length() == 0||binding.textView272.text==" فروع الشركة") {
            binding.textView272.setError("This field is required")
            return false
        }
        else if (Idd == -122) {
            binding.textView21.setError("This field is required")
            return false
        }

        else if ( RegionId == -333) {
            binding.textView23.setError("This field is required")
            return false
        }

        // after all validation return true.
        return true
    }
}