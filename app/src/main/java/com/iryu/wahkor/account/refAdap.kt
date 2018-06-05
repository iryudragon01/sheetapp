package com.iryu.wahkor.account

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class refillAdaper(context: Context, private var itemlist: ArrayList<refillsumModel>) : BaseAdapter() {

    private var context: Context?= context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
       val view = LayoutInflater.from(context).inflate(R.layout.refillsum,parent,false)
        val vh=ViewHolder(view)
        vh.refillsumname.text=itemlist[position].refillsumname
        vh.refillsumvalume.text=itemlist[position].refillsumvalume

        return view
    }


    override fun getItem(position: Int): Any {
        return itemlist[position]
         }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getCount(): Int {
       return itemlist.size
    }

    private class ViewHolder(view:View?) {
        var refillsumname:TextView
        var refillsumvalume:TextView
        init{
            this.refillsumname=view?.findViewById(R.id.refillsum_name) as TextView
            this.refillsumvalume=view?.findViewById(R.id.refillsum_valume) as TextView


        }
    }
    }


