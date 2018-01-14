package com.agdroid.calculatordemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by andre on 24.12.2017.
 */

public class DisplayFragment extends Fragment implements CalculatorContract.PublishToView {

    private static final String KEY_TV_DISPLAY = "keytvdisplay";

    private CalculatorContract.ForwardDisplayInteractionToPresenter forwardInteraction;
    private TextView tv_display;

    public DisplayFragment() {
        // Required empty public constructor
    }

    public static DisplayFragment newInstance() {
        return new DisplayFragment();
    }

    public void setPresenter (CalculatorContract.ForwardDisplayInteractionToPresenter fI) {
        this.forwardInteraction = fI;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_display, container, false);
        tv_display = (TextView) v.findViewById(R.id.tv_display);
        return v;
    }

    @Override
    public void showResult(String result) {
        tv_display.setText(result);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TV_DISPLAY,tv_display.getText().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            String rowOne = savedInstanceState.getString(KEY_TV_DISPLAY, "Fehler");
            tv_display.setText(rowOne);
            forwardInteraction.onRestartDisplay(rowOne);
        }
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
