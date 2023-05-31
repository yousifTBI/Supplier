package com.tbi.supplierplus.framework.ui.daily_closing

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tbi.supplierplus.framework.ui.daily_closing.expensesAdapter.ExpensesClosingFragment
import com.tbi.supplierplus.framework.ui.daily_closing.itemsReceived.ItemsReceivedFragment
import com.tbi.supplierplus.framework.ui.daily_closing.purchasesclosings.PurchasesClosingFragment
import com.tbi.supplierplus.framework.ui.daily_closing.summaryReport.SummeryReportKFragment
import com.tbi.supplierplus.framework.ui.daily_closing.supplierReturns.SupplierReturnsFragment
import com.tbi.supplierplus.framework.ui.reports.SalesReportFragment

class ViewPagerAdabter(Containar: Fragment,val fragmentList:List<Fragment>) : FragmentStateAdapter(Containar) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
//        if (position === 0) {
//          //  return    ItemsReceivedFragment()
//            }
            if (position === 0) {
                return   ExpensesClosingFragment()
        }
       // if (position === 2) {
       //     return   SupplierReturnsFragment()
       // }
//        if (position === 1) {
//            return   PurchasesClosingFragment()
//        }
        if (position === 1) {
            return  SalesReportFragment()
        }
        else
            return ExpensesClosingFragment()
    }

}