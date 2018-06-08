package com.iryu.wahkor.account


import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_stock.*

var loadstock=true
//var return_airpay=""
var return_stockdata=""

class StockActivity : AppCompatActivity() {
    private var stockadape=ArrayList<stockModel>()
    private var stockcount=0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        menubt01.setOnClickListener { startActivity(Intent(this,MainActivity::class.java)) }
        //menubt03.setOnClickListener { startActivity(Intent(this, fstockActivity::class.java)) }
        menubt04.setOnClickListener { }
        menubt01.setBackgroundColor(unselectBTcolor)
        menubt02.setBackgroundColor(selectBTcolor)
        menubt03.setBackgroundColor(unselectBTcolor)
        menubt04.setBackgroundColor(unselectBTcolor)
        menubt01.text="หน้าหลัก"
        menubt02.text="บัตร"
        menubt03.text=""
        menubt04.text=""
        addstock()

    }
    fun arrangeData(){
        stockadape.clear()
        return_stockdata =""
        val name = data_stock_name.split(",")
            stockcount=name.size-1
        val firstdata = data_stock_first.split (",")
        val lastdata = data_stock_last.split(",")
                datestart.setText(startdateaccount)

        val airname =data_airpay_name.split(",")
        val airfirst = data_airpay_first.split(",")
        val airlast = data_airpay_last.split(",")
        for (i in 1 until name.size){
            if (i!=1){
                return_stockdata +=","}
            stockadape.add(stockModel(
                    name[i],
                    firstdata[i],
                    lastdata[i]))
            return_stockdata +=lastdata[i]
        }

        for (i in 1 until airname.size){
            stockadape.add(stockModel(
                    airname[i],
                    "0",
                    (airlast[i].toInt()-airfirst[i].toInt()).toString()))
        }}
    fun addstock(){
        arrangeData()
        val myadape= stock_adapter(this, stockadape, 35f, 20f, 20f)
        stock_showlist.adapter=myadape
        stock_showlist.onItemClickListener=AdapterView.OnItemClickListener{ _, _, position,_ ->

            myAlert(this,position)
            }}


        fun myAlert(context: Context, position:Int) {
            val alert= AlertDialog.Builder(this)
            var edittextlast: EditText?=null
            with(alert){
                setTitle(stockadape[position].stockname)
                setMessage("ยอดล่าสุด")

                edittextlast=EditText(context)
                edittextlast!!.hint= stockadape[position].stocklast
                edittextlast!!.inputType = InputType.TYPE_CLASS_PHONE
                setPositiveButton("OK") { dialog, _ ->
                    val last=edittextlast!!.text.toString()
                     if (checkeval(last)) {
                          if(position<stockcount){
                       data_stock_last= inputData(position,last, data_stock_last,stockadape[position].stockstart.toInt()-300,39999)
                    }else{
                          data_airpay_last= inputData(position-stockcount,last, data_airpay_last,0,50000)

                      }}
                     arrangeData()
                    stock_showlist.adapter= stock_adapter(context, stockadape, 35f, 20f, 20f)

                }

                setNegativeButton("NO") {
                    dialog, _ ->
                    dialog.dismiss()
                }
            }

            // Dialog
            val dialog = alert.create()
            dialog.setView(edittextlast)
            dialog.show()
        }

    override fun onBackPressed() {startActivity(Intent(this,MainActivity::class.java))    }
    }

