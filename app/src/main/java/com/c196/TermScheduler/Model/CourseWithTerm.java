package com.c196.TermScheduler.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Relation;

public class CourseWithTerm implements Parcelable {
    @Embedded
    public Course course;
    @Relation(
            parentColumn = "term_id",
            entityColumn = "term_id",
            entity = Term.class
    )
    public Term term;

    public CourseWithTerm(Course course, Term term) {
        this.course = course;
        this.term = term;
    }

    protected CourseWithTerm(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CourseWithTerm> CREATOR = new Creator<CourseWithTerm>() {
        @Override
        public CourseWithTerm createFromParcel(Parcel in) {
            return new CourseWithTerm(in);
        }

        @Override
        public CourseWithTerm[] newArray(int size) {
            return new CourseWithTerm[size];
        }
    };
}
