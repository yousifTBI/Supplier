package com.tbi.supplierplus.framework.ui.sales.customers.product_selection

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import com.tbi.supplierplus.BillActivity2
import com.tbi.supplierplus.R
import com.tbi.supplierplus.databinding.FragmentCustomerProfileBinding
import com.tbi.supplierplus.databinding.FragmentProductSelectionBinding
import com.tbi.supplierplus.framework.ui.sales.SalesViewModel
import com.tbi.supplierplus.testAdabters.AllCustomersAdapter
import com.tbi.supplierplus.testAdabters.OnClickListenerss

class ProductSelectionFragment : Fragment() {
    private lateinit var binding   :FragmentProductSelectionBinding
    val viewModel: SalesViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductSelectionBinding.inflate(inflater)
       // binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //  if (query.length > 1)
                //    adapte.filter.filter(query)
                //   adapte.notifyDataSetChanged()
                viewModel.filterCustomers(query)
                //   adapte.notifyDataSetChanged()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //  if (newText.length > 1)
                //       adapte.filter.filter(newText)


                viewModel.filterCustomers(newText)
                //  adapte.notifyDataSetChanged()
                return false
            }
        })

        val  adapte= AllCustomersAdapter(OnClickListenerss {
            //    viewModel.setCustomerID("2")


            val Customer_ID=it.Customer_ID.toString()
            val Unpaid_deferred=it.Unpaid_deferred.toString()
            val CompanyName=it.item.toString()
            // val x=it.Customer_ID.toString()
            // Toast.makeText(activity,it.Customer_ID.toString(),Toast.LENGTH_LONG).show()
            val intent = Intent(activity, ChangeSpecialPriceActivity::class.java)
                .apply {
                    putExtra("Customer_ID", Customer_ID)
                   // putExtra("Unpaid_deferred", Unpaid_deferred)
                    putExtra("CompanyName", CompanyName)
                    // putExtra("EXTRA_MESSAGE", s)
                    //    putExtra("EXTRA_MESSAGE", s)
                }
            startActivity(intent)
            //  findNavController().navigate(
            //     // CustomersFragmentDirections.actionCustomersFragmentToBillItemsFragment()
            //     CustomersFragmentDirections.actionCustomersFragmentToBillItems2Fragment(s)

            //    //  CustomersFragmentDirections.actionCustomersFragmentToProductSelectionFragment()
            //          //.actionCustomersFragmentToCustomerProfileFragment()
            //  )
            //  getActivity()?.getFragmentManager()?.popBackStack()

            //  getActivity()?.getFragmentManager().  popBackStack(R.id.customersFragment, true)
            // activity?.onBackPressed()
            // findNavController().navigate(
            //     CustomersFragmentDirections.actionCustomersFragmentToCustomerProfileFragment()
            // )

        })

        viewModel.getAllCustomers()
        viewModel.getAllCustomersLiveData.observe(viewLifecycleOwner){
            //   Toast.
            binding.recyclerView.adapter = adapte
            adapte.submitList(it)
        }
        // Inflate the layout for this fragment
     //   return inflater.inflate(R.layout.fragment_product_selection, container, false)
        return binding.root
    }


}