package com.ianmsutherland.ianmsutherlandscheduler.UI.Term;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ianmsutherland.ianmsutherlandscheduler.Entities.Term;
import com.ianmsutherland.ianmsutherlandscheduler.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermHolder> {

    private final LayoutInflater mInflater;
    private final Context context;
    private List<Term> mTerms;
    private TextView termNameView;
    private TextView termStartDateView;
    private TextView termEndDateView;

    private String TAG = "SchedulerDatabaseLog";

    class TermHolder extends RecyclerView.ViewHolder {
        private final TextView termNameItemView;
        private final TextView termStartDateItemView;
        private final TextView termEndDateItemView;

        private TermHolder(View itemView) {
            super(itemView);
            termNameItemView = itemView.findViewById(R.id.termNameView);
            termStartDateItemView = itemView.findViewById(R.id.termStartDateView);
            termEndDateItemView = itemView.findViewById(R.id.termEndDateView);
            termNameItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    Term term = mTerms.get(position);
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetailsActivity.class);
                    intent.putExtra("termName", current.getName());
                    intent.putExtra("termId", current.getId());
                    intent.putExtra("termStartDate", current.getStartDate());
                    intent.putExtra("termEndDate",current.getEndDate());
                    intent.putExtra("position",position);
                    context.startActivity(intent);
                }
            });
        }
    }

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public TermHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_term, parent, false);
        return new TermHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermHolder holder, int position) {

        if (mTerms != null) {
            final Term current = mTerms.get(position);
            holder.termNameItemView.setText(current.getName());
            holder.termStartDateItemView.setText(current.getStartDate());
            holder.termEndDateItemView.setText(current.getEndDate());
            holder.termNameItemView.setTag(mTerms.get(position));
        }
        else {
            holder.termNameItemView.setText("No terms yet.");
        }
    }

    @Override
    public int getItemCount() {
        if (mTerms == null) {
            return 0;
        }
        else {
            return mTerms.size();
        }
    }

    public void setTerms(List<Term> term){
        mTerms = term;
        notifyDataSetChanged();
    }

}
