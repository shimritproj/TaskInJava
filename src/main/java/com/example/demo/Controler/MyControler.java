package com.example.demo.Controler;


import java.io.IOException;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;

import com.example.demo.Model.MyModel;
import com.example.demo.View.MyView;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyControler{
    MyView view;
    MyModel model;
    String account_balance;
    String list_of_transactions;
    //------------------input----------------------
    String UserName = "Shimrit";
    String Id_Account ="7b5092b8-73f1-471d-96d8-31b20dca999b";
    String Last_Successful_Aggregation_Date = "3-14-2021";
     //----------------------------------------------
    public MyControler() {
        view = new MyView();
        model = new MyModel();
    }
    @RequestMapping(method = RequestMethod.GET, path = "/result")
    public String getOutput() throws IOException, ParseException {
     //---------------------------------------------------------Bonus--------------------------------------------
     /*   ScheduledExecutorService execService = Executors.newScheduledThreadPool(1);
        execService.scheduleAtFixedRate(() -> {
            model.refresh_data();
        }, 0, 24, TimeUnit.HOURS);*/
        //--------------------------------------------------------------------------------------------------------
        account_balance = model.get_account_balance(UserName, Id_Account, Last_Successful_Aggregation_Date);
        list_of_transactions = model.get_list_of_transactions(UserName,Id_Account, Last_Successful_Aggregation_Date);
        return view.result(account_balance,list_of_transactions);
    }
}
