package com.agdroid.calculatordemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        CalculatorContract.PublishToView,
        View.OnClickListener {

    TextView tv_display;
    CalculatorPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_display = (TextView) findViewById(R.id.tv_display);

        Button btn_one = (Button) findViewById(R.id.btn_number_one);
        Button btn_two = (Button) findViewById(R.id.btn_number_two);
        Button btn_three = (Button) findViewById(R.id.btn_number_three);
        Button btn_decimal = (Button) findViewById(R.id.btn_decimal);
        Button btn_plus = (Button) findViewById(R.id.btn_plus);
        Button btn_enter = (Button) findViewById(R.id.btn_enter);
        Button btn_clear = (Button) findViewById(R.id.btn_clear);

        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_decimal.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
        btn_clear.setOnClickListener(this);

        btn_clear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                presenter.onDeleteLongClick();
                return true;
            }
        });

        //"this" funktioniert, weil die MainActivity "CalculatorContract.PublishToView"
        //implementiert und damit auch vom Type publishedResult (CalculatorContract.PublishToView) ist.
        presenter = new CalculatorPresenter(this);
    }

    //Hier landen alle Click-Events, die auf irgendeinen Button in der View erfolgen
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_number_one:
                presenter.onNumberClick(1);
                break;
            case R.id.btn_number_two:
                presenter.onNumberClick(2);
                break;
            case R.id.btn_number_three:
                presenter.onNumberClick(3);
                break;
            case R.id.btn_decimal:
                presenter.onDecimalClick();
                break;
            case R.id.btn_plus:
                presenter.onOperatorClick("+");
                break;
            case R.id.btn_enter:
                presenter.onEvaluateClick();
                break;
            case R.id.btn_clear:
                presenter.onDeleteShortClick();
                break;
        }
    }


    @Override
    public void showResult(String result) {
        tv_display.setText(result);
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
