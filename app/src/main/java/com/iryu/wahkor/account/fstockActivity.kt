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
        menubt04.setOnClickListener { startActivity(Intent(this,StatementActivity::class.java)) }
        //menubt04.setOnClickListener { UpdateManager().update() }
        menubt01.setBackgroundColor(unselectBTcolor)
        menubt02.setBackgroundColor(selectBTcolor)
        menubt03.setBackgroundColor(unselectBTcolor)
        menubt04.setBackgroundColor(unselectBTcolor)
        menubt03.text="เพิ่มสินค้า"
        menubt01.text="หน้าหลัก"
        menubt02.text="ขนม/น้ำ"
        menubt04.text="รายรับรายจ่าย"
        datestart.setText(startdateaccount)
        arrangeData()
    }
    fun arrangeData(){
        val name = data_fstock_name.split(",")
        val firstdata = data_fstock_first.split (",")
        val lastdata = data_fstock_last.split(",")

        for (i in 1 until name.size){
            var myadd:Int
            if(refillsum==""){myadd=0}else{
                myadd=refillsum.split(",")[i-1].toInt()}

            fstockadape.add(stockModel(
                    name[i],
                    (firstdata[i].toInt() + myadd).toString(),
                    lastdata[i]))
        }

        var myadape= stock_adapter(this, fstockadape, 35f, 20f, 20f)
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
            edittextlast!!.inputType = InputType.TYPE_CLASS_NUMBER
            setPositiveButton("OK") { dialog, _ -> dialog.dismiss()
                val last=edittextlast!!.text.toString()
                fstockadape[position].stocklast=last
               stock_showlist.adapter= stock_adapter(context, fstockadape, 35f, 20f, 20f)
                data_fstock_last= data_fstock_last.split(",")[0]
                for (i in 0 until  fstockadape.size){ // do not include last item
                      data_fstock_last+=","+fstockadape[i].stocklast
                }
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
        startActivity(Intent(this, StockActivity::class.java))
    }
}

