package com.agdroid.calculatordemo;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;
import org.junit.Test;

import java.nio.charset.MalformedInputException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_format() {
        Locale[] locales = NumberFormat.getAvailableLocales();
        double myNumber = -1234.56;
        NumberFormat form;
        for (int j = 0; j < 4; ++j) {
            System.out.println("FORMAT j=" + j);
            for (int i = 0; i < locales.length; ++i) {
                if (locales[i].getCountry().length() == 0) {
                    continue; // Skip language-only locales
                }
                System.out.print(locales[i].getDisplayName());
                switch (j) {
                    case 0:
                        form = NumberFormat.getInstance(locales[i]);
                        break;
                    case 1:
                        form = NumberFormat.getIntegerInstance(locales[i]);
                        break;
                    case 2:
                        form = NumberFormat.getCurrencyInstance(locales[i]);
                        break;
                    default:
                        form = NumberFormat.getPercentInstance(locales[i]);
                        break;
                }
                if (form instanceof DecimalFormat) {
                    System.out.print(": " + ((DecimalFormat) form).toPattern());
                }
                System.out.print(" -> " + form.format(myNumber));
                try {
                    System.out.println(" -> " + form.parse(form.format(myNumber)));
                } catch (ParseException e) {
                }
            }
        }

    }

    @Test
    public void test_format_calculator() {
        Locale[] locales = NumberFormat.getAvailableLocales();
        NumberFormat form;
        String languageTag;
        final Double MAX = 1000000000000.0; //(12 Nullen)
        String muster = "#,##0.###";
        Double doubleNumber = -123456789123456789.123456789123456789;
        //String stringNumber = doubleNumber.toString();
        String stringNumber = String.valueOf(doubleNumber);

        for (int i = 0; i < locales.length; ++i) {
            if (locales[i].getCountry().length() == 0) {
                //System.out.println(" Skip language-only locales -> " + locales[i].toString());
                continue; // Skip language-only locales
            }

            languageTag = locales[i].toLanguageTag();
            switch (languageTag) {
                case "en-US":
                case "de-DE":
                case "de-CH":
                    System.out.print(locales[i].getDisplayName() + " -> ");
                    System.out.print(locales[i].toLanguageTag() + " -> ");
                    System.out.print(" -> (stringNumber: " + stringNumber + ") -> ");

                    //So erstellt man lokale Instance -< ABER: Warum ist form vom Typ DecimalFormat
                    form = NumberFormat.getInstance(locales[i]);

                    if ((Math.abs(doubleNumber) > MAX) || (Math.abs(doubleNumber) < 1 / MAX)) {
                        //E-Notation
                        muster = "0.#########E0";
                    } else {
                        muster = "#,##0.############";
                    }

                    if (form instanceof DecimalFormat) {
                        ((DecimalFormat) form).applyPattern(muster);

                        //Zeigt Muster/Pattern für die Landeskennung
                        // (Für alle 3 Länder gleich, weil
                        //      ","  steht für groupe seperator
                        //      "."  steht für decimal separator
                        //      "0"  steht für Ziffer (Pflicht)
                        //      "#"  steht für Ziffer, aber nur wenn in Zahl vorhanden
                        System.out.print(": " + ((DecimalFormat) form).toPattern());
                    }

                    System.out.print(" -> " + form.format(doubleNumber));
                    try {
                        System.out.println(" -> " + form.parse(form.format(doubleNumber)));
                    } catch (ParseException e) {
                    }

                    break;
                default:
            }


        }

    }


    @Test
    public void test_format_calculator2() {
        final int DIGITS = 12; // max. 12 echte Ziffern zzgl. E-Notation
        final double MAX = Math.pow(10, DIGITS) - 1;
        final double MIN = Math.pow(10, -(DIGITS - 1));
        Double doubleNumber = -123456789.123456789;
        //Double doubleNumber = 0.00000000012345678912345;
        String stringNumber = doubleNumber.toString();
        String valueOfNumber = String.valueOf(doubleNumber);
        String pattern = "#,##0.##";
        Double parseDouble;
        int stellenVorKomma;
        long wertVorKomma;


        Locale locales = new Locale("de", "DE");
        //Locale locales = new Locale("en", "US");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locales);
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;

        System.out.println(locales.toString());
        System.out.println("Original (Double)= " + doubleNumber);
        System.out.println("Original (Double.toString)= " + stringNumber);
        System.out.println("Original (String.valueOf)= " + valueOfNumber);

        //Berechnung von Pattern -> Fallunterscheidung
        System.out.println("Grenzen => MAX =  " + MAX + "   MIN = " + MIN);

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
            stellenVorKomma = (int) Math.floor(Math.log10(Math.abs(wertVorKomma)))+1;

            System.out.println("wertVorKomma =  " + wertVorKomma);
            System.out.println("stellenVorKomma =  " + stellenVorKomma);

            if (doubleNumber - wertVorKomma == 0 ) {
                pattern = "#,##0";
            } else {
                pattern = "#,##0.";
                for (int i = stellenVorKomma; i < DIGITS; i++) {
                    pattern += "#";
                }
            }
        }

        decimalFormat.applyPattern(pattern);

        System.out.println("pattern = " + decimalFormat.toPattern());

        System.out.println("decimalFormat (Übergabe an Display)= " + decimalFormat.format(doubleNumber));

        try {
            parseDouble = decimalFormat.parse(decimalFormat.format(doubleNumber)).doubleValue();
            System.out.println("parseDouble = " + parseDouble);
        } catch (ParseException e) {
        }
    }


    @Test
    public void test_arity() throws SyntaxException {
        Symbols symbols = new Symbols();
        String expression = "pi*e*13/3";
        expression = "sqrt(4)";
        expression = "2.2e4";
        expression = "2345234.23*2";
        String s1 = "572049587675487124646.876458";

        try {
            double value = symbols.eval(expression);
            System.out.println("Wurzelzeichen -> \u221A ind Pi -> \u03c0");
            System.out.println("Ergebnis " + value);
        } catch (SyntaxException e) {
            System.out.println("Fehlermeldung =>" + e.toString());
            e.printStackTrace();
        }

        Double d = 14684453644654694865136844.68;
        d = 0.00000000000000000000000000001468445364465469486513684468;
        d = 0.0000000000014684;
        d = 10.0005262600000000078698760978;
        System.out.println("System.out.println(String.valueOf(d)) -> " + String.valueOf(d));

        //NumberFormat formatter = new DecimalFormat();
        //formatter = new DecimalFormat("0.############E0");
        //DecimalFormat df = new DecimalFormat( "###,##0.00" );
        //DecimalFormat df = new DecimalFormat( "0.0000000E0" );
        //System.out.println("System.out.println(df.format(d)) -> " + df.format(d));
        //LÖSUNG -> Je nach Größe mit
        //   a) new DecimalFormat( "0.0000000E0" ); oder
        //   b) DecimalFormat( "###,##0.00" )
        String sf = String.format("%e", d);
        System.out.println("String.format(\"%e\", d) ->" + sf);

        //System.out.printf("%e",d);

        final Double MAX = 1000000000000.0;
        DecimalFormat df;
        if ((d > MAX) || (d < 1 / MAX)) {
            df = new DecimalFormat("0.000000000000E0");
        } else {
            df = new DecimalFormat("###,##0.00");
        }
        System.out.println("System.out.println(df.format(d)) -> " + df.format(d));
    }


    //So geht das nicht...
    @Test
    public void test_calculation() {
        Calculation calc = new Calculation();
        String expression = "10/3";
        String result;

        calc.setCurrentExpression(expression);
        calc.performEvaluation();
    }


}