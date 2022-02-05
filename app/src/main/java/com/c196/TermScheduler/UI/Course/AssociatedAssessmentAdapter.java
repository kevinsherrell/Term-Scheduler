package com.c196.TermScheduler.UI.Course;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c196.TermScheduler.Model.AssessmentWithCourse;
import com.c196.TermScheduler.Model.CourseWithTerm;
import com.c196.TermScheduler.R;
import com.c196.TermScheduler.UI.Term.AssessmentDetail;
import com.c196.TermScheduler.UI.Term.AssociatedCourseAdapter;

import java.util.List;

public class AssociatedAssessmentAdapter extends RecyclerView.Adapter<AssociatedAssessmentAdapter.ViewHolder> {
    private String TAG = "AssociatedCourseAdapter";
    private final List<AssessmentWithCourse> assessmentList;

    public AssociatedAssessmentAdapter(List<AssessmentWithCourse> assessmentList) {
        this.assessmentList = assessmentList;
    }

    @NonNull
    @Override
    public AssociatedAssessmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_course_recyclerview_assessment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssociatedAssessmentAdapter.ViewHolder holder, int position) {
        AssessmentWithCourse current = assessmentList.get(position);

        // COME BACK THIS LATER
//        holder.idTextView.setText(String.valueOf(current.assessment.getId()));
//        holder.titleTextView.setText(current.assessment.getTitle());
//        holder.startTextView.setText(current.term.getStart().toString());
//        holder.endTextView.setText(current.term.getEnd().toString());
//        holder.instructorTextView.setText(String.valueOf(current.course.getId()));
//        holder.noteTextView.setText(current.course.getNote());
//        holder.statusTextView.setText(current.course.getStatus());

        holder.idTextView.setText(String.valueOf(current.assessment.getId()));
        holder.titleTextView.setText(current.assessment.getTitle());
        holder.dateTextView.setText(current.assessment.getDate().toString());
        holder.descriptionTextView.setText(current.assessment.getDescription());
        holder.typeTextView.setText(current.assessment.getType());
    }

    @Override
    public int getItemCount() {
        return assessmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView
                idTextView,
                titleTextView,
                dateTextView,
                descriptionTextView,
                typeTextView;
        public Button assessmentDetailButton;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Context viewContext = itemView.getContext();
            assessmentDetailButton = itemView.findViewById(R.id.courseAssessmentDetailButton);
            idTextView = itemView.findViewById(R.id.courseAssesmentIdTextView);
            titleTextView = itemView.findViewById(R.id.courseAssesmentTitleTextView);
            dateTextView = itemView.findViewById(R.id.courseAssesmentDateTextView);
            descriptionTextView = itemView.findViewById(R.id.courseAssessmentDescriptionTextView);
            typeTextView = itemView.findViewById(R.id.courseAssessmentTypeTextView);

            parentLayout = itemView.findViewById(R.id.courseAssessmentItem);

            assessmentDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final AssessmentWithCourse current = assessmentList.get(position);
                    Intent intent = new Intent(viewContext, AssessmentDetail.class);
                    intent.putExtra("assessment", current);
                }
            });

        }
    }
}