package com.iryu.wahkor.account

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add_statement.view.*
import kotlinx.android.synthetic.main.activity_main.*

var acClick=false
var summariesdata=""
var mainfirsttime=true
val empty=""
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logout.setOnClickListener {
            mainfirsttime=true;startActivity(Intent(this,LoginActivity::class.java))}
        logout.text= GoogleUser
        over_view.setBackgroundColor(selectBTcolor)
        list.setBackgroundColor(unselectBTcolor)
        save.setBackgroundColor(unselectBTcolor)
        logout.setBackgroundColor(unselectBTcolor)
        if (mainfirsttime){
            mainfirsttime=false
            GoogleScript().execute("action=summaries&GoogleId=${GoogleID}")}else{
            setsummaries()
        }
        over_view.setOnClickListener { GoogleScript().execute("action=summaries&GoogleId=${GoogleID}") }
        save.setOnClickListener{
            cleardata()
            update();save.isClickable=false}
        list.setOnClickListener { if (acClick){startActivity(Intent(this, StockActivity::class.java)) }}
        list.text="บัญชี"
        over_view.text="หน้าแรก"
        save.text="บันทึก"
        date.setOnClickListener { showdetail("date") }
        stock.setOnClickListener { showdetail("StockActivity") }
        fstock.setOnClickListener { showdetail("FstockActivity") }
        income.setOnClickListener { showdetail("StatementActivity") }
        expense.setOnClickListener { showdetail("StatementActivity") }
        daywork.setOnClickListener { showdetail("daywork") }
        nightwork.setOnClickListener { showdetail("nightwork") }
        all.setOnClickListener { showdetail("all") }
    }
    fun cleardata() {

        stock.setText("")
        fstock.setText("")
        income.setText("")
        expense.setText("")
        daywork.setText("")
        nightwork.setText("")
        all.setText("")

    }
    fun setsummaries(){
        val calculator=Calculator()
        val data= summariesdata.split(",")
        val daymoney=data[0].toInt()
        val nightmoney=data[1].toInt()
        val stocksetText="บัตร และ airpay "+(calculator.getStock()+calculator.getAirpay()).toString()+" บาท"
        val fstocktext="น้ำและขนม "+(calculator.getFstock().toString())+ " บาท"
        val incometext="รายรับ "+(calculator.getIncome().toString())+" บาท"
        val expensetext= "รายจ่าย"+(calculator.getExpense().toString()) + " บาท"
        val dayworktext="กะกลางวัน "+data[0]+" บาท"
        val nightworktext="กะกลางคืน "+data[1]+" บาท"

        date.text=startdateaccount
        stock.text=stocksetText
        fstock.text=fstocktext
        income.text=incometext
        expense.text=expensetext
        daywork.text=dayworktext
        nightwork.text=nightworktext
        val money=calculator.getStock()+
                calculator.getAirpay()+
                calculator.getFstock()+
                calculator.getIncome()-
                calculator.getExpense()-
                daymoney-nightmoney

        val alltext="ยอดเงิน "+money.toString()+" บาท"
        all.text=alltext
    }


    fun update(){
        var url="action=senddata"
        url+="&return_refill=${if (nosavenewfood!= newfood)preupdate(newfood) else empty}" +
             "&del_income=${del_income}&del_expense=${del_expense}"
        url+="&tempincome=${tempincome}&tempexpense=${tempexpense}"
        url+="&GoogleId=${GoogleID}"
        url+="&airpaystart=${data_airpay_first}"
        url+="&return_stockdata="+if(data_stock_last!= noupdateStock)  preupdate(data_stock_last) else ""
        url+="&return_airpay="+if(data_airpay_last!= noupdateAirpay)  preupdate(data_airpay_last) else ""
        url+="&return_fstockdata="+if(data_fstock_last!= noupdateFstock)  preupdate(data_fstock_last) else ""

        GoogleScript().execute(url)
        newfood="";loadfstock =true;loadstock =true;tempincome="";tempexpense="";del_expense="";del_income=""
    }
    inner class GoogleScript():GoogleSheet(){
        override fun onPostExecute(result: String?) {
println(result)
            val unwrap=(result as String).split("<||>")
            if (unwrap[0]=="getdataall"){GoogleSheet().sendResult(result)
                GoogleScript().execute("action=summaries&GoogleId=${GoogleID}")
            }
            if (unwrap[0]=="summaries"){println(unwrap[1]);summariesdata=unwrap[1];setsummaries()}
           else{
                println(result)}
            return
        }
    }

    override fun onBackPressed() {
        return
    }
    fun preupdate(data : String):String{
        val sub=data.split(",")
        var Rdata=""
        for (i in 1 until sub.size){
          Rdata+= if (i==0)evalstring.eval(sub[i]).toInt().toString() else ","+evalstring.eval(sub[i]).toInt().toString()
        }
        return Rdata
    }
    fun showdetail(name:String){
        if (!acClick)return
        when(name){
            "StockActivity" ->startActivity(Intent(this,StockActivity::class.java))
            "FstockActivity" ->startActivity(Intent(this,fstockActivity::class.java))
            "StatementActivity" ->startActivity(Intent(this,StatementActivity::class.java))

        }
    }
}