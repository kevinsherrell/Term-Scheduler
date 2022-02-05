package com.c196.TermScheduler.UI.Term;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.TermScheduler.Model.Term;
import com.c196.TermScheduler.Model.TermViewModel;
import com.c196.TermScheduler.R;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


public class TermAdd extends AppCompatActivity {
    public String TAG = "TermAdd";
    // id termAddTitleInput
    public EditText termAddTitleInput;
    // id termAddStartButton and termAddSubmit
    public Button termAddStartButton, termAddSubmit;
    // id termAddDateText
    public TextView termAddDateText;

    public TermViewModel model;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private LocalDate lDate;
    private Date startDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);

        termAddStartButton = findViewById(R.id.termAddStartButton);
        termAddSubmit = findViewById(R.id.termAddSubmit);
        termAddTitleInput = findViewById(R.id.termAddTitleInput);
        termAddDateText = findViewById(R.id.termAddDateText);
        termAddStartButton.setOnClickListener(view -> {
            Log.d(TAG, "onCreate: onClickDate");
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    TermAdd.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    dateSetListener,
                    year,
                    month,
                    day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: " + year + "/" + month + "/" + day);
                String dateFromPicker = "";
                String monthString;
                String dayString;
                if (month < 10) {
                    monthString = "0" + month;
                } else {
                    monthString = String.valueOf(month);
                }
                if (day < 10) {
                    dayString = "0" + day;
                } else {
                    dayString = String.valueOf(day);
                }
                dateFromPicker = String.format("%d-%s-%s", year, monthString, dayString);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                lDate = LocalDate.parse(dateFromPicker, formatter);
                startDate = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                termAddDateText.setText(startDate.toString());

                Log.d(TAG, "onDateSet: " + startDate);
            }
        };

        termAddSubmit.setOnClickListener(view -> {
            Log.d(TAG, "onCreate: onClickSubmit");
            insertTerm();
            backToTermList();
            Toast.makeText(getApplicationContext(), "Course Saved Successfully", Toast.LENGTH_LONG).show();

        });

    }

    public void insertTerm() {
        model = new ViewModelProvider.AndroidViewModelFactory(TermAdd.this.getApplication()).create(TermViewModel.class);
        String title = termAddTitleInput.getText().toString();
//        Date date = Date.from(Instant.parse(termAddDateText.toString()));
        Date endDate = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).plusMonths(6).toInstant());
        Term term = new Term(title, startDate, endDate);
        model.insert(term);
    }

    public void backToTermList() {
        Intent intent = new Intent(TermAdd.this, TermList.class);
        startActivity(intent);
    }
}