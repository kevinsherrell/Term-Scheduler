package com.c196.TermScheduler.UI.Course;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.TermScheduler.Model.AssessmentViewModel;
import com.c196.TermScheduler.Model.Course;
import com.c196.TermScheduler.Model.CourseViewModel;
import com.c196.TermScheduler.R;
import com.c196.TermScheduler.UI.Assessment.AssessmentAdd;
import com.c196.TermScheduler.UI.Assessment.AssessmentDetail;
import com.c196.TermScheduler.UI.Term.AssociatedCourseAdapter;
import com.c196.TermScheduler.UI.Term.CourseAdd;
import com.c196.TermScheduler.UI.Term.TermDetail;
import com.c196.TermScheduler.Utils.TermReceiver;

import java.sql.Date;

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
    private static String instructorName;
    private static String instructorPhone;
    private static String instructorEmail;
    public Button alertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        createNotificationChannel();
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
        Button shareNoteButton = findViewById(R.id.shareNoteButton);
        alertButton = findViewById(R.id.courseDetailAlarmButton);
        alertButton.setOnClickListener(view -> {
            Intent startIntent = new Intent(CourseDetail.this, TermReceiver.class);
            startIntent.putExtra("TITLE", "CURSE START");
            startIntent.putExtra("TEXT", "You have a course starting today.");
            Intent endIntent = new Intent(CourseDetail.this, TermReceiver.class);
            endIntent.putExtra("TITLE", "COURSE END");
            endIntent.putExtra("TEXT", "You have a course ending today");
            PendingIntent startIntentP = PendingIntent.getBroadcast(CourseDetail.this, 0, startIntent, 0);
            PendingIntent endIntentP = PendingIntent.getBroadcast(CourseDetail.this, 1, endIntent, 0);
            AlarmManager startManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            AlarmManager endManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            startManager.set(AlarmManager.RTC_WAKEUP, Date.parse(start), startIntentP);
            endManager.set(AlarmManager.RTC_WAKEUP, Date.parse(end), endIntentP);
            Toast.makeText(getApplicationContext(), "Alerts have been set for : " + start + " and " + end, Toast.LENGTH_LONG).show();
        });

        shareNoteButton.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, note);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Here are my course notes");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
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
            instructorName = getIntent().getStringExtra("instructorName");
            instructorPhone = getIntent().getStringExtra("instructorPhone");
            instructorEmail = getIntent().getStringExtra("instructorEmail");
            status = getIntent().getStringExtra("status");

            idView.setText(id);
            titleView.setText(title);
            startView.setText(start);
            endView.setText(end);
            noteView.setText(note);
            courseInstructorView.setText("instructor: " + instructorName + " " + instructorPhone + " " + instructorEmail);
            statusView.setText(status);
        }
    }

    public static void showToast(Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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
}