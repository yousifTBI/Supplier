package com.tbi.supplierplus.business.utils

import android.content.Context
import android.util.Log
import com.tbi.supplierplus.business.models.*
import com.tbi.supplierplus.business.pojo.AllCustomers
import com.tbi.supplierplus.business.pojo.Tasks
import com.tbi.supplierplus.business.repository.GeocodeResponse
import com.tbi.supplierplus.framework.datasource.requests.State
import com.tbi.supplierplus.framework.datasource.responses.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.InetAddress

fun LoginEnvelope.fromEnvelopeToModel() = User(
    userName = this.body!!.response!!.result!!.UserName,
    groupName = this.body!!.response!!.result!!.GroupName,
    distributor = this.body!!.response!!.result!!.Distributor,
    distributorID = this.body!!.response!!.result!!.Distributor_ID,
    massID = this.body!!.response!!.result!!.MassID,
    userID = this.body!!.response!!.result!!.UserId,
    printerName = this.body!!.response!!.result!!.PrintrName,
    message = this.body!!.response!!.result!!.Message,
)
fun isInternetAvailable(): Boolean {
    return try {
        val ipAddr: InetAddress = InetAddress.getByName("google.com")
        //You can replace it with your name
        !ipAddr.equals("")
    } catch (e: Exception) {
        false
    }
}

fun  wrapWithFlow(function: Deferred<Tasks<AllCustomers>>): Flow<State<Tasks<AllCustomers>>>{
    return flow {

        if (isInternetAvailable()){

            emit(State.Loading)
            try {
                val tas = function

              //  Log.d("ssssssssssssss", tas.State.toString() + "aaaaaaaaaa")
                emit(State.Success( tas.await()))
            } catch (ex: Exception) {
                emit(State.Error( ex.message.toString()))
                Log.d("ssssssssssssss", ex.message + "aaaaaaaaaa")

            }
        }else{
            emit(State.Error( "الاتصال ب الانترنت ضعيف"))
        }
    }

}
fun ItemsBySearchEnvelope.fromEnvelopeToModel(): List<Item> {
    val list = mutableListOf<Item>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        list.add(
            Item(
                name = body!!.response!!.result!!.itemName!!.names!![it],
                id = body!!.response!!.result!!.itemID!!.ids!![it]
            )
        )
    }
    return list
}

fun CustomerBySearchEnvelope.fromEnvelopeToModel(): List<Customer> {
    val list = mutableListOf<Customer>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        list.add(
            Customer(
                customerName = body!!.response!!.result!!.itemName!!.names!![it],
                customerID = body!!.response!!.result!!.itemID!!.ids!![it]
            )
        )
    }
    return list
}

fun CustomerInfoEnvelope.fromEnvelopeToModel(customerID: String): Customer =
    Customer(
        customerID = customerID,
        customerName = this.body!!.response!!.result!!.customerName,
        code = this.body!!.response!!.result!!.customerCode,
        deferred = this.body!!.response!!.result!!.deferred,
        rangeID = this.body!!.response!!.result!!.rangeID,
        region = this.body!!.response!!.result!!.region,
        regionID = this.body!!.response!!.result!!.regionID
    )

fun ItemInfoEnvelope.fromEnvelopeToModel(): Item = Item(
    id = this.body!!.response!!.result!!.itemID,
    name = this.body!!.response!!.result!!.itemName,
    categoryName = this.body!!.response!!.result!!.categoryName,
    groupID = this.body!!.response!!.result!!.itemGroupID,
    priceID = this.body!!.response!!.result!!.priceID,
    sellingPrice = this.body!!.response!!.result!!.itemSellingPrice,
    size = this.body!!.response!!.result!!.itemSize,
    supplierID = this.body!!.response!!.result!!.supplierID,
    supplierName = this.body!!.response!!.result!!.SupplierName,
    supplyPrice = this.body!!.response!!.result!!.itemSupplyPrice
)

