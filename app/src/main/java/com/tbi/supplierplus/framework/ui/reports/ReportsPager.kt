package com.tbi.supplierplus.framework.ui.reports

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.tbi.supplierplus.business.models.ItemsSummary

class ReportsPager(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return ItemsReportFragment()
            1 -> return SalesReportFragment()
            2 -> return CustomerStatementFragment()

        }
        return Fragment()
    }

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "اصناف"
            1 -> return "يومية"
            2 -> return "كشف الحساب"
        }
        return ""
    }
}