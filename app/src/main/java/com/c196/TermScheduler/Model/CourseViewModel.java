package com.c196.TermScheduler.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.c196.TermScheduler.Data.SchedulerRepository;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    public static SchedulerRepository repository;
    public final LiveData<List<Course>> allCourses;
//    public final LiveData<List<CourseWithTerm>> coursesWithTerm;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = new SchedulerRepository(application);
        allCourses = repository.getAllCourses();
//        coursesWithTerm = getCoursesWithTerm();
    }


    public LiveData<List<CourseWithTerm>> getCoursesWithTerm(int id) {
        return repository.getCoursesWithTerm( id);
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public static void insert(Course course) {
        repository.insertCourse(course);
    }

    public LiveData<Course> getCourseById(int id) {
        return repository.getCourseById(id);
    }

    public static void update(Course course) {
        repository.updateCourse(course);
    }

    public static void delete(Course course) {
        repository.deleteCourse(course);
    }
}
