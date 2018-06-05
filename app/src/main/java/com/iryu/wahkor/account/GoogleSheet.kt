package com.iryu.wahkor.account

import android.os.AsyncTask
import java.net.HttpURLConnection
import java.net.URL


lateinit var refilldetail:String
lateinit var refillsum:String
var GoogleID:String=""
var GoogleUser:String=""
var GoogleLevel:Int=0
var newfood:String=""
var IncomeData:String=""
var ExpenseData:String=""
var getdatatime:Int=0
var startdateaccount=""
open class GoogleSheet:AsyncTask<String,String,String>(){
    override fun doInBackground(vararg params: String?): String {
        var url="https://script.google.com/macros/s/AKfycbwH6fLr3mO1-KcreVb5HcNoI_wATVveJs7RmOayBpYq/dev?"

        for (i in 0 until params.size){url+=params[i]}
        //println(url)
        val result :String
        val connection=URL(url).openConnection() as HttpURLConnection


        try {
            connection.connect()
            result=connection.inputStream.bufferedReader().readText()
        }finally {
            connection.disconnect()
        }

        return result
    }
fun sendResult(result: String){onPostExecute(result)}
    override fun onPostExecute(result: String?) {
        getdatatime++
        val unwarp=(result as String).split("<||>")
        val headrev=unwarp[0].split("_")
        when(headrev[0]) {
            "refill"       -> refill(unwarp[1])
            "stock"        -> stock(headrev[1],result)
            "income"       -> income(unwarp[1])
            "outcome"      -> outcome(unwarp[1])
            "getdataall" -> alldataafferlogin(result)
            else -> println(result)

        }
        return
    }

    private fun alldataafferlogin(result: String) {
        val subdata=result.split("|||")
        for ( i in 1 until subdata.size) {
            onPostExecute(subdata[i])
        }
        acClick=true
        }

    }

    private fun income(data: String){IncomeData=data}
    private fun outcome(data: String){ExpenseData=data}
    private fun stock(headrev: String, data: String) {
        when (headrev) {

            "fstock" -> fstocksetdata(data.split("<||>")[1])
            "stock"   ->stocksetdata( data.split("<||>")[1])
            "airpay"   ->airpaysetdata( data.split("<||>")[1])
        }
 }
fun airpaysetdata(data: String) {
    data_airpay_name=data.split("<&&>")[0]
    data_airpay_price=data.split("<&&>")[1]
    data_airpay_first=data.split("<&&>")[2]
    data_airpay_last=data.split("<&&>")[3]
    noupdateAirpay= data_airpay_last
}

fun stocksetdata(data: String) {
    loadstock =false
    data_stock_name=data.split("<&&>")[0]
    data_stock_price=data.split("<&&>")[1]
    data_stock_first=data.split("<&&>")[2]
    data_stock_last=data.split("<&&>")[3]
    startdateaccount= data_stock_first.split(",")[0]
    noupdateStock= data_stock_last
}
fun fstocksetdata(data: String) {
    loadfstock =false
    data_fstock_name=data.split("<&&>")[0]
    data_fstock_price=data.split("<&&>")[1]
    data_fstock_first=data.split("<&&>")[2]
    data_fstock_last=data.split("<&&>")[3]
    noupdateFstock= data_fstock_last
}

    private fun refill( data:String){
        val sub=data.split("<sum>")
        if (sub.size==2){
        refilldetail=sub[0]
        refillsum=sub[1]}
        else{
            println(data)}

    }
