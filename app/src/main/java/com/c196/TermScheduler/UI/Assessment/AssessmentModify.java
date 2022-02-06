package com.c196.TermScheduler.UI.Assessment;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.TermScheduler.Model.Assessment;
import com.c196.TermScheduler.Model.AssessmentViewModel;
import com.c196.TermScheduler.R;
import com.c196.TermScheduler.UI.Course.CourseDetail;

import java.sql.Array;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class AssessmentModify extends AppCompatActivity {

    private static final String TAG = "AssessmentAdd";
    private String courseId;
    private String courseTitle;
    private String courseStart;
    private String courseEnd;
    private String courseNote;

    public String id;
    public String title;
    public String description;
    public String assessmentDate;
    public String type;

    public EditText titleInput;
    public Spinner typeBox;
    public TextView assessmentDateView;
    public Button dateButton, assessmentSubmit;
    public EditText descriptionInput;
    public TextView dateText;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private LocalDate lDate;
    private Date date;
    private AssessmentViewModel model;

    public ArrayList<String> typeList;

    public int year;
    public int month;
    public int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);
        getIncomingIntent();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeBox.setAdapter(typeAdapter);
        typeBox.setSelection(typeList.indexOf(type));

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(assessmentDate));
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        Log.d(TAG, "onCreate: " + year + month + day);
        formatDate(year, month, day);

        dateButton.setOnClickListener(view -> {
            Log.d(TAG, "onCreate: date button on click");
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(new Date(assessmentDate));
//            year = cal.get(Calendar.YEAR);
//            month = cal.get(Calendar.MONTH);
//            day = cal.get(Calendar.DAY_OF_MONTH);
//            Log.d(TAG, "onCreate: " + year + month + day);
            DatePickerDialog dialog = new DatePickerDialog(
                    AssessmentModify.this,
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
                date = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                dateText.setText(date.toString());

                Log.d(TAG, "onDateSet: " + date);
            }
        };

        assessmentSubmit.setOnClickListener(view -> {
            Log.d(TAG, "onCreate: ");

            updateAssessment();
            backToCourseDetail();
            Toast.makeText(getApplicationContext(), "Assessment Saved Successfully", Toast.LENGTH_LONG).show();

        });

    }


    public void getIncomingIntent() {
        if (getIntent() != null) {
            // course information
            courseId = getIntent().getStringExtra("courseId");
            courseTitle = getIntent().getStringExtra("courseTitle");
            courseStart = getIntent().getStringExtra("courseStart");
            courseEnd = getIntent().getStringExtra("courseEnd");
            courseNote = getIntent().getStringExtra("courseNote");
            // assessment information
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            description = getIntent().getStringExtra("description");
            assessmentDate = getIntent().getStringExtra("date");
            type = getIntent().getStringExtra("type");

            titleInput = findViewById(R.id.assessmentModTitleInput);
            typeBox = findViewById(R.id.assessmentModTypeBox);
            assessmentDateView = findViewById(R.id.assessmentDateView);
            dateButton = findViewById(R.id.assessmentModDateButton);
            descriptionInput = findViewById(R.id.assessmentModDescriptionInput);
            assessmentSubmit = findViewById(R.id.assessmentModSubmit);
            dateText = findViewById(R.id.assessmentDateView);

            String[] typeArray = getResources().getStringArray(R.array.type_array);
            typeList = new ArrayList<>();
            for (String s : typeArray) {
                typeList.add(s);
            }

            titleInput.setText(title);
            descriptionInput.setText(description);
            dateText.setText(assessmentDate);

        }
    }

    public void updateAssessment() {
        model = new ViewModelProvider.AndroidViewModelFactory(AssessmentModify.this.getApplication()).create(AssessmentViewModel.class);
        int cId = Integer.parseInt(courseId);
        int aId = Integer.parseInt(id);
        String type = typeBox.getSelectedItem().toString();
        String title = titleInput.getText().toString();
        String description = descriptionInput.getText().toString();
        Date assessmentDate = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Assessment assessment = new Assessment(type, title, description, assessmentDate, cId);
        assessment.setId(aId);
        Log.d(TAG, "insertAssessment: " + type + " " + title + " " + description + " " + assessmentDate + " " + id);
        model.update(assessment);
    }

    public void backToCourseDetail() {
        Intent intent = new Intent(AssessmentModify.this, CourseDetail.class);
        intent.putExtra("id", courseId);
        intent.putExtra("title", courseTitle);
        intent.putExtra("start", courseStart);
        intent.putExtra("end", courseEnd);
        intent.putExtra("note", courseNote);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
        date = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        dateText.setText(date.toString());

        Log.d(TAG, "onDateSet: " + date);
    }
}