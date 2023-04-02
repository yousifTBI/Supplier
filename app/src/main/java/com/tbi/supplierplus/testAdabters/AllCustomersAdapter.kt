package com.tbi.supplierplus.testAdabters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.AllCustomers
import com.tbi.supplierplus.databinding.AllcustomersRowBinding

//shift  f6
class AllCustomersAdapter (val onClickListener: OnClickListenerss) :
    ListAdapter<AllCustomers, CustomersViewHolder>(DiffCallback){

    var photosList: ArrayList<AllCustomers> = ArrayList()
    var photosListFiltered: ArrayList<AllCustomers> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomersViewHolder {
        val binding = DataBindingUtil.inflate<AllcustomersRowBinding>(
            LayoutInflater.from(parent.context), R.layout.allcustomers_row, parent, false
        )
        return CustomersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomersViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> onClickListener.onClick(it1) }
        }
    }
   // fun filterList(filterlist: ArrayList<AllCustomers?>) {
   //     // below line is to add our filtered
   //     // list in our course array list.
   //     courseModelArrayList = filterlist
   //     // below line is to notify our adapter
   //     // as change in recycler view data.
   //     notifyDataSetChanged()
   // }

    companion object DiffCallback : DiffUtil.ItemCallback<AllCustomers>() {
        override fun areItemsTheSame(oldItem: AllCustomers, newItem: AllCustomers): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AllCustomers, newItem: AllCustomers): Boolean {
            return oldItem.Customer_ID == newItem.Customer_ID
        }
    }







}

class CustomersViewHolder(private var binding: AllcustomersRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(customer: AllCustomers) {
        binding.data = customer
        binding.executePendingBindings()
    }
}

class OnClickListenerss(val clickListener: (customer: AllCustomers) -> Unit) {
    fun onClick(customer: AllCustomers) = clickListener(customer)
}