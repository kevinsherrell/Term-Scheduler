package com.c196.TermScheduler.UI.Term;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.TermScheduler.Data.SchedulerRepository;
import com.c196.TermScheduler.Model.Course;
import com.c196.TermScheduler.Model.CourseViewModel;
import com.c196.TermScheduler.R;

public class TermDetail extends AppCompatActivity {
    public CourseViewModel model;
    private RecyclerView recyclerView;
    private AssociatedCourseAdapter adapter;
    private static String TAG = "TermDetailActivity";
    private String id;
    private String title;
    private String start;
    private String end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        getIncomingIntent();

        recyclerView = findViewById(R.id.termDetailRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        model = new ViewModelProvider.AndroidViewModelFactory(TermDetail.this.getApplication()).create(CourseViewModel.class);

        model.getCoursesWithTerm(Integer.parseInt(id)).observe(this, courses -> {
            adapter = new AssociatedCourseAdapter((courses));
            recyclerView.setAdapter(adapter);
        });


        Button addCourse = findViewById(R.id.addCourse);
        addCourse.setOnClickListener(view -> {
            Intent intent = new Intent(TermDetail.this, CourseAdd.class);
            intent.putExtra("id", id);
            intent.putExtra("title", title);
            intent.putExtra("start", start);
            intent.putExtra("end", end);
            startActivity(intent);

        });
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent");
        if (getIntent().hasExtra("id")) {
            Log.d(TAG, "getIncomingIntent: has ID " + getIntent().getStringExtra("id"));
            TextView idView = findViewById(R.id.tDetailID);
            TextView startView = findViewById(R.id.termDetailStart);
            TextView titleView = findViewById(R.id.termDetailTitle);
            TextView endView = findViewById(R.id.termDetailEnd);

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            start = getIntent().getStringExtra("start");
            end = getIntent().getStringExtra("end");
            idView.setText(id);
            titleView.setText(title);
            startView.setText(start);
            endView.setText(end);

        }
    }


}