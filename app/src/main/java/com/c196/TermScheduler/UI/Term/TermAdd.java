package com.c196.TermScheduler.UI.Term;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.c196.TermScheduler.R;

public class TermAdd extends AppCompatActivity {
    private static String TAG = "TermViewActivity";
    private int termID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent");
        if (getIntent().hasExtra("id")) {
            Log.d(TAG, "getIncomingIntent: has ID " + getIntent().getStringExtra("id"));
            termID = Integer.parseInt(getIntent().getStringExtra("id"));
        }
    }

    private void setTerm() {

    }
}