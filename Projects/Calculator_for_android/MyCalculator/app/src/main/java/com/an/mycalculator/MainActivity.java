package com.an.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Calculator myCal;
    TextView tv_screen;

    Button[] btn_numeric;
    Button btn_c;
    Button btn_sum;
    Button btn_sub;
    Button btn_div;
    Button btn_mul;
    Button btn_dot;
    Button btn_inv;

    Button btn_log;
    Button btn_n;
    Button btn_exp;

    Button btn_hist;
    Button btn_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_reset:
                if(myCal.power){
                    myCal.powerOff();
                    myCal.powerOn();
                    tv_screen.setText("0");
                }
                return true;

            case R.id.menu_power:
                if(!myCal.power) {
                    tv_screen.setText("0");
                    myCal.powerOn();
                }
                else {
                    tv_screen.setText("");
                    myCal.powerOff();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void init(){
        myCal = new Calculator();
        tv_screen = findViewById(R.id.tv_screen);

        btn_numeric = new Button[10];

        btn_numeric[0] = findViewById(R.id.btn_0);
        btn_numeric[1] = findViewById(R.id.btn_1);
        btn_numeric[2] = findViewById(R.id.btn_2);
        btn_numeric[3] = findViewById(R.id.btn_3);
        btn_numeric[4] = findViewById(R.id.btn_4);
        btn_numeric[5] = findViewById(R.id.btn_5);
        btn_numeric[6] = findViewById(R.id.btn_6);
        btn_numeric[7] = findViewById(R.id.btn_7);
        btn_numeric[8] = findViewById(R.id.btn_8);
        btn_numeric[9] = findViewById(R.id.btn_9);

        btn_c = findViewById(R.id.btn_c);
        btn_sum = findViewById(R.id.btn_sum);
        btn_sub = findViewById(R.id.btn_sub);
        btn_mul = findViewById(R.id.btn_mul);
        btn_div = findViewById(R.id.btn_div);
        btn_log = findViewById(R.id.btn_log);
        btn_n = findViewById(R.id.btn_n);
        btn_exp = findViewById(R.id.btn_exp);

        btn_dot =findViewById(R.id.btn_dot);
        btn_inv = findViewById(R.id.btn_inv);
        btn_result = findViewById(R.id.btn_result);
        btn_hist = findViewById(R.id.btn_hist);

        final char arr[]= {'0','1','2','3','4','5','6','7','8','9'};

        for(int i = 0; i < 10; i++) {
            final char num = arr[i];
            btn_numeric[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(myCal.error){
                        errorState(num);
                        return;
                    }

                    if(myCal.power) {
                        if (myCal.inputMode) {
                            myCal.currentValue = myCal.currentValue + num;
                        } else {
                            myCal.currentValue = num + "";
                            myCal.inputMode = true;
                        }

                        tv_screen.setText(myCal.currentValue);
                    }
                }
            });
        }

        btn_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myCal.power) {
                    myCal.currentValue = "0";
                    myCal.currentOperation = "=";
                    myCal.currentResult = 0;
                    myCal.inputMode = false;
                    myCal.floatMode = false;
                    tv_screen.setText(myCal.currentValue);
                }
            }
        });

        btn_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myCal.power) {
                    calculate();

                    myCal.currentResult = Double.parseDouble(myCal.currentValue);
                    myCal.currentOperation = "+";
                    myCal.inputMode = false;
                    myCal.floatMode = false;
                    tv_screen.setText(myCal.currentValue);
                }
            }
        });

        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myCal.power) {
                    calculate();

                    myCal.currentResult = Double.parseDouble(myCal.currentValue);
                    myCal.currentOperation = "-";
                    myCal.inputMode = false;
                    myCal.floatMode = false;
                    tv_screen.setText(myCal.currentValue);
                }
            }
        });

        btn_mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myCal.power) {
                    calculate();

                    myCal.currentResult = Double.parseDouble(myCal.currentValue);
                    myCal.currentOperation = "*";
                    myCal.inputMode = false;
                    myCal.floatMode = false;

                    tv_screen.setText(myCal.currentValue);
                }
            }
        });

        btn_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myCal.power) {
                    calculate();
                    myCal.currentResult = Double.parseDouble(myCal.currentValue);
                    myCal.currentOperation = "/";
                    myCal.inputMode = false;
                    myCal.floatMode = false;

                    tv_screen.setText(myCal.currentValue);
                }
            }
        });

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myCal.power) {
                    calculate();
                    myCal.currentOperation = "log";
                    myCal.expOp.setData(Double.parseDouble(myCal.currentValue), 0, "log");
                    myCal.currentResult = myCal.expOp.opType();
                    myCal.log = "log(" + myCal.currentValue + ")";
                    myCal.currentValue = Double.toString(myCal.currentResult);
                    myCal.inputMode = false;
                    myCal.floatMode = false;

                    tv_screen.setText(myCal.currentValue);

                }
            }
        });

        btn_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myCal.power) {
                    calculate();
                    myCal.log = myCal.currentValue;
                    myCal.currentResult = Double.parseDouble(myCal.currentValue);
                    myCal.currentOperation = "^";
                    myCal.inputMode = false;
                    myCal.floatMode = false;

                    myCal.currentValue = "0";
                    tv_screen.setText(myCal.currentValue);
                }
            }
        });

        btn_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myCal.power) {
                    calculate();
                    myCal.currentResult = Double.parseDouble(myCal.currentValue);
                    myCal.expOp.setData(Math.E, myCal.currentResult, "exp");
                    myCal.currentResult = myCal.expOp.opType();
                    myCal.currentOperation = "exp";
                    myCal.inputMode = false;
                    myCal.floatMode = false;
                    myCal.currentValue = Double.toString(myCal.currentResult);
                    tv_screen.setText(myCal.currentValue);
                }
            }
        });

        btn_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myCal.power) {
                    if (myCal.currentValue.charAt(0) == '-') {
                        myCal.currentValue = myCal.currentValue.substring(1);
                    } else {
                        myCal.currentValue = "-" + myCal.currentValue;
                    }
                    tv_screen.setText(myCal.currentValue);
                }
            }
        });

        btn_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myCal.power) {
                    if (!myCal.floatMode) {
                        myCal.currentValue = myCal.currentValue + ".";
                        myCal.floatMode = true;
                        tv_screen.setText(myCal.currentValue);
                    }
                }
            }
        });

        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myCal.error){
                    errorState('0');
                    return;
                }
                if(myCal.power) {
                    calculate();
                    if(myCal.error) return;
                    myCal.log += (" = " + myCal.currentValue);
                    myCal.history.insertHistory(myCal.log);
                    myCal.log = "";
                }
            }
        });

        btn_hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
    public void errorState(char num){
        myCal.error = false;
        myCal.currentValue = num+"";
        myCal.currentOperation = "=";
        myCal.currentResult = 0;
        myCal.inputMode = false;
        myCal.floatMode = false;
        tv_screen.setText(myCal.currentValue);
    }

    public void calculate(){

        //make history string
        if(!myCal.currentOperation.equals("=")){
            if(myCal.currentOperation.equals("/") && myCal.currentValue .equals("0")){
                myCal.currentValue = "0";
                myCal.currentResult = 0;
                myCal.log = "";
                myCal.inputMode = false;
                myCal.floatMode = false;
                tv_screen.setText("Error");

                myCal.error = true;
                return;
            }
            if(!myCal.currentOperation.equals("log") && !myCal.currentOperation.equals("exp"))
                myCal.log += (" " + myCal.currentOperation + " " + myCal.currentValue);
        }
        else {
            myCal.log = myCal.currentValue;
        }


        if(myCal.currentOperation.equals("+") || myCal.currentOperation.equals("-") || myCal.currentOperation.equals("*") || myCal.currentOperation.equals("/")) {
            myCal.basicOp.setData(myCal.currentResult, Double.parseDouble(myCal.currentValue), myCal.currentOperation);
            double result = myCal.basicOp.opType();
            myCal.currentValue = Double.toString(result);
            myCal.currentResult = result;
        }
        else if(myCal.currentOperation.equals("=")){
            //do nothing
        }
        else if(myCal.currentOperation.equals("log")){
            //do nothing
        }
        else{
            if(myCal.currentOperation.equals("^")){
                myCal.expOp.setData(myCal.currentResult, Double.parseDouble(myCal.currentValue), myCal.currentOperation);
            }
            else if(myCal.currentOperation.equals("exp")){
                myCal.expOp.setData(Math.E, Double.parseDouble(myCal.currentValue), myCal.currentOperation);
            }

            double result = myCal.expOp.opType();
            myCal.currentValue = Double.toString(result);
            myCal.currentResult = result;
        }

        myCal.currentOperation = "=";
        myCal.inputMode = false;
        myCal.floatMode = true;
        tv_screen.setText(myCal.currentValue);
    }

}
