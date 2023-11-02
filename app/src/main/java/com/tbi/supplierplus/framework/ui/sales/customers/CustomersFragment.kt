package com.tbi.supplierplus.framework.ui.sales.customers

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.tbi.supplierplus.BillActivity2
import com.tbi.supplierplus.business.models.Branch
import com.tbi.supplierplus.databinding.FragmentCustomersBinding
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.sales.addCompany.Data
import com.tbi.supplierplus.framework.ui.sales.addCompany.Details
import com.tbi.supplierplus.framework.ui.sales.add_customer.AddCustomerViewModel
import com.tbi.supplierplus.framework.ui2.mortag3.SellOrMortg3Activity
import com.tbi.supplierplus.testAdabters.AllCustomersAdapter
import com.tbi.supplierplus.testAdabters.OnClickListenerss
import dagger.hilt.android.AndroidEntryPoint
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener

@AndroidEntryPoint
class CustomersFragment : Fragment() {

    private lateinit var binding: FragmentCustomersBinding

    //   private val viewModel: SalesViewModel by activityViewModels()
    val viewModel: AddCustomerViewModel by activityViewModels()
    var listCom = ArrayList<Data>()
    var listBranches = ArrayList<Branch>()
    var listDetails = ArrayList<Details>()
    var ComName = ""
    var Idd: Int = 0
    var branchId: Int = 0

    var barnchId =-333
    var companyId =-333
    lateinit var Customer_ID :String
    lateinit var Unpaid_deferred :String
    lateinit var CompanyName :String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCustomersBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.spinKit.isVisible = false


        viewModel.GetAllCompaniesVM()


        viewModel.getAllCompaniesLiveData.observe(viewLifecycleOwner) {

            when (it) {

                is com.tbi.supplierplus.framework.ui.login.State.Loading -> binding.spinKit.isVisible =
                    true
                is com.tbi.supplierplus.framework.ui.login.State.Success -> {
                    binding.spinKit.isVisible = false

                    if (it.data.Data.isNullOrEmpty()){

                    }else{
                        listCom = it.data.Data as ArrayList<Data>


                    }

                }
                is com.tbi.supplierplus.framework.ui.login.State.Error -> binding.spinKit.isVisible =
                    false

            }
        }





        binding.companyCard.setOnClickListener {
            barnchId = -333
            CompanyPopUp()
            binding.textView22.setText("")
            binding.branchCard.isVisible = true



        }
//
//
//
//
//
//
        viewModel.getBranchesDetailsLiveData.observe(viewLifecycleOwner) {
            when (it) {

                is com.tbi.supplierplus.framework.ui.login.State.Loading -> binding.spinKit.isVisible =
                    true
                is com.tbi.supplierplus.framework.ui.login.State.Success -> {
                    it.data.Details.BranchName
                    binding.spinKit.isVisible = false

                    if (it.data.Details == null) {
                    } else {


                      //  binding.branchCard.isVisible = false
                        //  Log.d("ddd",it.data.Details.BranchName)

                        binding.com.setText(it.data.Details.BranchName.toString() + " ")
                        binding.numOfRecord.setText(it.data.Details.Address.toString() + " ")
                        binding.comCode.setText(it.data.Details.RegionID.toString() + " ")
                        binding.branchcode.setText(it.data.Details.ContactName.toString() + " ")
                        binding.streat.setText(it.data.Details.Telephone1.toString()+" ")
                     //  listDetails = it.data.Details as ArrayList<Details>

        // lifecycleScope.launchWhenStarted{
        //     viewModel.customes(activity!!)
        //     viewModel.getAllCustomers()
        // }
        // viewModel.Regionsd2.observe(viewLifecycleOwner){

                    }

                }
                is com.tbi.supplierplus.framework.ui.login.State.Error -> binding.spinKit.isVisible =
                    false

            }

        }

//
        binding.branchCard.setOnClickListener {
            BranchesPopUp()
        }

              viewModel.getAllBranchesLiveData.observe(viewLifecycleOwner) {

                  when (it) {

                      is com.tbi.supplierplus.framework.ui.login.State.Loading -> binding.spinKit.isVisible =
                          true
                      is com.tbi.supplierplus.framework.ui.login.State.Success -> {

                          binding.spinKit.isVisible = false
                          if (it.data.Data == null) {
                          } else {
                              listBranches = it.data.Data



                          }
                      }
                      is com.tbi.supplierplus.framework.ui.login.State.Error -> binding.spinKit.isVisible =
                          false

                  }
              }

//
//
//


