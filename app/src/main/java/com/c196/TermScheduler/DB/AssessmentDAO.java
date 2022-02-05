package com.c196.TermScheduler.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.c196.TermScheduler.Model.Assessment;
import com.c196.TermScheduler.Model.AssessmentWithCourse;
import com.c196.TermScheduler.Model.CourseWithTerm;
import com.c196.TermScheduler.Model.Term;

import java.util.List;

@Dao
public interface AssessmentDAO {

    // insert course
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

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

    // get all assessment belonging to course
    @Query("SELECT * FROM assessment_table WHERE assessment_table.course_id == :id")
    List<Assessment> getAssessmentsByFK(int id);

    // get assessments with associated term information
    @Query("SELECT * FROM assessment_table WHERE course_id = :id")
    LiveData<List<AssessmentWithCourse>> getAssessmentsWithTerm(int id);


    @Query("SELECT * FROM assessment_table WHERE assessment_table.assessment_id == :id")
    LiveData<Assessment> getAssessmentById(int id);
}
