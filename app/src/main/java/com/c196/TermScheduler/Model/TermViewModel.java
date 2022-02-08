package com.c196.TermScheduler.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.c196.TermScheduler.Data.SchedulerRepository;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    public static SchedulerRepository repository;
    public final LiveData<List<Term>> allTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repository  = new SchedulerRepository(application);
        allTerms = repository.getAllTerms();
    }

    public LiveData<List<Term>> getAllTerms(){return allTerms;}
    public static void insert(Term term) {repository.insertTerm(term);}
    public Term getTermById(int id){return repository.getTermById(id);}
    public static void update(Term term){repository.updateTerm(term);}
    public static void delete(Term term){repository.deleteTerm(term);}
}