        // viewModel.  getAllCustomerTesthandel1()
        //  binding.spinKit.isVisible=true
        // binding.spinKit.isVisible=false


        //    val adapter = CustomersAdapter(OnClickListener {
        //        viewModel.setCustomerID(it.customerID)
        //        findNavController().navigate(
        //            CustomersFragmentDirections.actionCustomersFragmentToCustomerProfileFragment()
        //
        //        )
        //
        //
        //    })
        //   viewModel.getAllCustomerTesthandel1()

        //      lifecycleScope.launchWhenStarted{
        //          viewModel.addRateStateFlow.collect {
        //              when (it) {
        //                  is NetworkState.Idle -> {
        //                      return@collect
        //                  }
        //                  is NetworkState.Loading -> {
        //                     // visProgress(true)
        //                  }
        //                  is NetworkState.Error -> {
        //                     // visProgress(false)
        //                     // it.handleErrors(mContext, null)
        //                  }
        //                  is NetworkState.Result<*> -> {
        //                   //   visProgress(false)
        //                     // handleResult(it.response as TaskUsersResponse)
        //                  }
        //              }
        //
        //          }
        //      }


//        val adapters = CustomersAdapter1(OnClickListeners {
//            // viewModel.setCustomerID(it.customerID)
//            // findNavController().navigate(
//            //     CustomersFragmentDirections.actionCustomersFragmentToCustomerProfileFragment()
//            // )
//        })
        //بيضرب هنا
        //   viewModel.setUser(
        //       CustomersFragmentArgs.fromBundle(requireArguments()).user
        //   )

        binding.SubmittttttID.setOnClickListener {
            if (CheckAllFields()){
//            val Customer_ID = it.Customer_ID.toString()
//            val Unpaid_deferred = it.Unpaid_deferred.toString()
//            val CompanyName = it.item.toString()

            val intent = Intent(activity, SellOrMortg3Activity::class.java)
                .apply {
                    putExtra("Customer_ID", Customer_ID)
                    putExtra("Unpaid_deferred", Unpaid_deferred)
                    putExtra("CompanyName", CompanyName)

                }
            startActivity(intent)
        }
        }
//        val adapte = AllCustomersAdapter(OnClickListenerss {
//
//            val Customer_ID = it.Customer_ID.toString()
//            val Unpaid_deferred = it.Unpaid_deferred.toString()
//            val CompanyName = it.item.toString()
//
//            val intent = Intent(activity, BillActivity2::class.java)
//                .apply {
//                    putExtra("Customer_ID", Customer_ID)
//                    putExtra("Unpaid_deferred", Unpaid_deferred)
//                    putExtra("CompanyName", CompanyName)
//
//                }
//            startActivity(intent)
//
//
//        })

        // lifecycleScope.launchWhenStarted{
        //     viewModel.customes(activity!!)
        //     viewModel.getAllCustomers()
        // }
        // viewModel.Regionsd2.observe(viewLifecycleOwner){

        // }
        // viewModel.getAllCustomersLiveData.observe(viewLifecycleOwner){

        //   //   binding.recyclerView.adapter = adapte
        //   //   adapte.submitList(it)
        // }


//        viewModel. Regionsd.observe(viewLifecycleOwner) {
//
//            when( it){
//
//                is State.Loading -> {
//                    binding.spinKit.isVisible=true
//                }
//                is State.Success ->  {
//                    binding.spinKit.isVisible=false
//                    binding.recyclerView.adapter = adapte
//                    adapte.submitList(it.data.data)
//
//
//                }
//
//                is State.Error ->   {
//
//                    binding.spinKit.isVisible=false
//                }
//
//            }
//
//        }

//
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//              //  if (query.length > 1)
//                //    adapte.filter.filter(query)
//             //   adapte.notifyDataSetChanged()
//                   viewModel.filterCustomers(query)
//             //   adapte.notifyDataSetChanged()
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//              //  if (newText.length > 1)
//             //       adapte.filter.filter(newText)
//
//
//                 viewModel.filterCustomers(newText)
//              //  adapte.notifyDataSetChanged()
//                return false
//            }
//        })

//       binding.btnQr.setOnClickListener {
//           findNavController().navigate(
//               CustomersFragmentDirections.actionCustomersFragmentToCustomerQRCodeScannerFragment(
//                   "customer"
//               )
//           )
//       }

