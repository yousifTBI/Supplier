package com.tbi.supplierplus.framework.ui.sales

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.Customer
import com.tbi.supplierplus.business.models.Item
import com.tbi.supplierplus.business.models.LocationDetails
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.pojo.*
import com.tbi.supplierplus.business.pojo.addCustomer.NewCustomer
import com.tbi.supplierplus.business.pojo.addCustomer.Ranges
import com.tbi.supplierplus.business.pojo.addCustomer.Regions
import com.tbi.supplierplus.business.pojo.addCustomer.RusNewCustomer
import com.tbi.supplierplus.business.pojo.billModels.SaleingBill
import com.tbi.supplierplus.business.pojo.bills.NewBill
import com.tbi.supplierplus.business.pojo.price.EditProductprice
import com.tbi.supplierplus.business.pojo.price.SpecialPrice
import com.tbi.supplierplus.business.pojo.reports.ReportSpecificCustomer
import com.tbi.supplierplus.business.repository.GeocodeRepository
import com.tbi.supplierplus.business.repository.ReportsRepository
import com.tbi.supplierplus.business.repository.SalesRepository
import com.tbi.supplierplus.business.repository.SalesRepositoryImpl
import com.tbi.supplierplus.business.utils.Constants
import com.tbi.supplierplus.business.utils.NetworkState
import com.tbi.supplierplus.business.utils.isInternetAvailable
import com.tbi.supplierplus.framework.datasource.network.SupplierAPI
import com.tbi.supplierplus.framework.datasource.requests.State
import com.tbi.supplierplus.framework.shared.SharedPreferencesCom
import com.tbi.supplierplus.framework.ui.login.wrapWithFlowApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class SalesViewModel @Inject constructor(
    private val salesRepository: SalesRepository,
    private val geocodeRepository: GeocodeRepository,
    private val salesRepository2: SalesRepositoryImpl,private val api: SupplierAPI
) :
    ViewModel() {


   // var listBill=ArrayList<SaleingBill>()
   // var listBill = LiveData<MutableLiveData<SaleingBill>>()

   var v:Int = 0
    get() = v
    set

    //  private val _allCustomers =
 //      MutableLiveData<List<Customer>>()
 //  var msgToPrint = ""
 //  val print = MutableLiveData<Boolean>(false)
 //  private val _customers = MutableLiveData<List<Customer>>()

 //  val navigateToCustomersProfile = MutableLiveData(false)
 //  val navigateToExecute = MutableLiveData(false)
 //  var billNo = ""

 //  /**TOTAL VALUES**/
 //  var sales = MutableLiveData(0.0f)
 //  var totalReturns = MutableLiveData(0.0f)
 //  var totalItemsDiscount = MutableLiveData(0.0f)
 //  var appliedDiscount = MutableLiveData("0.0")
 //  var oldDeferred = MutableLiveData(0.0f)
 //  var billDiscountPercentage = MutableLiveData("0")
 //  var collection = MutableLiveData("0")
 //  var toBeDeferredPayment = MutableLiveData("0")
 //  var billCash = MutableLiveData(0.0f)
 //  var requiredAmount = MutableLiveData(0.0f)
 //  var totalBill = MutableLiveData(0.0f)
 //  var discountValue = MutableLiveData(0.0f)

 //  private val _bill = MutableLiveData<MutableList<Item>?>()
 //  val bill: LiveData<MutableList<Item>?>
 //      get() = _bill

 //  private val _returns = MutableLiveData<MutableList<Item>?>()
 //  val returns: LiveData<MutableList<Item>?>
 //      get() = _returns

 //  private var locationDetails = MutableLiveData<LocationDetails>()


 //  /**To Read Values**/
 //  var discount = MutableLiveData("0")
 //  var quantity = MutableLiveData("0")
 //  var returnWeight = MutableLiveData("0")

 //  /**Current Values**/
 //  val currentItemID = MutableLiveData<String>(null)
 //  private val selectedCustomerID = MutableLiveData("")

 //  val customers: LiveData<List<Customer>>
 //      get() = _customers
///
 //  private val _allItems = MutableLiveData<List<Item>>()

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
    var getProducers = MutableLiveData<List<Items>>()
    var setNewBillTip = MutableLiveData<Tasks<Tesssst>>()


    var getAllCustomersLiveData = MutableLiveData<List<AllCustomers>>()
    var CustomersLiveData = MutableLiveData<List<AllCustomers>>()
    var getProducerBycodeLiveData = MutableLiveData< Tasks<Producer>>()

 //   fun setUser(user: User) {
 //       this.user.value = user
 //       getCustomers()
 //   }

 //  fun setCustomerID(customerID: String) {
 //      startWithNewCustomer()

 //      selectedCustomerID.value = customerID
 //      viewModelScope.launch {
 //          salesRepository.getCustomerInfo(user.value!!.userID, customerID)
 //              .collect {
 //                 customer.value = it
 //              }
 //      }


 //  }

 //  private fun getCustomers() {
 //      viewModelScope.launch {
 //          salesRepository.getCustomers(user.value!!.userID).collect {
 //              _allCustomers.value = it
 //              _customers.value = it
 //          }
 //      }
 //  }

 //  init {
 //      _bill.value = ArrayList()
 //      _returns.value = ArrayList()
 //  }

//EditItemByBarcode
var editProductpriceLiveData = MutableLiveData<EditProductprice?>()
var editProductpricemass = MutableLiveData<com.tbi.supplierplus.framework.ui.login.State<Tasks<ReportSpecificCustomer>>>()
var editProductpriceState = MutableLiveData<Int>()

    fun editItemByBarcode( editProductprice: EditProductprice) {
    viewModelScope.launch {
        EditItemByBarcode(editProductprice).collect{
          //  editProductpriceState.value=it
            editProductpricemass.value=it

           // Log.d("AddCustomer",it.Message + ""+it.State)
           // StatesAddNewCustomer.value=it.Message
        }
    }
}


    fun EditItemByBarcode(editProductprice: EditProductprice) = wrapWithFlowApi(

        fetch = {
             api.EditItemByBarcodeAPI(editProductprice)
        }

    ).flowOn(Dispatchers.IO)

    var getItemByBarcodeLiveData = MutableLiveData<State<Tasks<Items>>>()
    var NotFoundItemByBarcodefulsLiveData = MutableLiveData<String>()
    var StatesAddNewCustomer = MutableLiveData<String>()

    fun AddNewCustomer(newCustomer: NewCustomer) {
        viewModelScope.launch {
            salesRepository.AddCustomer(newCustomer).collect{
                Log.d("AddCustomer",it.Message + ""+it.State)
                StatesAddNewCustomer.value=it.Message
            }
        }
    }

    var RangesLiveData = MutableLiveData<List<Ranges>>()

     fun GetRange(UserId: String){
         viewModelScope.launch {
             salesRepository.GetRange(UserId).collect{
                 RangesLiveData.value=it.data
             }
         }
     }

    var SetSpecialItemPriceLiveData = MutableLiveData<com.tbi.supplierplus.framework.ui.login.State<Tasks<SpecialPrice>>>()


    fun  SetSpecialItemPrice( specialPrice: SpecialPrice){
        viewModelScope.launch {
            SetSpecialItemPriceAPI(specialPrice).collect{

                SetSpecialItemPriceLiveData.value=it

            }
        }
    }
    fun SetSpecialItemPriceAPI(specialPrice: SpecialPrice) = wrapWithFlowApi(

        fetch = {
            api.SetSpecialItemPriceAPI(specialPrice)
        }

    ).flowOn(Dispatchers.IO)


    var RegionsLiveData = MutableLiveData<List<Regions>>()


    fun  getRegions(User_ID: String, Range_Id: String) {
        Log.d("cardView5", "test")

                viewModelScope.launch {
            salesRepository.Get_Region(User_ID, Range_Id).collect{
              //  Log.d("cardView5", it.Message+""+it.data.get(0).Region.toString())

                RegionsLiveData.value=it.data
            }
        }
    }

    fun getItemByBarcodeV1API( salas_Id:String, Barcode:String,Cus_id:String){
        viewModelScope.launch {
            salesRepository. getItemByBarcodeV1API(salas_Id,Barcode,Cus_id).collect{
             //   if (it.State==1){
                    getItemByBarcodeLiveData.value=it
                        //.Item!!
             // }else{
             //     Log.d("messagee","العنصر غير موجود")

             //     NotFoundItemByBarcodefulsLiveData.value=it.Message
             //     Log.d("messagee",it.Message)

             // }

            }
        }
    }

    fun getItemByBarcodeV1AP( salas_Id:String, Barcode:String,Cus_id:String){
        viewModelScope.launch {
            salesRepository. getItemByBarcodeV1API(salas_Id,Barcode,Cus_id).collect{

            }
        }
    }

  fun  setNewPill(bill: NewBill){
      viewModelScope.launch {
          salesRepository. addNewPill(bill).collect{
              setNewBillTip.value=it

              Log.e("haaaaaaaager", it.Message+"viewmodel")

          }
      }
  }

  // var x:String=""
  //  lateinit var
 //   @SuppressLint("NullSafeMutableLiveData")
    fun getProducerBycode(sales_Id: Int, Item_ID : String, Cus_id  : String?):String{
       // var x:Producer
     // val job =
      var x:String=""
      viewModelScope.launch {

          salesRepository.getProducerBycode(sales_Id, Item_ID, Cus_id).collect {

               getProducerBycodeLiveData.value=it
              //  it.ArrList
               x = it.Item!!.ItemName1
             // x = it.item?.ItemName1.toString()

          }


      }

      return x
    }

    fun getProducerBycodeVM(sales_Id: Int, Item_ID : String, Cus_id  : String){
        viewModelScope.launch {

            salesRepository.getProducerBycode(sales_Id, Item_ID, Cus_id).collect {

                getProducerBycodeLiveData.value=it


            }


        }

    }





    var Regionsd = MutableLiveData<State<Tasks<AllCustomers>>>()


    fun getAllCustomerTesthandel1(){
        // SharedPreferencesCom.getInstance().gerSharedphoneNumber()
        Log.d("makeText","Loading8")
        viewModelScope.launch {
            Log.d("dddd",SharedPreferencesCom.getInstance().gerSharedUser_ID().toString())
            salesRepository.getAllCustomerTesthandel(SharedPreferencesCom.getInstance().gerSharedUser_ID().toString()).collect{
                Log.d("makeText","Loading2")
                Regionsd.value=it
            }
        }
    }

    var Regionsd2 = MutableLiveData<com.tbi.supplierplus.framework.ui.login.State<Tasks<AllCustomers>>>()
    fun customes(context: Context){
        viewModelScope.launch {
            salesRepository2.customer("2",context).collect{
                Regionsd2.value=it

            }
        }
    }
    fun getAllCustomers(){
      //  _addRateStateFlow.value=NetworkState.Loading

        viewModelScope.launch {
           // runApi(_addRateStateFlow,
           //     salesRepository.getAllCustomers("2")
           //     )
            salesRepository.getAllCustomers(SharedPreferencesCom.getInstance().gerSharedUser_ID().toString()).collect{
               // CustomersLiveData.value=it.data
               // getAllCustomersLiveData.value=it.data
                //  it.ArrList
            }

            //  salesRepository.getItemss("2").collect {
//
            //      //   _allItemss.value = it
            //      x.value = it.AllCustomers
            //      // _itemss.value = it
            //      // currentItemID.value = it[0].id
//
            //  }
        }
    }

    fun getAllItemss(Cus_id:Int){
        viewModelScope.launch {
            salesRepository.getItemss(SharedPreferencesCom.getInstance().gerSharedUser_ID().toInt(),Cus_id).collect {

             //   _allItemss.value = it
                getProducers.value = it.data
               // _itemss.value = it
               // currentItemID.value = it[0].id

            }
        }
    }
//   fun getAllItems() {
//       viewModelScope.launch {
//           salesRepository.getItems(user.value!!.userID).collect {

//               _allItems.value = it
//               _items.value = it
//               currentItemID.value = it[0].id
//           }
//       }

//   }


     fun filterCustomers(query: String) {
         Log.i("FilterQuery", query)
         if ( getAllCustomersLiveData.value !=null){
             getAllCustomersLiveData .value =    CustomersLiveData .value!!.filter { query in it.item }

         }
     }

 //  fun filterItems(query: String) {
 //      _items.value = _allItems.value!!.filter { query in it.id }
 //  }

 //  fun setCurrentItemID(itemID: String) {
 //      currentItemID.value = itemID
 //  }

//   fun updateItem() {
//       viewModelScope.launch {
//           salesRepository.getItemInfo(
//               userID = user.value!!.userID,
//               customerID = selectedCustomerID.value!!,
//               itemID = currentItemID.value!!
//           ).collect { currentItem.value = it }
//       }
//   }

//   fun addToBill() {
//       updateBill()
//   }

//   fun addToReturns() {
//       updateReturns()
//   }

//   private fun updateBill() {
//       val item = Item(
//           name = currentItem.value!!.name,
//           id = currentItem.value!!.id,
//           quantity = quantity.value!!,
//           size = currentItem.value!!.size,
//           sellingPrice = currentItem.value!!.sellingPrice,
//           discount = discount.value!!,
//           supplierID = currentItem.value!!.supplierID,
//           supplyPrice = currentItem.value!!.supplyPrice,
//           supplierName = currentItem.value!!.supplierName,
//           priceID = currentItem.value!!.priceID,
//           groupID = currentItem.value!!.groupID,
//           categoryName = currentItem.value!!.categoryName
//       )

//       val list = _bill.value
//       list!!.add(item)
//       _bill.value = list

//       message.value = "تم اضافة " + item.name + " بقيمة " + item.totalSale()

//   }

//   private fun updateReturns() {
//       val item = Item(
//           name = currentItem.value!!.name,
//           id = currentItem.value!!.id,
//           size = returnWeight.value!!,
//           sellingPrice = currentItem.value!!.sellingPrice,
//           categoryName = currentItem.value!!.categoryName,
//           groupID = currentItem.value!!.groupID,
//           priceID = currentItem.value!!.priceID,
//           supplierName = currentItem.value!!.supplierName,
//           supplyPrice = currentItem.value!!.supplyPrice,
//           supplierID = currentItem.value!!.supplierID,
//           quantity = currentItem.value!!.quantity,
//           discount = currentItem.value!!.discount
//       )

//       val list = _returns.value
//       list!!.add(item)
//       _returns.value = list

//       message.value = "تم اضافة مرتجع" + item.name + " بقيمة " + item.totalReturn()

//   }

//   fun registerBillAndPrint() {
//       Log.e("PrintError1", "e.message.toString()")

//       viewModelScope.launch {
//           if (oldDeferred.value!! > collection.value!!.toFloat())
//               billCash.value = 0.0f

//           if (oldDeferred.value!! <= collection.value!!.toFloat())
//               billCash.value =
//                   collection.value!!.toFloat().minus(customer.value!!.deferred.toFloat())

//           salesRepository.saveBill(
//               bill = _bill.value!!.toList(),
//               customerID = customer.value!!.customerID,
//               billDiscount = totalItemsDiscount.value.toString(),
//               editor = user.value!!.userName,
//               salesID = user.value!!.userID,
//               total = totalBill.value.toString(),
//               cash = billCash.value.toString(),
//               deferred = toBeDeferredPayment.value!!,
//               returnAmount = totalReturns.value.toString(),
//               returns = _returns.value!!.toList(),
//               oldDeferred = customer.value!!.deferred,
//           ).collect {
//               message.value = it.message
//               billNo = it.billNumber
//           }
//           msgToPrint = "اسم العميل ".plus(customer.value!!.customerName).plus("\n")

//           msgToPrint = msgToPrint.plus("رقم الفاتورة ").plus(billNo).plus("\n")
//           msgToPrint =
//               msgToPrint.plus("بواسطة ").plus(user.value!!.userName + "-" + user.value!!.userID)
//                   .plus("\n")
//           msgToPrint =
//               msgToPrint.plus("التوقيت ").plus(SimpleDateFormat("dd/M/yyyy").format(Date()))
//                   .plus(" ").plus(SimpleDateFormat("hh:mm:ss").format(Date())).plus("\n")

//           if (_bill.value!!.isNotEmpty()) {
//               msgToPrint = msgToPrint.plus("==========الفاتورة==========")
//               msgToPrint = msgToPrint.plus("\n")
//               repeat(_bill.value!!.size) {
//                   msgToPrint = msgToPrint.plus("الصنف " + _bill.value!![it].name)
//                   msgToPrint = msgToPrint.plus("   ")
//                   msgToPrint = msgToPrint.plus(
//                       "الكمية " + _bill.value!![it].quantity
//                   )
//                   msgToPrint = msgToPrint.plus("   ")
//                   msgToPrint = msgToPrint.plus("الوزن " + _bill.value!![it].size)
//                   msgToPrint = msgToPrint.plus("   ")
//                   msgToPrint.plus("\n")
//                   msgToPrint = msgToPrint.plus(
//                       "السعر " + _bill.value!![it].sellingPrice
//                   )
//                   msgToPrint = msgToPrint.plus("   ")

//                   msgToPrint = msgToPrint.plus("الخصم " + _bill.value!![it].discount)
//                   msgToPrint = msgToPrint.plus("   ")

//                   msgToPrint =
//                       msgToPrint.plus("الإجمالي " + _bill.value!![it].totalSale().toString())
//                   msgToPrint = msgToPrint.plus("\n")
//                   msgToPrint = msgToPrint.plus("##########")
//                   msgToPrint = msgToPrint.plus("\n")
//               }
//           }

//           if (_returns.value!!.isNotEmpty()) {
//               msgToPrint = msgToPrint.plus("=========المرتجعات==========")
//               msgToPrint = msgToPrint.plus("\n")
//               repeat(_returns.value!!.size) {
//                   msgToPrint = msgToPrint.plus("الصنف ${_returns.value!![it].name}")
//                   msgToPrint = msgToPrint.plus("   ")

//                   msgToPrint = msgToPrint.plus("الوزن ${_returns.value!![it].size}")
//                   msgToPrint = msgToPrint.plus("   ")

//                   msgToPrint = msgToPrint.plus("الإجمالي ${_returns.value!![it].totalReturn()}")
//                   msgToPrint = msgToPrint.plus("\n")
//                   msgToPrint = msgToPrint.plus("##########")
//                   msgToPrint = msgToPrint.plus("\n")
//               }
//           }

//           msgToPrint = msgToPrint.plus("اجمالي الفاتورة قبل الخصم و المرتجعات ")
//               .plus(sales.value.toString())
//               .plus("\n")
//           msgToPrint = msgToPrint.plus("اجمالي الفاتورة بعد الخصم و المرتجعات ")
//               .plus(totalBill.value.toString())
//               .plus("\n")
//           msgToPrint = msgToPrint.plus("اجمالي المرتجعات ").plus(totalReturns.value).plus("\n")
//           msgToPrint = msgToPrint.plus("اجمالي خصم الفاتورة ")
//               .plus(totalItemsDiscount.value!!.plus(appliedDiscount.value!!.toFloat())).plus("\n")
//           msgToPrint = msgToPrint.plus("المطلوب دفعه ").plus(requiredAmount.value).plus("\n")
//           msgToPrint = msgToPrint.plus("الباقي ").plus(toBeDeferredPayment.value).plus("\n")
//           if (billCash.value!!.toFloat() != 0.0f)
//               msgToPrint = msgToPrint.plus("كاش ").plus(billCash.value).plus("\n")
//           msgToPrint = msgToPrint.plus("المدفوع ").plus(collection.value).plus("\n")


//           msgToPrint = msgToPrint.plus("\n")
//           msgToPrint = msgToPrint.plus("\n")
//           msgToPrint = msgToPrint.plus("Powered by")
//           msgToPrint = msgToPrint.plus("\n")
//           msgToPrint = msgToPrint.plus("\n")
//           msgToPrint = msgToPrint.plus("Technology & Business Integration")
//           msgToPrint = msgToPrint.plus("\n")
//           msgToPrint = msgToPrint.plus("\n")

//           Log.i("MSG_TO_PRINT", msgToPrint)
//           print.value = true
//       }
//   }

////  fun getPlace(lat: Double, lng: Double) {
////      viewModelScope.launch {
////          geocodeRepository.getLocation(lat, lng).collect { locationDetails.value = it }
////      }
////  }

///   fun execute() {
///       executeSales()
//       executeReturns()
//       executeItemsDiscount()
//       executeTotalBill()
//       executeRequiredAmount(false)
//       navigateToExecute.value = true
//   }

//   private fun executeSales() {
//       sales.value = 0.0f
//       _bill.value!!.forEach { sales.value = sales.value!!.plus(it.totalSale()) }
//   }

//   private fun executeReturns() {
//       totalReturns.value = 0.0f
//       _returns.value!!.forEach {
//           totalReturns.value = totalReturns.value!!.plus(it.totalReturn())
//       }
//   }

//  private fun executeItemsDiscount() {
//      totalItemsDiscount.value = 0.0f
//      _bill.value!!.forEach {
//          totalItemsDiscount.value = totalItemsDiscount.value!!.plus(it.discount.toFloat())
//      }
//  }

//  private fun executeTotalBill() {
//      totalBill.value = 0.0f
//      totalBill.value =
//          sales.value!!.minus(totalItemsDiscount.value!!).minus(totalReturns.value!!)
//  }

//  fun executeRequiredAmount(isPercentage: Boolean) {
//      if (appliedDiscount.value!!.isEmpty())
//          discountValue.value = 0.0f
//      else {
//          if (!isPercentage)
//              discountValue.value = appliedDiscount.value!!.toFloat()
//          else {
//              val percentage = appliedDiscount.value!!.toFloat().div(100)
//              discountValue.value = totalBill.value!!.times(percentage)
//          }
//      }
//      requiredAmount.value = totalBill.value!!.plus(customer.value!!.deferred.toFloat())
//          .minus(discountValue.value!!)
//  }

//    fun calculateNewDeferred(collection: Float) = requiredAmount.value!!.minus(collection)

    ///fun onDoneNavigateToCustomersProfile() {
  //      navigateToCustomersProfile.value = false
    //}

    //fun onDoneNavigateToExecute() {
      //  navigateToExecute.value = false
    //}

//  private fun startWithNewCustomer() {
//      this.sales = MutableLiveData(0.0f)
//      this.totalReturns = MutableLiveData(0.0f)
//      this.totalItemsDiscount = MutableLiveData(0.0f)
//      this.appliedDiscount = MutableLiveData("0.0")
//      this.oldDeferred = MutableLiveData(0.0f)
//      this.billDiscountPercentage = MutableLiveData("0")
//      this.collection = MutableLiveData("0")
//      this.toBeDeferredPayment = MutableLiveData("0")
//      this.billCash = MutableLiveData(0.0f)
//      this.requiredAmount = MutableLiveData(0.0f)
//      this.totalBill = MutableLiveData(0.0f)
//      this.discountValue = MutableLiveData(0.0f)
//      this.discount = MutableLiveData("0")
//      this.quantity = MutableLiveData("0")
//      this.returnWeight = MutableLiveData("0")
//      _bill.value = ArrayList()
//      _returns.value = ArrayList()
//      this.locationDetails = MutableLiveData<LocationDetails>()
//  }

  //fun textAsBitmap(textWidth: Int, textSize: Int, context: Context): Bitmap? {

  //    // Get text dimensions
  //    val textPaint = TextPaint(
  //        Paint.ANTI_ALIAS_FLAG
  //                or Paint.LINEAR_TEXT_FLAG
  //    )
  //    textPaint.style = Paint.Style.FILL_AND_STROKE
  //    val typeface = ResourcesCompat.getFont(context, R.font.kawkabregular)
  //    textPaint.typeface = typeface
  //    textPaint.color = Color.BLACK
  //    textPaint.textSize = textSize.toFloat()
  //    val mTextLayout = StaticLayout(
  //        msgToPrint, textPaint,
  //        textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
  //    )

  //    // Create bitmap and canvas to draw to
  //    val b = Bitmap.createBitmap(textWidth, mTextLayout.height, Bitmap.Config.RGB_565)
  //    val c = Canvas(b)

  //    // Draw background
  //    val paint = Paint(
  //        (Paint.ANTI_ALIAS_FLAG
  //                or Paint.LINEAR_TEXT_FLAG)
  //    )
  //    paint.style = Paint.Style.FILL
  //    paint.color = Color.WHITE
  //    paint.typeface = typeface
  //    c.drawPaint(paint)

////raw text
  //    c.save()
  //    c.translate(0f, 0f)
  //    mTextLayout.draw(c)
  //    c.restore()
  //    return b
  //}

}

fun <T> runApi(
    _apiStateFlow: MutableStateFlow<NetworkState>,
    block:Flow <T>
) {

    _apiStateFlow.value = NetworkState.Loading
    try {
        if (isInternetAvailable())
            CoroutineScope(Dispatchers.IO).launch {

                kotlin.runCatching {
                    block
                }.onFailure {

                    //  Log.e(TAG, "runApi: 3")
                    when (it) {
                        is java.net.UnknownHostException ->
                            _apiStateFlow.value =
                                NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
                        is java.net.ConnectException ->
                            _apiStateFlow.value =
                                NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
                        else -> _apiStateFlow.value =
                            NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
                    }

                }.onSuccess {

                    //  Log.e(TAG, "runApi: 4")
                    if (it != null)
                        _apiStateFlow.value = NetworkState.Result(it)
                    else {
                        //     Log.e(TAG, "runApi: ${it.errorBody()}")
                        _apiStateFlow.value = NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
                    }
                }

            }
        else
            _apiStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
    } catch (e: Exception) {
        // Log.e(TAG, "runApi: ${e.message}")
    }


}