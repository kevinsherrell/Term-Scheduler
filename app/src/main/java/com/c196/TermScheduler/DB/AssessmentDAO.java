package com.c196.TermScheduler.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.c196.TermScheduler.Model.Assessment;
import com.c196.TermScheduler.Model.Term;

import java.util.List;

@Dao
public interface AssessmentDAO {

    // insert course
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Assessment assesment);

    // update course
    @Update
    void update(Assessment assessment);

    // Delete one from assessment table
    @Delete
    void delete(Assessment assessment);

    // delete all from assessment table
    @Query("DELETE FROM assessment_table")
    void deleteAll();

    // get all assessments
    @Query("SELECT * FROM assessment_table")
    LiveData<List<Assessment>> getAllAssessments();

    @Query("SELECT * FROM assessment_table WHERE assessment_table.assessment_id == :id")
    LiveData<Assessment> getAssessmentById(int id);
}
