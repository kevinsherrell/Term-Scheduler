package com.c196.TermScheduler.UI.Term;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c196.TermScheduler.Data.SchedulerRepository;
import com.c196.TermScheduler.Model.TermViewModel;
import com.c196.TermScheduler.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class TermList extends AppCompatActivity {
    private String TAG = "MainActivity";
    private ExtendedFloatingActionButton addTermButton;
    private TermViewModel termViewModel;
    private SchedulerRepository repository;
    private RecyclerView recyclerView;
    private TermAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        recyclerView = findViewById(R.id.termRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termViewModel = new ViewModelProvider.AndroidViewModelFactory(TermList.this.getApplication()).create(TermViewModel.class);

        termViewModel.getAllTerms().observe(this, terms -> {
//            for(Term term: terms){
//                Log.d(TAG, "Term: " + term.toString());
//            }
            adapter = new TermAdapter((terms));
            recyclerView.setAdapter(adapter);
        });

        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Log.i(TAG, "onCreate: " + fab);
            Intent intent = new Intent(TermList.this, TermAdd.class);
            startActivity(intent);
        });

    }

}