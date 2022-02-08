package com.c196.TermScheduler.UI.Term;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c196.TermScheduler.Data.SchedulerRepository;
import com.c196.TermScheduler.Model.Term;
import com.c196.TermScheduler.Model.TermViewModel;
import com.c196.TermScheduler.R;
import com.c196.TermScheduler.Utils.TermReceiver;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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




}