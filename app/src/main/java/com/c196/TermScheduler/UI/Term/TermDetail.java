package com.c196.TermScheduler.UI.Term;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.c196.TermScheduler.R;

public class TermDetail extends AppCompatActivity {
    private static String TAG = "TermDetailActivity";
    private String id;
    private String title;
    private String start;
    private String end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        getIncomingIntent();

        Button addCourse = findViewById(R.id.addCourse);
        addCourse.setOnClickListener(view -> {
            Intent intent = new Intent(this.getApplicationContext(), TermAdd.class);
            intent.putExtra("id", id);
            intent.putExtra("title", title);
            intent.putExtra("start", start);
            intent.putExtra("end", end);
            startActivity(intent);
        });
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent");
        if (getIntent().hasExtra("id")) {
            Log.d(TAG, "getIncomingIntent: has ID " + getIntent().getStringExtra("id"));
            TextView idView = findViewById(R.id.tDetailID);
            TextView startView = findViewById(R.id.termDetailStart);
            TextView titleView = findViewById(R.id.termDetailTitle);
            TextView endView = findViewById(R.id.termDetailEnd);

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            start = getIntent().getStringExtra("start");
            end = getIntent().getStringExtra("end");
            idView.setText(id);
            titleView.setText(title);
            startView.setText(start);
            endView.setText(end);

        }
    }

}