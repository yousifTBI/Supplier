package com.tbi.supplierplus.framework.ui.purchase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbi.supplierplus.business.models.Item
import com.tbi.supplierplus.business.models.PurchaseItem
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.pojo.Items
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.business.pojo.puarchase.AddBurchace
import com.tbi.supplierplus.business.pojo.puarchase.ItemsPRCh
import com.tbi.supplierplus.business.pojo.puarchase.Puarchase
import com.tbi.supplierplus.business.repository.PurchaseRepository
import com.tbi.supplierplus.business.repository.SalesRepository
import com.tbi.supplierplus.framework.datasource.requests.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val purchaseRepository: PurchaseRepository,
   // private val salesRepository: SalesRepository
) :ViewModel() {
 //  var user = MutableLiveData<User>()
 //  var currentItemID = MutableLiveData<String>()
 //  var currentItem = MutableLiveData<Item>()
 //  var itemCount = MutableLiveData("")

 //  private val _items = MutableLiveData<List<Item>>()
 //  val items: LiveData<List<Item>>
 //      get() = _items
 var getItemByBarcodeLiveData = MutableLiveData<State<Tasks<Items>>>()
    var NotFoundItemByBarcodefulsLiveData = MutableLiveData<String>()
    val getitemss = MutableLiveData<List<ItemsPRCh>>()
    val getPuarchase = MutableLiveData<List<Puarchase>>()
    val getPuarchaseItem = MutableLiveData<Puarchase>()
   // val getitems: LiveData<List<ItemsPRCh>>
   //     get() = _getitems

    fun getitemPurchase() {
        viewModelScope.launch {
            purchaseRepository.getPurchases("2").collect {
                if (it.State==1){
                    getPuarchaseItem.value = it.Item!!
                    getPuarchase.value = it.data
                }

            }
        }

    }


    fun getItemByBarcodeV1API( salas_Id:String, Barcode:String,Cus_id:String){
        viewModelScope.launch {
            purchaseRepository. getItemByBarcodeV1API(salas_Id,Barcode,Cus_id).collect{
              // if (it.State==1){
              //     getItemByBarcodeLiveData.value=it.Item!!
              // }else{
              //     Log.d("messagee","العنصر غير موجود")

              //     NotFoundItemByBarcodefulsLiveData.value=it.Message
              //     Log.d("messagee",it.Message)

              // }
                getItemByBarcodeLiveData.value=it

            }
        }
    }

    fun getPurchases() {
        viewModelScope.launch { purchaseRepository.getItemsforPuarches("2").collect {

            getitemss.value = it.data
        }}

    }


    var SucssaddNewPurchases = MutableLiveData<String>()
    fun addNewPurchases(addBurchace: AddBurchace){
        viewModelScope.launch {
            purchaseRepository.addPurchases(addBurchace).collect {
          //      Log.d("nnooono", it.Message+"-"+it.State)
                Log.d("addNewPurchases", it.Message)

                SucssaddNewPurchases.value=it.Message
                if (it.data !=null){
                    getPuarchase.value = it.data
                    Log.d("addNewPurchases", it.Message)

                }

            //
            }
        }
    }

  //  private val _purchasedItems = MutableLiveData<List<PurchaseItem>?>()
  //  val purchasedItems: LiveData<List<PurchaseItem>?>
  //      get() = _purchasedItems

 //  fun setUser(user: User) {
 //      this.user.value = user
 //      viewModelScope.launch {
 //          getPurchasedItems()
 //          getItems()
 //      }
 //  }

 //  fun setCurrentItem(item: String) {
 //      currentItemID.value = item
 //      updateItem(item)
 //  }

 //  private fun updateItem(item: String) {
 //      viewModelScope.launch {
 //          salesRepository.getItemInfo(
 //              userID = user.value!!.userID,
 //              itemID = item,
 //              customerID = "2"
 //          ).collect { currentItem.value = it }
 //      }
 //  }

 //  private suspend fun getPurchasedItems() {
 //      purchaseRepository.getPurchases(user.value!!.userID)
 //          .collect { _purchasedItems.value = it }

 //  }

  // private suspend fun getItems() {
  //     salesRepository.getItems(user.value!!.userID).collect {
  //         _items.value = it
  //         updateItem(it[0].id)
  //     }
  // }
//
 //   fun purchaseItem() {
 //       viewModelScope.launch {
 //           if (itemCount.value!!.isNotEmpty() && itemCount.value!!.toInt() != 0)
 //               purchaseRepository.addPurchase(
 //                   itemID = currentItem.value!!.id,
 //                   itemCount = itemCount.value!!.toInt(),
 //                   userID = user.value!!.userID,
 //                   supplierID = currentItem.value!!.supplierID
 //               ).collect { _purchasedItems.value = it }
 //       }
  //  }


}
