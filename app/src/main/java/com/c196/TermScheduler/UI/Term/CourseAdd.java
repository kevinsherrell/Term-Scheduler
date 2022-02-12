package com.c196.TermScheduler.UI.Term;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import java.util.Calendar;
import java.util.Date;

public class CourseAdd extends AppCompatActivity {
    private static final String TAG = "CourseAdd";
    public EditText titleInput;
    public Spinner statusBox;
    public Spinner instructorBox;
    public TextView startText;
    public TextView endText;
    public Button submitButton, startButton, endButton;
    public EditText noteInput;
    public EditText instructorName;
    public EditText instructorPhone;
    public EditText instructorEmail;
    public String termId;
    public String termTitle;
    public String termStart;
    public String termEnd;

    private DatePickerDialog.OnDateSetListener startDateSetListener;
    private DatePickerDialog.OnDateSetListener endDateSetListener;

    private LocalDate lDate;
    public Date today;
    public Date startDate;
    public Date endDate;

    public CourseViewModel model;

    public Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        getIncomingIntent();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Course");
        actionBar.setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusBox.setAdapter(statusAdapter);
//////////////////////////////////////////////
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        formatStart(year, month, day);
        today = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        startButton.setOnClickListener(view -> {
//            Log.d(TAG, "onCreate: onClickDate");
            formatStart(year, month, day);
            startDate = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            startText.setText(startDate.toString());
            DatePickerDialog dialog = new DatePickerDialog(
                    CourseAdd.this,
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
                    CourseAdd.this,
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
        submitButton.setOnClickListener(view -> {
            model = new ViewModelProvider.AndroidViewModelFactory(CourseAdd.this.getApplication()).create(CourseViewModel.class);

//            Log.d("onclik", startDate.toString());
//            Log.d("ONCLICK COURSE ADD", "onCreate: ");
//            if(startDate.before(today)){
//                showMessage("course cannot start before today");
//            }else{
//
            if (createCourse()) {
                model.insert(course);
                backToTermDetail();
            } else {
                showMessage("please check all fields and try again");
            }
//            showMessage("Course Saved Successfully");
//            }
        });


    }

    public void getIncomingIntent() {
        if (getIntent() != null) {
            termId = getIntent().getStringExtra("id");
            termTitle = getIntent().getStringExtra("title");
            termStart = getIntent().getStringExtra("start");
            termEnd = getIntent().getStringExtra("end");

            titleInput = findViewById(R.id.courseAddTitleInputMod);
            noteInput = findViewById(R.id.courseAddNoteInputMod);
            statusBox = findViewById(R.id.typeBoxMod);
//            instructorBox = findViewById(R.id.instructorBox);
            submitButton = findViewById(R.id.courseAddSubmitMod);
            startText = findViewById(R.id.startTextMod);
            endText = findViewById(R.id.endTextMod);
            instructorName = findViewById(R.id.iNameTextMod);
            instructorPhone = findViewById(R.id.iPhoneTextMod);
            instructorEmail = findViewById(R.id.iEmailTextMod);
            startButton = findViewById(R.id.startButtonMod);
            endButton = findViewById(R.id.endButtonMod);

//            startDate.setText(termStart);
//            endDate.setText(termEnd);
        }


    }

    public boolean createCourse() {
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
            return true;
//        model.insert(course);
        } else {
            return false;
        }

    }

    public void backToTermDetail() {
        Intent intent = new Intent(CourseAdd.this, TermList.class);
        startActivity(intent);
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