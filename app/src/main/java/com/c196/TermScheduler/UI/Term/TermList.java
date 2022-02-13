package com.c196.TermScheduler.UI.Term;

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
import com.c196.TermScheduler.Model.Term;
import com.c196.TermScheduler.Model.TermViewModel;
import com.c196.TermScheduler.R;
import com.c196.TermScheduler.UI.Assessment.AssessmentList;
import com.c196.TermScheduler.UI.Course.CourseList;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.time.LocalDate;
import java.util.Date;

public class TermList extends AppCompatActivity {
    private String TAG = "MainActivity";
    private ExtendedFloatingActionButton addTermButton;
    private TermViewModel termViewModel;
    private SchedulerRepository repository;
    private RecyclerView recyclerView;
    private TermAdapter adapter;
    public LocalDate lDate;
    public Date startDate;
    public static int termNotificationCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Term List (Home)");
        recyclerView = findViewById(R.id.termRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termViewModel = new ViewModelProvider.AndroidViewModelFactory(TermList.this.getApplication()).create(TermViewModel.class);

        termViewModel.getAllTerms().observe(this, terms -> {
            for (Term term : terms) {
                Log.d(TAG, "onCreate: " + term.toString());
            }

            adapter = new TermAdapter((terms));
            recyclerView.setAdapter(adapter);
        });



        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Log.i(TAG, "onCreate: " + fab);
            Intent termAddIntent = new Intent(TermList.this, TermAdd.class);
            startActivity(termAddIntent);
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
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.cList:
                toClist();
                this.finish();
                return true;
            case R.id.aList:
                toAlist();
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

public void toClist(){
        Intent intent = new Intent(TermList.this, CourseList.class);
        startActivity(intent);
}
    public void toAlist() {
        Intent intent = new Intent(TermList.this, AssessmentList.class);
        startActivity(intent);
    }
}