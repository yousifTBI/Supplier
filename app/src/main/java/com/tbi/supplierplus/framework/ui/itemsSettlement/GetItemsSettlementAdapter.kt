
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
import com.tbi.supplierplus.business.models.ReturnItemsSettelment
import com.tbi.supplierplus.business.pojo.settelment.ItemsSettelment
import com.tbi.supplierplus.databinding.ItemsSettelmentItemBinding
import com.tbi.supplierplus.databinding.ReturnsItemsSettelmentBinding
import com.tbi.supplierplus.framework.ui.sales.customers.OnClickListener
import kotlinx.android.synthetic.main.items_settelment_item.view.*

// for recyclers
class GetItemsSettlementAdapter(val onClickListener: OnItemSettlementClickListener)
    : ListAdapter<ItemsSettelment, GetItemsSettelmentViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetItemsSettelmentViewHolder {
        val binding = DataBindingUtil.inflate<ItemsSettelmentItemBinding>(
            LayoutInflater.from(parent.context), R.layout.items_settelment_item, parent, false
        )

        return GetItemsSettelmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GetItemsSettelmentViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

        holder.itemView.settlement.setOnClickListener(View.OnClickListener {
            getItem(position)?.let { it1 -> onClickListener.onClick(it1) }
        })

    }

    companion object DiffCallback : DiffUtil.ItemCallback<ItemsSettelment>() {
        override fun areItemsTheSame(oldItem: ItemsSettelment, newItem: ItemsSettelment): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ItemsSettelment, newItem: ItemsSettelment): Boolean {
            return oldItem.Item_ID == newItem.Item_ID
        }
    }


}

class GetItemsSettelmentViewHolder(private var binding:ItemsSettelmentItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Item: ItemsSettelment) {
        binding.data = Item
        binding.executePendingBindings()
    }
}

class OnItemSettlementClickListener(val clickListener: (itemsSettlement: ItemsSettelment) -> Unit) {
    fun onClick(itemsSettlement: ItemsSettelment) = clickListener(itemsSettlement)
}