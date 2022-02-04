package com.c196.TermScheduler.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.c196.TermScheduler.Model.Term;

import java.util.List;

@Dao
public interface TermDAO {

    // insert term
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Term term);

    // update term
    @Update
    void update(Term term);

    // Delete one from term table
    @Delete
    void delete(Term term);

    // delete all from term table
    @Query("DELETE FROM term_table")
    void deleteAll();

    // get all terms
    @Query("SELECT * FROM term_table")
    LiveData<List<Term>> getAllTerms();

    @Query("SELECT * FROM term_table WHERE term_table.term_id == :id")
    LiveData<Term> getTermById(int id);


}
