package com.iryu.wahkor.account

import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_stock.*
fun checkeval(data:String):Boolean{
    try {
        evalstring.eval(data)
        return true
    }catch (e:Throwable){return false}
}
fun inputData( position:Int,inputdata:String,updatedata:String,min:Int,max:Int):String {
    if (evalstring.eval(inputdata)<min ||evalstring.eval(inputdata)>max) {
        println("input out of range")
        return updatedata
    }else{
        val data=updatedata.split(",")
        var me=data[0]
        for (i in 1 until data.size){
            me+=","
            if(position+1 != i)  me+=data[i] else me+=inputdata
        }
        return me
    }
}
fun setFstockFirstdata(data:String){
    if (data==""){return}
    val first= data_fstock_first.split(",")
    val subdata=data.split(",")
    data_fstock_first=first[0]
    for (i in 1 until first.size){
        data_fstock_first+=","+(first[i].toInt()+evalstring.eval(subdata[i])).toInt().toString()
    }
}
fun diffOldandNew(olddata:String,newdata:String){
    var diffdata=olddata.split(",")[0]
    for (i in 1 until olddata.split(",").size){
        diffdata+=","+(newdata.split(",")[i].toInt()-
                       olddata.split(",")[i].toInt()).toString()
    }

    setFstockFirstdata(diffdata)
}