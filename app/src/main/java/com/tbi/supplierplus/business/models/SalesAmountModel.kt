package com.tbi.supplierplus.business.models

data class SalesAmountModel(

    var actual_amount: Double,
    var required_amount: Double,
    var Actual_MoneyReceiptPapers_count: Double,
    var Actual_MoneyReceiptPapers_Amount: Double,
    var Actual_DeferredMoneyPaper_count: Double,
    var Actual_DeferredMoneyPaper_Amount: Double,
    var payment_paper_count_required: Int,
    var payment_paper_amount: Double,
    var colection_paper_count_required: Double,
    var colection_paper_amount: Double

    )
