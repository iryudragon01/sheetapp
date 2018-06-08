package com.iryu.wahkor.account

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.widget.AdapterView
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_stock.*

public var del_income=""
public var del_expense=""
class StatementActivity : AppCompatActivity() {
    private var itemnum=0
    private var statementadape=ArrayList<stockModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        datestart.setText(startdateaccount)
        menubt02.text="รายรับ"
        menubt03.text="รายจ่าย"
        menubt04.text="เพิ่ม รายการ"
        menubt01.text="หน้าหลัก"
        menubt01.setOnClickListener { startActivity(Intent(this,MainActivity::class.java)) }
        menubt02.setOnClickListener {}
        menubt03.setOnClickListener {}
         menubt04.setOnClickListener { startActivity(Intent(this,AddStatementActivity::class.java)) }
        menubt01.setBackgroundColor(unselectBTcolor)
        menubt02.setBackgroundColor(selectBTcolor)
        menubt03.setBackgroundColor(selectBTcolor)
        menubt04.setBackgroundColor(unselectBTcolor)
        addStatement()
    }
    private fun addStatement() {
        del_income=""
        del_expense=""
        if(IncomeData!=""){
            val inelement= IncomeData.split("<&&>")
        for (i in 0 until inelement.size){
            if (i!=0){del_income+=","}
            if (inelement[i] !=""){
                del_income+="0"
                val sub=inelement[i].split(",")
                statementadape.add(stockModel(sub[0], sub[1], sub[2]))
                itemnum++
            }
        }}
        if(ExpenseData!=""){
            val exelement= ExpenseData.split("<&&>")
        for (i in 0 until exelement.size){
            if (exelement[i] !=""){
                if (del_expense !=""){del_expense+=","}
                del_expense+="0"
                println("round${i} del_expnse=${del_expense}")
                val sub=exelement[i].split(",")
                statementadape.add(stockModel(sub[0], sub[1], sub[2]))
                itemnum++
            }
        }}
        if (tempincome!="") {
            val temp = tempincome.split("<<__>>")
            for (i in 0 until temp.size) {
                statementadape.add(stockModel("income", temp[i].split(",")[1], temp[i].split(",")[2]))
            }
        }
            if (tempexpense!=""){
                val temp= tempexpense.split("<<__>>")
                for (i in 0 until temp.size){
                    statementadape.add(stockModel("expense", temp[i].split(",")[1], temp[i].split(",")[2]))}

            }
        val myadape= stock_adapter(this, statementadape, 20f, 35f, 35f)
        stock_showlist.adapter=myadape
            stock_showlist.onItemClickListener=AdapterView.OnItemClickListener{ _,_,position,_ ->
                myAlert(this,position)
            }
    }

    fun myAlert(context: Context, position:Int) {
        if (itemnum>position){return}
        val alert= AlertDialog.Builder(this)
        var edittextlast: EditText?=null
        with(alert){
            setTitle(statementadape[position].stockstart)
            setMessage("ยอดล่าสุด")

            edittextlast= EditText(context)
            edittextlast!!.hint= statementadape[position].stocklast
            edittextlast!!.inputType = InputType.TYPE_CLASS_NUMBER
            setPositiveButton("OK") { dialog, _ -> dialog.dismiss()
                val last=edittextlast!!.text.toString()
                statementadape[position].stocklast=last
                stock_showlist.adapter= stock_adapter(context, statementadape, 20F, 35F, 35F)
                tempincome=""
                tempexpense=""
                for(i in 0 until statementadape.size){
                    if(statementadape[i].stockname=="expense") {
                        if (tempexpense!=""){  tempexpense+=","   }
                        tempexpense+=statementadape[i].stockstart+","+statementadape[i].stocklast

                    }
                    if(statementadape[i].stockname=="income") {
                        if (tempincome!=""){  tempincome+=","   }
                        tempincome+=statementadape[i].stockstart+","+statementadape[i].stocklast

                    }
            }}

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

    override fun onBackPressed() {
        startActivity(Intent(this, StockActivity::class.java))
    }
}

