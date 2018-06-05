package com.iryu.wahkor.account

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_statement.*

 var tempexpense:String=""
 var tempincome:String=""
class AddStatementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_statement)
        incomecheck.isChecked= !expensecheck.isChecked
        expensecheck.setOnClickListener{incomecheck.isChecked= !expensecheck.isChecked}
        incomecheck.setOnClickListener{expensecheck.isChecked= !incomecheck.isChecked}
        okbt.setOnClickListener{setuptemp()}
        cancelbt.setOnClickListener { name.setText("");valume.setText("") }
        cancelbt.text="Clear"

    }
    private fun callparent(){
        startActivity(Intent(this,StatementActivity::class.java))
    }
    private fun setuptemp() {
        if (name.text.toString()!="" && valume.text.toString()!="" && valume.text.toString()!="0"){}else{return}
        if (incomecheck.isChecked){
            if (tempincome !=""){tempincome+="__"}
            tempincome+=name.text.toString()+","+valume.text.toString()
        }else{
            if (tempexpense !=""){tempexpense+="__"}
            tempexpense+=name.text.toString()+","+valume.text.toString()}
            name.setText("");valume.setText("")
        callparent()
    }

    override fun onBackPressed() {
        callparent()
    }
}
