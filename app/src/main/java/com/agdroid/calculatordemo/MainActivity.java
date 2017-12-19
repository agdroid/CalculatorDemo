package com.agdroid.calculatordemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String mResultString = "";
    private TextView tv_display;

    Symbols symbols = new Symbols();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_display = (TextView) findViewById(R.id.tv_display);

        Button btn_one = (Button) findViewById(R.id.btn_number_one);
        Button btn_two = (Button) findViewById(R.id.btn_number_two);
        Button btn_three = (Button) findViewById(R.id.btn_number_three);
        Button btn_plus = (Button) findViewById(R.id.btn_plus);
        Button btn_enter = (Button) findViewById(R.id.btn_enter);
        Button btn_clear = (Button) findViewById(R.id.btn_clear);


        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
        btn_clear.setOnClickListener(this);


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


    //Hier landen alle Click-Events, die auf irgendeinen Button in der View erfolgen
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_number_one:
                mResultString += "1";
                break;
            case R.id.btn_number_two:
                mResultString += "2";
                break;
            case R.id.btn_number_three:
                mResultString += "3";
                break;
            case R.id.btn_plus:
                mResultString += "+";
                break;
            case R.id.btn_enter:
                try {
                    Double result = symbols.eval(mResultString);
                    mResultString = Double.toString(result);
                } catch (SyntaxException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_clear:
                if (mResultString.length() > 0) {
                    mResultString = mResultString.substring(0, mResultString.length() - 1);
                }
                break;

        }
        displayView(tv_display, mResultString);

    }
}