fun AddPurchaseEnvelope.fromEnvelopeToModel(): List<PurchaseItem> {
    val list = mutableListOf<PurchaseItem>()
    Log.i("Message", this.body!!.response!!.result!!.message)
    repeat(this.body!!.response!!.result!!.itemName!!.names!!.size) {
        val split = this.body!!.response!!.result!!.itemName!!.names!![it].split("|")
        list.add(
            PurchaseItem(
                itemName = split[1],
                itemID = split[2],
                price = split[3],
                total = split[4],
                quantity = split[0],
                message = this.body!!.response!!.result!!.message
            )
        )
    }
    return list


}

fun PurchaseEnvelope.fromEnvelopeToModel(): List<PurchaseItem> {
    val list = mutableListOf<PurchaseItem>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        val split = body!!.response!!.result!!.itemName!!.names!![it].split("|")
        list.add(
            PurchaseItem(
                itemName = split[1],
                itemID = split[2],
                price = split[3],
                total = split[4],
                quantity = split[0],
            )
        )
    }
    return list
}

fun AddBillEnvelope.fromEnvelopeToModel(): BillResult =
    BillResult(
        message = this.body!!.response!!.result!!.message,
        billNumber = this.body!!.response!!.result!!.billNumber,
    )

fun SalesSummaryEnvelope.fromEnvelopeToModel(): SalesSummary =
    SalesSummary(
        message = this.body!!.response!!.result!!.message,
        returnAmount = this.body!!.response!!.result!!.returnAmount,
        collection = this.body!!.response!!.result!!.collection,
        expenses = this.body!!.response!!.result!!.expenses,
        totalBill = this.body!!.response!!.result!!.totalBill,
        totalBillCash = this.body!!.response!!.result!!.totalBillCash,
        totalBillAfterDiscount = this.body!!.response!!.result!!.totalBillAfterDiscount,
        totalBillBeforeDiscount = this.body!!.response!!.result!!.totalBillBeforeDiscount,
        totalBillDeferred = this.body!!.response!!.result!!.totalBillDeferred,
        totalBillDiscount = this.body!!.response!!.result!!.totalBillDiscount,
        totalBillNet = this.body!!.response!!.result!!.totalBillNet,
        totalItemsCount = this.body!!.response!!.result!!.totalItemsCount,
        totalItemsDiscount = this.body!!.response!!.result!!.totalItemsDiscount
    )

fun ItemsSummaryReportEnvelope.fromEnvelopeToModel(): List<ItemsSummary> {
    val list = mutableListOf<ItemsSummary>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        val split = body!!.response!!.result!!.itemName!!.names!![it].split(":")
        list.add(
            ItemsSummary(
                quantity = split[0],
                sales = split[1],
                credit = split[2],
                productName = split[3]
            )
        )
    }
    return list
}


fun ExpensesEnvelope.fromEnvelopeToModel(): List<Expenses> {
    val list = mutableListOf<Expenses>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        val split = body!!.response!!.result!!.itemName!!.names!![it].split(":")
        list.add(
            Expenses(
                name = split[2],
                id = split[0],
                quantity = split[1]
            )
        )
    }
    return list

}

fun ExpensesTypesEnvelope.fromEnvelopeToModel(): List<ExpensesType> {
    val list = mutableListOf<ExpensesType>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        list.add(
            ExpensesType(
                name = body!!.response!!.result!!.itemName!!.names!![it],
                id = body!!.response!!.result!!.itemID!!.ids!![it]
            )
        )
    }
    return list

}


fun DailyClosingExpensesEnvelope.fromEnvelopeToModel(): List<DailyClosing> {
    val list = mutableListOf<DailyClosing>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        list.add(
            DailyClosing(
                name = body!!.response!!.result!!.itemName!!.names!![it],
                id = ""
            )
        )
    }
    return list

}

