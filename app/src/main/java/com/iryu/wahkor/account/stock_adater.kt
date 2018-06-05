package com.iryu.wahkor.account


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

public class stock_adapter(context: Context, private var Item: ArrayList<stockModel>, private var R1:Float, private var R2:Float, private var R3:Float) : BaseAdapter() {
    private var context: Context?= context
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view=LayoutInflater.from(context).inflate(R.layout.stock_adapter,parent,false)
        val stockName:TextView=view.findViewById(R.id.stock_name) as TextView
        val stockStart:TextView=view.findViewById(R.id.stock_start) as TextView
        val stockLast:TextView=view.findViewById(R.id.stock_last) as TextView
        stockName.textSize= R1
        stockStart.textSize=R2
        stockLast.textSize=R3
        stockName.text=Item[position].stockname
        stockStart.text=Item[position].stockstart
        stockLast.text=Item[position].stocklast

        return view
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return Item.size
    }

}