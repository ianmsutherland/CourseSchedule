package com.ianmsutherland.ianmsutherlandscheduler.UI.Assessment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ianmsutherland.ianmsutherlandscheduler.Entities.Assessment;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Course;
import com.ianmsutherland.ianmsutherlandscheduler.R;
import com.ianmsutherland.ianmsutherlandscheduler.UI.Course.CourseDetailsActivity;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentHolder> {

    private final LayoutInflater mInflater;
    private final Context context;
    private List<Assessment> mAssessments;
    private String TAG = "SchedulerDatabaseLog";

    class AssessmentHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentNameItemView;

        private AssessmentHolder(View itemView) {
            super(itemView);
            assessmentNameItemView = itemView.findViewById(R.id.assessmentNameView);
            assessmentNameItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetailsActivity.class);
                    intent.putExtra("assessmentName", current.getName());
                    intent.putExtra("assessmentId", current.getId());
                    intent.putExtra("courseId", current.getCourse());
                    intent.putExtra("assessmentStartDate", current.getStartDate());
                    intent.putExtra("assessmentEndDate",current.getEndDate());
                    intent.putExtra("assessmentType",current.getType());
                    intent.putExtra("position",position);
                    context.startActivity(intent);
                }
            });
        }
    }

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public void onBindViewHolder(AssessmentHolder holder, int position) {
        if (mAssessments != null) {
            final Assessment current = mAssessments.get(position);
            holder.assessmentNameItemView.setText(current.getName());
        }
        else {
            holder.assessmentNameItemView.setText("No assessments yet.");
        }
    }

    @Override
    public AssessmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_assessment, parent, false);
        return new AssessmentHolder(itemView);
    }

    @Override
    public int getItemCount() {
        if (mAssessments == null) {
            return 0;
        } else {
            return mAssessments.size();
        }
    }

    public void setAssessments(List<Assessment> assessment){
        mAssessments = assessment;
        notifyDataSetChanged();
    }

}
