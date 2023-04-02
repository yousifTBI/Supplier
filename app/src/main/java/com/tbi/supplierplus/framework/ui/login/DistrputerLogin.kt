package com.tbi.supplierplus.framework.ui.login

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_tbi")
data class DistrputerLogin(
   @PrimaryKey
   @ColumnInfo(name = "UserName")
   var UserName :String,
   @ColumnInfo(name = "GroupName")
   var GroupName :String,
   @ColumnInfo(name = "PrintrName")
   var  PrintrName:String,
   @ColumnInfo(name = "Distributor")
   var  Distributor:String,
   @ColumnInfo(name = "Distributor_ID")
   var     Distributor_ID: Int,
   @ColumnInfo(name = "userID")
    var     userID : Int,
) : Parcelable
