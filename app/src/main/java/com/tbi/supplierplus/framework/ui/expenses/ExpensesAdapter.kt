package com.tbi.supplierplus.framework.ui.expenses

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.tbi.supplierplus.R
import com.tbi.supplierplus.business.models.Item
import com.tbi.supplierplus.business.pojo.expenses.ExpensesSearch

class ExpensesTypeAdapter(val context: Context, var dataSource: List<ExpensesSearch>) :
    BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.expenses_row, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.name.text = dataSource.get(position).ExpenseType

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

    private class ItemHolder(row: View?) {
        val name: TextView

        init {
            name = row?.findViewById(R.id.itemNameTxt) as TextView
        }
    }

}