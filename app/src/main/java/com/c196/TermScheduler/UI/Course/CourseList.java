package com.c196.TermScheduler.UI.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.c196.TermScheduler.Model.CourseWithTerm;
import com.c196.TermScheduler.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c196.TermScheduler.Data.SchedulerRepository;
import com.c196.TermScheduler.Model.Course;
import com.c196.TermScheduler.Model.CourseViewModel;
import com.c196.TermScheduler.Model.Term;
import com.c196.TermScheduler.Model.TermViewModel;
import com.c196.TermScheduler.R;
import com.c196.TermScheduler.UI.Assessment.AssessmentList;
import com.c196.TermScheduler.UI.Term.AssociatedCourseAdapter;
import com.c196.TermScheduler.UI.Term.TermAdapter;
import com.c196.TermScheduler.UI.Term.TermAdd;
import com.c196.TermScheduler.UI.Term.TermList;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class CourseList extends AppCompatActivity {
    private String TAG = "CourseList";
    private CourseViewModel courseViewModel;
    private SchedulerRepository repository;
    private RecyclerView recyclerView;
    private AssociatedCourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Course List");
        recyclerView = findViewById(R.id.termDetailRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(CourseList.this.getApplication()).create(CourseViewModel.class);

        List<Course> coursesForAdapter = null;
        courseViewModel.getCoursesWithTerm().observe(this, courses -> {
            for (CourseWithTerm course : courses) {
                Log.d(TAG, "onCreate: " + course.toString());

            }

            adapter = new AssociatedCourseAdapter(courses);

            recyclerView.setAdapter(adapter);
        });


    }

    //    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_term_list, menu);
//        return true;
//    }
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
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tList:
                toTlist();
                this.finish();
                return true;
            case R.id.aList:
                toAlist();
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toTlist() {
        Intent intent = new Intent(CourseList.this, TermList.class);
        startActivity(intent);
    }

    public void toAlist() {
        Intent intent = new Intent(CourseList.this, AssessmentList.class);
        startActivity(intent);
    }
}
