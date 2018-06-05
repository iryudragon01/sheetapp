package com.iryu.wahkor.account

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

var acClick=false
var summariesdata=""
var mainfirsttime=true
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logout.setOnClickListener {startActivity(Intent(this,LoginActivity::class.java))}
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
      eval()
    }
    fun eval(){
        val mytext="5+3*6-2"
        var num="";var op=""
        for(c in mytext){
            if (c in '0' until '9') num+=c else {op+=c;num+="," }
        }
        var number=mytext.split(",")
        var summulti=""
        var lastnum="m"
        for (i in 0 until number.size-2){
            if (op[i]=='*'){
                if (summulti==""){summulti=number[i]+","+number[i+1]}

            }
        }

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
        date.setText(startdateaccount)
        stock.setText((calculator.getStock()+calculator.getAirpay()).toString())
        fstock.setText(calculator.getFstock().toString())
        income.setText(calculator.getIncome().toString())
        expense.setText(calculator.getExpense().toString())

        daywork.setText(data[0])
        nightwork.setText(data[1])
        val money=calculator.getStock()+
                calculator.getAirpay()+
                calculator.getFstock()+
                calculator.getIncome()-
                calculator.getExpense()-
                daymoney-nightmoney


        all.setText(money.toString())
    }


    fun update(){
        var url="action=senddata"
        url+="&return_refill=${newfood}&del_income=${del_income}&del_expense=${del_expense}"
        url+="&tempincome=${tempincome}&tempexpense=${tempexpense}"
        url+="&GoogleId=${GoogleID}"
        url+="&airpaystart=${data_airpay_first}"
        url+="&return_stockdata="+if(data_stock_last!= noupdateStock)  data_stock_last else ""
        url+="&return_airpay="+if(data_airpay_last!= noupdateAirpay)  data_airpay_last else ""
        url+="&return_fstockdata="+if(data_fstock_last!= noupdateFstock)  data_fstock_last else ""
        GoogleScript().execute(url)
        newfood="";loadfstock =true;loadstock =true;tempincome="";tempexpense="";del_expense="";del_income=""
    }
    inner class GoogleScript():GoogleSheet(){
        override fun onPostExecute(result: String?) {

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
}