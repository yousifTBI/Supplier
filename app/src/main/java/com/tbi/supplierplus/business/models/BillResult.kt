package com.tbi.supplierplus.business.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BillResult(

    var message: String,
    var billNumber: String

) : Parcelable
