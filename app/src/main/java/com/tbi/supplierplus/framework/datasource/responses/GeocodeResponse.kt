package com.tbi.supplierplus.business.repository

data class GeocodeResponse(
    var status: String?, var results: List<GeocodeResult>?, var plus_code: GeocodePlusCode?
)

data class GeocodeResult(var formatted_address: String?, var place_id: String?)

data class GeocodePlusCode(var compound_code: String?, var global_code: String?)
