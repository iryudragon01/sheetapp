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
        menubt03.setOnClickListener { startActivity(Intent(this, fstockActivity::class.java)) }
        menubt04.setOnClickListener {  startActivity(Intent(this,StatementActivity::class.java)) }
        menubt01.setBackgroundColor(unselectBTcolor)
        menubt02.setBackgroundColor(selectBTcolor)
        menubt03.setBackgroundColor(unselectBTcolor)
        menubt04.setBackgroundColor(unselectBTcolor)
        menubt01.text="หน้าหลัก"
        menubt02.text="บัตร"
        menubt04.text="+/-"
        menubt03.text="ขนม/เครื่องดื่ม"
        arrangeData()
    }
    fun arrangeData(){
        return_stockdata =""
        val name = data_stock_name.split(",")
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
        stockcount=name.size
        for (i in 1 until airname.size){
            stockadape.add(stockModel(
                    airname[i],
                    "0",
                    (airlast[i].toInt()-airfirst[i].toInt()).toString()))
        }
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
                edittextlast!!.inputType = InputType.TYPE_CLASS_TEXT
                setPositiveButton("OK") { dialog, _ -> dialog.dismiss()
                    var last=edittextlast!!.text.toString()
                    stockadape[position].stocklast=last
                    stock_showlist.adapter= stock_adapter(context, stockadape, 35f, 20f, 20f)
                    data_stock_last = data_stock_last.split(",")[0]
                    for (i in 0 until  stockcount-1){
                        data_stock_last+=","+stockadape[i].stocklast
                    }
                    println("data airpay lastbefor=${data_airpay_last}")
                    data_airpay_last = data_airpay_last.split(",")[0]
                    for (i in stockcount-1 until stockadape.size){
                        data_airpay_last +=","+stockadape[i].stocklast
                    }
                    println("data airpay lastall=${data_airpay_last}")
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

