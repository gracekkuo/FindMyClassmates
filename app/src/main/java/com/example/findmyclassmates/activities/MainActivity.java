package com.example.findmyclassmates.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findmyclassmates.R;
import com.example.findmyclassmates.activities.mainFeatures.TabbedFeatures;

public class MainActivity extends AppCompatActivity {

    // creating variables for our edittext, button and dbhandler

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}