        //   viewModel.navigateToCustomersProfile.observe(viewLifecycleOwner) {
        //       if (it) {
        //           findNavController().navigate(
        //               CustomersFragmentDirections.actionCustomersFragmentToCustomerProfileFragment()
        //           )
        //           viewModel.onDoneNavigateToCustomersProfile()
        //       }
        //   }


        return binding.root
    }
    // private fun <T> handleResult(response: T) {

    //   Log.e(TAG, "handleResult: $response")

    //    when (response) {
    //        is GeneralResponse -> {
    //            when (response.code) {
    //                Constants.Codes.SUCCESSES_CODE -> {
    //                    goto(getString(R.string.task_rated_sucessfully))
    //                }
    //                else -> {
    //                    NetworkState.Error(response.code, response.msg).handleErrors(mContext)
    //                }
    //            }
    //        }
    //        is TaskUsersResponse -> {
    //            when (response.code) {
    //                Constants.Codes.SUCCESSES_CODE -> {
    //                    if (response.data.isEmpty())
    //                        goto(getString(R.string.there_are_no_users_to_rate))
    //                    else {
    //                        users.addAll(response.data)
    //                        ui()
    //                    }
    //                }
    //                else -> {
    //                    NetworkState.Error(response.code, response.msg).handleErrors(mContext)
    //                }
    //            }
    //        }
    //    }
    //}

    fun CompanyPopUp() {
        SimpleSearchDialogCompat(context, "ادخل اسم الشركه  " + "\n", "search", null,
            listCom, SearchResultListener { baseSearchDialogCompat, item, pos ->

                binding.branchCard.isVisible = true
                clearText()
                companyId =item.ID()
                branchApi(item.ID())
                ComName = item.getTitle()
                binding.textView21.setText(ComName)
                baseSearchDialogCompat.dismiss()
            }).show()
    }

    fun BranchesPopUp() {
        SimpleSearchDialogCompat(context, "ادخل اسم الفرع  " + "\n", "search", null,
            listBranches, SearchResultListener { baseSearchDialogCompat, item, pos ->



                if (  listBranches[pos].salesStatus =="False") {
                    // Handle the unclickable item
                    // Optionally, you can show a message or perform any other desired action
                    Toast.makeText(context, "هذا الفرع ليس تابع لك", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, "هذا الفرع ليس تابع لك", Toast.LENGTH_SHORT).show()
                    return@SearchResultListener
                }
                else{
                    Customer_ID =item.ID().toString()
                    Unpaid_deferred =item.Unpaid_deferred().toString()
                    CompanyName =item.getTitle()



                    barnchId =item.ID()
                    item.Unpaid_deferred().toString()
                    //  Log.d("Unpaid_defxserred",item.ID().toString())
                    viewModel.GetAllBranchDetailsVM(item.ID())
                    ComName = item.getTitle()
                    binding.textView22.setText(ComName)
                    binding.BuildingNumber.setText(item.Unpaid_deferred().toString() + " ")
                }




                baseSearchDialogCompat.dismiss()
            }).show()
    }

    fun branchDetailsApi(branchDetailsID: Int) {
        //  viewModel.GetAllBranchDetailsVM(branchID)
        viewModel.GetAllBranchDetailsVM(branchDetailsID)

        listBranches.clear()

    }

    fun branchApi(branchID: Int) {
      //  viewModel.GetAllBranchDetailsVM(branchID)
        viewModel.GetAllBranchesVM(branchID)
        listBranches.clear()

    }


    fun clearText() {
        binding.com.setText(" ")
        binding.numOfRecord.setText(" ")
        binding.comCode.setText(" ")
        binding.branchcode.setText(" ")
        binding.BuildingNumber.setText(" ")
        binding.streat.setText(" ")
    }

    private fun CheckAllFields(): Boolean {
        if (companyId == -333) {
            binding.textView21.setError("This field is required")
            val toast = Toast.makeText(context, "اختار الشركه  ", Toast.LENGTH_LONG)
            toast.show()
            return false
        }
        if (barnchId == -333) {
            binding.textView22.setError("This field is required")
            val toast = Toast.makeText(context, "اختار الفرع ", Toast.LENGTH_LONG)
            return false
        }

        // after all validation return true.
        return true
    }
}