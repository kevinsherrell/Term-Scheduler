package com.c196.TermScheduler.UI.Term;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.c196.TermScheduler.R;

public class TermAdd extends AppCompatActivity {
    public EditText titleInput;
    public Spinner statusBox;
    public Spinner instructorBox;
    public TextView startDate;
    public TextView endDate;
    public Button submitButton;

    public String termId;
    public String termTitle;
    public String termStart;
    public String termEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);
        getIncomingIntent();
    }

    public void getIncomingIntent() {
        termId = getIntent().getStringExtra("id");
        termTitle = getIntent().getStringExtra("title");
        termStart = getIntent().getStringExtra("start");
        termEnd = getIntent().getStringExtra("end");

        titleInput = findViewById(R.id.titleInput);
        statusBox = findViewById(R.id.statusBox);
        instructorBox = findViewById(R.id.instructorBox);
        submitButton = findViewById(R.id.submitButton);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);

        startDate.setText(termStart);
        endDate.setText(termEnd);

    }
}