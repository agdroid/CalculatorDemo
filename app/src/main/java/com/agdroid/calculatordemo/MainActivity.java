package com.agdroid.calculatordemo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();

        DisplayFragment displayFragment = DisplayFragment.newInstance();
        InputFragment inputFragment = InputFragment.newInstance();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frag_display, displayFragment);
        transaction.add(R.id.frag_input, inputFragment);
        transaction.commit();

        // Der Kostruktor erwartet den Type "CalculatorContract.PublishToView".
        // displayFragment ist auch vom Type CalculatorContract.PublishToView wegen Interface
        CalculatorPresenter presenter = new CalculatorPresenter(displayFragment);

        //setPresenter vrlangt Type "CalculatorContract.ForwardInteractionToPresenter".
        //Der presenter hat den aus Inerface.
        inputFragment.setPresenter(presenter);

    }

}
