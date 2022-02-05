package com.c196.TermScheduler.UI.Term;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.TermScheduler.Model.Course;
import com.c196.TermScheduler.Model.CourseViewModel;
import com.c196.TermScheduler.R;

public class CourseAdd extends AppCompatActivity {
    //    public static final String EXTRA_REPLY = "com.c196.TermScheduler.REPLY";
    public EditText titleInput;
    public Spinner statusBox;
    public Spinner instructorBox;
    public TextView startDate;
    public TextView endDate;
    public Button submitButton;
    public EditText noteInput;

    public String termId;
    public String termTitle;
    public String termStart;
    public String termEnd;

    public CourseViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        getIncomingIntent();

        ArrayAdapter<CharSequence> instructorAdapter = ArrayAdapter.createFromResource(this,
                R.array.instructor_array, android.R.layout.simple_spinner_item);
        instructorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instructorBox.setAdapter(instructorAdapter);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        instructorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusBox.setAdapter(statusAdapter);

        submitButton.setOnClickListener(view -> {
            Log.d("ONCLICK COURSE ADD", "onCreate: ");

            createCourse();
            backToTermDetail();
            Toast.makeText(getApplicationContext(), "Course Saved Successfully", Toast.LENGTH_LONG).show();
        });
    }

    public void getIncomingIntent() {
        if (getIntent() != null) {
            termId = getIntent().getStringExtra("id");
            termTitle = getIntent().getStringExtra("title");
            termStart = getIntent().getStringExtra("start");
            termEnd = getIntent().getStringExtra("end");

            titleInput = findViewById(R.id.titleInput);
            noteInput = findViewById(R.id.noteInput);
            statusBox = findViewById(R.id.statusBox);
            instructorBox = findViewById(R.id.instructorBox);
            submitButton = findViewById(R.id.submitButton);
            startDate = findViewById(R.id.startDate);
            endDate = findViewById(R.id.endDate);

            startDate.setText(termStart);
            endDate.setText(termEnd);
        }


    }

    public void createCourse() {
        model = new ViewModelProvider.AndroidViewModelFactory(CourseAdd.this.getApplication()).create(CourseViewModel.class);
        String instructor = instructorBox.getSelectedItem().toString();
        String status = statusBox.getSelectedItem().toString();
        String title = titleInput.getText().toString();
        String note;
        //term id
        int id = Integer.parseInt(termId);
        if (noteInput.getText() == null || noteInput.getText().toString().isEmpty()) {
            note = "none";
        } else {
            note = noteInput.getText().toString();
        }
        Course course = new Course(instructor, note, title, status, id);
        model.insert(course);

    }

    public void backToTermDetail() {
        Intent intent = new Intent(CourseAdd.this, TermList.class);
        startActivity(intent);
    }
}