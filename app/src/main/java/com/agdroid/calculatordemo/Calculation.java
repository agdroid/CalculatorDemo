package com.agdroid.calculatordemo;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

/**
 * Created by andre on 20.12.2017.
 * <p>
 * Prüft auf korrekte Eingabewerte, berechnet den Ausdruck oder erzeugt Fehlermeldung
 */

public class Calculation {

    private final Symbols symbols;
    private CalculationResult calculationResult;
    private final int MAX_INPUT = 100;

    //Ausdruck der berechnet werden soll wie "45*10"
    private static String currentExpression;

    interface CalculationResult {
        void onExpressionChanged(String result, boolean successful);
    }

    public void setCalculationResultListener(CalculationResult calculationResult) {
        //calculationResult zeigt auf CalculatorPresenter@????
        this.calculationResult = calculationResult;
        currentExpression = "";
    }

    public Calculation() {
        symbols = new Symbols();
    }

    public void setCurrentExpression(String currentExpression) {
        Calculation.currentExpression = currentExpression;
        calculationResult.onExpressionChanged(currentExpression, true);
    }

    /**
     * Die folgenden 6 Funktionen reagieren auf die 6 interfaces von CalculatorContract
     */

    /**
     * #1 Reaktion auf  void onDeleteShortClick();
     * Delete a single character from currentExpression, unless empty
     * "" - invalid
     * 543 - valid
     * 45*65 - valid
     */
    public void deleteCharacter() {
        if (currentExpression.length() > 0) {
            currentExpression = currentExpression.substring(0, currentExpression.length() - 1);
            calculationResult.onExpressionChanged(currentExpression, true);
        } else {
            calculationResult.onExpressionChanged("Invalid Input", false);
        }
    }

    /**
     * #2 Reaktion auf  void onDeleteLongClick();
     * Delete entire expression unless empty
     * "" - invalid
     */
    public void deleteExpression() {
        if (currentExpression.equals("")) {
            calculationResult.onExpressionChanged("Invalid Input", false);
        }
        currentExpression = "";
        calculationResult.onExpressionChanged(currentExpression, true);
    }

    /**
     * #3 Reaktion auf  void onNumberClick(int number);
     * Append number to currentExpression if valid:
     * "0" & number is 0 - invalid
     * "12345678909876543" - invalid
     *
     * @param number
     */
    public void appendNumber(String number) {
        if (currentExpression.startsWith("0") && number.equals("0")) {
            calculationResult.onExpressionChanged("Invalid Input", false);
        } else {
            if (currentExpression.length() <= MAX_INPUT) {
                currentExpression += number;
                calculationResult.onExpressionChanged(currentExpression, true);
            } else {
                calculationResult.onExpressionChanged("Expression Too Long", false);
            }
        }
    }

    /**
     * #4 Reaktion auf  void onOperatorClick(String operator);
     * Append an Operator to currentExpression, if valid:
     * 56 - valid
     * 56* - invalid
     * 56*2 - valid
     * "" - invalid
     *
     * @param operator one of:
     *                 - "×" -> Für arity ersetzt später ersetzt mit "*"
     *                 - "÷"
     *                 - "-" -> Für arity später ersetzt mit "/"
     *                 - "+"
     */
    public void appendOperator(String operator) {
        if (validateExpression(currentExpression)) {
            currentExpression += operator;
            calculationResult.onExpressionChanged(currentExpression, true);
        }
    }


    /**
     * #5 Reaktion auf   void onDecimalClick();
     * See type comment for appendOperator
     */
    public void appendDecimal() {
        NumberFormat numberFormat = NumberFormat.getInstance();  //holt lokale Einstellungen
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        DecimalFormatSymbols formatSymbols = decimalFormat.getDecimalFormatSymbols();
        Character decimalSeparator = formatSymbols.getDecimalSeparator();

        if (validateExpression(currentExpression)) {
            currentExpression += decimalSeparator.toString();
            calculationResult.onExpressionChanged(currentExpression, true);
        }
    }


