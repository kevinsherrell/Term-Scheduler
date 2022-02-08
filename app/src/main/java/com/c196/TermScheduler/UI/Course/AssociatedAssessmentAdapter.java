package com.c196.TermScheduler.UI.Course;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c196.TermScheduler.DB.SchedulerDB;
import com.c196.TermScheduler.Data.SchedulerRepository;
import com.c196.TermScheduler.Model.Assessment;
import com.c196.TermScheduler.Model.AssessmentWithCourse;
import com.c196.TermScheduler.Model.CourseWithTerm;
import com.c196.TermScheduler.Model.Term;
import com.c196.TermScheduler.R;
import com.c196.TermScheduler.UI.Assessment.AssessmentDetail;
import com.c196.TermScheduler.UI.Assessment.AssessmentModify;

import java.util.List;

public class AssociatedAssessmentAdapter extends RecyclerView.Adapter<AssociatedAssessmentAdapter.ViewHolder> {
    private String TAG = "AssociatedCourseAdapter";
    private final List<AssessmentWithCourse> assessmentList;
    Term currentTerm;

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
        public Button assessmentDetailButton, assessmentDeleteButton, assessmentModifyButton;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Context viewContext = itemView.getContext();
            assessmentDetailButton = itemView.findViewById(R.id.courseAssessmentDetailButton);
            assessmentModifyButton = itemView.findViewById(R.id.assessmentModifyButton);
            assessmentDeleteButton = itemView.findViewById(R.id.assessmentDeleteButton);
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
                    SchedulerRepository repository = new SchedulerRepository((Application) viewContext.getApplicationContext());
                    SchedulerDB.databaseWriteExecutor.execute(() -> {
                        currentTerm = repository.getTermById(current.course.getTermId());
                    });

                    Intent intent = new Intent(viewContext, AssessmentDetail.class);

                    intent.putExtra("id", String.valueOf(current.assessment.getId()));
                    intent.putExtra("type", current.assessment.getType());
                    intent.putExtra("title", current.assessment.getTitle());
                    intent.putExtra("description", current.assessment.getDescription());
                    intent.putExtra("date", current.assessment.getDate().toString());
                    intent.putExtra("courseTitle", current.course.getTitle());
                    intent.putExtra("courseId", current.course.getId());
                    intent.putExtra("courseInstructor", current.course.getInstructor());
                    intent.putExtra("courseNote", current.course.getNote());
                    intent.putExtra("courseStatus", current.course.getStatus());
                    viewContext.startActivity(intent);
                }
            });

            assessmentModifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final AssessmentWithCourse current = assessmentList.get(position);
                    Intent intent = new Intent(viewContext, AssessmentModify.class);
                    intent.putExtra("id", String.valueOf(current.assessment.getId()));
                    intent.putExtra("type", current.assessment.getType());
                    intent.putExtra("title", current.assessment.getTitle());
                    intent.putExtra("description", current.assessment.getDescription());
                    intent.putExtra("date", current.assessment.getDate().toString());
                    intent.putExtra("courseId", String.valueOf(current.course.getId()));
                    intent.putExtra("courseTitle", current.course.getTitle());
                    intent.putExtra("courseId", String.valueOf(current.course.getId()));
                    intent.putExtra("courseInstructor", current.course.getInstructor());
                    intent.putExtra("courseNote", current.course.getNote());
                    intent.putExtra("courseStatus", current.course.getStatus());
                    viewContext.startActivity(intent);
                }
            });


            assessmentDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final AssessmentWithCourse current = assessmentList.get(position);
                    SchedulerRepository repository = new SchedulerRepository((Application) viewContext.getApplicationContext());
                    SchedulerDB.databaseWriteExecutor.execute(() -> {
                        Looper.prepare();
                        repository.deleteAssessment(current.assessment);
                        CourseDetail.showToast(viewContext, "Assessment deleted successfully");

                    });
                }
            });

        }
    }
}
