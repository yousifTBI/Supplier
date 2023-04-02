package com.tbi.supplierplus.framework.ui.purchase.newPurchase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbi.supplierplus.business.pojo.puarchase.ItemsPRCh
import com.tbi.supplierplus.business.repository.PurchaseRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class PurachaseViewModel @Inject constructor(
    private val purchaseRepository: PurchaseRepository,
    // private val salesRepository: SalesRepository
) : ViewModel() {

    val getitemss = MutableLiveData<List<ItemsPRCh>>()

    fun itemPurchase() {
        viewModelScope.launch {
            try {
                purchaseRepository.getItemsforPuarches("2").collect {

                    getitemss.value=it.data
                }
            }catch (ex:Exception){

            }



        }
    }
}