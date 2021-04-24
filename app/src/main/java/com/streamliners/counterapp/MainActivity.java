package com.streamliners.counterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.streamliners.counterapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private int qty = 0;
    private ActivityMainBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initializing the binding
        // To create layout using layout inflater
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setupEventHandlers();
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
}