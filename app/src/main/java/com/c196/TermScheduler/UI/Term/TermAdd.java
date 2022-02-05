package com.c196.TermScheduler.UI.Term;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.c196.TermScheduler.R;

public class TermAdd extends AppCompatActivity {
    public String TAG = "TermAdd";
    // id termAddTitleInput
    public EditText termAddTitleInput;
    // id termAddStartButton and termAddSubmit
    public Button termAddStartButton, termAddSubmit;
    // id termAddDateText
    public TextView termAddDateText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);

        termAddStartButton = findViewById(R.id.termAddStartButton);
        termAddSubmit = findViewById(R.id.termAddSubmit);
        termAddStartButton.setOnClickListener(view->{
            Log.d(TAG, "onCreate: onClickDate");
        });

        termAddSubmit.setOnClickListener(view->{
            Log.d(TAG, "onCreate: onClickSubmit");
        });
    }

}