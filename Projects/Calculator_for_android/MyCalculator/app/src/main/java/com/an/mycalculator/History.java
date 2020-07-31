package com.an.mycalculator;
import java.util.ArrayList;

public class History{
    public static ArrayList<String> history = new ArrayList<>();


    public void insertHistory(String hist){
        if(history.size() == 10){
            history.remove(0);
        }
        history.add(hist);
    }

    public void removeAll(){
        history.clear();
    }


}
