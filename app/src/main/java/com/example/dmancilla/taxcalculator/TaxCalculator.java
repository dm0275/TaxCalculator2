package com.example.dmancilla.taxcalculator;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxCalculator extends AppCompatActivity {

    private EditText amount, tax;
    private TextView taxTotal,taxesAmount;
    private Button calculate, clearIt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tax_calculator);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.calculator2);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        amount = (EditText)findViewById(R.id.inputAmount);
        tax = (EditText) findViewById(R.id.taxAmount);
        taxTotal = (TextView) findViewById(R.id.taxTotal);
        calculate = (Button) findViewById(R.id.calculate);
        taxesAmount = (TextView) findViewById(R.id.taxes);
        clearIt = (Button) findViewById(R.id.clearBtn);

        tax.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO)
                {
                    calculateIt(v);
                    handled = true;
                }
                return handled;
            }
        });
    }
    public void clearIt(View view)
    {
        tax.setText(null);
        amount.setText(null);
        taxesAmount.setText(null);
        taxTotal.setText(null);
    }
    public void calculateIt(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        if (amount.getText().toString().trim().length() == 0 || tax.getText().toString().trim().length() == 0)
        {
            Toast test1 = Toast.makeText(getBaseContext(), "Please fill in all fields",Toast.LENGTH_SHORT);
            taxesAmount.setText("0.00");
            taxTotal.setText("0.00");
            test1.show();
        }
        else if (amount.getText().charAt(0) == '.' || tax.getText().charAt(0) == '.')
        {
            Toast test1 = Toast.makeText(getBaseContext(), "Please enter a number",Toast.LENGTH_SHORT);
            taxesAmount.setText("0.00");
            taxTotal.setText("0.00");
            test1.show();
        }
        else
        {
            String input = amount.getText().toString();
            String taxAmount = tax.getText().toString();
            Double percent = Double.parseDouble(taxAmount);
            Double newAmount = Double.parseDouble(input);
            percent = percent / 100.00;
            double total = (newAmount * percent) + newAmount;
            double taxes = (newAmount * percent);
            BigDecimal result = BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP);
            BigDecimal taxResult = BigDecimal.valueOf(taxes).setScale(2, RoundingMode.HALF_UP);
            String tt = taxResult.toString();
            String value = result.toString();
            taxesAmount.setText("$"+tt);
            taxTotal.setText("$"+value);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tax_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit)
        {
            finish();
            System.exit(0);
        }
        else if (id == R.id.action_about)
        {
            Toast test1 = Toast.makeText(getBaseContext(), "App made by dm0275",Toast.LENGTH_SHORT);
            test1.show();
        }

        return super.onOptionsItemSelected(item);
    }

}
