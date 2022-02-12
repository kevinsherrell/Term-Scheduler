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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.TermScheduler.Model.Course;
import com.c196.TermScheduler.Model.CourseViewModel;
import com.c196.TermScheduler.R;
import com.c196.TermScheduler.Utils.Converters;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CourseModify extends AppCompatActivity {
    //    public static final String EXTRA_REPLY = "com.c196.TermScheduler.REPLY";
    public EditText titleInput;
    public Spinner statusBox;
    public Spinner instructorBox;
    public TextView startText;
    public TextView endText;
    public Button submitButton;
    public EditText noteInput;

    public String courseId;
    public String termId;
    public String termTitle;
    public String termStart;
    public String termEnd;
    public String courseTitle;
    public String courseStart;
    public String courseEnd;
    public EditText instructorName;
    public EditText instructorPhone;
    public EditText instructorEmail;
    public String courseStatus;
    public String courseNote;
    public Button startButton;
    public Button endButton;


    public CourseViewModel model;
    public ArrayList<String> instructorList;
    public ArrayList<String> statusList;
    public String iName;
    public String iPhone;
    public String iEmail;

    private LocalDate lDate;
    public Date today;
    public Date startDate;
    public Date endDate;
    public Course course;

    private DatePickerDialog.OnDateSetListener startDateSetListener;
    private DatePickerDialog.OnDateSetListener endDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        getIncomingIntent();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Modify Course");


        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, statusList);
        statusBox.setAdapter(statusAdapter);
        statusBox.setSelection(statusList.indexOf(courseStatus));
        submitButton.setOnClickListener(view -> {
            Log.d("ONCLICK COURSE ADD", "onCreate: ");
            model = new ViewModelProvider.AndroidViewModelFactory(CourseModify.this.getApplication()).create(CourseViewModel.class);

            if (modifyCourse()) {
                model.update(course);
                backToTermDetail();
            } else {
                showMessage("Please check your fields and try again");
            }
        });

        //////////////////////////////////////////////
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(courseStart));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        formatStart(year, month, day);
        startDate = Converters.fromTimestamp(Date.parse(courseStart));
        endDate = Converters.fromTimestamp(Date.parse(courseEnd));

//        today = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        startButton.setOnClickListener(view -> {
//            Log.d(TAG, "onCreate: onClickDate");

            formatStart(year, month, day);
            startDate = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            startText.setText(startDate.toString());
            DatePickerDialog dialog = new DatePickerDialog(
                    CourseModify.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    startDateSetListener,
                    year,
                    month,
                    day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();


        });
        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                formatStart(year, month, day);
                startDate = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                startText.setText(startDate.toString());
            }
        };
        endButton.setOnClickListener(view -> {
//            Log.d(TAG, "onCreate: onClickDate");
            DatePickerDialog dialog = new DatePickerDialog(
                    CourseModify.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    endDateSetListener,
                    year,
                    month,
                    day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            formatStart(year, month, day);
            endDate = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            endText.setText(endDate.toString());


        });
        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                formatStart(year, month, day);
                endDate = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                endText.setText(endDate.toString());
            }
        };
/////////////////////////////////////////////
    }

    public void getIncomingIntent() {
        if (getIntent() != null) {
            termId = getIntent().getStringExtra("termId");
            termTitle = getIntent().getStringExtra("termTitle");
            termStart = getIntent().getStringExtra("termStart");
            termEnd = getIntent().getStringExtra("termEnd");
            courseId = getIntent().getStringExtra("id");
            courseTitle = getIntent().getStringExtra("title");
            courseStart = getIntent().getStringExtra("start");
            courseEnd = getIntent().getStringExtra("end");
            courseStatus = getIntent().getStringExtra("status");
            courseNote = getIntent().getStringExtra("note");
            iName = getIntent().getStringExtra("instructorName");
            iPhone = getIntent().getStringExtra("instructorPhone");
            iEmail = getIntent().getStringExtra("instructorEmail");
            titleInput = findViewById(R.id.courseAddTitleInputMod);
            noteInput = findViewById(R.id.courseAddNoteInputMod);
            statusBox = findViewById(R.id.typeBoxMod);
//            instructorBox = findViewById(R.id.instructorBox);
            submitButton = findViewById(R.id.courseAddSubmitMod);
            startButton = findViewById(R.id.startButtonMod);
            endButton = findViewById(R.id.endButtonMod);
            startText = findViewById(R.id.startTextMod);
            endText = findViewById(R.id.endTextMod);
            instructorName = findViewById(R.id.iNameTextMod);
            instructorPhone = findViewById(R.id.iPhoneTextMod);
            instructorEmail = findViewById(R.id.iEmailTextMod);

//            startDate.setText();
//            endDate.setText(termEnd);
            titleInput.setText(courseTitle);
            startText.setText(courseStart);
            endText.setText(courseEnd);
            noteInput.setText(courseNote);
            instructorName.setText(iName);
            instructorPhone.setText(iPhone);
            instructorEmail.setText(iEmail);

            statusList = new ArrayList<>();
            statusList.add("Not Started");
            statusList.add("In Progress");
            statusList.add("Completed");
        }


    }

