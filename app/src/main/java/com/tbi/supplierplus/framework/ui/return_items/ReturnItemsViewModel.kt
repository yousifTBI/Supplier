package com.tbi.supplierplus.framework.ui.return_items

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbi.supplierplus.business.models.SetReturnItemsSettlement
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.business.pojo.returns.ReturnItems
import com.tbi.supplierplus.business.pojo.returns.Suppliers
import com.tbi.supplierplus.business.repository.ReturnItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReturnItemsViewModel @Inject constructor(val repo: ReturnItemsRepository) : ViewModel() {
    val user = MutableLiveData<User>()
    lateinit var supplierId:String
    lateinit var itemId :String
    lateinit var itemCount :String

    val returnItemsLiveData= MutableLiveData<List<Suppliers>>()
    val getReturnItemsSettelmentLiveData= MutableLiveData<Tasks<ReturnItems>>()
 //   val getReturnItemsSettelmentLiveData1= MutableLiveData<List<ReturnItems>>()


//   val returnItemsLiveData: MutableLiveData<List<Suppliers>> by lazy {
//       MutableLiveData<List<Suppliers>>()
//   }


  // val getReturnItemsSettelmentLiveData: MutableLiveData<List<ReturnItems>> by lazy {
  //     MutableLiveData<List<ReturnItems>>()
  // }

    val setReturnItemsLiveData: MutableLiveData<SetReturnItemsSettlement> by lazy {
        MutableLiveData<SetReturnItemsSettlement>()
    }


    fun getReturnItemsFillSpinner(){
        viewModelScope.launch {
            repo.getReturnItemsFillSpinner(
               // user.value!!.userID
            "2"
            ).collect {
                returnItemsLiveData.value = it.data
                Log.d("getReturn",it.Message)
            }
        }
    }


    fun getReturnItems(supplierId:String)
    {
        viewModelScope.launch {
            repo.getReturnItemsSettelmentRecycler(
              //  user.value!!.userID
                "2"
                ,supplierId).collect {
               getReturnItemsSettelmentLiveData.value = it



            }
        }
    }

 //   fun setReturnItems(userID: String, supplierId: String, itemId: String, itemCount: String){
 //       viewModelScope.launch {
 //           repo.setReturnItemsSettlement(userID,supplierId,itemId,itemCount).collect {
 //               setReturnItemsLiveData.value = it
 //           }
 //       }
 //   }




    fun setUser(user: User) {
        this.user.value = user
        getReturnItemsFillSpinner()
    }



}