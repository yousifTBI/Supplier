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
import com.tbi.supplierplus.databinding.FragmentDailyClosingBinding
import com.tbi.supplierplus.framework.ui.daily_closing.expensesAdapter.ExpensesClosingFragment
import com.tbi.supplierplus.framework.ui.daily_closing.itemsReceived.ItemsReceivedFragment
import com.tbi.supplierplus.framework.ui.daily_closing.purchasesclosings.PurchasesClosingFragment
import com.tbi.supplierplus.framework.ui.daily_closing.supplierReturns.SupplierReturnsFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DailyClosingFragment : Fragment() {

    val options = arrayOf("المنتجات المباعه", "المصاريف", "الموردين", "المشتريات")
    val fragment = listOf(
        ItemsReceivedFragment(), ExpensesClosingFragment()
        , PurchasesClosingFragment(), SupplierReturnsFragment()
    )
    private lateinit var binding: FragmentDailyClosingBinding
    private val viewModel: DailyClosingViewModel by  activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDailyClosingBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

      //  viewModel.   getDailyClosingPurchases()
        val pagerAdabter=ViewPagerAdabter(this,fragment)
        binding.ViewPager.adapter=pagerAdabter
        binding.tabLayout  .addTab(binding.tabLayout.newTab().setText("المنتجات المباعه"))
        binding.tabLayout  .addTab(binding.tabLayout.newTab().setText("المصاريف"))
       // binding.tabLayout  .addTab(binding.tabLayout.newTab().setText("الموردين"))
        binding.tabLayout  .addTab(binding.tabLayout.newTab().setText("المشتريات"))
        binding.tabLayout  .addTab(binding.tabLayout.newTab().setText("تقارير"))
      //  tablayout.addTab(binding.tabLayout.newTab().setText("dd"))
       // tablayout.a

viewModel.setUser()
       // initviewbager()
        viewModel.setClosingDayLiveData.observe(viewLifecycleOwner){
            val intent = activity!!.baseContext.packageManager.getLaunchIntentForPackage(
                activity!!.baseContext.packageName
            )
            intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Process.killProcess(Process.myPid())
            System.exit(0)
        }

     //  TabLayoutMediator(binding.tabLayout,binding.ViewPager) { tab, position ->
     // //     tab.text = "OBJECT ${(position + 1)}"
     //      tab.text = options[position]
     //  }.attach()


        ///////////////////////////
  //      binding.dailyClosingRecyclerView.adapter = DailyClosingAdapter()
//
  //      viewModel.setUser()
//
//
  //      val arrayAdapter =
  //          ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, options)
  //      binding.dailyClosingSpinner.adapter = arrayAdapter
  //      binding.dailyClosingSpinner.onItemSelectedListener =
  //          object : AdapterView.OnItemSelectedListener,
  //              AdapterView.OnItemClickListener {
  //              override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
  //                  if (options[p2] == "المنتجات المباعه") {
  //                      val adapter = DailyClosingAdapter()
  //                      viewModel.itemsDailyClosingLiveData.observe(viewLifecycleOwner) {
  //                          Log.i("ReturnsSize", it!!.size.toString())
  //                          binding.dailyClosingRecyclerView.adapter = adapter
  //                          binding.dailyClosingRecyclerView.setHasFixedSize(true)
  //                          adapter.submitList(it)
  //                      }
//
  //                  }else
  //                  if (options[p2] == "المصاريف") {
  //                      viewModel.getData()
  //                      val adapter = DailyClosingAdapter()
  //                      viewModel.closingExpensesLiveData.observe(viewLifecycleOwner) {
  //                          Log.i("ReturnsSize", it!!.size.toString())
  //                          binding.dailyClosingRecyclerView.adapter = adapter
  //                          binding.dailyClosingRecyclerView.setHasFixedSize(true)
  //                          adapter.submitList(it)
  //                      }
  //               //   } else if (options[p2] == "المنتجات المباعه") {
  //               //       val adapter = DailyClosingAdapter()
  //               //       viewModel.itemsDailyClosingLiveData.observe(viewLifecycleOwner) {
  //               //           Log.i("ReturnsSize", it!!.size.toString())
  //               //           binding.dailyClosingRecyclerView.adapter = adapter
  //               //           binding.dailyClosingRecyclerView.setHasFixedSize(true)
  //               //           adapter.submitList(it)
  //               //       }
////
  //                  } else if (options[p2] == "الموردين") {
  //                      val adapter = DailyClosingAdapter()
  //                      viewModel.summarySupplierLiveData.observe(viewLifecycleOwner) {
  //                          Log.i("ReturnsSize", it!!.size.toString())
  //                          binding.dailyClosingRecyclerView.adapter = adapter
  //                          binding.dailyClosingRecyclerView.setHasFixedSize(true)
  //                          adapter.submitList(it)
  //                      }
//
  //                  } else if (options[p2] == "المشتريات") {
  //                      val adapter = DailyClosingAdapter()
  //                      viewModel.purchasesLiveData.observe(viewLifecycleOwner) {
  //                          Log.i("ReturnsSize", it!!.size.toString())
  //                          binding.dailyClosingRecyclerView.adapter = adapter
  //                          binding.dailyClosingRecyclerView.setHasFixedSize(true)
  //                          adapter.submitList(it)
  //                      }
//
  //                  }
//
  //              }
//
  //              override fun onNothingSelected(p0: AdapterView<*>?) {
  //                  TODO("Not yet implemented")
  //              }
//
  //              override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
  //                  TODO("Not yet implemented")
  //              }
//
//
  //          }
//
//
//
//
/*//
  //      val supplieradapter = DailyClosingAdapter()
  //      viewModel.summarySupplierLiveData.observe(viewLifecycleOwner) {
  //          Log.i("ReturnsSize", it!!.size.toString())
  //          binding.dailyClosingRecyclerView.adapter = supplieradapter
  //          binding.dailyClosingRecyclerView.setHasFixedSize(true)
  //          supplieradapter.submitList(it)
  //      }
//
//
//
 *///
//
//
  //      viewModel.setClosingDayLiveData.observe(viewLifecycleOwner) {
  //         // Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
  //          val supplieradapter = DailyClosingAdapter()
  //          viewModel.setClosingDayLiveData.observe(viewLifecycleOwner) {
  //              binding.dailyClosingRecyclerView.adapter = supplieradapter
  //              binding.dailyClosingRecyclerView.setHasFixedSize(true)
  //          }
  //      }
       // tablayout.addTab(binding.tabLayout.newTab().setText("d"))
       // tablayout.addTab(binding.tabLayout.newTab().setText("dd"))
//
       // tablayout.addOnTabSelectedListener(object )
       // tablayout
       binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
           override fun onTabSelected(tab: TabLayout.Tab) {
              binding.ViewPager.setCurrentItem(tab.position)
           }

           override fun onTabUnselected(tab: TabLayout.Tab) {}
           override fun onTabReselected(tab: TabLayout.Tab) {}
       })
        //tabLayout.addTab(tabLayout.newTab().setText("Bar"));
        //tabLayout.addTab(tabLayout.newTab().setText("Foo"));
        return binding.root

    }

    private fun initviewbager() {
        val pagerAdabter=ViewPagerAdabter(this,fragment)
       // binding.ViewPager.adapter= pagerAdabter
        binding.ViewPager.adapter= pagerAdabter

        binding.ViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
            //    if (position == 0) {
            //        // you are on the first page
            //        binding.tabLayout.getTabAt(position)
            //    }
            //    else if (position == 1) {
            //        binding.tabLayout.getTabAt(position)
            //        // you are on the second page
            //    }
            //    else if (position == 2){
            //        binding.tabLayout.getTabAt(position)
            //        // you are on the third page
            //    }
            //    super.onPageSelected(position)
               // binding.tabLayout
               //     .selectTab(  binding.tabLayout.getTabAt(position))

            }
        })
          //  binding.tabLayout.setupWithViewPager(binding.ViewPager)
        //tablayout.setupWithViewPager(binding.viewPager)
    }

}