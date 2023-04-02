package com.tbi.supplierplus.framework.datasource

data class SupplierResult<T, Boolean, E: Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null)