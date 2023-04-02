package com.tbi.supplierplus

import java.util.regex.Pattern

class DialogBill (s:String){
    var name  :String = ""
    var group:String = ""
    var sur   :String= ""
    var size  :String = ""

    init {
        val delim = "-"


        val arr = Pattern.compile(delim).split(s)
        //  val list = str.split(delim)
        val name  =arr.get(0).toString()
        val group  =arr.get(1).toString()
        val sur    =arr.get(2).toString()
        val size   =arr.get(3).toString()
        this.name=name

       this. group=group
       this. sur=sur
       this. size=size
    }
    fun delim(string: String){
        val delim = "-"


        val arr = Pattern.compile(delim).split(string)
        //  val list = str.split(delim)
        val name=arr.get(0).toString()
        val group=arr.get(1).toString()
        val sur=arr.get(2).toString()
        val size=arr.get(3).toString()

    }
}