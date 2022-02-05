package com.c196.TermScheduler.UI.Term;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c196.TermScheduler.Model.CourseWithTerm;
import com.c196.TermScheduler.R;
import com.c196.TermScheduler.UI.Course.CourseDetail;

import java.util.List;

public class AssociatedCourseAdapter extends RecyclerView.Adapter<AssociatedCourseAdapter.ViewHolder> {
    private String TAG = "AssociatedCourseAdapter";
    private final List<CourseWithTerm> courseList;

    public AssociatedCourseAdapter(List<CourseWithTerm> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_term_recyclerview_course_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssociatedCourseAdapter.ViewHolder holder, int position) {
        CourseWithTerm current = courseList.get(position);

        holder.idTextView.setText(String.valueOf(current.course.getId()));
        holder.titleTextView.setText(current.course.getTitle());
        holder.startTextView.setText(current.term.getStart().toString());
        holder.endTextView.setText(current.term.getEnd().toString());
        holder.instructorTextView.setText(String.valueOf(current.course.getId()));
        holder.noteTextView.setText(current.course.getNote());
        holder.statusTextView.setText(current.course.getStatus());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView
                idTextView,
                titleTextView,
                startTextView,
                endTextView,
                instructorTextView,
                noteTextView,
                statusTextView;
        public Button courseDetailButton;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Context viewContext = itemView.getContext();
            courseDetailButton = itemView.findViewById(R.id.termCourseDetailButton);
            idTextView = itemView.findViewById(R.id.termCourseIdTextView);
            titleTextView = itemView.findViewById(R.id.termCourseTitleTextView);
            startTextView = itemView.findViewById(R.id.termCourseStartTextView);
            endTextView = itemView.findViewById(R.id.termCourseEndTextView);
            instructorTextView = itemView.findViewById(R.id.termCourseInstructorTextView);
            noteTextView = itemView.findViewById(R.id.termCourseNoteTextView);
            statusTextView = itemView.findViewById(R.id.termCourseStatusTextView);
            parentLayout = itemView.findViewById(R.id.termCourseItem);

            courseDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: ");
                    int position = getAdapterPosition();
                    final CourseWithTerm current = courseList.get(position);
                    Intent intent = new Intent(viewContext, CourseDetail.class);
                    intent.putExtra("id", String.valueOf(current.course.getId()));
                    intent.putExtra("title", current.course.getTitle());
                    intent.putExtra("start", current.term.getStart().toString());
                    intent.putExtra("end", current.term.getEnd().toString());
                    intent.putExtra("note", current.course.getNote());
                    viewContext.startActivity(intent);
                }
            });

        }
    }
}
