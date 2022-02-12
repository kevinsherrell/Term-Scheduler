package com.c196.TermScheduler.Model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;


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
    @ColumnInfo(name = "instructor_name")
    private String instructorName;

    @NonNull
    @ColumnInfo(name = "instructor_phone")
    private String instructorPhone;

    @NonNull
    @ColumnInfo(name = "instructor_email")
    private String instructorEmail;

    @NonNull
    @ColumnInfo(name="start")
    private Date start;

    @NonNull
    @ColumnInfo(name="end")
    private Date end;

    @ColumnInfo(name = "note", defaultValue = "none")
    private String note;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "status")
    private String status;



    public Course(@NonNull String instructorName, String instructorPhone, String instructorEmail, Date start, Date end, String note, @NonNull String title, @NonNull String status, int termId) {
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.note = note;
        this.title = title;
        this.status = status;
        this.termId = termId;
        this.start = start;
        this.end = end;
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
    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(@NonNull String instructorName) {
        this.instructorName = instructorName;
    }

    @NonNull
    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(@NonNull String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    @NonNull
    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(@NonNull String instructorEmail) {
        this.instructorEmail = instructorEmail;
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
