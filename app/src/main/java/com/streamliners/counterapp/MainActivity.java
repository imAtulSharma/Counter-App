package com.streamliners.counterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.streamliners.counterapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private int qty = 0;
    private ActivityMainBinding b;
    private int minVal, maxVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Initializing the binding
        // To create layout using layout inflater
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setupEventHandlers();
        getInitialCount();

        // Restore on saved instances
        if (savedInstanceState != null) {
            b.qty.setText(savedInstanceState.getInt(Constants.COUNT, -3) + "");
        } else {
            // Create preference reference

            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            b.qty.setText(preferences.getInt(Constants.COUNT, -3) + "");
        }
    }

    /**
     * Get the data from the starter activity
     */
    private void getInitialCount() {
        Bundle bundle = getIntent().getExtras();

        if (bundle == null)
            return;

        // Getting all the data which is come from the starter activity
        qty = bundle.getInt(Constants.INITIAL_COUNT_KEY, 0);
        minVal = bundle.getInt(Constants.MINIMUM_VALUE, Integer.MIN_VALUE);
        maxVal = bundle.getInt(Constants.MAXIMUM_VALUE, Integer.MAX_VALUE);

        if(qty != 0) {
            b.sendBackButton.setVisibility(View.VISIBLE);
        }

        b.qty.setText(String.valueOf(qty));
    }

    /**
     * Trigger Event handlers to listen the actions
     */
    private void setupEventHandlers() {
        // Listening action od clicking on increase button
        b.incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            // Task should be done on click on the button view
            public void onClick(View v) {
                // Calling increase function
                incQty();
            }
        });

        // Listening action od clicking on decrease button
        b.decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            // Task should be done on click on the button view
            public void onClick(View v) {
                // Calling decrease function
                decQty();
            }
        });
    }

    /**
     * To decrease the quantity and update the result in Text View
     */
    public void decQty() {
        b.qty.setText(--qty + "");
    }

    /**
     * To increase the quantity and update the result in Text View
     */
    public void incQty() {
        b.qty.setText(++qty + "");
    }

    /**
     * To send the final count back to the starter activity
     * @param view button view which is triggered
     */
    public void sendDataBack(View view) {
        // Validate count
        if (qty >= minVal && qty <= maxVal) {
            // Send the data to the starter activity
            Intent intent = new Intent();
            intent.putExtra(Constants.FINAL_COUNT, qty);
            setResult(RESULT_OK, intent);

            // Close the activity
            finish();
        }
        // When not in range
        else {
            Toast.makeText(this, "Not in Range!", Toast.LENGTH_SHORT).show();
        }
    }

    // Instance State


    @Override
    protected void onPause() {
        super.onPause();

        // Create preference reference

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        preferences.edit()
                .putInt(Constants.COUNT, qty)
                .apply();

        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            }
        };

        preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(Constants.COUNT, qty);
    }
}