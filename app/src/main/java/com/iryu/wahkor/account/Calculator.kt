package com.iryu.wahkor.account


class Calculator{

    fun getStock():Int{
        var money=0
        for (i in 1 until data_stock_first.split(",").size){
            money+=(-data_stock_first.split(",")[i].toInt()+
                    evalstring.eval(data_stock_last.split(",")[i]).toInt())*
                    data_stock_price.split(",")[i].toInt()}
        return money
    }
    fun getAirpay():Int{
        var stockmoney=0
        for (i in 1 until data_airpay_first.split(",").size){
            stockmoney+=(-data_airpay_first.split(",")[i].toInt()+
                    evalstring.eval(data_airpay_last.split(",")[i]).toInt())*
                    data_airpay_price.split(",")[i].toInt()}
        return stockmoney
    }
    fun getFstock():Int{


        var money=0.0
        for (i in 1 until data_fstock_first.split(",").size){
            money+=evalstring.eval(
                    "("+data_fstock_first.split(",")[i]+"-"+
                    data_fstock_last.split(",")[i]+")*"+
                    data_fstock_price.split(",")[i])}
        return money.toInt()

    }
    fun getIncome():Int{
        var money=0
        if(IncomeData==""){return 0}else {
        for (i in 0 until IncomeData.split("<&&>").size){
            money+= IncomeData.split("<&&>")[i].split(",")[2].toInt()
        }
        }
        return money
    }

fun getExpense():Int{
    var money=0
    if(ExpenseData==""){return 0}else {
        for (i in 0 until ExpenseData.split("<&&>").size){
            money+=ExpenseData.split("<&&>")[i].split(",")[2].toInt()
        }
    }
    return money
}}