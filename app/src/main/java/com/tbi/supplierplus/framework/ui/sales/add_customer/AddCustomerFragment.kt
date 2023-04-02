package com.tbi.supplierplus.framework.ui.sales.add_customer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.tbi.supplierplus.business.pojo.addCustomer.NewCustomer
import com.tbi.supplierplus.business.pojo.addCustomer.Ranges
import com.tbi.supplierplus.business.pojo.addCustomer.Regions
import com.tbi.supplierplus.business.pojo.addItem.Supplier
import com.tbi.supplierplus.databinding.FragmentAddCustomerBinding
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import dagger.hilt.android.AndroidEntryPoint
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener

@AndroidEntryPoint
class AddCustomerFragment : Fragment() {
    private lateinit var binding: FragmentAddCustomerBinding
    private val viewModel: SalesViewModel by activityViewModels()
    var Regions=ArrayList<Regions>()
    var Ranges=ArrayList<Ranges>()
    var Regions_ID:String = ""
    var Ranges_ID:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddCustomerBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
     //   viewModel.   StatesAddNewCustomer.observe(l){
     //
     //   }
        //25

      // viewModel. getRegions("2",Ranges_ID)
        viewModel. GetRange("2")
    //    viewModel.Regions.observe(viewLifecycleOwner){
//
    //        Regions= it as ArrayList<Regions>
    //    }
     //  viewModel.Ranges.observe(viewLifecycleOwner){
     //      Ranges= it as ArrayList<Ranges>
     //  }

     //  binding.cardView4.setOnClickListener {
     //       SimpleSearchDialogCompat(activity,"المنطقه  "+"\n","search",null,
     //           init1(), SearchResultListener{
     //                   baseSearchDialogCompat, item, pos ->
     //               binding.Ids.setText(item.Region)
     //               //  binding.Supl.setText(item.Supplier_ID)
     //               Regions_ID= item.RecordID.toString()
     //               baseSearchDialogCompat.dismiss()
     //           }).show()
     //  }

     //  binding.cardView5.setOnClickListener {
     //      SimpleSearchDialogCompat(activity,"العنوان "+"\n","search",null,
     //          init2(), SearchResultListener{
     //                  baseSearchDialogCompat, item, pos ->
     //              binding.typeOfItem.setText(item.Range)
     //              //  binding.Supl.setText(item.Supplier_ID)
     //              Ranges_ID= item.RecordID.toString()

     //              Log.d("cardView5",item.RecordID.toString()+"="+Ranges_ID.toString())
     //              viewModel. getRegions("2",Ranges_ID)
     //              viewModel.Regions.observe(viewLifecycleOwner){
     //                  Log.d("cardView5", it.get(0).Region.toString()+"=="+it.get(0).RecordID
     //                      )

     //                  Regions= it as ArrayList<Regions>
     //              }



     //              baseSearchDialogCompat.dismiss()
     //          }).show()
     //  }
     //// SimpleSearchDialogCompat(this,"ادخل الموزع  "+"\n","search",null,
     ////     init(), SearchResultListener{
     ////             baseSearchDialogCompat, item, pos ->
     ////         binding.Ids.setText(item.CompanyName)
     ////         //  binding.Supl.setText(item.Supplier_ID)
     ////         Supplier_ID= item.Supplier_ID.toString()
     ////         baseSearchDialogCompat.dismiss()
     ////     }).show()
     //    binding.printbtn.setOnClickListener {
     //  viewModel.AddNewCustomer(NewCustomer(
     //      binding.barcode.text.toString(),
     //      binding.barcode.text.toString(),
     //      binding.nameOfItem.text.toString(),
     //      binding.nameOfItem.text.toString(),
     //      binding.size.text.toString(),
     //      binding.sellingprice.text.toString(),
     //      "2",
     //      Ranges_ID,
     //      Regions_ID,
     //      binding.Buyingprice.text.toString().toDouble(),
     //      binding.BuyingpriceLatitude.text.toString().toDouble(),
     //  ))
     //  }
        return binding.root
    }

    private fun init1(): ArrayList<Regions>? {
        return Regions

    }
    private fun init2(): ArrayList<Ranges>? {
       return Ranges

    }
}