package com.tbi.supplierplus.framework.datasource.requests

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities

fun getRegisterRequestBody(androidID: String, serialID: String, userName: String): RequestBody {
    return getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <Registration_Request xmlns=\"http://tempuri.org/\">\n" +
                "        <android_id>$androidID</android_id>\n" +
                "        <Serial_No>$serialID</Serial_No>\n" +
                "        <userName>$userName</userName>\n" +
                "    </Registration_Request>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>"
    )
}

fun getLoginRequestBody(androidID: String, serialID: String): RequestBody {
    return getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <GetLogin_By_Mac_ID xmlns=\"http://tempuri.org/\">\n" +
                "      <android_id>$androidID</android_id>\n" +
                "      <Serial_No>$serialID</Serial_No>\n" +
                "    </GetLogin_By_Mac_ID>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>"
    )
}

fun getCustomerBySearchBody(userID: String): RequestBody {
    return getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <GetCusBySearch xmlns=\"http://tempuri.org/\">\n" +
                "      <userID>$userID</userID>\n" +
                "    </GetCusBySearch>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>"
    )
}

fun getItemsBySearchRequestBody(userID: String): RequestBody = getRequestBody(
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <GetItemBySearch xmlns=\"http://tempuri.org/\">\n" +
            "      <userID>$userID</userID>\n" +
            "    </GetItemBySearch>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>"
)

fun getAddCollectionRequestBody(
    userID: String,
    amount: String,
    customerID: String,
    remainingAmount: String
): RequestBody = getRequestBody(
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <AddCollection xmlns=\"http://tempuri.org/\">\n" +
            "      <UserID>$userID</UserID>\n" +
            "      <Amount>$amount</Amount>\n" +
            "      <Cus_id>$customerID</Cus_id>\n" +
            "      <RemainingAmount>$remainingAmount</RemainingAmount>\n" +
            "    </AddCollection>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>"
)

fun getCustomerInfoRequestBody(userID: String, customerID: String): RequestBody = getRequestBody(
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <GetCus xmlns=\"http://tempuri.org/\">\n" +
            "      <CustomerID>$customerID</CustomerID>\n" +
            "      <userid>$userID</userid>\n" +
            "    </GetCus>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>"
)

fun getExpensesRequestBody(userID: String): RequestBody = getRequestBody(
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <GetExpenses xmlns=\"http://tempuri.org/\">\n" +
            "      <UserID>$userID</UserID>\n" +
            "    </GetExpenses>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>"
)

fun getExpensesTypesRequestBody(userID: String): RequestBody = getRequestBody(
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <GetFillSpinner xmlns=\"http://tempuri.org/\">\n" +
            "      <SpinnerID>Expenses</SpinnerID>\n" +
            "      <Pramter1>$userID</Pramter1>\n" +
            "      <Pramter2></Pramter2>\n" +
            "      <Pramter3></Pramter3>\n" +
            "    </GetFillSpinner>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>"
)

fun addExpensesRequestBody(
    userID: String,
    amount: String,
    comment: String,
    expenseId: String
): RequestBody = getRequestBody(
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <AddExpenses xmlns=\"http://tempuri.org/\">\n" +
            "      <UserID>$userID</UserID>\n" +
            "      <Amount>$amount</Amount>\n" +
            "      <comment>$comment</comment>\n" +
            "      <ExpenseID>$expenseId</ExpenseID>\n" +
            "    </AddExpenses>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>"
)


fun getDailyClosingExpensesRequestBody(userID: String): RequestBody = getRequestBody(
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <GetClosingDay_Summry_Expenses xmlns=\"http://tempuri.org/\">\n" +
            "      <User_ID>$userID</User_ID>\n" +
            "    </GetClosingDay_Summry_Expenses>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>"
)

fun getDailyClosingSummaryItemsRequestBody(userID: String): RequestBody = getRequestBody(
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <GetClosingDay_Summry_items xmlns=\"http://tempuri.org/\">\n" +
            "      <User_ID>$userID</User_ID>\n" +
            "    </GetClosingDay_Summry_items>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>"
)

fun getDailyClosingPurchasesRequestBody(userID: String): RequestBody = getRequestBody(
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <GetPurchases xmlns=\"http://tempuri.org/\">\n" +
            "      <User_ID>$userID</User_ID>\n" +
            "    </GetPurchases>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>"
)

fun getDailyClosingSummarySupplierRequestBody(userID: String): RequestBody = getRequestBody(
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <GetClosingDay_Summry_Supplier xmlns=\"http://tempuri.org/\">\n" +
            "      <User_ID>$userID</User_ID>\n" +
            "    </GetClosingDay_Summry_Supplier>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>"
)


