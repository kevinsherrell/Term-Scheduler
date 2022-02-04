package com.c196.TermScheduler.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.c196.TermScheduler.Model.Course;
import com.c196.TermScheduler.Model.Term;

import java.util.List;

@Dao
public interface CourseDAO {

    // insert course
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Course course);
    // update course
    @Update
    void update(Course course);
    // Delete one from course table
    @Delete
    void delete(Course course);
    // delete all from course table
    @Query("DELETE FROM course_table")
    void deleteAll();
    // get all courses
    @Query("SELECT * FROM course_table")
   LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM course_table WHERE course_table.course_id == :id")
    LiveData<Course> getCourseById(int id);
}
