package com.an.mycalculator;

public class ExpOp extends Op{

    public double opType () {
        if (oper.equals("log")) {
            return log();
          }
        else  {
            return exp();
        }
    }
    //opType METHOD : when oper is log(a,b) or exp(a,b)
    // when exp, set val1 as constant "e"


    double log () {
        double logAb = Math.log(val1);
        return logAb;
    }

    double exp () {
        double expVal = Math.pow(val1, val2);
        return expVal;
    }

    // EXP METHOD : (val1)^(val2), when exp (e^n), set val1 as constant "e"


}