fun setDailyClosingRequestBody(userID: String): RequestBody = getRequestBody(
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <SetClosingDay xmlns=\"http://tempuri.org/\">\n" +
            "      <User_ID>$userID</User_ID>\n" +
            "    </SetClosingDay>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>"
)

fun getItemInfoByNameRequestBody(
    userID: String,
    customerID: String,
    itemName: String
): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <GetItemByName xmlns=\"http://tempuri.org/\">\n" +
                "      <sales_Id>$userID</sales_Id>\n" +
                "      <Item_Name>$itemName</Item_Name>\n" +
                "      <Cus_id>$customerID</Cus_id>\n" +
                "    </GetItemByName>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )


fun getPurchaseRequestBody(userID: String): RequestBody = getRequestBody(
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <GetPurchases xmlns=\"http://tempuri.org/\">\n" +
            "      <User_ID>$userID</User_ID>\n" +
            "    </GetPurchases>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>"
)

fun addPurchaseRequestBody(
    userID: String,
    count: Int,
    itemID: String,
    supplierID: String
): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <Purchases xmlns=\"http://tempuri.org/\">\n" +
                "      <Item_ID>$itemID</Item_ID>\n" +
                "      <Item_Count>$count</Item_Count>\n" +
                "      <User_ID>$userID</User_ID>\n" +
                "      <Supplier_ID>$supplierID</Supplier_ID>\n" +
                "    </Purchases>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )


fun getSalesSummaryRequestBody(
    userID: String

): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <GetselesSummry xmlns=\"http://tempuri.org/\">\n" +
                "      <Userid>$userID</Userid>\n" +
                "      <Password></Password>\n" +
                "    </GetselesSummry>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )

fun getItemsSummaryRequestBody(userID: String): RequestBody {

    return getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <GetItemsSummryReport xmlns=\"http://tempuri.org/\">\n" +
                "      <User_ID>$userID</User_ID>\n" +
                "    </GetItemsSummryReport>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )

}
fun getInfoItemByIDRequestBody(
    userID: String,
    customerID: String,
    itemID: String
): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <GetItemByBarcode xmlns=\"http://tempuri.org/\">\n" +
                "      <sales_Id>$userID</sales_Id>\n" +
                "      <Item_ID>$itemID</Item_ID>\n" +
                "      <Cus_id>$customerID</Cus_id>\n" +
                "    </GetItemByBarcode>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )


fun addBillRequestBody(
    billDetails: String,
    customerID: String,
    billDiscount: String,
    editor: String,
    salesID: String,
    totalAmountBeforeDiscount: String,
    totalAmountAfterDiscount: String,
    cash: String,
    deferred: String,
    returnAmount: String,
    oldDeferred: String,
    billReturn: String

): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <setpill xmlns=\"http://tempuri.org/\">\n" +
                "      <pil_Details>$billDetails</pil_Details>\n" +
                "      <CusID>$customerID</CusID>\n" +
                "      <PillDiscount>$billDiscount</PillDiscount>\n" +
                "      <Editor>$editor</Editor>\n" +
                "      <Sales_ID>$salesID</Sales_ID>\n" +
                "      <TotalAmountBeforDiscount>$totalAmountBeforeDiscount</TotalAmountBeforDiscount>\n" +
                "      <TotalAmountAfterDiscount>$totalAmountAfterDiscount</TotalAmountAfterDiscount>\n" +
                "      <Cash>$cash</Cash>\n" +
                "      <Deferred>$deferred</Deferred>\n" +
                "      <collection>$oldDeferred</collection>\n" +
                "      <ReturnAmount>$returnAmount</ReturnAmount>\n" +
                "      <PillReturn>$billReturn</PillReturn>\n" +
                "    </setpill>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )


fun getFillSpinnerReturnItemsRequestBody(userID: String, spinnerId: String): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <GetFillSpinner xmlns=\"http://tempuri.org/\">\n" +
                "      <SpinnerID>$spinnerId</SpinnerID>\n" +
                "      <Pramter1>$userID</Pramter1>\n" +
                "      <Pramter2></Pramter2>\n" +
                "      <Pramter3></Pramter3>\n" +
                "    </GetFillSpinner>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )

fun getReturnItemsSettelmentRequestBody(userID: String, supplierId: String): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <Get_Return_items_settelment xmlns=\"http://tempuri.org/\">\n" +
                "      <user_id>$userID</user_id>\n" +
                "      <supplier_id>$supplierId</supplier_id>\n" +
                "    </Get_Return_items_settelment>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )

fun setItemsSettlementRequestBody(
    userID: String,
    supplierId: String,
    itemId: String,
    itemCount: String
): RequestBody = getRequestBody(
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <Set_items_settelment xmlns=\"http://tempuri.org/\">\n" +
            "      <Item_ID>$itemId</Item_ID>\n" +
            "      <Item_Count>$itemCount</Item_Count>\n" +
            "      <User_ID>$userID</User_ID>\n" +
            "      <Supplier_ID>$supplierId</Supplier_ID>\n" +
            "    </Set_items_settelment>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>"
)

