package com.c196.TermScheduler.UI.Course;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.TermScheduler.Model.AssessmentViewModel;
import com.c196.TermScheduler.Model.CourseViewModel;
import com.c196.TermScheduler.R;
import com.c196.TermScheduler.UI.Assessment.AssessmentAdd;
import com.c196.TermScheduler.UI.Term.AssociatedCourseAdapter;
import com.c196.TermScheduler.UI.Term.CourseAdd;
import com.c196.TermScheduler.UI.Term.TermDetail;

public class CourseDetail extends AppCompatActivity {
    public AssessmentViewModel model;
    private RecyclerView recyclerView;
    private AssociatedAssessmentAdapter adapter;
    private static String TAG = "CourseDetailActivity";
    private static String id;
    private static String title;
    private static String start;
    private static String end;
    private static String note;
    private static String status;
    private static String instructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Course Detail");
        getIncomingIntent();
        recyclerView = findViewById(R.id.courseDetailRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        model = new ViewModelProvider.AndroidViewModelFactory(CourseDetail.this.getApplication()).create(AssessmentViewModel.class);

        model.getAssessmentsWithCourse(Integer.parseInt(id)).observe(this, Assessments -> {
            adapter = new AssociatedAssessmentAdapter((Assessments));
            recyclerView.setAdapter(adapter);
        });

        Button addAssessment = findViewById(R.id.addAssessment);
        addAssessment.setOnClickListener(view -> {
            Intent intent = new Intent(CourseDetail.this, AssessmentAdd.class);
            intent.putExtra("id", id);
            intent.putExtra("title", title);
            intent.putExtra("start", start);
            intent.putExtra("end", end);
            startActivity(intent);

        });
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

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent");
        if (getIntent().hasExtra("id")) {
            Log.d(TAG, "getIncomingIntent: has ID " + getIntent().getStringExtra("id"));
            TextView idView = findViewById(R.id.cDetailID);
            TextView startView = findViewById(R.id.courseDetailStart);
            TextView titleView = findViewById(R.id.courseDetailTitle);
            TextView endView = findViewById(R.id.courseDetailEnd);
            TextView noteView = findViewById(R.id.courseNoteDetail);
            TextView courseInstructorView = findViewById(R.id.courseInstructorView);
            TextView statusView = findViewById(R.id.statusView);

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            start = getIntent().getStringExtra("start");
            end = getIntent().getStringExtra("end");
            note = getIntent().getStringExtra("note");
            instructor = getIntent().getStringExtra("instructor");
            status = getIntent().getStringExtra("status");

            idView.setText(id);
            titleView.setText(title);
            startView.setText(start);
            endView.setText(end);
            noteView.setText(note);
            courseInstructorView.setText(instructor);
            statusView.setText(status);
        }
    }

    public static void showToast(Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}