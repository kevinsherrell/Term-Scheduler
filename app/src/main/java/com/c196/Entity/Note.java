package com.c196.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "note_id")
    private int id;

    @NonNull
    @ColumnInfo(name = "note_detail")
    private String noteDetail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNoteDetail() {
        return noteDetail;
    }

    public void setNoteDetail(@NonNull String noteDetail) {
        this.noteDetail = noteDetail;
    }
}
