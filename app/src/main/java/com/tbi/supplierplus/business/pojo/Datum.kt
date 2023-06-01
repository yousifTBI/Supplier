package com.tbi.supplierplus.business.pojo

data class Datum(



    val RecordID: Int,


    val Item_ID: Int,


    val SalesName: String,


    var ItemName: String,
    var Itemname: String,


    val Item_Count: Double,


    val User_ID: Int,


    val Edit_date: String,


    val _Edit_date: String,


    val Supplier_ID: Int,

    val dailyClosing: Any? = null,


    val Storekeeper_confirm: Int,


    val StorekeeperName: String,


    val sales_confirm: Int,


    val Storekeeper_id: Any? = null,


    val sales_id: Int,


    val sales_confirm_date: Any? = null,


    val Storekeeper_confirm_date: Any? = null,


    val Settlement: Int,

    val type: Int,


    val RequstNo: Any? = null
)
