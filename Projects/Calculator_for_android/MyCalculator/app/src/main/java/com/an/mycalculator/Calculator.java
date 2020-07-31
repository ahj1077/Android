package com.an.mycalculator;

public class Calculator{
    History history;
    BasicOp basicOp;
    ExpOp expOp;

    public boolean power;
    public boolean error;
    public boolean inputMode;
    public boolean floatMode;
    public String currentValue = "0";
    public double currentResult = 0;
    public String currentOperation = "=";
    public String log = "";

    Calculator(){
        history = new History();
        basicOp= new BasicOp();
        expOp = new ExpOp();
        init();
    }

    public void init(){
        currentValue = "0";
        currentResult = 0;
        currentOperation = "=";
        log = "";
        inputMode = false;
        floatMode = false;
        power = false;
    }

    public void powerOn(){
        if(!power){
            init();
            power = true;
        }
    }

    public void powerOff(){
        if(power){
            history.removeAll();
            init();
        }
    }
}
