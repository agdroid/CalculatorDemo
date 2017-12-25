package com.agdroid.calculatordemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by andre on 24.12.2017.
 */

public class InputFragment extends Fragment implements View.OnClickListener {

    private CalculatorContract.ForwardInteractionToPresenter forwardInteraction;

    Button btn_one, btn_two, btn_three, btn_decimal, btn_plus, btn_enter, btn_clear;

    public void setPresenter (CalculatorContract.ForwardInteractionToPresenter fI) {
        this.forwardInteraction = fI;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_input, container, false);

        btn_one = (Button) v.findViewById(R.id.btn_number_one);
        btn_two = (Button) v.findViewById(R.id.btn_number_two);
        btn_three = (Button) v.findViewById(R.id.btn_number_three);
        btn_decimal = (Button) v.findViewById(R.id.btn_decimal);
        btn_plus = (Button) v.findViewById(R.id.btn_plus);
        btn_enter = (Button) v.findViewById(R.id.btn_enter);
        btn_clear = (Button) v.findViewById(R.id.btn_clear);

        btn_one.setOnClickListener(this);  //this sthet hier für inputFragment
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_decimal.setOnClickListener(this);
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
            case R.id.btn_decimal:
                forwardInteraction.onDecimalClick();
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
