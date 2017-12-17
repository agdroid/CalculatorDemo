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

    }

    private void displayView(TextView v, String mResultString) {
        v.setText(mResultString);
    }
}
