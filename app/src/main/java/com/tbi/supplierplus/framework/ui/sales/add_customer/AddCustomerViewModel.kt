package com.tbi.supplierplus.framework.ui.sales.add_customer


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbi.supplierplus.business.models.Branch
import com.tbi.supplierplus.business.models.ItemsModel
import com.tbi.supplierplus.business.pojo.Producer
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.login.wrapWithFlowApi
import com.tbi.supplierplus.framework.ui.sales.addBranch.AddBranchModel
import com.tbi.supplierplus.framework.ui.sales.addBranch.BranchBackModel
import com.tbi.supplierplus.framework.ui.sales.addCompany.BranchDetailsModel
import com.tbi.supplierplus.framework.ui.sales.addCompany.BranchModel
import com.tbi.supplierplus.framework.ui.sales.addCompany.CompanyTask
import com.tbi.supplierplus.framework.ui.sales.addCompany.Data
import com.tbi.supplierplus.framework.ui.sales.customers.product_selection.CustomerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class AddCustomerViewModel @Inject constructor(
    private val api: SupplierAPI
) :
    ViewModel() {

    var CustomerLiveData = MutableLiveData<State<CustomerTask<CustomerModel>>>()
    var getAllCompaniesLiveData = MutableLiveData<State<CompanyTask>>()
    var getAllBranchesLiveData = MutableLiveData<State<BranchModel<Branch>>>()
    var getBranchesDetailsLiveData = MutableLiveData<State<BranchDetailsModel>>()
    var addBranchLiveData = MutableLiveData<State<BranchBackModel>>()
    var getProductByBarcodeLivedata = MutableLiveData<State<Tasks<ItemsModel>>>()


    fun addCustomerVM(customerModel: CustomerModel) {
        viewModelScope.launch {
            addCustomer(customerModel).collect {

                CustomerLiveData.value = it


            }
        }
    }

    fun GetAllCompaniesVM() {
        viewModelScope.launch {
            GetCompaniesName(SharedPreferencesCom.getInstance().gerSharedDistributor_ID().toInt() ).collect {


                getAllCompaniesLiveData.value = it


            }
        }
    }

    fun GetAllBranchesVM(ComID: Int  ) {
        viewModelScope.launch {
            GetBranchesName(ComID,SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt()).collect {


                getAllBranchesLiveData.value = it


            }
        }
    }


    fun GetAllBranchDetailsVM(BranchID: Int) {
        viewModelScope.launch {
            GetBranchDetails(BranchID).collect {


                getBranchesDetailsLiveData.value = it


            }
        }
    }



    fun AddBranchVM(add: AddBranchModel) {
        viewModelScope.launch {
           AddBranch(add).collect {


               addBranchLiveData.value = it


            }
        }
    }


    fun GetProductByBarcodeVM(sales_Id: Int,   Barcode: String, Cus_id: String) {
        viewModelScope.launch {
          GetProductByBarcode(sales_Id,Barcode,Cus_id).collect {


              getProductByBarcodeLivedata.value = it


            }
        }
    }




    fun addCustomer(customerModel: CustomerModel) = wrapWithFlowApi {
        api.AddCustomerAPI(customerModel)
    }.flowOn(Dispatchers.IO)


    fun GetCompaniesName(DisGroupID :Int) = wrapWithFlowApi {
        api.GetAllCompaniesAPI(DisGroupID )
    }.flowOn(Dispatchers.IO)


    fun GetBranchesName(ComID: Int ,UserId: Int) = wrapWithFlowApi {
        api.GetAllBranchesAPI(ComID,UserId)
    }.flowOn(Dispatchers.IO)


    fun GetBranchDetails(BranchID: Int) = wrapWithFlowApi {
        api.GetBranchDetailsAPI(BranchID)
    }.flowOn(Dispatchers.IO)



    fun AddBranch(add: AddBranchModel) = wrapWithFlowApi {
        api.AddBranchAPI(add)
    }.flowOn(Dispatchers.IO)


  fun GetProductByBarcode(sales_Id: Int,   Barcode: String,Cus_id: String) = wrapWithFlowApi {
        api.GetItemByBarcodeAPI(sales_Id,Barcode,Cus_id)
    }.flowOn(Dispatchers.IO)


}