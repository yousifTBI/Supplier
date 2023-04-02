package com.tbi.supplierplus.framework.ui.reports

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.pojo.AllCustomers
import com.tbi.supplierplus.databinding.CustomerRowBinding

class CustomerSelectionAdapter(val context: Context, var dataSource: List<AllCustomers>) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View
        val vh: CustomerHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.customer_statement_row, parent, false)
            vh = CustomerHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as CustomerHolder
        }
        vh.name.text = dataSource.get(position).item

        return view
    }

    override fun getItem(position: Int): Any? {
        return dataSource[position];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class CustomerHolder(row: View?) {
        val name: TextView

        init {
            name = row?.findViewById(R.id.itemNameTxt) as TextView
        }
    }

}