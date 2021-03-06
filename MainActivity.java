// MainActivity.java
// Calculates a bill total based on a tip percentage
package com.kwon.k.tipcalculator;
// adding comment
import android.support.v7.app.AppCompatActivity; // base class (API Level)
import android.os.Bundle; // for saving (in the user interface) state information (onCreate method)
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener (events generated by edit text)
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing the tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // Seek-bar listener (response when user move seek bar)
import android.widget.TextView; // for displaying text

import java.text.NumberFormat; // for currency formatting (locale specific percentage and currency)

// MainActivity class for the Tip Calculator app (compatibility support)
public class MainActivity extends AppCompatActivity {

    // currency and percent formatter objects
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private double billAmount = 0.0; // bill amount entered by the user
    private double percent = 0.15; // initial tip percentage
    private TextView amountTextView; // shows formatted bill amount
    private TextView percentTextView; // shows tip percentage
    private TextView tipTextView; // shows calculated tip amount
    private TextView totalTextView; // shows calculated total bill amount

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call superclass onCreate, no execute without this code
                                             // source code
        setContentView(R.layout.activity_main); // inflate the GUI (display the apps GUI) auto-generated and
                                                  // sees the layout of the app (represents the file in res folder)

        // get references to programmatically manipulated TextViews
        this.amountTextView = (TextView) findViewById(R.id.amountTextView);
        this.percentTextView = (TextView) findViewById(R.id.percentTextView);
        this.tipTextView = (TextView) findViewById(R.id.tipTextView);
        this.totalTextView = (TextView) findViewById(R.id.totalTextView);
        tipTextView.setText(currencyFormat.format(0)); // currency formatted version receive
        totalTextView.setText(currencyFormat.format(0)); // long value and returns in String representation

        // set amountEditText's TextWatcher
        EditText amountEditText =
                (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // set percentSeekBar's onSeekBar's OnSeekBarChangeListener
        SeekBar percentSeekBar =
                (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    private void calculate() {
        // format percent and display in percentTextView
        percentTextView.setText(percentFormat.format(this.percent));

        // calculate the tip and total
        double tip = this.billAmount * this.percent;
        double total = this.billAmount + tip;

        //display tip and total formatted as currency
        tipTextView.setText(currencyFormat.format(tip)); // currency formatted String
        totalTextView.setText(currencyFormat.format(total)); // currency formatted String
    }

    private final OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener() {
                // update percent, then call calculate
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { //fromUser true = user, false = program
                    percent = progress / 100.0; // set percent based on program
                    calculate(); // calculate and display tip and total
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            };

    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                billAmount = Double.parseDouble(s.toString()) / 100.0;
                amountTextView.setText(currencyFormat.format(billAmount)); // bill Amount in amountTextView object
            } catch (NumberFormatException e) {
                amountTextView.setText(""); // allow hit text to be displayed again
                billAmount = 0.0;
                amountTextView.setText(currencyFormat.format(billAmount));
            }
        }

        @Override
        public void afterTextChanged(Editable s) { }
    };
}
