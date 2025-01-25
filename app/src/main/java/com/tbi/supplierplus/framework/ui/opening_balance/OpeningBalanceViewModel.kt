package com.tbi.supplierplus.framework.ui.opening_balance

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbi.supplierplus.business.models.CustomerDebits
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.pojo.opening.AddOpening
import com.tbi.supplierplus.business.pojo.opening.OpeningBalance
import com.tbi.supplierplus.business.repository.DebitsRepository
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpeningBalanceViewModel @Inject constructor(private val debitsRepository: DebitsRepository) :
    ViewModel() {
    private val _balances = MutableLiveData<List<OpeningBalance>?>()
    val balances: LiveData<List<OpeningBalance>?>
        get() = _balances
    private val _balances2 = MutableLiveData<List<OpeningBalance>?>()
    val balances2: LiveData<List<OpeningBalance>?>
        get() = _balances2

    private val _balance = MutableLiveData<OpeningBalance?>()
    val balance: LiveData<OpeningBalance?>
        get() = _balance

    val msg = MutableLiveData("")
    val navToExecution = MutableLiveData(false)
    var user = MutableLiveData<User>()
    var collection = MutableLiveData("0.0")

    fun setUser(user: User) {
        this.user.value = user
        getBalances()
        collection.value = "0.0"
    }

    fun setBalance(newDebits: OpeningBalance) {
        this._balance.value = newDebits
    }

    fun filterCustomers(query: String) {
        Log.i("FilterQuery", query)
        val originalList = _balances2.value
        if (originalList != null) {
            val filteredList = originalList.filter { it.cusName.contains(query, ignoreCase = true) }
            _balances.value = filteredList
        }
    }

    private fun getBalances() {
        viewModelScope.launch {
            debitsRepository.getOpeningBalances(SharedPreferencesCom.getInstance().gerSharedUser_ID()).collect {
                Log.d("getOpeningBalances",SharedPreferencesCom.getInstance().gerSharedDistributor_ID())

                _balances.value = it.data
                _balances2.value = it.data
                msg.value = "الرصيد الإفتتاحي لكل العملاء"
            }
        }
    }

    fun navigateToExecution() {
        navToExecution.value = true
    }

    fun onDoneNavigateToExecution() {
        navToExecution.value = false
    }

    fun onMsgShowDone() {
        msg.value = ""
    }

    fun onAdd() {
        viewModelScope.launch {
            Log.d("AddOpening",balance.value?.cus_id.toString()+"-"+collection.value!!)

            debitsRepository.addBalance(
                AddOpening(balance.value!!.cus_id.toString(),SharedPreferencesCom.getInstance().gerSharedUser_ID(),collection.value!!)

            ).collect {
                Log.e("AddOpening", "task"+it.Message+"Message")
                msg.value = it.Message
             }

        }
    }

}