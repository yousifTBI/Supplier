package com.tbi.supplierplus.framework.ui.sales.addBranch

import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.tbi.supplierplus.business.pojo.addCustomer.Ranges
import com.tbi.supplierplus.business.pojo.addCustomer.Regions
import com.tbi.supplierplus.databinding.FragmentAddBranchBinding
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import com.tbi.supplierplus.framework.ui.sales.addCompany.Data
import com.tbi.supplierplus.framework.ui.sales.add_customer.AddCustomerViewModel
import com.tbi.supplierplus.framework.ui.sales.customers.product_selection.CustomerModel
import dagger.hilt.android.AndroidEntryPoint
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener


@AndroidEntryPoint
class AddBranchFragment : Fragment() {
    private lateinit var binding: FragmentAddBranchBinding
    lateinit var viewModel: AddCustomerViewModel
    private val salesViewModel: SalesViewModel by activityViewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    var listCom = ArrayList<Data>()
    var RangeList = ArrayList<Ranges>()
    var RegionList = ArrayList<Regions>()
    var long = 0.0
    var lat = 0.0
    var Idd: Int = -122
    var ComName = ""
    var RangeName = ""
    var RegionName = ""
    var RegionId = -333
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
            binding = FragmentAddBranchBinding.inflate(inflater)
            viewModel = ViewModelProvider(this).get(AddCustomerViewModel::class.java)

            binding.spinKit.isVisible = false

            binding.companyCard.setOnClickListener {
                popUp()
            }


            binding.RangeCard.setOnClickListener {
                RangepopUp()
            }



            binding.RegionCard.setOnClickListener {
                RegionpopUp()
            }




            binding.SubmittttttID.setOnClickListener {


                Log.d("sdsret",Idd.toString())
//            val json=    AddBranchModel(
//                    1,
//                    binding.com.text.toString(),
//                    binding.numOfRecord.text.toString(),
//                    binding.comCode.text.toString(),
//                    binding.branchcode.text.toString(),
//                    binding.streat.text.toString(),
//                    binding.BuildingNumber.text.toString(),
//                    "",
//                    "",
//                    "",
//                    "",
//                    8,
//                    64,
//                    1,
//                    Idd,
//                    1.0,
//                    1.0,
//                    binding.BuildingNumber.text.toString(),
//                SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt()
//                ).toJson()
  //              Log.d("AddBranchVM",json)


                if (CheckAllFields()){
                viewModel.AddBranchVM(
                    AddBranchModel(
                        1,
                        binding.com.text.toString(),
                        binding.numOfRecord.text.toString(),
                        binding.comCode.text.toString(),
                        binding.branchcode.text.toString(),
                        binding.streat.text.toString(),
                        binding.BuildingNumber.text.toString(),
                        "",
                        "",
                        "",
                        "",
                        SharedPreferencesCom.getInstance().gerSharedDistributor_ID().toInt(),
                        64,
                        RegionId,
                        Idd,
                        1.0,
                        1.0,
                        binding.BuildingNumber.text.toString(),
                        SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt()
                                          )
                )



            }
            }

            viewModel.addBranchLiveData.observe(viewLifecycleOwner) {
                when (it) {

                    is State.Loading -> binding.spinKit.isVisible = true
                    is State.Success -> {
                        binding.spinKit.isVisible = false


                        Toast.makeText(context, it.data.Message, Toast.LENGTH_SHORT).show()
                        binding.com.setText("")
                        binding.numOfRecord.setText("")
                        binding.comCode.setText("")
                        binding.branchcode.setText("")
                        binding.streat.setText("")
                        binding.BuildingNumber.setText("")

                    }
                    is State.Error -> {
                        binding.spinKit.isVisible = false
                        Toast.makeText(context, it.messag, Toast.LENGTH_SHORT).show()
                    }
                }
            }

          //  SharedPreferencesCom.getInstance().gerSharedUser_ID()
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



            viewModel.GetAllCompaniesVM()
            viewModel.  getAllCompaniesLiveData.observe(viewLifecycleOwner) {
                when (it) {

                    is State.Loading -> binding.spinKit.isVisible = true
                    is State.Success -> {
                        binding.spinKit.isVisible = false
                        listCom= it.data.Data as ArrayList<Data>


                    }
                    is State.Error ->
                    {
                        binding.spinKit.isVisible = false
                    }

                }
            }



