package com.c196.TermScheduler.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Relation;

public class AssessmentWithCourse implements Parcelable {
    @Embedded
    public Assessment assessment;
    @Relation(
            parentColumn = "course_id",
            entityColumn = "course_id",
            entity = Course.class
    )
    public Course course;

    public AssessmentWithCourse(Assessment assessment, Course course) {
        this.assessment = assessment;
        this.course = course;
    }

    protected AssessmentWithCourse(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AssessmentWithCourse> CREATOR = new Creator<AssessmentWithCourse>() {
        @Override
        public AssessmentWithCourse createFromParcel(Parcel in) {
            return new AssessmentWithCourse(in);
        }

        @Override
        public AssessmentWithCourse[] newArray(int size) {
            return new AssessmentWithCourse[size];
        }
    };
}