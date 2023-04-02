package com.tbi.supplierplus.framework.ui.daily_closing

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbi.supplierplus.business.models.Expenses
import com.tbi.supplierplus.business.models.SetDailyClosing
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.business.pojo.closing.SupplierReport
import com.tbi.supplierplus.business.pojo.reports.InvoiceDetails
import com.tbi.supplierplus.business.repository.DailyClosingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyClosingViewModel @Inject constructor(val repo: DailyClosingRepository) : ViewModel() {

    var user = MutableLiveData<User>()

    // the DailyClosingExpenses
    val closingExpensesLiveData: MutableLiveData<List<SupplierReport>> by lazy {
        MutableLiveData<List<SupplierReport>>()
    }

    val purchasesLiveData: MutableLiveData<List<SupplierReport>> by lazy {
        MutableLiveData<List<SupplierReport>>()
    }

    val summarySupplierLiveData: MutableLiveData<List<SupplierReport>> by lazy {
        MutableLiveData<List<SupplierReport>>()
    }


    val itemsDailyClosingLiveData: MutableLiveData<List<SupplierReport>> by lazy {
        MutableLiveData<List<SupplierReport>>()
    }

    val setClosingDayLiveData: MutableLiveData<Tasks<InvoiceDetails>> by lazy {
        MutableLiveData<Tasks<InvoiceDetails>>()
    }

    fun getDailyClosingSummarySupplier(){

        viewModelScope.launch {
            repo.getDailyClosingSummarySupplier("2").collect {
                if (it.data !=null&&it.State==1){
                    summarySupplierLiveData.value = it.data

                }
            }
        }
    }
    var task=MutableLiveData<Tasks<InvoiceDetails>>()

    fun closeTheDay(){
        viewModelScope.launch {
            repo.setDailyClosing("2").collect {
                setClosingDayLiveData.value = it
             //  if (it.data !=null&&it.State==1){
             //      setClosingDayLiveData.value = it


             //  }
                //task.value=it
            }
        }
    }


    // get closing expenses every update
    fun getData(){
        viewModelScope.launch {
            repo.getDailyClosingExpenses("2").collect {
                if (it.data !=null&&it.State==1){
                    closingExpensesLiveData.value = it.data

                }
            }
        }
    }

    // closing the purchases
    fun getDailyClosingPurchases(){
        viewModelScope.launch {
            repo.getDailyClosingPurchases("2").collect {
                if (it.data !=null&&it.State==1){
                    purchasesLiveData.value = it.data

                }

            }
        }
    }
//GetClosingDay_Summry_items
    fun getDailyClosingSummaryItems() {
        viewModelScope.launch {
            repo.getDailyClosingSummaryItems("2").collect {
                if (it.data !=null&&it.State==1){
                    itemsDailyClosingLiveData.value = it.data

                }

            }
        }
    }




    fun setUser(
        //user: User
    ) {
       // this.user.value = user
        getDailyClosingPurchases()
        getData()
        getDailyClosingSummarySupplier()
        getDailyClosingSummaryItems()
    }









}