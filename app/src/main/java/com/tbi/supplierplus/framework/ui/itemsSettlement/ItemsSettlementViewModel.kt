package com.tbi.supplierplus.framework.ui.itemsSettlement

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbi.supplierplus.business.models.*
import com.tbi.supplierplus.business.pojo.returns.Suppliers
import com.tbi.supplierplus.business.pojo.settelment.ItemsSettelment
import com.tbi.supplierplus.business.pojo.settelment.SetItemsSettelment
import com.tbi.supplierplus.business.repository.ItemsSettlementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class ItemsSettlementViewModel @Inject constructor(val repo: ItemsSettlementRepository) : ViewModel() {

    val user = MutableLiveData<User>()
    lateinit var supplierId:String
    lateinit var itemId:String
    lateinit var returnAmount:String

   // val massgeAdd = MutableLiveData<String> ()

    val itemsSettlementSuppliersLiveData= MutableLiveData<List<Suppliers>>()
    val Settlement= MutableLiveData<String>()

    val getItemsSettlementLiveData= MutableLiveData<List<ItemsSettelment>>()


    val getItemsVsBillLiveData: MutableLiveData<List<ItemsVsBill>> by lazy {
        MutableLiveData<List<ItemsVsBill>>()
    }

    val setItemsSettlementLiveData: MutableLiveData<SetReturnItemsSettlement> by lazy {
        MutableLiveData<SetReturnItemsSettlement>()
    }

    fun getItemsSettlementSuppliersFillSpinner(){
        viewModelScope.launch {
            repo.getReturnItemsFillSpinner("2").collect {
               // Log.i("PETER",it.size.toString())
                itemsSettlementSuppliersLiveData.value = it.data
            }
        }
    }
    fun Setitemssettelment(setItemsSettelment: SetItemsSettelment){
        viewModelScope.launch {
            repo.Setitemssettelment(setItemsSettelment).collect {
                Log.d("returnCount",it.Message+"  "+it.State.toString())

                // Log.i("PETER",it.size.toString())
                Settlement.value = it.Message
            }
        }
    }


    fun getItemsSettlement(supplierId:String){
        viewModelScope.launch {
            repo.getItemsSettelMent("2",supplierId).collect {
                getItemsSettlementLiveData.value = it.data
            }
        }
    }

    fun getItemsVsBill(){
        viewModelScope.launch {
            repo.getItemsVsBill(user.value!!.userID,itemId).collect {
                Log.i("itemvsbill",it.size.toString())
                getItemsVsBillLiveData.value = it
            }
        }
    }

    fun setItemsSettlement(itemId:String,supplierId:String,returnAmount:String){
        viewModelScope.launch {
            repo.setItemsSettlement(user.value!!.userID,itemId,supplierId,returnAmount).collect {
                Log.i("setting",it.toString())
                setItemsSettlementLiveData.value = it
            }
        }
    }


    fun setUser(user: User) {
        this.user.value = user
        getItemsSettlementSuppliersFillSpinner()
    }
}