        return binding.root
    }




    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                activity!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity!!,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ), 101
            )
            checkGPS()
        } else {
            checkGPS()
        }
    }

    private fun checkGPS() {
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 2000

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val result = LocationServices.getSettingsClient(
            activity!!
        ).checkLocationSettings(builder.build())

        result.addOnCompleteListener {

            try {

                val response = it.getResult(ApiException::class.java)


            } catch (e: ApiException) {

                e.printStackTrace()

                when (e.statusCode) {

                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {

                        val resolveApiException = e as ResolvableApiException
                        resolveApiException.startResolutionForResult(activity!!, 200)

                    } catch (sendIntentException: IntentSender.SendIntentException) {

                    }
                }
            }
        }
    }

    private fun fetchLocation() {

        var task = fusedLocationClient.lastLocation
        //        val location: Location? = task.result

        //        Log.d("gmmIntentUri", long+"lll")
        task.addOnSuccessListener {
            Log.d("gmmIntentUri",it.latitude.toString())


            if (it != null) {

                if (CheckAllFields()) {
                    binding.spinKit.isVisible=true

                    viewModel.addCustomerVM(
                        CustomerModel(
                            1,
                            binding.com.text.toString(),
                            binding.numOfRecord.text.toString(),
                            binding.comCode.text.toString(),
                            binding.branchcode.text.toString(),
                            binding.streat.text.toString(),
                            binding.BuildingNumber.text.toString(),
                            "",
                            "sample string 9",
                            "2023-05-20T11:07:07.9181084+03:00",
                            "sample string 10",
                            1,
                            0,
                            1,
                            "sample string 11",
                            SharedPreferencesCom.getInstance().gerSharedUser_ID(),

                            //  SharedPreferencesCom.getInstance().gerSharedDistributor_ID(),

//                        binding.regin.text.toString(),
//                        binding.postalcode.text.toString(),
                            //                        it.latitude,
                            //                        it.longitude
                        )
                    )


                    viewModel.AddBranchVM(
                        AddBranchModel(
                            1,
                            binding.com.text.toString(),
                            binding.numOfRecord.text.toString(),
                            binding.comCode.text.toString(),
                            binding.branchcode.text.toString(),
                            binding.streat.text.toString(),
                            binding.BuildingNumber.text.toString(),
                            "",
                            "",
                            "",
                            "",
                            8,
                            64,
                            RegionId,
                            78,
                            it.latitude,
                            it.longitude,
                            binding.BuildingNumber.text.toString(),
                            SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt()
                            //  SharedPreferencesCom.getInstance().gerSharedDistributor_ID(),

//                        binding.regin.text.toString(),
//                        binding.postalcode.text.toString(),
                            //                        it.latitude,
                            //                        it.longitude
                        )
                    )

                }
                long = it.longitude
                lat = it.latitude
                Log.d("gmmIntentUdsdri", long.toString()+"two")

//                 val gmmIntentUri =
//                     Uri.parse("geo:$lat,$long?q=hotels")
// //                Log.d("gmmIntentUri", long+"three")
//                 val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//                 mapIntent.setPackage("com.google.android.apps.maps")
//                 startActivity(mapIntent)
            }
        }

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
        } else if (binding.branchcode.length() == 0) {
            binding.branchcode.setError("This field is required")
            return false
        } else if (binding.streat.length() == 0) {
            binding.streat.setError("This field is required")
            return false
        } else if (binding.BuildingNumber.length() == 0) {
            binding.BuildingNumber.setError("This field is required")
            return false
        }

      else if (binding.textView21.length() == 0) {
            binding.textView21.setError("This field is required")
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


    fun popUp() {
        SimpleSearchDialogCompat(context, "ادخل اسم الشركه  " + "\n", "search", null,
            listCom, SearchResultListener { baseSearchDialogCompat, item, pos ->
                ComName = item.getTitle()
                binding.textView21.setText(ComName)
                Idd = item.ID()

//                binding.com.setText(" ")
//                binding.numOfRecord.setText(" ")
//                binding.comCode.setText(" ")
//                binding.branchcode.setText(" ")
//                binding.BuildingNumber.setText(" ")
//                binding.streat.setText(" ")

             //   Idd = item.ID()
//                Log.d("ddd", Idd.toString())
//                ComName = item.getTitle()
//                binding.textView21.setText(ComName)
//
//                viewModel.GetAllCompaniesVM()
//                viewModel.GetAllBranchDetailsVM(Idd)



                baseSearchDialogCompat.dismiss()
            }).show()
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
                Log.d("RegionName", item.RecordIDs())
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




}