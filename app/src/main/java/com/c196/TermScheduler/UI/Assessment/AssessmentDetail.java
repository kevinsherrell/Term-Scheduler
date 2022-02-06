package com.c196.TermScheduler.UI.Assessment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.c196.TermScheduler.R;

public class AssessmentDetail extends AppCompatActivity {

    public TextView adID, adTitle, adDate, adDescription, adType, adCourseTitle, adCourseInstructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        getIncomingIntent();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Assessment Detail");
    }

    public void getIncomingIntent() {
        adID = findViewById(R.id.adID);
        adTitle = findViewById(R.id.adTitle);
        adDescription = findViewById(R.id.adDescription);
        adType = findViewById(R.id.adType);
        adDate = findViewById(R.id.adDate);
        adCourseTitle = findViewById(R.id.adCourseTitle);
        adCourseInstructor = findViewById(R.id.adCourseInstructor);

        adID.setText(getString(R.string.adIDString) + getIntent().getStringExtra("id"));
        adTitle.setText(getString(R.string.adTitleString) + getIntent().getStringExtra("title"));
        adDate.setText(getString(R.string.adDateString) + getIntent().getStringExtra("date"));
        adDescription.setText(getString(R.string.adDescriptionString) + getIntent().getStringExtra("description"));
        adType.setText(getString(R.string.adTypeString) + getIntent().getStringExtra("type"));
        adCourseTitle.setText(getString(R.string.adCourseTitleString) + getIntent().getStringExtra("courseTitle"));
        adCourseInstructor.setText(getString(R.string.adCourseInstructorString) + getIntent().getStringExtra("courseInstructor"));
    }
}