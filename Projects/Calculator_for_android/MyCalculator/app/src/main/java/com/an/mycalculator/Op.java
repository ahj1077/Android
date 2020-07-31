package com.an.mycalculator;

public class Op {
    double val1, val2;
    String oper;

    protected void setData(double val1, double val2, String oper) {
        this.val1 = val1;
        this.val2 = val2;
        this.oper = oper;
    }

}
