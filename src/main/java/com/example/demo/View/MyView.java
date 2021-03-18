package com.example.demo.View;




public class MyView {
    
    public String result(String account_balance, String list_of_transactions){
       
        String s;
        s = "Financial aggregation data :  ";
        s+= "account balance : "+account_balance+" and list of transactions : "+list_of_transactions;
        return s;
       
    }  
}
