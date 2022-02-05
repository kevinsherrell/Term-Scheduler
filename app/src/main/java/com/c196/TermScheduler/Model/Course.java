package com.c196.TermScheduler.Model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "course_table",
        foreignKeys = {
                @ForeignKey(
                        entity = Term.class,
                        parentColumns = "term_id",
                        childColumns = "term_id"
                )
        })
public class Course {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "course_id")
    private int id;

    @NonNull
    @ColumnInfo(name = "term_id")
    private int termId;

    @NonNull
    @ColumnInfo(name = "instructor")
    private String instructor;


    @ColumnInfo(name = "note", defaultValue = "none")
    private String note;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "status")
    private String status;

    public Course(@NonNull String instructor, String note, @NonNull String title, @NonNull String status, int termId) {
        this.instructor = instructor;
        this.note = note;
        this.title = title;
        this.status = status;
        this.termId = termId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    @NonNull
    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(@NonNull String instructor) {
        this.instructor = instructor;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
