package com.c196.TermScheduler.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.c196.TermScheduler.Data.SchedulerRepository;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    public static SchedulerRepository repository;
    public final LiveData<List<Assessment>> allAssessments;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        repository = new SchedulerRepository(application);
        allAssessments = repository.getAllAssessments();
    }


    public LiveData<List<AssessmentWithCourse>> getAssessmentsWithCourse(int id) {
        return repository.getAssessmentsWithTerm(id);
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        return allAssessments;
    }

    public static void insert(Assessment assessment) {
        repository.insertAssessment(assessment);
    }

    public LiveData<Assessment> getAssessmentById(int id) {
        return repository.getAssessmentById(id);
    }

    public static void update(Assessment assessment) {
        repository.updateAssessment(assessment);
    }

    public static void delete(Assessment assessment) {
        repository.deleteAssessment(assessment);
    }
}