    /**
     * #6 Reaktion auf  void onEvaluateClick();
     * If currentExpression passes checks, pass currentExpression to symbols object, \
     * then return the result.
     */
    public void performEvaluation() {
        if (validateExpression(currentExpression)) {
            try {
                Double result = symbols.eval(prepareEvaluation(currentExpression));
                currentExpression = formatExpressionForDisplay(result);
                calculationResult.onExpressionChanged(currentExpression, true);
            } catch (SyntaxException e) {
                calculationResult.onExpressionChanged("Invalid Input", false);
                e.printStackTrace();
            }
        }
    }

    /**
     * Helper function to validate expressions against the following checks:
     * "" - invalid;
     * 8765 - valid
     *
     * @param expression
     * @return
     */
    public boolean validateExpression(String expression) {
        if (expression.endsWith("×") ||
                expression.endsWith("÷") ||
                expression.endsWith("-") ||
                expression.endsWith("+")
                ) {
            calculationResult.onExpressionChanged("Invalid Input", false);
            return false;
        } else if (expression.equals("")) {
            calculationResult.onExpressionChanged("Empty Expression", false);
            return false;
        } else if (expression.length() > MAX_INPUT) {
            calculationResult.onExpressionChanged("Expression Too Long", false);
            return false;
        } else {
            return true;
        }
    }


    // Formatiert das Ergebnis für die Ausgabe
    private String formatExpressionForDisplay(Double doubleNumber) {
        final int DIGITS = 12; // max. 12 echte Ziffern zzgl. E-Notation
        final double MAX = Math.pow(10, DIGITS) - 1;
        final double MIN = Math.pow(10, -(DIGITS - 1));
        String pattern = "";
        int stellenVorKomma;
        long wertVorKomma;

        NumberFormat numberFormat = NumberFormat.getInstance();  //holt lokale Einstellungen
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;

        //Berechnung von Pattern -> Fallunterscheidung
        if (Math.abs(doubleNumber) > MAX || Math.abs(doubleNumber) < MIN) {
            //E-Notation
            pattern = "0.";
            for (int i = 1; i < DIGITS; i++) {
                pattern += "#";
            }
            pattern += "E0";
        } else {
            //Normale Anzeige
            wertVorKomma = doubleNumber.longValue();
            if (wertVorKomma == 0) {
                stellenVorKomma = 1; // weil log10(0) nicht definiert ist...
            } else {
                stellenVorKomma = (int) Math.floor(Math.log10(Math.abs(wertVorKomma))) + 1;
            }

            if (doubleNumber - wertVorKomma == 0) {  //Ganze Zahlen Dezimaltrennzeichen
                pattern = "#,##0";
            } else if (stellenVorKomma == DIGITS) {  //damit am Ende nicht der DecimalSeparator stehen bleibt
                pattern = "#,##0";
            } else {
                pattern = "#,##0.";
                for (int i = stellenVorKomma; i < DIGITS; i++) {
                    pattern += "#";
                }
            }
        }

        decimalFormat.applyPattern(pattern);
        return decimalFormat.format(doubleNumber);
    }


    // #1: Die Trennzeichen der Gruppierungen müssen vor Berechnung entfernt werden
    // #2: Die Decimaltrennzeichen für arity durch einen "." ersetzten
    public String prepareEvaluation(String expression) {

        NumberFormat numberFormat = NumberFormat.getInstance();  //holt lokale Einstellungen
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        DecimalFormatSymbols formatSymbols = decimalFormat.getDecimalFormatSymbols();

        Character decimalSeparator = formatSymbols.getDecimalSeparator();
        Character groupingSeparator = formatSymbols.getGroupingSeparator();

        // #1 Alle Grouping Seperatoren löschen÷
        expression = expression.replace(groupingSeparator.toString(), "");

        // #2 decimalSeperator wenn erforderlich auf "." (US-Schreibweise) wegen arity setzen
        if (!decimalSeparator.toString().equals(".")) {
            expression = expression.replace(decimalSeparator.toString(), ".");
        }

        // #3 Ersetz Divisionszeichen "÷" -> "/" für arity
        expression = expression.replace("÷", "/");

        // #3 Ersetz Multiplikationszeichen "×" -> "*" für arity
        expression = expression.replace("×", "*");

        return expression;
    }

}
