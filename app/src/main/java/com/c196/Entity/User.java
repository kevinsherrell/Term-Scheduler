package com.c196.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// User can have many terms
// User can add many courses to those terms
// courses can have one instructor
// courses can have may assessments


@Entity(tableName = "user_table",
        foreignKeys = @ForeignKey(
                entity = Term.class,
                parentColumns = "term_id",
                childColumns = "term_id"
        ))
public class User {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "user_id")
    private int id;

    @ColumnInfo(name = "term_id")
    private int termId;

    @NonNull
    @ColumnInfo(name = "username")
    private String username;

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
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }
}
