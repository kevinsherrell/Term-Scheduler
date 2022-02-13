package com.c196.TermScheduler.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.c196.TermScheduler.DB.AssessmentDAO;
import com.c196.TermScheduler.DB.CourseDAO;
import com.c196.TermScheduler.DB.SchedulerDB;
import com.c196.TermScheduler.DB.TermDAO;
import com.c196.TermScheduler.Model.Assessment;
import com.c196.TermScheduler.Model.AssessmentWithCourse;
import com.c196.TermScheduler.Model.Course;
import com.c196.TermScheduler.Model.CourseWithTerm;
import com.c196.TermScheduler.Model.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SchedulerRepository {
    private final AssessmentDAO assessmentDAO;
    private final CourseDAO courseDAO;
    private final TermDAO termDAO;


    private LiveData<List<Course>> allCourses;
    private LiveData<List<Term>> allTerms;
    private LiveData<List<Assessment>> allAssessments;
    private LiveData<List<CourseWithTerm>> allCoursesWithTerm;

    public SchedulerRepository(Application application) {
        SchedulerDB db = SchedulerDB.getDatabase(application);
        termDAO = db.termDAO();
        courseDAO = db.courseDAO();
        assessmentDAO = db.assessmentDAO();
        allTerms = termDAO.getAllTerms();
        allAssessments = assessmentDAO.getAllAssessments();
        allCourses = courseDAO.getAllCourses();
//        allCoursesWithTerm = getCoursesWithTerm();
    }


    // course ===============================================
    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

//    public LiveData<List<CourseWithTerm>> getCoursesWithTerm(int id) {
//        return courseDAO.getCoursesWithTerm( id);
//    }

    public LiveData<List<CourseWithTerm>> getCoursesWithTerm() {
        return courseDAO.getCoursesWithTerm( );
    }

    public LiveData<List<AssessmentWithCourse>> getAssessmentsWithTerm(){
        return assessmentDAO.getAssessmentsWithTerm();
    }
 public Course getOneCourseByFK(int id){
        return courseDAO.getOneCourseByFK(id);
 }
    public LiveData<Course> getCourseById(int id) {
        return courseDAO.getCourseById(id);
    }

    public List<Course> getCoursesByFK(int id){
        return courseDAO.getCoursesByFK(id);
    }
    public List<Assessment> getAssessmentsByFK(int id){
        return assessmentDAO.getAssessmentsByFK(id);
    }
    public void insertCourse(Course course) {
        SchedulerDB.databaseWriteExecutor.execute(() -> {
            courseDAO.insert(course);
        });

    }

    public void updateCourse(Course course) {
        SchedulerDB.databaseWriteExecutor.execute(() -> {
            courseDAO.update(course);
        });

    }

    public void deleteCourse(Course course) {
        SchedulerDB.databaseWriteExecutor.execute(() -> {
            courseDAO.delete(course);
        });

    }

    // term= =====================================================
    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    public Term getTermById(int id) {
        return termDAO.getTermById(id);
    }

    public void insertTerm(Term term) {
        SchedulerDB.databaseWriteExecutor.execute(() -> {
            termDAO.insert(term);
        });

    }

    public void updateTerm(Term term) {
        SchedulerDB.databaseWriteExecutor.execute(() -> {
            termDAO.update(term);
        });

    }

    public void deleteTerm(Term term) {
        SchedulerDB.databaseWriteExecutor.execute(() -> {
            termDAO.delete(term);
        });

    }

    public void deleteAllTerms() {
        SchedulerDB.databaseWriteExecutor.execute(() -> {
            termDAO.deleteAll();
        });

    }

    // assessment =====================================================
    public LiveData<List<Assessment>> getAllAssessments() {
        return allAssessments;
    }

    public LiveData<Assessment> getAssessmentById(int id) {
        return assessmentDAO.getAssessmentById(id);
    }

    public void insertAssessment(Assessment assessment) {
        SchedulerDB.databaseWriteExecutor.execute(() -> {
            assessmentDAO.insert(assessment);
        });

    }

    public void updateAssessment(Assessment assessment) {
        SchedulerDB.databaseWriteExecutor.execute(() -> {
            assessmentDAO.update(assessment);
        });

    }

    public void deleteAssessment(Assessment assessment) {
        SchedulerDB.databaseWriteExecutor.execute(() -> {
            assessmentDAO.delete(assessment);
        });

    }


    public void setAllCourses() {
        SchedulerDB.databaseWriteExecutor.execute(() -> {
            allCourses = courseDAO.getAllCourses();
        });

    }


//    public void selectAllTerms() {
//        SchedulerDB.databaseWriteExecutor.execute(() -> {
//            termDAO.getAllTerms();
//        });
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }


    //    public void getAllTerms(){
//
//    }


}