fun getCustomerStatementRequestBody(customerID: String): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <Get_Customer_Statement xmlns=\"http://tempuri.org/\">\n" +
                "      <Cus_ID>$customerID</Cus_ID>\n" +
                "    </Get_Customer_Statement>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )


fun getCustomerDebitsRequestBody(distributorID: String): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <Get_AllDebts xmlns=\"http://tempuri.org/\">\n" +
                "      <Distributor_ID>3</Distributor_ID>\n" +
                "    </Get_AllDebts>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )


fun getBillDetailsRequestBody(billNo: String, customerID: String): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <Get_Pill_Details xmlns=\"http://tempuri.org/\">\n" +
                "      <PillNo>$billNo</PillNo>\n" +
                "      <Cusid>$customerID</Cusid>\n" +
                "    </Get_Pill_Details>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )

fun getOpeningBalanceRequestBody(distributorID: String): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <GetAllcustomersOpeningBalance xmlns=\"http://tempuri.org/\">\n" +
                "      <distributor_id>3</distributor_id>\n" +
                "    </GetAllcustomersOpeningBalance>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )

fun getAddBalanceRequestBody(userID: String, amount: String, customerID: String): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <AddOpeningBalance xmlns=\"http://tempuri.org/\">\n" +
                "      <UserID>$userID</UserID>\n" +
                "      <Amount>$amount</Amount>\n" +
                "      <Cus_id>$customerID</Cus_id>\n" +
                "    </AddOpeningBalance>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )

fun getRegionsRequestBody(userID: String): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <Get_Region xmlns=\"http://tempuri.org/\">\n" +
                "      <UserID>$userID</UserID>\n" +
                "      <Range_Id>1</Range_Id>\n" +
                "    </Get_Region>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )

fun getAddCustomerRequestBody(
    userID: String,
    companyName: String,
    contactName: String,
    telephone1: String,
    telephone2: String,
    email: String,
    address: String,
    regionID: String,
    distributorID: String,
): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <AddCustomer xmlns=\"http://tempuri.org/\">\n" +
                "      <CompanyName>$companyName</CompanyName>\n" +
                "      <ContactName>$contactName</ContactName>\n" +
                "      <Telephone1>$telephone1</Telephone1>\n" +
                "      <Telephone2>$telephone2</Telephone2>\n" +
                "      <Email>$email</Email>\n" +
                "      <Address>$address</Address>\n" +
                "      <UserID>$userID</UserID>\n" +
                "      <Distributor_ID>$distributorID</Distributor_ID>\n" +
                "      <Region_ID>$regionID</Region_ID>\n" +
                "    </AddCustomer>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )

fun setItemsReturnsSettlementRequestBody(
    userID: String,
    supplierId: String,
    itemId: String,
    itemCount: String
): RequestBody = getRequestBody(
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <Set_items_settelment xmlns=\"http://tempuri.org/\">\n" +
            "      <Item_ID>$itemId</Item_ID>\n" +
            "      <Item_Count>$itemCount</Item_Count>\n" +
            "      <User_ID>$userID</User_ID>\n" +
            "      <Supplier_ID>$supplierId</Supplier_ID>\n" +
            "    </Set_items_settelment>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>"
)


fun getItemsSettlementRequestBody(userID: String, supplierId: String): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <Get_items_settelment xmlns=\"http://tempuri.org/\">\n" +
                "      <user_id>$userID</user_id>\n" +
                "      <supplier_id>$supplierId</supplier_id>\n" +
                "    </Get_items_settelment>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )


fun getItemsVsBillRequestBody(userID: String, itemId: String): RequestBody =
    getRequestBody(
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <Get_itemsVS_Bill xmlns=\"http://tempuri.org/\">\n" +
                "      <User_ID>$userID</User_ID>\n" +
                "      <Item_id>$itemId</Item_id>\n" +
                "    </Get_itemsVS_Bill>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
    )

fun addItemsReturnRequestBody(
    userID: String,
    amount: String,
    size: String,
    customerID: String,
    itemId: String
): RequestBody = getRequestBody(
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <soap:Body>\n" +
            "    <AddItemsReturn xmlns=\"http://tempuri.org/\">\n" +
            "      <UserID>$userID</UserID>\n" +
            "      <Amount>$amount</Amount>\n" +
            "      <size>$size</size>\n" +
            "      <Cus_id>$customerID</Cus_id>\n" +
            "      <item_id>$itemId</item_id>\n" +
            "    </AddItemsReturn>\n" +
            "  </soap:Body>\n" +
            "</soap:Envelope>"
)

private fun getRequestBody(body: String) =
    body.toRequestBody("text/xml".toMediaTypeOrNull())

