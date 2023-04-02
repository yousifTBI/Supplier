package com.tbi.supplierplus.framework.ui.addNewItem

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.business.pojo.addItem.NewItem
import com.tbi.supplierplus.business.pojo.addItem.Supplier
import com.tbi.supplierplus.business.pojo.addItem.TypeOfcategory
import com.tbi.supplierplus.business.repository.AddItemRepository
import com.tbi.supplierplus.framework.datasource.requests.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class ItemViewModel @Inject constructor(
    private   val addItemRepository : AddItemRepository
): ViewModel(){
     val Groups = MutableLiveData<List<TypeOfcategory>>()
     val Suppliers = MutableLiveData<List<Supplier>>()
     val massgeAdd = MutableLiveData<String> ()
     val StateAdd = MutableLiveData<State<Tasks<Supplier>>> ()


    fun addNewItem(setItem: NewItem){
        viewModelScope.launch {
            addItemRepository.setNewItem(setItem).collect{


                    StateAdd.value=it


            }
        }
    }
    fun getItemGroupSpinner(UserId: String, Supplier_ID: String){
        viewModelScope.launch {
            addItemRepository.getItemGroup(UserId,Supplier_ID).collect {
                if (it.State==1){
                    Groups.value = it.data
                }


            }
        }
    }


    fun getSuppliers(){
        viewModelScope.launch {
            addItemRepository.GetSuppliers("2").collect {
                if (it.State==1){
                    Suppliers.value = it.data
                }


            }
        }
    }

}