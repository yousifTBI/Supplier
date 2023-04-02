package com.tbi.supplierplus.framework.ui.add_items_return

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.tbi.supplierplus.business.models.Customer
import com.tbi.supplierplus.business.models.Item
import com.tbi.supplierplus.business.models.LocationDetails
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.repository.GeocodeRepository
import com.tbi.supplierplus.business.repository.SalesRepository
import com.tbi.supplierplus.business.utils.toJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddItemsReturnViewModel @Inject constructor(private val salesRepository: SalesRepository, ) :
    ViewModel() {

    private val _allCustomers =
        MutableLiveData<List<Customer>>()

    private val _customers = MutableLiveData<List<Customer>>()

    val navigateToCustomersProfile = MutableLiveData(false)
    val navigateToExecute = MutableLiveData(false)


    /**TOTAL VALUES**/
    var totalReturns = MutableLiveData(0.0f)
    var oldDeferred = MutableLiveData(0.0f)
    var collection = MutableLiveData("0")
    var toBeDeferredPayment = MutableLiveData("0")
    var billCash = MutableLiveData(0.0f)
    var requiredAmount = MutableLiveData(0.0f)


    private val _returns = MutableLiveData<MutableList<Item>?>()
    val returns: LiveData<MutableList<Item>?>
        get() = _returns

    /**To Read Values**/
    var returnWeight = MutableLiveData("0")

    /**Current Values**/
    val currentItemID = MutableLiveData<String>(null)
    private val selectedCustomerID = MutableLiveData("")

    val customers: LiveData<List<Customer>>
        get() = _customers

    private val _allItems = MutableLiveData<List<Item>>()

    var message = MutableLiveData<String?>(null)
    fun onDoneMsg() {
        message.value = null
    }

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>>
        get() = _items

    var user = MutableLiveData<User>()
    var customer = MutableLiveData<Customer>()
    var currentItem = MutableLiveData<Item>()

    fun setUser(user: User) {
        this.user.value = user
        getCustomers()
    }

    fun setCustomerID(customerID: String) {
        startWithNewCustomer()

        selectedCustomerID.value = customerID
        viewModelScope.launch {
            salesRepository.getCustomerInfo(user.value!!.userID, customerID)
                .collect {
                    customer.value = it
                }
        }


    }

    private fun getCustomers() {
        viewModelScope.launch {
            salesRepository.getCustomers(user.value!!.userID).collect {
                _allCustomers.value = it
                _customers.value = it
            }
        }
    }

    init {
        _returns.value = ArrayList()
    }

    fun getAllItems() {
        viewModelScope.launch {
            salesRepository.getItems(user.value!!.userID).collect {
                _allItems.value = it
                _items.value = it
                currentItemID.value = it[0].id
            }
        }

    }


    fun filterCustomers(query: String) {
        Log.i("FilterQuery", query)
        _customers.value = _allCustomers.value!!.filter { query in it.customerName }
    }

    fun filterItems(query: String) {
        _items.value = _allItems.value!!.filter { query in it.id }
    }

    fun setCurrentItemID(itemID: String) {
        currentItemID.value = itemID
    }

    fun updateItem() {
        viewModelScope.launch {
            salesRepository.getItemInfo(
                userID = user.value!!.userID,
                customerID = selectedCustomerID.value!!,
                itemID = currentItemID.value!!
            ).collect { currentItem.value = it }
        }
    }

    fun addToReturns() {
        updateReturns()
    }

    private fun updateReturns() {
        val item = Item(
            name = currentItem.value!!.name,
            id = currentItem.value!!.id,
            size = returnWeight.value!!,
            sellingPrice = currentItem.value!!.sellingPrice,
            categoryName = currentItem.value!!.categoryName,
            groupID = currentItem.value!!.groupID,
            priceID = currentItem.value!!.priceID,
            supplierName = currentItem.value!!.supplierName,
            supplyPrice = currentItem.value!!.supplyPrice,
            supplierID = currentItem.value!!.supplierID,
            quantity = currentItem.value!!.quantity,
            discount = currentItem.value!!.discount
        )

        val list = _returns.value
        list!!.add(item)
        _returns.value = list

        message.value = "تم اضافة مرتجع" + item.name + " بقيمة " + item.totalReturn()

    }

    fun registerBillAndPrint() {

        viewModelScope.launch {
            _returns.value!!.forEach {
                viewModelScope.launch { salesRepository.addItemsReturnForUsers(
                    userID = user.value!!.userID,
                    amount = it.quantity,
                    size = it.size,
                    customerID = customer.value!!.customerID,
                    itemId = it.id
                ).collect { Log.i("MSG", it[0].message)
                    message.value = it[0].message
                } }
            }

        }
    }

    fun execute() {
        executeReturns()
        executeRequiredAmount()
        navigateToExecute.value = true
    }


    private fun executeReturns() {
        totalReturns.value = 0.0f
        _returns.value!!.forEach {
            totalReturns.value = totalReturns.value!!.plus(it.totalReturn())
        }
    }


    fun executeRequiredAmount() {
        requiredAmount.value = customer.value!!.deferred.toFloat()
            .plus(totalReturns.value!!)
    }

    fun calculateNewDeferred(collection: Float) = requiredAmount.value!!.minus(collection)

    fun onDoneNavigateToCustomersProfile() {
        navigateToCustomersProfile.value = false
    }

    fun onDoneNavigateToExecute() {
        navigateToExecute.value = false
    }

    private fun startWithNewCustomer() {
        this.totalReturns = MutableLiveData(0.0f)
        this.oldDeferred = MutableLiveData(0.0f)
        this.collection = MutableLiveData("0")
        this.toBeDeferredPayment = MutableLiveData("0")
        this.billCash = MutableLiveData(0.0f)
        this.requiredAmount = MutableLiveData(0.0f)
        this.returnWeight = MutableLiveData("0")
        _returns.value = ArrayList()
    }
}