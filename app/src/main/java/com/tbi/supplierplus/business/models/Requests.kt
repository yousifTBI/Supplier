package com.tbi.supplierplus.business.models

data class Requests(




val RecordID: Int,

val Item_ID: Long,

val SalesName: String,

val ItemName: String,

val Item_Count: Double,

val User_ID: Int,

val Edit_date: String,

val Supplier_ID: Int,
val dailyClosing: Any?,

val Storekeeper_confirm: Int,

val StorekeeperName: String,

val sales_confirm: Int,

val Storekeeper_id: Int,

val sales_id: Int,

val sales_confirm_date: String,

val Storekeeper_confirm_date: Any?,

val Settlement: Int,
val type: Int,

val RequstNo: Int,


)
