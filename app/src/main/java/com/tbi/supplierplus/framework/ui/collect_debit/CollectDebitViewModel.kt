package com.tbi.supplierplus.framework.ui.collect_debit

import android.util.Log
import androidx.lifecycle.*
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.pojo.opening.AddCollection
import com.tbi.supplierplus.business.pojo.opening.AddOpening
import com.tbi.supplierplus.business.pojo.opening.OpeningBalance
import com.tbi.supplierplus.business.repository.DebitsRepository
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectDebitViewModel @Inject constructor(private val debitsRepository: DebitsRepository) :
    ViewModel() {
    private val _debits = MutableLiveData<List<OpeningBalance>?>()
    val debits: LiveData<List<OpeningBalance>?>
        get() = _debits

    private val _debits2 = MutableLiveData<List<OpeningBalance>?>()
    val debits2: LiveData<List<OpeningBalance>?>
        get() = _debits2

    private val _debit = MutableLiveData<OpeningBalance?>()
    val debit: LiveData<OpeningBalance?>
        get() = _debit

    val msg = MutableLiveData("")
    val navToExecution = MutableLiveData(false)
    var user = MutableLiveData<User>()


    var collection = MutableLiveData("")
    var remaining = MutableLiveData(0.0f)

    fun setUser(user: User) {
        this.user.value = user
        getDebits()
        collection = MutableLiveData("")
        remaining = MutableLiveData(0.0f)
    }
    fun filterCustomers(query: String) {
        if ( _debits.value !=null){
        //    _debits .value =   _debits2 .value!!.filter { query in it.cusName }

        }
    }
    fun setDebit(newDebits: OpeningBalance) {
        this._debit.value = newDebits

    }

    fun navigateToExecution() {
        navToExecution.value = true
    }

    fun onDoneNavigateToExecution() {
        navToExecution.value = false
    }

     fun getDebits() {
        viewModelScope.launch {
            debitsRepository.getCustomerDebits(SharedPreferencesCom.getInstance().gerSharedUser_ID()).collect {
                if(it.State==1){
                    _debits.value = it.data
                    _debits2.value = it.data
                    msg.value = "هذه القائمة تحتوي علي العملاء المتأخرين فقط"
                }else{


                }

            }
        }
    }

    fun onMsgShowDone() {
        msg.value = ""
    }

    fun   onCollect(addCollection: AddCollection) {
        viewModelScope.launch {

            debitsRepository.addCollection(addCollection
             ).collect {
                if (it.State==1){
                    msg.value = it.Message
                }else{


                }

               }
        }
    }

    fun calculateRemaining(collection: Float) {
      remaining.value = _debit.value!!.Debts.toFloat().minus(collection)

    }
}