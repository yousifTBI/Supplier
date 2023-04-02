package com.tbi.supplierplus.framework.ui.login

import android.R
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "LoginModel", indices = [Index(value = ["comid"], unique = true)])


data class LoginModel(

    @PrimaryKey(autoGenerate = true)
    var comid: Int,


    @ColumnInfo(defaultValue = "")
    var branchID: Int,

    @ColumnInfo(defaultValue = "")
    var POS_id: Int,

    @ColumnInfo(defaultValue = "")
    var Name: String,

    @ColumnInfo(defaultValue = "")
    var tax_id: Int,

    @ColumnInfo(defaultValue = "")
    var type: String,

    @ColumnInfo(defaultValue = "")
    var taxpayerActivityCode: String,

    @ColumnInfo(defaultValue = "")
    var BranchName: String,

    @ColumnInfo(defaultValue = "")
    var country: String,
    @ColumnInfo(defaultValue = "")
    var governate: String,

    @ColumnInfo(defaultValue = "")
    var regionCity: String,

    @ColumnInfo(defaultValue = "")
    var street: String,

    @ColumnInfo(defaultValue = "")
    var buildingNumber: String,

    @ColumnInfo(defaultValue = "")
    var postalCode: String,

    @ColumnInfo(defaultValue = "")
    var LicenseExpiryDate: String,

    @ColumnInfo(defaultValue = "")
    var POSName: String,

    @ColumnInfo(defaultValue = "")
    var posserial: String,

    @ColumnInfo(defaultValue = "")
    var clintId: String,

    @ColumnInfo(defaultValue = "")
    var scId: String,

    @ColumnInfo(defaultValue = "")
    var pososversion: String,

    @ColumnInfo(defaultValue = "")
    var ENVIRONMENT: String,

    @ColumnInfo(defaultValue = "")
    var AndroidID: String,

    @ColumnInfo(defaultValue = "")
    var EnvironmentStatues: String,

    @ColumnInfo(defaultValue = "")
    var TypeVersion: String,

    @ColumnInfo(defaultValue = "")
    var SendToEtaWay: String,

    @ColumnInfo(defaultValue = "")
    var SendWayName: String,

    @ColumnInfo(defaultValue = "")
    var ItemFlag: String,

    @ColumnInfo(defaultValue = "")
    var ItemFlagName: String,

    @ColumnInfo(defaultValue = "")
    var PriceFlag: String,

    @ColumnInfo(defaultValue = "")
    var PriceFlagName: String

    )



