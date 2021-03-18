package com.example.demo.Model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.json.*;


public class MyModel {
    Map<String,dictionary_hash> map = new HashMap<String, dictionary_hash>();
    String current_acc;
    
    
    
    public MyModel(){

    }
    //update dictionary
    public void updateDictionary(String username, String Id_Account,String current_acc, String list_account, String last_success_date){
        dictionary_hash temp = new dictionary_hash();
        temp.Current_Acoount = current_acc;
        temp.Last_successful_aggregation_date = last_success_date;
        temp.Username = username;
        temp.List_Account = list_account;
        map.put(Id_Account,temp);
    }
    //get account balance from html file
    public String get_account_balance(String userName, String id_Account, String Last_Successful_Aggregation_Date) throws IOException {
        String[] arr_id;
        int res = 1;
        String[] arr_account;
        if(map.containsKey(id_Account)) {
            dictionary_hash temp = map.get(id_Account);
            current_acc = temp.Current_Acoount;
            if(current_acc != ""){
                return current_acc;
            }
        }
        //parse html
        Document doc_html =  Jsoup.connect("https://shirelzisso.github.io/mockbank/mock.html").get();
        for (Element e : doc_html.body().getElementsByClass("col-sm-6")) {
            if (!((e.text()).contains("ID"))){
                continue;
            }
            arr_id = e.text().split("ID");
            res = check(arr_id[1].substring(1),id_Account);
             if(res == 1) {  
                arr_account = doc_html.body().getElementsByClass("account").text().split(" ");
                updateDictionary(userName, id_Account,current_acc, "" , Last_Successful_Aggregation_Date);
                return arr_account[arr_account.length-1];
            }
        }
        return "This is not exist";
    }
    // compare if str1 == str2 
    public int check(String str1, String str2) {
        int index = 0;
        if(str1.length()!=str2.length()){
            return 2;
        }
        int n = str1.length();
        
        while(index<n){
            if(str1.charAt(index) != str2.charAt(index)){
                 return 0;
            }
            index++;
        }
        return 1;
     }
    
    public String get_list_of_transactions(String username,String Id_Account, String last_success_date) throws org.json.simple.parser.ParseException {
        
        String list_acc;

        if(map.containsKey(Id_Account)) {
            dictionary_hash temp = map.get(Id_Account);
            list_acc = temp.List_Account;
            if(list_acc != ""){
                return list_acc;
            }
        }
        String transactions;
        //JSON parser object to parse read file
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        Object obj;
        try {
            obj = parser.parse(new FileReader("https://github.com/shirelzisso/mockbank/blob/main/mock.json"));

            JSONObject json_obj = (JSONObject) obj;
            JSONArray accountList = (JSONArray) json_obj.get("accounts");

            for (int i = 0 ; i < accountList.length();i++) {
                JSONObject obj_account = accountList.getJSONObject(i);
                String id = obj_account.getString("account");
                if(check(id,Id_Account)==1){
                    transactions = obj_account.getString("transactions");
                    updateDictionary(username, Id_Account,current_acc, transactions , last_success_date);
                    return transactions;
                }
            }
  
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    
       return "This is not exist";
    }
    //-------------------------------------------------Bonus-------------------------------------------
    //every 24 hours I will clear the dictinary and read the Json file and HTML file.
    public void refresh_data() {
        map.clear();
        update_from_html();
        update_from_json();
    }

    private void update_from_json() {
    }

    private void update_from_html() {
    }
    //-----------------------------------------------------------------------------------------------
}
