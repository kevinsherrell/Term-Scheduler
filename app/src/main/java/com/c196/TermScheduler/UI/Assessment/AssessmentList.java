package com.c196.TermScheduler.UI.Assessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.c196.TermScheduler.Data.SchedulerRepository;
import com.c196.TermScheduler.Model.Assessment;
import com.c196.TermScheduler.Model.AssessmentViewModel;
import com.c196.TermScheduler.Model.AssessmentWithCourse;
import com.c196.TermScheduler.Model.Course;
import com.c196.TermScheduler.Model.CourseViewModel;
import com.c196.TermScheduler.Model.CourseWithTerm;
import com.c196.TermScheduler.R;
import com.c196.TermScheduler.UI.Course.AssociatedAssessmentAdapter;
import com.c196.TermScheduler.UI.Course.CourseList;
import com.c196.TermScheduler.UI.Term.AssociatedCourseAdapter;
import com.c196.TermScheduler.UI.Term.TermList;

import java.util.List;

public class AssessmentList extends AppCompatActivity {
    private String TAG = "CourseList";
    private AssessmentViewModel assessmentViewModel;
    private SchedulerRepository repository;
    private RecyclerView recyclerView;
    private AssociatedAssessmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Assessment List");
        recyclerView = findViewById(R.id.courseDetailRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentViewModel = new ViewModelProvider.AndroidViewModelFactory(AssessmentList.this.getApplication()).create(AssessmentViewModel.class);

        assessmentViewModel.getAssessmentsWithCourse().observe(this, assessments -> {


            adapter = new AssociatedAssessmentAdapter(assessments);

            recyclerView.setAdapter(adapter);
        });
    }

    public void showDeleteAlert(String message) {
        runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        });
    }

    public static void showToast(Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assessment_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tList:
                toTlist();
                this.finish();
                return true;
            case R.id.cList:
                toClist();
                this.finish();
                ;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toTlist() {
        Intent intent = new Intent(AssessmentList.this, TermList.class);
        startActivity(intent);
    }

    public void toClist() {
        Intent intent = new Intent(AssessmentList.this, CourseList.class);
        startActivity(intent);
    }

}