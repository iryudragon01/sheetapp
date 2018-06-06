package com.iryu.wahkor.account

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


lateinit var callmain:Intent
var iscallmian=0
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val submitbt=findViewById<Button>(R.id.summitbt)
        submitbt.setOnClickListener {login();}
        callmain=Intent(this,MainActivity::class.java)
        refilldetail="";refillsum=""; GoogleID=""; GoogleUser="";GoogleLevel=0
         newfood=""; IncomeData="";ExpenseData="";
         return_stockdata ="";tempexpense=""
        tempincome="";del_income="";del_expense="";
    }

    private fun login() {
        val username=findViewById<EditText>(R.id.userbox)
        val password=findViewById<EditText>(R.id.passbox)
        val suburl="callfrom=android&action=login&user=${username.text.toString()}&password=${password.text.toString()}"
        GoogleScript().execute(suburl)
    }
      inner class GoogleScript:GoogleSheet(){
        override fun onPostExecute(result: String?) {
            val unwarp=(result as String).split("<||>")
            if (unwarp[0]=="login") {
                val parm = unwarp[1].split(",")
                GoogleUser = parm[0]
                GoogleLevel = parm[1].toInt()
                GoogleID = parm[2]
                getdatatime=0
                iscallmian=1
                GoogleSheet().execute("action=getdataall&GoogleId=${GoogleID}")
                startActivity(callmain)
            }else{
                Toast.makeText(this@LoginActivity,result,Toast.LENGTH_LONG).show()
            }
        }
    }//end inner  class

    override fun onBackPressed() {
        //finish()
        finishAffinity()
    }
}
