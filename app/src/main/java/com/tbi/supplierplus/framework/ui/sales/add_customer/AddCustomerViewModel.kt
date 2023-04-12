package com.tbi.supplierplus.framework.ui.sales.add_customer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbi.supplierplus.business.pojo.price.EditProductprice
import com.tbi.supplierplus.business.repository.GeocodeRepository
import com.tbi.supplierplus.business.repository.SalesRepository
import com.tbi.supplierplus.business.repository.SalesRepositoryImpl
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.ui.login.State


import com.tbi.supplierplus.framework.ui.login.Task3
import com.tbi.supplierplus.framework.ui.login.wrapWithFlowApi
import com.tbi.supplierplus.framework.ui.sales.customers.product_selection.CustomerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AddCustomerViewModel @Inject constructor(
    private val api: SupplierAPI
) :
    ViewModel() {

    var CustomerLiveData = MutableLiveData<State<Task3<CustomerModel>>>()


    fun addCustomerVM( customerModel: CustomerModel) {
        viewModelScope.launch {
            addCustomer(customerModel).collect{

                CustomerLiveData.value=it


            }
        }
    }

    fun addCustomer(customerModel: CustomerModel) = wrapWithFlowApi {
        api.AddCustomerAPI(customerModel)
    }.flowOn(Dispatchers.IO)

}