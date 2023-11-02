package com.tbi.supplierplus.framework.ui.sales.editBranch

import android.provider.ContactsContract.RawContacts.Data
import java.util.Date

data class EditBranchDetailsModel(
    var Record_ID: Int,
    var BranchName: String,
    var ContactName: String,
    var Telephone1: String,
    var Telephone2: String,
    var Email: String,
    var Address: String,
    var Comment: String,
    var Editor: String,
    var Date: Date,
    var SalesContact: String,
    var Distributor_ID: Int,
    var Region_ID: Int,
    var comid: Int,
    var Longitude: Double,
    var Latitude: Double,
    var AddressUrl: String,
    var UserId: Int,
)
