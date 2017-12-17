package com.agdroid.calculatordemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String mResultString = "";
    private TextView tv_display;

    private Button btn_one;
    private Button btn_two;
    private Button btn_three;
    private Button btn_plus;
    private Button btn_enter;
    private Button btn_clear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_display = (TextView) findViewById(R.id.tv_display);

        btn_one = (Button) findViewById(R.id.btn_number_one);
        btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultString += "1";
                displayView(tv_display, mResultString);
            }
        });

        btn_two = (Button) findViewById(R.id.btn_number_two);
        btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultString += "2";
                displayView(tv_display, mResultString);
            }
        });

        btn_three = (Button) findViewById(R.id.btn_number_three);
        btn_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultString += "3";
                displayView(tv_display, mResultString);
            }
        });

        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultString += "+";
                displayView(tv_display, mResultString);
            }
        });

        btn_enter = (Button) findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultString = "Berechnung fehlt noch...";
                displayView(tv_display, mResultString);
                mResultString = "";
            }
        });

        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResultString.length() > 0) {
                    mResultString = mResultString.substring(0, mResultString.length() - 1);
                }
                displayView(tv_display, mResultString);
            }
        });

        btn_clear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mResultString = "";
                displayView(tv_display, mResultString);
                return true;
            }
        });


    }

    private void displayView(TextView v, String mResultString) {
        v.setText(mResultString);
    }
}
