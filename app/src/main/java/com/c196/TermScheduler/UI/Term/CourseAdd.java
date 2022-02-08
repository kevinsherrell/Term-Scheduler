package com.c196.TermScheduler.UI.Term;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.c196.TermScheduler.Utils.TermReceiver;

import java.sql.Date;
import java.time.Instant;

public class CourseAdd extends AppCompatActivity {
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
        createNotificationChannel();
        AlarmManager startManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        AlarmManager endManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent startIntent = new Intent(CourseAdd.this, TermReceiver.class);
        startIntent.putExtra("TITLE", "CURSE START");
        startIntent.putExtra("TEXT", "You have a course starting today.");
        Intent endIntent = new Intent(CourseAdd.this, TermReceiver.class);
        endIntent.putExtra("TITLE", "COURSE END");
        endIntent.putExtra("TEXT", "You have a course ending today");
        PendingIntent startIntentP = PendingIntent.getBroadcast(CourseAdd.this, 0, startIntent, 0);
        PendingIntent endIntentP = PendingIntent.getBroadcast(CourseAdd.this, 1, endIntent, 0);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Course");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Log.d("courseadd", "onCreate: " + Date.parse(termStart));
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

            createCourse(startManager, endManager, startIntentP, endIntentP);
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

            titleInput = findViewById(R.id.courseAddTitleInput);
            noteInput = findViewById(R.id.courseAddNoteInput);
            statusBox = findViewById(R.id.typeBox);
            instructorBox = findViewById(R.id.instructorBox);
            submitButton = findViewById(R.id.courseAddSubmit);
            startDate = findViewById(R.id.courseAddStartDate);
            endDate = findViewById(R.id.courseAddEndDate);

            startDate.setText(termStart);
            endDate.setText(termEnd);
        }


    }

    public void createCourse(AlarmManager startManager, AlarmManager endManager, PendingIntent startIntentP, PendingIntent endIntentP) {
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
        startManager.set(AlarmManager.RTC_WAKEUP, Date.parse(termStart), startIntentP);
        endManager.set(AlarmManager.RTC_WAKEUP, Date.parse(termEnd), endIntentP);
    }

    public void backToTermDetail() {
        Intent intent = new Intent(CourseAdd.this, TermList.class);
        startActivity(intent);
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "termReminderChannel";
            String description = "Channel for term reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("termNotify", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}