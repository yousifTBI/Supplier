package com.tbi.supplierplus.framework.ui.daily_closing

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.tbi.supplierplus.business.utils.LoadingDialog
import com.tbi.supplierplus.databinding.FragmentDailyClosingBinding
import com.tbi.supplierplus.framework.ui.daily_closing.expensesAdapter.ExpensesClosingFragment
import com.tbi.supplierplus.framework.ui.daily_closing.itemsReceived.ItemsReceivedFragment
import com.tbi.supplierplus.framework.ui.daily_closing.purchasesclosings.PurchasesClosingFragment
import com.tbi.supplierplus.framework.ui.daily_closing.supplierReturns.SupplierReturnsFragment
import com.tbi.supplierplus.framework.ui.reports.ReportsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DailyClosingFragment : Fragment() {

    val options = arrayOf("المنتجات المباعه", "المصاريف", "الموردين", "المشتريات")
    val fragment = listOf(
        ItemsReceivedFragment(),
        ExpensesClosingFragment(),
        PurchasesClosingFragment(),
        SupplierReturnsFragment()
    )
    private val viewModel2: ReportsViewModel by activityViewModels()
    private lateinit var binding: FragmentDailyClosingBinding
    private val viewModel: DailyClosingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDailyClosingBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        val pagerAdabter = ViewPagerAdabter(this, fragment)
        binding.ViewPager.adapter = pagerAdabter
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("المنتجات المباعه"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("المصاريف"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("المشتريات"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("تقارير"))

        val loading = LoadingDialog(this.requireActivity())
        viewModel.setUser()

        viewModel.setClosingDayLiveData.observe(viewLifecycleOwner) {

            loading.isDismiss()

            viewModel.   getDailyClosingSummaryItems()
            viewModel.    getData()
            viewModel.   getDailyClosingPurchases()
            viewModel2.getselesRepor()
            viewModel.    getData()
            //activity?.recreate()
            val pagerAdabter = ViewPagerAdabter(this, fragment)
            binding.ViewPager.adapter = pagerAdabter


//            val intent = activity!!.baseContext.packageManager.getLaunchIntentForPackage(
//                activity!!.baseContext.packageName
//            )
//
//            intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//            Process.killProcess(Process.myPid())
//            System.exit(0)
        }

        binding.closingDayButton.setOnClickListener {
            loading.startLoading()
            viewModel.closeTheDay()

        }

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.ViewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return binding.root

    }

    private fun initviewbager() {
        val pagerAdabter = ViewPagerAdabter(this, fragment)

        binding.ViewPager.adapter = pagerAdabter

        binding.ViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {


            }
        })

    }

}