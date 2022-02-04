package com.c196.TermScheduler.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.c196.TermScheduler.Model.Assessment;
import com.c196.TermScheduler.Model.Course;
import com.c196.TermScheduler.Model.Term;
import com.c196.TermScheduler.Utils.Converters;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class SchedulerDB extends RoomDatabase {

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile SchedulerDB INSTANCE;
    public static int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static SchedulerDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SchedulerDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SchedulerDB.class, "scheduler_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

    public static final Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            LocalDate localDate = LocalDate.now();
            Date startDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).plusMonths(6).toInstant());
            databaseWriteExecutor.execute(() -> {
                TermDAO tDAO = INSTANCE.termDAO();
                CourseDAO cDAO = INSTANCE.courseDAO();
                AssessmentDAO aDAO = INSTANCE.assessmentDAO();
                tDAO.deleteAll();
                Term term = new Term("Title", startDate, endDate);
                tDAO.insert(term);
                term = new Term("Title", startDate, endDate);
                tDAO.insert(term);
                term = new Term("Title", startDate, endDate);
                tDAO.insert(term);

            });
        }
    };

}
