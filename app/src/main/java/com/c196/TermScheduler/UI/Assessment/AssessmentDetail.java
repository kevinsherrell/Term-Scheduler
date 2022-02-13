package com.c196.TermScheduler.UI.Assessment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.TermScheduler.R;
import com.c196.TermScheduler.UI.Course.CourseDetail;
import com.c196.TermScheduler.UI.Term.TermDetail;
import com.c196.TermScheduler.UI.Term.TermList;
import com.c196.TermScheduler.Utils.TermReceiver;

import java.util.Date;

public class AssessmentDetail extends AppCompatActivity {

    public TextView adID, adTitle, adDate, adDescription, adType, adCourseTitle, adCourseInstructor, adCourseNote, adCourseId, adCourseStart, adCourseEnd;
    public Button alertButton;
    public String id, title, date, description, type, courseTitle, courseInstructor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        getIncomingIntent();
        createNotificationChannel();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Assessment Detail");
        alertButton = findViewById(R.id.assessmentAlertButton);
        alertButton.setOnClickListener(view -> {
            AlarmManager startManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent startIntent = new Intent(AssessmentDetail.this, TermReceiver.class);
            startIntent.putExtra("TITLE", "ASSESSMENT");
            startIntent.putExtra("TEXT", "You have an assessment today");
            PendingIntent startIntentP = PendingIntent.getBroadcast(AssessmentDetail.this, 3, startIntent, 0);
            startManager.set(AlarmManager.RTC_WAKEUP, Date.parse(date), startIntentP);
            Toast.makeText(getApplicationContext(), "Alert has been set for : " + date,Toast.LENGTH_LONG).show();
        });
    }

    public void getIncomingIntent() {
        adID = findViewById(R.id.adID);
        adTitle = findViewById(R.id.adTitle);
        adDescription = findViewById(R.id.adDescription);
        adType = findViewById(R.id.adType);
        adDate = findViewById(R.id.adDate);
        adCourseTitle = findViewById(R.id.adCourseTitle);
        adCourseInstructor = findViewById(R.id.adCourseInstructor);

        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("date");
        description = getIntent().getStringExtra("description");
        type = getIntent().getStringExtra("type");
        courseInstructor = getIntent().getStringExtra("courseInstructor");
        courseTitle = getIntent().getStringExtra("courseTitle");

        adID.setText(getString(R.string.adIDString) + id);
        adTitle.setText(getString(R.string.adTitleString) + title);
        adDate.setText(getString(R.string.adDateString) + date);
        adDescription.setText(getString(R.string.adDescriptionString) + description);
        adType.setText(getString(R.string.adTypeString) + type);
        adCourseTitle.setText(getString(R.string.adCourseTitleString) + courseTitle);
        adCourseInstructor.setText(getString(R.string.adCourseInstructorString) +courseInstructor);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                goHome();
//                this.finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {

        Toast.makeText(AssessmentDetail.this, "Use back button on action bar", Toast.LENGTH_LONG).show();

    }

    public void goHome() {
        Intent intent = new Intent(AssessmentDetail.this, TermList.class);
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
    public void backToTermDetail() {
        Bundle bundle = getIntent().getExtras();
        Intent intent = new Intent(AssessmentDetail.this, TermList.class);
        intent.putExtra("id", bundle.getString("courseId"));
        intent.putExtra("title", bundle.getString("courseTitle"));
        intent.putExtra("start", bundle.getString("courseStart"));
        intent.putExtra("end", bundle.getString("courseEnd"));
        intent.putExtra("note", bundle.getString("courseNote"));
        intent.putExtra("status", bundle.getString("courseStatus"));
        intent.putExtra("instructorName", bundle.getString("instructorName"));
        intent.putExtra("instructorPhone", bundle.getString("instructorPhone"));
        intent.putExtra("instructorEmail", bundle.getString("instructorEmail"));

        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        backToTermDetail();
        return true;
    }
}