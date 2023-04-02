package com.tbi.supplierplus.business.pojo.puarchase

import ir.mirrajabi.searchdialog.core.Searchable

data class ItemsPRCh(


     var ItemName      : String,
     var Item_ID       : Int  ,
     var Supply_Price  : Double,
     var Selling_Price : Double  ,
     var Supplier_ID   : Int

): Searchable {
     private val ItemID:String
          get() {
               return Item_ID.toString() }

     private val   SupplierID:String
          get() {
               return Supplier_ID.toString()
          }

     private val  SupplyPrice:String
          get() {
               return Supply_Price.toString()
          }

     private val  SellingPrice:String
          get() {
               return Selling_Price.toString()
          }
//////////////////////
     override fun getTitle(): String {
          return ItemName
     }

     fun getItem_ID ():String{
          return  ItemID
     }
     fun getSupply_Price():String{
          return  SupplyPrice
     }

     fun getSupplier_ID():String{
          return  SupplierID
     }
     fun getSelling_Price():String{
          return SellingPrice
     }
}
