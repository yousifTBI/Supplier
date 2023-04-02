package com.tbi.supplierplus.framework.ui.daily_closing

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tbi.supplierplus.framework.ui.daily_closing.expensesAdapter.ExpensesClosingFragment
import com.tbi.supplierplus.framework.ui.daily_closing.itemsReceived.ItemsReceivedFragment
import com.tbi.supplierplus.framework.ui.daily_closing.purchasesclosings.PurchasesClosingFragment
import com.tbi.supplierplus.framework.ui.daily_closing.summaryReport.SummeryReportKFragment
import com.tbi.supplierplus.framework.ui.daily_closing.supplierReturns.SupplierReturnsFragment

class ViewPagerAdabter(Containar: Fragment,val fragmentList:List<Fragment>) : FragmentStateAdapter(Containar) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        if (position === 0) {
            return    ItemsReceivedFragment()
            }
            if (position === 1) {
                return   ExpensesClosingFragment()
        }
       // if (position === 2) {
       //     return   SupplierReturnsFragment()
       // }
        if (position === 2) {
            return   PurchasesClosingFragment()
        }
        if (position === 3) {
            return  SummeryReportKFragment()
        }
        else
            return ExpensesClosingFragment()
    }

}