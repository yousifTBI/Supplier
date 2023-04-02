package com.tbi.supplierplus.business.pojo.bills

import com.tbi.supplierplus.business.models.BillDetails
import com.tbi.supplierplus.business.pojo.billModels.SaleingBill

data class NewBill(


     var   CusID                        : String="0.0",
     var   PillDiscount                 : String="0.0",
     var   Editor                    : String="0.0",
     var   Sales_ID                      : String="0.0",
     var   TotalAmountBeforDiscount      : String="0.0",
     var   TotalAmountAfterDiscount   : String="0.0",
     var   Cash                                : String="0.0",
     var   Deferred                    : String="0.0",
     var   collection                  : String="0.0",
     var   ReturnAmount                : String="0.0",
     var   Bill_Details: ArrayList<SaleingBill>


)
