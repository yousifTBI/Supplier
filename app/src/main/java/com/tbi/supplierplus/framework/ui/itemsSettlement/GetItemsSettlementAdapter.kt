
package com.tbi.supplierplus.framework.ui.itemsSettlement

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.Customer
import com.tbi.supplierplus.business.models.Requests
import com.tbi.supplierplus.business.models.ReturnItemsSettelment

import com.tbi.supplierplus.databinding.ItemsSettelmentItemBinding
import com.tbi.supplierplus.databinding.OrdersRowBinding
import com.tbi.supplierplus.databinding.ReturnsItemsSettelmentBinding
import com.tbi.supplierplus.framework.ui.sales.customers.OnClickListener
import kotlinx.android.synthetic.main.items_settelment_item.view.*

// for recyclers
class GetItemsSettlementAdapter(val onClickListener: OnItemSettlementClickListener)
    : ListAdapter<Requests, GetItemsSettelmentViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetItemsSettelmentViewHolder {
        val binding = DataBindingUtil.inflate<OrdersRowBinding>(
            LayoutInflater.from(parent.context), R.layout.orders_row, parent, false
        )

        return GetItemsSettelmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GetItemsSettelmentViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> onClickListener.onClick(it1) }
        }


    }

    companion object DiffCallback : DiffUtil.ItemCallback<Requests>() {
        override fun areItemsTheSame(oldItem: Requests, newItem: Requests): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Requests, newItem: Requests): Boolean {
            return oldItem.Item_ID == newItem.Item_ID
        }
    }


}

class GetItemsSettelmentViewHolder(private var binding:OrdersRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Item: Requests) {
        binding.data = Item
        binding.executePendingBindings()
    }
}

class OnItemSettlementClickListener(val clickListener: (itemsSettlement: Requests) -> Unit) {
    fun onClick(itemsSettlement: Requests) = clickListener(itemsSettlement)
}