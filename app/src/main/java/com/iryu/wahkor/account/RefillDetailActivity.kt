package com.iryu.wahkor.account

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_stock.*

class RefillDetailActivity : AppCompatActivity() {
    private var  Refilladd=ArrayList<refillsumModel>()
    private var enableAlert="no"
    private var  RefillDetail=ArrayList<refillsumModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        datestart.visibility= View.GONE
        menubt01.setOnClickListener { startActivity(Intent(this,MainActivity::class.java)) }
        menubt02.setOnClickListener { startActivity(Intent(this, fstockActivity::class.java)) }
        menubt03.setOnClickListener { addElementRefilldetail();menubt03.setBackgroundColor(selectBTcolor); menubt04.setBackgroundColor(unselectBTcolor) }
        menubt04.setOnClickListener { addElementRefilladd();menubt03.setBackgroundColor(unselectBTcolor); menubt04.setBackgroundColor(selectBTcolor) }
        menubt01.setBackgroundColor(unselectBTcolor)
        menubt02.setBackgroundColor(unselectBTcolor)
        menubt03.setBackgroundColor(selectBTcolor)
        menubt04.setBackgroundColor(unselectBTcolor)
        menubt01.text="หน้าหลัก"
        menubt02.text="น้ำ/ขนม"
        menubt03.text="รายการ"
        menubt04.text="เพิ่มสินค้าใหม่"
        addElementRefilldetail()



    }
    fun addElementRefilldetail(){
        RefillDetail.clear()
        enableAlert="no"
        val element= refilldetail.split("<&&>")
        for (i in 0 until element.size){
            val raw=element[i].split(",")
            var cell=""
            for (j in 1 until data_fstock_name.split(",").size){
                if(raw[j]!="0"){
                    cell+=data_fstock_name.split(",")[j]+"="+raw[j]+"\n"}}
            RefillDetail.add(refillsumModel(raw[0],cell))}


        val adapter=refillAdaper(this,RefillDetail)
        stock_showlist.adapter=adapter

    }
    fun addElementRefilladd(){
        Refilladd.clear()
        enableAlert="yes"
        val element= data_fstock_name.split(",")
        val value= newfood.split(",")
        for (i in 1 until element.size){
            Refilladd.add(refillsumModel(element[i], value[i] )) }
            val adapter=refillAdaper(this,Refilladd)
            stock_showlist.adapter=adapter
         stock_showlist.onItemClickListener=AdapterView.OnItemClickListener{ _,_, position,_ ->
            myAlert(this,position, Refilladd)
        }}

    fun myAlert(context: Context, position:Int,myRefill:ArrayList<refillsumModel>) {
        if (enableAlert=="no"){ return}
        val olddata=newfood
        val alert= AlertDialog.Builder(this)
        var edittextlast: EditText?=null
        with(alert){
            setTitle(myRefill[position].refillsumname)
            setMessage("ยอดล่าสุด")

            edittextlast= EditText(context)
            edittextlast!!.hint= myRefill[position].refillsumvalume
            edittextlast!!.inputType = InputType.TYPE_CLASS_NUMBER
            setPositiveButton("OK") { dialog, _ -> dialog.dismiss()
                val last=edittextlast!!.text.toString()
                if (checkeval(last)){
                    newfood= inputData(position,last,newfood,0,1000)
                                    }
                diffOldandNew(olddata, newfood)
                addElementRefilladd()
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
    override fun onBackPressed() {
        startActivity(Intent(this, fstockActivity::class.java))
    }
}
