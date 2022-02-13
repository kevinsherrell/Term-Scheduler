package com.c196.TermScheduler.UI.Term;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.TermScheduler.Model.Term;
import com.c196.TermScheduler.Model.TermViewModel;
import com.c196.TermScheduler.R;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TermModify extends AppCompatActivity {
    public String TAG = "TermAdd";
    // id termAddTitleInput
    public EditText termModifyTitleInput;
    // id termAddStartButton and termAddSubmit
    public Button termModifyStartButton, termModifySubmit;
    // id termAddDateText
    public TextView termModifyDateText;
    public TextView termModifyDateEnd;

    public TermViewModel model;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private LocalDate lDate;
    private Date startDate;

    private String id;
    private String title;
    private String start;
    private String end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_modify);
        getIncomingIntent();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Modify Term");
        termModifyStartButton = findViewById(R.id.termModifyStartButton);
        termModifySubmit = findViewById(R.id.termModifySubmit);
        termModifyTitleInput = findViewById(R.id.termModifyTitleInput);
//        termModifyDateText = findViewById(R.id.termModifyDateText);
//        termModifyDateEnd = findViewById(R.id.termModDateEnd);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(start));
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH);
        int day = cal.get(cal.DAY_OF_MONTH);
        formatDate(year, month, day);
        termModifyStartButton.setOnClickListener(view -> {
            Log.d(TAG, "onCreate: onClickDate");
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(new Date(start));
//            int year = cal.get(cal.YEAR);
//            int month = cal.get(cal.MONTH);
//            int day = cal.get(cal.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(
                    TermModify.this,
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
                Date endDate = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).plusMonths(6).toInstant());

                termModifyDateText.setText(startDate.toString());
                termModifyDateEnd.setText(endDate.toString());
                Log.d(TAG, "onDateSet: " + startDate);
            }
        };

        termModifySubmit.setOnClickListener(view -> {
            Log.d(TAG, "onCreate: onClickSubmit");
            updateTerm();
            backToTermList();
            Toast.makeText(getApplicationContext(), "Term Updated Successfully", Toast.LENGTH_LONG).show();

        });
    }

    public void updateTerm() {
        model = new ViewModelProvider.AndroidViewModelFactory(TermModify.this.getApplication()).create(TermViewModel.class);
        String title = termModifyTitleInput.getText().toString();
//        Date date = Date.from(Instant.parse(termAddDateText.toString()));
        Date endDate = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).plusMonths(6).toInstant());
        Term term = new Term(title, startDate, endDate);
        term.setId(Integer.parseInt(id));
        Log.d(TAG, "updateTerm: endDate: " + endDate);
        model.update(term);
    }



    public void getIncomingIntent() {
        if (getIntent() != null) {
            termModifyDateText = findViewById(R.id.termModifyDateText);
            termModifyDateEnd = findViewById(R.id.termModDateEnd);
            termModifyTitleInput = findViewById(R.id.termModifyTitleInput);

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            start = getIntent().getStringExtra("start");
            end = getIntent().getStringExtra("end");
            termModifyTitleInput.setText(title);
            termModifyDateText.setText(start);
            termModifyDateEnd.setText(end);
            int nDate = new Date(start).getYear();

            Log.d(TAG, "getIncomingIntent: day" + end);
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void formatDate(int year, int month, int day) {
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
        Date endDate = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).plusMonths(6).toInstant());

        termModifyDateText.setText(startDate.toString());
        termModifyDateEnd.setText(endDate.toString());
//        Log.d(TAG, "onDateSet: " + date);
    }

    public void backToTermList() {
        Intent intent = new Intent(TermModify.this, TermList.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        backToTermList();
        return true;
    }
}