package com.agdroid.calculatordemo;

import android.view.View;

/**
 * Created by andre on 20.12.2017.
 *
 * CalculationPresenter managed nur den Transfer der Daten von der View zum Model (Calculation)
 * Hier wird weder was angezeigt, noch findet eine Berechnung statt,
 */

public class CalculatorPresenter implements
        CalculatorContract.ForwardInteractionToPresenter,
        Calculation.CalculationResult {

    private CalculatorContract.PublishToView publishResult;
    private Calculation calc;

    //Die Übergebende View muss ebenfalls CalculatorContract.PublishToView implementieren,
    //um auch vom Type CalculatorContract.PublishToView zu sein.
    public CalculatorPresenter(CalculatorContract.PublishToView publishResult) {
        this.publishResult = publishResult;
        this.calc = new Calculation();
        calc.setCalculationResultListener(this);
    }

    @Override
    public void onNumberClick(int number) {
        calc.appendNumber(Integer.toString(number));
    }

    @Override
    public void onOperatorClick(String operator) {
        calc.appendOperator(operator);
    }

    @Override
    public void onEvaluateClick() {
        calc.performEvaluation();
    }

    @Override
    public void onDeleteShortClick() {
        calc.deleteCharacter();
    }

    @Override
    public void onDeleteLongClick() {
        calc.deleteExpression();
    }

    @Override
    public void onExpressionChanged(String result, boolean successful) {
        if (successful) {
            publishResult.showResult(result);
        } else {
            publishResult.showToastMessage(result);
        }
    }
}
