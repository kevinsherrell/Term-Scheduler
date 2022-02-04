package com.c196.TermScheduler.UI;

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

import com.c196.TermScheduler.Model.Term;
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
        public TextView id;
        public TextView title;
        public TextView start;
        public TextView end;

        LinearLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Context viewContext = itemView.getContext();
            termDetailButton = itemView.findViewById(R.id.termDetailButton);
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
                    intent.putExtra("id", current.getId());
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("start", current.getStart());
                    intent.putExtra("end", current.getEnd());
                    viewContext.startActivity(intent);
                }

            });

        }
    }
}
