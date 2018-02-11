package com.agdroid.calculatordemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

/**
 * Created by andre on 24.12.2017.
 */

public class InputFragment extends Fragment implements View.OnClickListener {

    private CalculatorContract.ForwardInputInteractionToPresenter forwardInteraction;

    Button btn_one, btn_two, btn_three, btn_four, btn_five, btn_six, btn_seven, btn_eight, btn_nine,
            btn_zero, btn_decimal, btn_divide, btn_multiply, btn_subtrac, btn_plus,
            btn_enter, btn_clear;

    public InputFragment() {
        // Required empty public constructor
    }

    public static InputFragment newInstance(){
        return new InputFragment();
    }

    public void setPresenter (CalculatorContract.ForwardInputInteractionToPresenter fI) {
        this.forwardInteraction = fI;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NumberFormat numberFormat = NumberFormat.getInstance();  //holt lokale Einstellungen
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        DecimalFormatSymbols formatSymbols = decimalFormat.getDecimalFormatSymbols();
        Character decimalSeparator = formatSymbols.getDecimalSeparator();

        View v = inflater.inflate(R.layout.fragment_input, container, false);

        btn_one = (Button) v.findViewById(R.id.btn_number_one);
        btn_two = (Button) v.findViewById(R.id.btn_number_two);
        btn_three = (Button) v.findViewById(R.id.btn_number_three);
        btn_four = (Button) v.findViewById(R.id.btn_number_four);
        btn_five = (Button) v.findViewById(R.id.btn_number_five);
        btn_six = (Button) v.findViewById(R.id.btn_number_six);
        btn_seven = (Button) v.findViewById(R.id.btn_number_seven);
        btn_eight = (Button) v.findViewById(R.id.btn_number_eight);
        btn_nine = (Button) v.findViewById(R.id.btn_number_nine);
        btn_zero = (Button) v.findViewById(R.id.btn_number_zero);
        btn_decimal = (Button) v.findViewById(R.id.btn_decimal);
        btn_plus = (Button) v.findViewById(R.id.btn_plus);
        btn_subtrac = (Button) v.findViewById(R.id.btn_subtrac);
        btn_divide = (Button) v.findViewById(R.id.btn_divide);
        btn_multiply = (Button) v.findViewById(R.id.btn_multiply);
        btn_enter = (Button) v.findViewById(R.id.btn_enter);
        btn_clear = (Button) v.findViewById(R.id.btn_clear);

        btn_one.setOnClickListener(this);  //this sthet hier für inputFragment
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        btn_five.setOnClickListener(this);
        btn_six.setOnClickListener(this);
        btn_seven.setOnClickListener(this);
        btn_eight.setOnClickListener(this);
        btn_nine.setOnClickListener(this);
        btn_zero.setOnClickListener(this);
        btn_decimal.setOnClickListener(this);
        btn_decimal.setText(decimalSeparator.toString());  //je nach Länderkennung
        btn_divide.setOnClickListener(this);
        btn_multiply.setOnClickListener(this);
        btn_subtrac.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
        btn_clear.setOnClickListener(this);

        btn_clear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                forwardInteraction.onDeleteLongClick();
                return true;
            }
        });

        return v;
    }

    //Hier landen alle Click-Events, die auf irgendeinen Button in der View erfolgen
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_number_one:
                forwardInteraction.onNumberClick(1);
                break;
            case R.id.btn_number_two:
                forwardInteraction.onNumberClick(2);
                break;
            case R.id.btn_number_three:
                forwardInteraction.onNumberClick(3);
                break;
            case R.id.btn_number_four:
                forwardInteraction.onNumberClick(4);
                break;
            case R.id.btn_number_five:
                forwardInteraction.onNumberClick(5);
                break;
            case R.id.btn_number_six:
                forwardInteraction.onNumberClick(6);
                break;
            case R.id.btn_number_seven:
                forwardInteraction.onNumberClick(7);
                break;
            case R.id.btn_number_eight:
                forwardInteraction.onNumberClick(8);
                break;
            case R.id.btn_number_nine:
                forwardInteraction.onNumberClick(9);
                break;
            case R.id.btn_number_zero:
                forwardInteraction.onNumberClick(0);
                break;
            case R.id.btn_decimal:
                forwardInteraction.onDecimalClick();
                break;
            case R.id.btn_divide:
                forwardInteraction.onOperatorClick("÷");
                break;
            case R.id.btn_multiply:
                forwardInteraction.onOperatorClick("×");
                break;
            case R.id.btn_subtrac:
                forwardInteraction.onOperatorClick("-");
                break;
            case R.id.btn_plus:
                forwardInteraction.onOperatorClick("+");
                break;
            case R.id.btn_enter:
                forwardInteraction.onEvaluateClick();
                break;
            case R.id.btn_clear:
                forwardInteraction.onDeleteShortClick();
                break;
        }
    }

}
