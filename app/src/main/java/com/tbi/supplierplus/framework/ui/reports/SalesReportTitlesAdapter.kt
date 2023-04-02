package com.tbi.supplierplus.framework.ui.reports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.SalesSummaryTitles
import com.tbi.supplierplus.databinding.RowSalesReportBinding

class SalesReportTitlesAdapter():
    ListAdapter<SalesSummaryTitles, SalesReportTitlesViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesReportTitlesViewHolder {
        val binding = DataBindingUtil.inflate<RowSalesReportBinding>(
            LayoutInflater.from(parent.context), R.layout.row_sales_report, parent, false
        )
        return SalesReportTitlesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SalesReportTitlesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<SalesSummaryTitles>() {
        override fun areItemsTheSame(oldItem: SalesSummaryTitles, newItem: SalesSummaryTitles): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SalesSummaryTitles, newItem: SalesSummaryTitles): Boolean {
            return oldItem.title == newItem.title
        }
    }


}

class SalesReportTitlesViewHolder(private var binding: RowSalesReportBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SalesSummaryTitles) {
        binding.data = item
        binding.executePendingBindings()
    }
}

