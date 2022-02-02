package com.c196.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "term_table",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "course_id",
                childColumns = "course_id"
        ))
public class Term {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "term_id")
    private int id;

    @ColumnInfo(name = "course_id")
    private int courseId;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "start")
    private Date start;

    @NonNull
    @ColumnInfo(name = "end")
    private Date end;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public Date getStart() {
        return start;
    }

    public void setStart(@NonNull Date start) {
        this.start = start;
    }

    @NonNull
    public Date getEnd() {
        return end;
    }

    public void setEnd(@NonNull Date end) {
        this.end = end;
    }
}
