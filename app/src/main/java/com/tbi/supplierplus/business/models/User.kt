package com.tbi.supplierplus.business.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_tbl")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "userName")
    var userName: String,
    @ColumnInfo(name = "groupName")
    var groupName: String,
    @ColumnInfo(name = "distributor")
    var distributor: String,
    @ColumnInfo(name = "distributorID")
    var distributorID: String,
    @ColumnInfo(name = "massID")
    var massID: String,
    @ColumnInfo(name = "userID")
    var userID: String,
    @ColumnInfo(name = "printerName")
    var printerName: String,
    @ColumnInfo(name = "message")
    var message: String
) : Parcelable
