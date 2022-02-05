package com.c196.TermScheduler.UI.Term;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.c196.TermScheduler.DB.SchedulerDB;
import com.c196.TermScheduler.DB.TermDAO;
import com.c196.TermScheduler.Data.SchedulerRepository;
import com.c196.TermScheduler.Model.Course;
import com.c196.TermScheduler.Model.CourseWithTerm;
import com.c196.TermScheduler.Model.Term;
import com.c196.TermScheduler.Model.TermViewModel;
import com.c196.TermScheduler.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {
    private String TAG = "TermAdapter";
    private final List<Term> termList;

    public TermAdapter(List<Term> termList) {
        this.termList = termList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_term_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Term term = termList.get(position);

        holder.id.setText(String.valueOf(term.getId()));
        holder.title.setText(term.getTitle());
        holder.start.setText(term.getStart().toString());
        holder.end.setText(term.getEnd().toString());
    }

    @Override
    public int getItemCount() {
        return termList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Button termDetailButton;
        public Button termDeleteButton;
        public TextView id;
        public TextView title;
        public TextView start;
        public TextView end;

        LinearLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Context viewContext = itemView.getContext();
            termDetailButton = itemView.findViewById(R.id.termDetailButton);
            termDeleteButton = itemView.findViewById(R.id.termDeleteButton);
            id = itemView.findViewById(R.id.idTextView);
            title = itemView.findViewById(R.id.titleTextView);
            start = itemView.findViewById(R.id.startTextView);
            end = itemView.findViewById(R.id.endTextView);

            parentLayout = itemView.findViewById(R.id.termItem);


            termDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = termList.get(position);
                    Log.d(TAG, "onClick: " + itemView.getContext().toString());
                    Intent intent = new Intent(viewContext, TermDetail.class);
                    intent.putExtra("id", String.valueOf(current.getId()));
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("start", String.valueOf(current.getStart()));
                    intent.putExtra("end", String.valueOf(current.getEnd()));
                    viewContext.startActivity(intent);
                }

            });
            termDeleteButton.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int courseCount = 0;
                    int position = getAdapterPosition();
                    final Term current = termList.get(position);
                    SchedulerRepository repository = new SchedulerRepository((Application) viewContext.getApplicationContext());
                    Course associated = repository.getOneCourseByFK(current.getId());
                    Log.d(TAG, "onClick: " + associated.getId());
//                        repository.deleteTerm(current);

//                    if (repository.getOneCourseByFK(current.getId()) == null) {
//                        Toast.makeText(viewContext.getApplicationContext(), "Course Saved Successfully", Toast.LENGTH_LONG).show();
//                    } else {
//
//                    }
                }
            }));
        }
    }
}
