package com.iryu.wahkor.account


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.widget.AdapterView
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_stock.*

  var loadfstock:Boolean=true
  var selectBTcolor=Color.parseColor("#009099")
  var unselectBTcolor=Color.parseColor("#00ff99")
class fstockActivity : AppCompatActivity() {
    private var fstockadape=ArrayList<stockModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        menubt01.setOnClickListener { startActivity(Intent(this,MainActivity::class.java)) }
       // menubt02.setOnClickListener { startActivity(Intent(this,fstockActivity::class.java)) }
        menubt03.setOnClickListener { startActivity(Intent(this, RefillDetailActivity::class.java)) }
        menubt04.setOnClickListener {  }

        menubt01.setBackgroundColor(unselectBTcolor)
        menubt02.setBackgroundColor(selectBTcolor)
        menubt03.setBackgroundColor(unselectBTcolor)
        menubt04.setBackgroundColor(unselectBTcolor)
        menubt03.text="เพิ่มสินค้า"
        menubt01.text="หน้าหลัก"
        menubt02.text="ขนม/น้ำ"
        menubt04.text=""
        datestart.setText(startdateaccount)
        setfstock()

    }
    fun eval(text:String):Int{
        var num=""
        var op=""
        for(i in text) {
            if (i in '0' until '9'){num+=i}else{num+="<>";op+=i}
        }
        println(num+"\n"+op)
        return 10
    }
    fun arrangeData(){
        fstockadape.clear()
        val name = data_fstock_name.split(",")
        val firstdata = data_fstock_first.split (",")
        val lastdata = data_fstock_last.split(",")

        for (i in 1 until name.size){
            fstockadape.add(stockModel(name[i],firstdata[i],lastdata[i]))
        }}
    fun setfstock(){
        arrangeData()
        val myadape= stock_adapter(this, fstockadape, 35f, 20f, 20f)
        stock_showlist.adapter=myadape
        stock_showlist.onItemClickListener=AdapterView.OnItemClickListener{ _, _, position, _ ->
            myAlert(this,position)

        }}


    fun myAlert(context: Context, position:Int) {
        val alert= AlertDialog.Builder(this)
        var edittextlast: EditText?=null
        with(alert){
            setTitle(fstockadape[position].stockname)
            setMessage("ยอดล่าสุด")

            edittextlast=EditText(context)
            edittextlast!!.hint= fstockadape[position].stocklast
            edittextlast!!.inputType = InputType.TYPE_CLASS_PHONE
            setPositiveButton("OK") { dialog, _ -> dialog.dismiss()
                val last=edittextlast!!.text.toString()
                if (checkeval(last)){
                    data_fstock_last= inputData(position,last, data_fstock_last,0,fstockadape[position].stockstart.toInt())

                }
                arrangeData()
               stock_showlist.adapter= stock_adapter(context, fstockadape, 35f, 20f, 20f)
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
        startActivity(Intent(this, MainActivity::class.java))
    }
}

