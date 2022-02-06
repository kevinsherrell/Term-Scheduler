package com.c196.TermScheduler.UI.Assessment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.TermScheduler.R;
import com.c196.TermScheduler.UI.Course.CourseDetail;
import com.c196.TermScheduler.UI.Term.TermList;

public class AssessmentDetail extends AppCompatActivity {

    public TextView adID, adTitle, adDate, adDescription, adType, adCourseTitle, adCourseInstructor, adCourseNote, adCourseId, adCourseStart, adCourseEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        getIncomingIntent();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goHome();
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Toast.makeText(AssessmentDetail.this, "Use back button on action bar", Toast.LENGTH_LONG).show();

    }
    public void goHome(){
        Intent intent = new Intent(AssessmentDetail.this, TermList.class);
//        intent.putExtra("id", courseid)
        startActivity(intent);


    }
}