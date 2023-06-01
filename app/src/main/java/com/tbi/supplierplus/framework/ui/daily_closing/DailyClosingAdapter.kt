package com.tbi.supplierplus.framework.ui.daily_closing

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.Expenses
import com.tbi.supplierplus.business.models.PenddingModel
import com.tbi.supplierplus.business.pojo.Datum
import com.tbi.supplierplus.business.pojo.closing.SupplierReport
import com.tbi.supplierplus.business.pojo.opening.OpeningBalance
import com.tbi.supplierplus.business.utils.toJson
import com.tbi.supplierplus.databinding.DailyClosingItemRow2Binding
import com.tbi.supplierplus.databinding.DailyClosingItemRowBinding
import com.tbi.supplierplus.databinding.ExpensesRowBinding
import com.tbi.supplierplus.databinding.PurchRowBinding
import com.tbi.supplierplus.framework.ui.collect_debit.OnDebitClickListener


class DailyClosingAdapter (val onClickListener: OnDebitClickListeners): ListAdapter<Datum, DailyClosingViewHolderH>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyClosingViewHolderH {
        val binding = DataBindingUtil.inflate<PurchRowBinding>(
            LayoutInflater.from(parent.context), R.layout.purch_row, parent, false
        )
        return DailyClosingViewHolderH(binding)
    }

    override fun onBindViewHolder(holder: DailyClosingViewHolderH, position: Int) {
        getItem(position)?.let {
            Log.d("getCurriygentMoI1",it.ItemName+" ")
            holder.bind(it) }

      //  getItem(position).


    }

    companion object DiffCallback : DiffUtil.ItemCallback<Datum>() {
        override fun areItemsTheSame(oldItem: Datum, newItem: Datum): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Datum, newItem: Datum): Boolean {
            return oldItem.Item_ID == newItem.Item_ID
        }
    }




}
class DailyClosingViewHolderH(private var binding: PurchRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Item: Datum) {
        Log.d("getCurriygentMoI1",Item.ItemName+" ")
        binding.data2 = Item
        binding.executePendingBindings()
    }



}
class OnDebitClickListeners(val clickListener: (CustomerDebit: Datum) -> Unit) {
    fun onClick(CustomerDebit: Datum) = clickListener(CustomerDebit)
}