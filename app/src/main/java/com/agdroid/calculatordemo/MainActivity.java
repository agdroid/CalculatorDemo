package com.agdroid.calculatordemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayFragment displayFragment =
                (DisplayFragment) getSupportFragmentManager().findFragmentById(R.id.frag_display);

        InputFragment inputFragment =
                (InputFragment) getSupportFragmentManager().findFragmentById(R.id.frag_input);

        // Der Kostruktor erwartet den Type "CalculatorContract.PublishToView".
        // displayFragment ist auch vom Type CalculatorContract.PublishToView wegen Interface
        CalculatorPresenter presenter = new CalculatorPresenter(displayFragment);

        //setPresenter vrlangt Type "CalculatorContract.ForwardInteractionToPresenter".
        //Der presenter hat den aus Inerface.
        inputFragment.setPresenter(presenter);

    }

}
