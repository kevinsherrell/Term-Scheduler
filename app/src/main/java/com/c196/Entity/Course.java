package com.c196.Entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.lang.ref.Reference;

//@Entity(tableName = "course_table",
//        foreignKeys = {
//                @ForeignKey(
//                        entity = Instructor.class,
//                        parentColumns = "instructor_id",
//                        childColumns = "instructor_id"),
//                @ForeignKey(
//                        entity = Term.class,
//                        parentColumns = "term_id",
//                        childColumns = "term_id"),
//                @ForeignKey(
//                        entity =
//                )
//        }
//
//)
@Entity(tableName = "course_table",
        foreignKeys = {
                @ForeignKey(
                        entity = Instructor.class,
                        parentColumns = "instructor_id",
                        childColumns = "instructor_id"
                ),
                @ForeignKey(
                        entity = Assessment.class,
                        parentColumns = "assessment_id",
                        childColumns = "assessment_id"
                ),
                @ForeignKey(
                        entity = Note.class,
                        parentColumns = "note_id",
                        childColumns = "note_id"
                )
        })
public class Course {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "course_id")
    private int id;

    @NonNull
    @ColumnInfo(name = "instructor_id")
    private int instructorId;

    @NonNull
    @ColumnInfo(name = "assessment_id")
    private int assessmentId;

    @ColumnInfo(name = "note_id")
    private int noteId;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "status")
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }
}
