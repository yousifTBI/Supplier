package com.tbi.supplierplus.framework.ui.reports

import android.util.Log
import androidx.lifecycle.*
import com.tbi.supplierplus.business.models.*
import com.tbi.supplierplus.business.pojo.AllCustomers
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.business.pojo.reports.*
import com.tbi.supplierplus.business.repository.ReportsRepository
import com.tbi.supplierplus.business.repository.SalesRepository
import com.tbi.supplierplus.business.utils.toJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val reportsRepository: ReportsRepository,
    private val salesRepository: SalesRepository
) : ViewModel() {
    val user = MutableLiveData<User>()
    val salesSummary = MutableLiveData<SalesSummary>()
    val itemsSummary = MutableLiveData<List<ItemsSummary>?>()
    val statement = MutableLiveData<List<Invoices>>()

    val customer = MutableLiveData<Customer>()
    val billDetails = MutableLiveData<BillDetails>()

    val selectedStatement = MutableLiveData<Invoices?>()
    val customerID = MutableLiveData<String>()
    private val _customers = MutableLiveData<List<AllCustomers>>()
    val customers: LiveData<List<AllCustomers>>
        get() = _customers

   // Tasks<AllCustomers>
    val ItemsReportLivedata = MutableLiveData<List<ItemReport>>()
    val selesReporLivedata = MutableLiveData<SelesReport?>()
    val CustomerIDs = MutableLiveData<String>()
    val CompanyNames = MutableLiveData<String>()


    //GetselesSummryForSpecificCustomer
val reporLivedata = MutableLiveData<ReportSpecificCustomer>()

    fun setCustomer(Customer_ID:String,

                    CompanyName  :String  ){

        CustomerIDs.value=Customer_ID
        CompanyNames.value=CompanyName

    }
    fun reportSpecificCustomer(customerId:String) {
        viewModelScope.launch {
            reportsRepository.GetselesSummryForSpecificCustomer("2",customerId).collect {
                reporLivedata.value = it.Item!!
            }
        }
    }

    fun getselesRepor() {
        viewModelScope.launch {
            reportsRepository.getSelesReport("2").collect {
                selesReporLivedata.value = it.Item
            }
        }
    }

     fun getItemsReport() {
        viewModelScope.launch {
            reportsRepository.getItemsReport("2").collect {
                ItemsReportLivedata.value = it.data
            }
        }
    }




 //  fun setUser(user: User) {
 //      this.user.value = user
 //      getSalesSummary(user.userID)
 //      getItemsSummary()
 //      getCustomers(user.userID)
 //  }

    fun setCustomerID(customerID: String) {
        this.customerID.value = customerID
    }

   fun setSelectedStatement(statement: Invoices) {
      selectedStatement.value = statement
       //selectedStatement.value = statement.Returns
   }

//  init {
//      viewModelScope.launch {
//          customerID.asFlow().collect { getCustomerStatement(it) }
//      }
//      viewModelScope.launch {
//          selectedStatement.asFlow().collect {
//              if (it != null)
//                  getBillDetails(
//                      customerID = customerID.value!!,
//                      billNo = selectedStatement.value!!.BillNo.toString()
//                  )
//          }
//      }
//  }

    private fun getSalesSummary(userID: String) {
        viewModelScope.launch {
            reportsRepository.getSalesSummary(userID = userID).collect {
                salesSummary.value = it
                Log.i("SalesSummary", it.toJson())
            }
        }
    }

    private fun getItemsSummary() {
        viewModelScope.launch {
            reportsRepository.getItemsSummary(userID = user.value!!.userID).collect {
                itemsSummary.value = it
            }
        }
    }

    fun getCustomerStatement(customerID: String) {
      //  viewModelScope.launch {
      //      viewModelScope.launch {
      //          salesRepository.getCustomerInfo(
      //              userID = user.value!!.userID,
      //              customerID = customerID
      //          ).collect { customer.value = it }
      //      }
            viewModelScope.launch {
                reportsRepository.getCustomerStatement(customerID).collect {
                    statement.value = it.data
                }
            }
       // }
    }

    fun getCustomers(userID: String) {
        viewModelScope.launch {
            salesRepository.getAllCustomers(userID).collect {
                _customers.value = it.data
            }
        }
    }
    val Sales = MutableLiveData<List<Sales>?>()
    val Returns = MutableLiveData<List<Returns>?>()

   suspend  fun   getBillDetails(customerID: String, billNo: String) {
        viewModelScope.launch {
            reportsRepository.getBillDetails(customerID, billNo)
                .collect {
                   // billDetails.value = it
                    Sales.value=  it.Item?. Sales
                    Returns.value=   it.Item?.Returns
                 //  Log.d("List<Returns>",it.Item?.Returns?.get(0)?.ItemName.toString()+"vm")
                 }
        }
    }

}