//    public void updateCourse() {
////        model = new ViewModelProvider.AndroidViewModelFactory(CourseModify.this.getApplication()).create(CourseViewModel.class);
//        String instructor = instructorBox.getSelectedItem().toString();
//        String status = statusBox.getSelectedItem().toString();
//        String title = titleInput.getText().toString();
//        String note;
//        //term id
//        int id = Integer.parseInt(courseId);
//        int idTerm = Integer.parseInt(termId);
//        if (noteInput.getText() == null || noteInput.getText().toString().isEmpty()) {
//            note = "none";
//        } else {
//            note = noteInput.getText().toString();
//        }
////        Course course = new Course(instructor, note, title, status, idTerm);
////        course.setId(id);
////        model.update(course);
//
//    }

    public void backToTermDetail() {
        Intent intent = new Intent(CourseModify.this, TermDetail.class);
        intent.putExtra("id", termId);
        intent.putExtra("title", termTitle);
        intent.putExtra("start", courseStart);
        intent.putExtra("end", courseEnd);
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

    public boolean modifyCourse() {
        String iName = instructorName.getText().toString();
        String iPhone = instructorPhone.getText().toString();
        String iEmail = instructorEmail.getText().toString();
        String status = statusBox.getSelectedItem().toString();
        String title = titleInput.getText().toString();
        String note;
        int ERROR_COUNT = 0;
        //term id
        int idTerm = Integer.parseInt(termId);
        if (noteInput.getText() == null || noteInput.getText().toString().isEmpty()) {
            note = "none";
        } else {
            note = noteInput.getText().toString();
        }
        if (instructorPhone.getText() == null || iPhone.isEmpty()) {
            iPhone = "5555555555";
        }
        if (iName.isEmpty()) {
            iName = "Default Value";
        }
        if (iEmail.isEmpty()) {
            iEmail = "Default Value";
        }
        if (title.isEmpty()) {
            titleInput.setText("Default Value");
        }
        Date tStart = Converters.fromTimestamp((Date.parse(termStart)));
        Date tEnd = Converters.fromTimestamp(Date.parse(termEnd));
        if (startDate != null) {
//            if (startDate.before(today)) {
//                ERROR_COUNT++;
//                showMessage("Course cannot start before today");
//            } else
            if (startDate.before(tStart)) {
                ERROR_COUNT++;
                showMessage("Course cannot start before term start");
            }
            if (endDate != null) {
                if (startDate.after(tEnd)) {
                    ERROR_COUNT++;
                    showMessage("Course cannot start after term end");
                } else if (endDate.before(tStart)) {
                    ERROR_COUNT++;
                    showMessage("Course cannot end before term start");
                }
            } else {
                ERROR_COUNT++;
                showMessage("Must choose a end date");

            }
        } else {
            ERROR_COUNT++;
            showMessage("Must choose a start date");
        }


        if (ERROR_COUNT < 1) {

            course = new Course(iName, iPhone, iEmail, startDate, endDate, note, title, status, idTerm);
            course.setId(Integer.parseInt(courseId));
            return true;
//        model.insert(course);
        } else {
            return false;
        }

    }

    public void formatStart(int year, int month, int day) {
        month = month + 1;
//    Log.d(TAG, "onDateSet: " + year + "/" + month + "/" + day);
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
//    Date endDate = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).plusMonths(6).toInstant());

//        Log.d(TAG, "onDateSet: " + date);
    }

    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}