fun DailyClosingSummaryItemsEnvelope.fromEnvelopeToModel(): List<DailyClosing> {
    val list = mutableListOf<DailyClosing>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        list.add(
            DailyClosing(
                name = body!!.response!!.result!!.itemName!!.names!![it],
                id = ""
            )
        )
    }
    return list

}


fun DailyClosingPurchasesEnvelope.fromEnvelopeToModel(): List<DailyClosing> {
    val list = mutableListOf<DailyClosing>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        list.add(
            DailyClosing(
                name = body!!.response!!.result!!.itemName!!.names!![it],
                id = ""
            )
        )
    }
    return list

}

fun DailyClosingSummarySupplierEnvelope.fromEnvelopeToModel(): List<DailyClosing> {
    val list = mutableListOf<DailyClosing>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        list.add(
            DailyClosing(
                name = body!!.response!!.result!!.itemName!!.names!![it],
                id = ""
            )
        )
    }
    return list

}


fun SetDailyClosingEnvelope.fromEnvelopeToModel(): SetDailyClosing =
    SetDailyClosing(
        message = this.body!!.response!!.result!!.Message,

        )


fun GetFillSpinnerReturnItemsEnvelope.fromEnvelopeToModel(): List<Item> {
    val list = mutableListOf<Item>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        list.add(
            Item(
                name = body!!.response!!.result!!.itemName!!.names!![it],
                id = body!!.response!!.result!!.itemId!!.ids!![it],
            )
        )
    }
    return list

}

fun AddExpensesEnvelope.fromEnvelopeToModel(): List<Expenses> {
    val list = mutableListOf<Expenses>()

    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        val split = body!!.response!!.result!!.itemName!!.names!![it].split(":")
        list.add(
            Expenses(
                name = split[2],
                id = split[0],
                quantity = split[1]
            )
        )
    }
    return list

}

fun GetReturnItemsSettelmentEnvelope.fromEnvelopeToModel(): List<ReturnItemsSettelment> {
    val list = mutableListOf<ReturnItemsSettelment>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        val split = body!!.response!!.result!!.itemName!!.names!![it].split(":")
        list.add(
            ReturnItemsSettelment(
                name = split[2],
                id = split[0],
                quantity = split[1]
            )
        )
    }
    return list
}

fun SetReturnItemsSettlementEnvelope.fromEnvelopeToModel(): SetReturnItemsSettlement =
    SetReturnItemsSettlement(
        message = this.body!!.response!!.result!!.message

    )

fun StatementReportEnvelope.fromEnvelopeToModel(): List<CustomerStatement> {
    val list = mutableListOf<CustomerStatement>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        val split = body!!.response!!.result!!.itemName!!.names!![it].split(":")
        list.add(
            CustomerStatement(
                split[0],
                split[1],
                split[2],
                split[3],
                split[4],
                split[5],
                split[6],
                split[7],
                split[8],
                split[9]
            )
        )

    }
    return list
}

fun GeocodeResponse.toModel(latitude: Double, longitude: Double) =
    LocationDetails(
        latitude = latitude,
        longitude = longitude,
        address = this.results!![0].formatted_address!!,
        compoundCode = this.plus_code!!.compound_code!!,
        placeID = this.results!![0].place_id!!,
        globalCode = this.plus_code!!.global_code!!
    )

fun BillDetailsEnvelope.fromEnvelopeToModel(): BillDetails {
    val billList = mutableListOf<Item>()
    repeat(body!!.response!!.result!!.bill!!.names!!.size) {
        val split = body!!.response!!.result!!.bill!!.names!![it].split(":")
        billList.add(
            Item(
                id = "",
                name = split[0],
                quantity = split[1],
                sellingPrice = split[2],
                total = split[3],
                size = split[4],
                discount = split[5]
            )
        )
    }

    val returnsList = mutableListOf<Item>()
    repeat(body!!.response!!.result!!.returns!!.names!!.size) {
        val split = body!!.response!!.result!!.returns!!.names!![it].split(":")
        returnsList.add(
            Item(
                size = split[0],
                quantity = split[1],
                name = split[2],
                id = ""
            )
        )
    }
    return BillDetails(bill = billList, returns = returnsList)
}

