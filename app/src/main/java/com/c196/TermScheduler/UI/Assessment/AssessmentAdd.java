package com.c196.TermScheduler.UI.Assessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
import com.c196.TermScheduler.UI.Term.TermList;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class AssessmentAdd extends AppCompatActivity {
    private static final String TAG = "AssessmentAdd";
    private String courseId;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);
        getIncomingIntent();
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeBox.setAdapter(typeAdapter);

        dateButton.setOnClickListener(view -> {
            Log.d(TAG, "onCreate: date button on click");
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    AssessmentAdd.this,
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

            insertAssessment();
            backToTermList();
//            Toast.makeText(getApplicationContext(), "Assessment Saved Successfully", Toast.LENGTH_LONG).show();

        });

    }


    public void getIncomingIntent() {
        if (getIntent() != null) {
            courseId = getIntent().getStringExtra("id");

            titleInput = findViewById(R.id.assessmentTitleInput);
            typeBox = findViewById(R.id.assessmentTypeBox);
            assessmentDateView = findViewById(R.id.assessmentDateView);
            dateButton = findViewById(R.id.assessmentDateButton);
            descriptionInput = findViewById(R.id.assessmentDescriptionInput);
            assessmentSubmit = findViewById(R.id.assessmentSubmit);
            dateText = findViewById(R.id.assessmentDateView);
        }
    }

    public void insertAssessment() {
        model = new ViewModelProvider.AndroidViewModelFactory(AssessmentAdd.this.getApplication()).create(AssessmentViewModel.class);
        int id = Integer.parseInt(courseId);
        String type = typeBox.getSelectedItem().toString();
        String title = titleInput.getText().toString();
        String description = descriptionInput.getText().toString();
        Date assessmentDate = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Assessment assessment = new Assessment(type, title, description, assessmentDate, id);
        Log.d(TAG, "insertAssessment: " + type + " " + title + " " + description + " " + assessmentDate + " " + id);
        model.insert(assessment);
    }

    public void backToTermList() {
        Intent intent = new Intent(AssessmentAdd.this, TermList.class);
        startActivity(intent);
    }
}