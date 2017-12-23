package com.agdroid.calculatordemo;

/**
 * Created by andre on 19.12.2017.
 */

public interface CalculatorContract {

    //Überträgt Ergebnis vom Presenter zur View
    interface PublishToView {
        void showResult(String result);
        // Für Fehlermeldung bei ungültigen Resultat
        void showToastMessage(String message);
    }

    //Übermittelt Click-Events von der View zum Presenter
    interface ForwardInteractionToPresenter {
        void onNumberClick(int number);
        void onDecimalClick();
        void onOperatorClick(String operator);
        void onEvaluateClick();
        void onDeleteShortClick();
        void onDeleteLongClick();
   }

}
