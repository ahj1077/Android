package com.an.mycalculator;

public class BasicOp extends Op{

    double opType() {
        switch(oper) {
            case "+":
                return sum(val1,val2);
            case "-":
                return sub(val1,val2);
            case "*":
                return mul(val1,val2);
            case "/":
                return div(val1,val2);
            default:
                return -1;
        }
    }


    double sum (double a, double b) {
        double result = a+b;
        return result;
    }

    double sub (double a, double b) {
        double result = a-b;
        return result;
    }

    double mul (double a, double b) {
        double result = a*b;
        return result;
    }

    double div (double a, double b) {
        if(b!=0) {
            double result = a/b;
            return result;
        }
        return 0;
    }
}

