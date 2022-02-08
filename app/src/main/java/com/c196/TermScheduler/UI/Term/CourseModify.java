package com.c196.TermScheduler.UI.Term;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.TermScheduler.Model.Course;
import com.c196.TermScheduler.Model.CourseViewModel;
import com.c196.TermScheduler.R;

import java.util.ArrayList;

public class CourseModify extends AppCompatActivity {
    //    public static final String EXTRA_REPLY = "com.c196.TermScheduler.REPLY";
    public EditText titleInput;
    public Spinner statusBox;
    public Spinner instructorBox;
    public TextView startDate;
    public TextView endDate;
    public Button submitButton;
    public EditText noteInput;

    public String courseId;
    public String termId;
    public String termTitle;
    public String courseTitle;
    public String termStart;
    public String termEnd;
    public String courseInstructor;
    public String courseStatus;
    public String courseNote;

    public CourseViewModel model;
    public ArrayList<String> instructorList;
    public ArrayList<String> statusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        getIncomingIntent();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Modify Course");
//        ArrayAdapter<CharSequence> instructorAdapter = ArrayAdapter.createFromResource(this,
//                R.array.instructor_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> instructorAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, instructorList);
        instructorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        instructorBox.setAdapter(instructorAdapter);
        instructorBox.setSelection(instructorList.indexOf(courseInstructor));

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, statusList);
        statusBox.setAdapter(statusAdapter);
        statusBox.setSelection(statusList.indexOf(courseStatus));
        submitButton.setOnClickListener(view -> {
            Log.d("ONCLICK COURSE ADD", "onCreate: ");

            updateCourse();
            backToTermDetail();
            Toast.makeText(getApplicationContext(), "Course Saved Successfully", Toast.LENGTH_LONG).show();
        });
    }

    public void getIncomingIntent() {
        if (getIntent() != null) {
            termId = getIntent().getStringExtra("termId");
            termTitle = getIntent().getStringExtra("termTitle");
            courseId = getIntent().getStringExtra("id");
            courseTitle = getIntent().getStringExtra("title");
            termStart = getIntent().getStringExtra("start");
            termEnd = getIntent().getStringExtra("end");
            courseInstructor = getIntent().getStringExtra("instructor");
            courseStatus = getIntent().getStringExtra("status");
            courseNote = getIntent().getStringExtra("note");

            titleInput = findViewById(R.id.courseAddTitleInput);
            noteInput = findViewById(R.id.courseAddNoteInput);
            statusBox = findViewById(R.id.typeBox);
            instructorBox = findViewById(R.id.instructorBox);
            submitButton = findViewById(R.id.courseAddSubmit);
            startDate = findViewById(R.id.courseAddStartDate);
            endDate = findViewById(R.id.courseAddEndDate);

            startDate.setText(termStart);
            endDate.setText(termEnd);
            titleInput.setText(courseTitle);
            startDate.setText(termStart);
            endDate.setText(termEnd);
            noteInput.setText(courseNote);

            instructorList = new ArrayList<>();
            instructorList.add("Julius Jones | 555-555-5555 | mrjones@email.com");
            instructorList.add("Ronald McDonald | 555-566-5555 | rmac@email.com");
            instructorList.add("Rando Mando | 555-577-5555 | rman@email.com");
            instructorList.add("Janet Jackson | 555-588-5555 | jj@email.com");
            instructorList.add("Jeniffer Lawrence | 555-599-5555 | jl@gmail.com");
            statusList = new ArrayList<>();
            statusList.add("Not Started");
            statusList.add("In Progress");
            statusList.add("Completed");
            Log.d("getIncomingIntent", "getIncomingIntent: term id = " + termId + instructorList.indexOf(courseInstructor));
        }


    }

    public void updateCourse() {
        model = new ViewModelProvider.AndroidViewModelFactory(CourseModify.this.getApplication()).create(CourseViewModel.class);
        String instructor = instructorBox.getSelectedItem().toString();
        String status = statusBox.getSelectedItem().toString();
        String title = titleInput.getText().toString();
        String note;
        //term id
        int id = Integer.parseInt(courseId);
        int idTerm = Integer.parseInt(termId);
        if (noteInput.getText() == null || noteInput.getText().toString().isEmpty()) {
            note = "none";
        } else {
            note = noteInput.getText().toString();
        }
        Course course = new Course(instructor, note, title, status, idTerm);
        course.setId(id);
        model.update(course);

    }

    public void backToTermDetail() {
        Intent intent = new Intent(CourseModify.this, TermDetail.class);
        intent.putExtra("id", termId);
        intent.putExtra("title", termTitle);
        intent.putExtra("start", termStart);
        intent.putExtra("end", termEnd);
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
}