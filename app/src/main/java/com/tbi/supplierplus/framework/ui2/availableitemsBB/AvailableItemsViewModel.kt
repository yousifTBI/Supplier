package com.tbi.supplierplus.framework.ui2.availableitemsBB

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbi.supplierplus.business.models.InvoicRequest
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.login.State
import com.tbi.supplierplus.framework.ui.login.isInternetAvailable
import com.tbi.supplierplus.framework.ui.login.wrapWithFlowApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import kotlinx.coroutines.launch
import retrofit2.http.Body
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class AvailableItemsViewModel @Inject constructor(
    private val api: SupplierAPI

) : ViewModel() {


    fun getAvailableItems() = wrapWithFlowApi {
        api.AvailableWareHouseItemsApi()
    }.flowOn(Dispatchers.IO)


    fun InvoicRequestVM(invoicRequest: InvoicRequest) = wrapWithFlowApi {
        api.AddWareHouseRequestApi(invoicRequest)
    }.flowOn(Dispatchers.IO)

    fun GetPendingRequestsMV() = wrapWithFlowApi {
        api.GetPendingRequestsApi(SharedPreferencesCom.getInstance().gerSharedUser_ID())
    }.flowOn(Dispatchers.IO)

    fun ConfirmSalesrRequestApi() = wrapWithFlowApi {
        api.ConfirmSalesrRequestApi(SharedPreferencesCom.getInstance().gerSharedUser_ID())
    }.flowOn(Dispatchers.IO)


     fun getItemsReport() = wrapWithFlowApi {
         api.getItemsReportAPI(SharedPreferencesCom.getInstance().gerSharedUser_ID())
    }.flowOn(Dispatchers.IO)

    fun getSubmitChangeQuantityVMI(  value: Double,   recordID: Int) = wrapWithFlowApi {
        api.getSubmitChangeQuantityAPI(value,recordID)
    }.flowOn(Dispatchers.IO)

    fun getPendingMortaga3at(  ) = wrapWithFlowApi {
        api.GetPendingMortaga3atAPI(SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt())
    }.flowOn(Dispatchers.IO)


    fun getConfirmSalesrRequest(  ) = wrapWithFlowApi {
        api.GetConfirmSalesrRequestAPI(SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt(),1)
    }.flowOn(Dispatchers.IO)

    fun getSubmitChangeMortaga3(  ID: Int, value: Int, amount: Int ) = wrapWithFlowApi {
        api.SubmitChangeMortaga3API(ID, value,amount)
    }.flowOn(Dispatchers.IO)


    fun getCloseSalesDayDatafForUser( ) = wrapWithFlowApi {
        api.GetCloseSalesDayDatafForUserAPI(SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt())
    }.flowOn(Dispatchers.IO)

    fun getConfirmCompleteAccount( ) = wrapWithFlowApi {
        api.GetConfirmCompleteAccountAPI(SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt(),2)
    }.flowOn(Dispatchers.IO)



}

