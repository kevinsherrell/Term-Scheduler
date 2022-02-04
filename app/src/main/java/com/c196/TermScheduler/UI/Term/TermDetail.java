package com.c196.TermScheduler.UI.Term;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.c196.TermScheduler.R;

public class TermDetail extends AppCompatActivity {
    private static String TAG = "TermDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent");
        if (getIntent().hasExtra("id")) {
            Log.d(TAG, "getIncomingIntent: has ID " + getIntent().getStringExtra("id"));
            TextView id = findViewById(R.id.tDetailID);
            TextView start = findViewById(R.id.termDetailStart);
            TextView title = findViewById(R.id.termDetailTitle);
            TextView end = findViewById(R.id.termDetailEnd);
            id.setText(getIntent().getStringExtra("id"));
            title.setText(getIntent().getStringExtra("title"));
            start.setText(getIntent().getStringExtra("start"));
            end.setText(getIntent().getStringExtra("end"));

        }
    }

}