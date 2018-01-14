package com.agdroid.calculatordemo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_DISPLAY_FRAGMENT = "tagdisplayfragment";
    private static final String TAG_INPUT_FRAGMENT = "taginputfragment";

    private FragmentManager manager;
    private DisplayFragment displayFragment;
    private InputFragment inputFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();

        //Erster Programmaufruf
        //Besser wäre zu testen, ob Fragments in ELSE-Zweig wirklich vorliegen
        if (savedInstanceState == null) {
            displayFragment = DisplayFragment.newInstance();
            inputFragment = InputFragment.newInstance();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.frag_display, displayFragment, TAG_DISPLAY_FRAGMENT);
            transaction.add(R.id.frag_input, inputFragment, TAG_INPUT_FRAGMENT);
            transaction.commit();
        } else {
            //mit savedInstanceState automatisch wiederhergestellt -> Variablen erneut zuweisen
            displayFragment = (DisplayFragment) manager.findFragmentByTag(TAG_DISPLAY_FRAGMENT);
            inputFragment = (InputFragment) manager.findFragmentByTag(TAG_INPUT_FRAGMENT);
        }

        // Der Konstruktor erwartet den Type "CalculatorContract.PublishToView".
        // displayFragment ist auch vom Type CalculatorContract.PublishToView wegen Interface
        CalculatorPresenter presenter = new CalculatorPresenter(displayFragment);

        //setPresenter vrlangt Type "CalculatorContract.ForwardInputInteractionToPresenter".
        //Der presenter hat den aus Interface.
        inputFragment.setPresenter(presenter);
        displayFragment.setPresenter(presenter);


    }

}
