package com.tbi.supplierplus.business.pojo

import ir.mirrajabi.searchdialog.core.Searchable

data class Items(

  val    item :String ,
  val    Item_ID :Int,
  val    Price_ID     :Int ,
  val   size    :Double ,
  val  Supplier_ID    :Int ,
  val  ItemGroup_ID   :Int ,
  val Supply_Price: Double,
  val CustomerSellingPrice:Double

): Searchable

{
   private val a:String
  get() {
  return Item_ID.toString() }

    private val    Price_ID1:String
        get() {
            return Price_ID.toString()
        }



    private val   Supplier_ID1:String
        get() {
            return Supplier_ID.toString()
        }

    private val  ItemGroup_ID1:String
        get() {
            return ItemGroup_ID.toString()
        }

    private val  Supply_Price1:String
        get() {
            return Supply_Price.toString()
        }
    private val  CustomerSellingPrice1:String
        get() {
            return CustomerSellingPrice.toString()
        }

    override fun getTitle(): String {
        return item
    }
    fun getID():String{
        return a
    }

    fun getPrice_ID():String{
        return  Price_ID1
    }
    fun getSupplier_ID():String{
        return Supplier_ID1
    }
    fun getItemGroup_ID():String{
        return ItemGroup_ID1
    }
    fun getCustomerSellingPrice():String{
        return CustomerSellingPrice1
    }
    fun getSupply_Price():String{
        return Supply_Price1
    }

    fun getsize():String{
        return size.toString()
    }
}
