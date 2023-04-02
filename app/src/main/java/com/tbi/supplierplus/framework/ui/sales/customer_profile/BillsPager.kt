package com.tbi.supplierplus.framework.ui.sales.customer_profile

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class BillsPager(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            1 -> return BillFragment()
            0 -> return ReturnsFragment()
        }
        return Fragment()
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            1 -> return "الفاتورة"
            0 -> return "المرتجعات"
        }
        return ""
    }
}