fun CustomerDebitsEnvelope.fromEnvelopeToModel(): List<CustomerDebits> {
    val list = mutableListOf<CustomerDebits>()
    repeat(body!!.response!!.result!!.debits!!.names!!.size) {
        val split = body!!.response!!.result!!.debits!!.names!![it].split(":")
        list.add(
            CustomerDebits(
                amount = split[0],
                customerName = split[1],
                customerID = split[2],
                region = split[3]
            )
        )
    }

    return list
}

fun OpeningBalanceEnvelope.fromEnvelopeToModel(): List<CustomerDebits> {
    val list = mutableListOf<CustomerDebits>()
    repeat(body!!.response!!.result!!.balances!!.names!!.size) {
        val split = body!!.response!!.result!!.balances!!.names!![it].split(":")
        list.add(
            CustomerDebits(
                amount = split[0],
                customerName = split[1],
                customerID = split[2],
                region = split[3]
            )
        )
    }

    return list
}

fun AddOpeningBalanceEnvelope.fromEnvelopeToModel(): String =
    this.body!!.response!!.result!!.Message

fun AddCollectionEnvelope.fromEnvelopeToModel(): String =
    this.body!!.response!!.result!!.message

fun RegionEnvelope.fromEnvelopeToModel(): List<Region> {
    val list = mutableListOf<Region>()
    repeat(body!!.response!!.result!!.regions!!.names!!.size) {
        val split = body!!.response!!.result!!.regions!!.names!![it].split(":")
        list.add(
            Region(
                id = split[0],
                name = split[1]
            )
        )
    }

    return list
}

fun AddCustomerEnvelope.fromEnvelopeToModel(): List<Customer> {
    val list = mutableListOf<Customer>()
    repeat(body!!.response!!.result!!.customers!!.names!!.size) {
        val split = body!!.response!!.result!!.customers!!.names!![it].split(":")
        list.add(
            Customer(
                customerID = split[0],
                customerName = split[1],
                message = body!!.response!!.result!!.Message
            )
        )
    }
    return list
}

fun GetItemsSettlementEnvelope.fromEnvelopeToModel(): List<GetItemsSettlement> {
    val list = mutableListOf<GetItemsSettlement>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        val split = body!!.response!!.result!!.itemName!!.names!![it].split(":")
        list.add(
            GetItemsSettlement(
                itemId = split[0],
                itemName = split[1],
                purchases = split[2],
                balance = split[3],
                salesUnit = split[4],
                unitPrice = split[5],
                salesPriceTotal = split[6],
                returnAmount = split[7],
                returnSize = split[8],

                )
        )
    }
    return list


}

fun GetItemsVsBillEnvelope.fromEnvelopeToModel():List<ItemsVsBill>{
    val list = mutableListOf<ItemsVsBill>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        list.add(
            ItemsVsBill(
                itemName =body!!.response!!.result!!.itemName!!.names!![it],
                message = this.body!!.response!!.result!!.message
            )
        )
    }
    return list
}
fun SetItemsSettlementEnvelope.fromEnvelopeToModel(): SetReturnItemsSettlement =
    SetReturnItemsSettlement(
        message = this.body!!.response!!.result!!.message

    )

fun AddItemsReturnEnvelope.fromEnvelopeToModel():List<AddItemsReturn>{
    val list = mutableListOf<AddItemsReturn>()
    repeat(body!!.response!!.result!!.itemName!!.names!!.size) {
        list.add(
            AddItemsReturn(
                itemName =body!!.response!!.result!!.itemName!!.names!![it],
                message = this.body!!.response!!.result!!.message
            )
        )
    }
    return list



}


 fun showHelperDialog(
    msg: String,
    mContext: Context,
    mDialogsListener: DialogsListener?
) {

       //Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
         mDialogsListener?.onDismiss()
}
