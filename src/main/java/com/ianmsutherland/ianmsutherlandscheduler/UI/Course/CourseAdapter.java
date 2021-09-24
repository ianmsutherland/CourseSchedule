package com.ianmsutherland.ianmsutherlandscheduler.UI.Course;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ianmsutherland.ianmsutherlandscheduler.Entities.Course;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Term;
import com.ianmsutherland.ianmsutherlandscheduler.R;
import com.ianmsutherland.ianmsutherlandscheduler.UI.Term.TermDetailsActivity;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {

    private final LayoutInflater mInflater;
    private final Context context;
    private List<Course> mCourses;
    private TextView courseStartDateView;
    private TextView courseEndDateView;
    private String TAG = "SchedulerDatabaseLog";

    class CourseHolder extends RecyclerView.ViewHolder {
        private final TextView courseNameItemView;
        // private final TextView courseStartDateItemView;
        // private final TextView courseEndDateItemView;

        private CourseHolder(View itemView) {
            super(itemView);
            courseNameItemView = itemView.findViewById(R.id.courseNameView);
            // courseStartDateItemView = itemView.findViewById(R.id.courseStartDateView);
            // courseEndDateItemView = itemView.findViewById(R.id.courseEndDateView);
            courseNameItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    Course course = mCourses.get(position);
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetailsActivity.class);
                    intent.putExtra("courseName", current.getName());
                    intent.putExtra("courseId", current.getId());
                    intent.putExtra("courseStartDate", current.getStartDate());
                    intent.putExtra("courseEndDate",current.getEndDate());
                    intent.putExtra("courseTerm",current.getTerm());
                    intent.putExtra("courseStatus",current.getStatus());
                    intent.putExtra("courseInstructorName",current.getInstructorName());
                    intent.putExtra("courseInstructorPhone",current.getInstructorPhone());
                    intent.putExtra("courseInstructorEmail",current.getInstructorEmail());
                    intent.putExtra("courseNote",current.getNote());
                    intent.putExtra("position",position);
                    context.startActivity(intent);

                }
            });
        }
    }

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, int position) {
        if (mCourses != null) {
            final Course current = mCourses.get(position);
            holder.courseNameItemView.setText(current.getName());
            // holder.courseStartDateItemView.setText(current.getStartDate());
            // holder.courseEndDateItemView.setText(current.getEndDate());
        }
        else {
            holder.courseNameItemView.setText("No terms yet.");
        }
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_course, parent, false);
        return new CourseHolder(itemView);
    }
    @Override
    public int getItemCount() {
        if (mCourses == null) {
            return 0;
        }
        else {
            return mCourses.size();
        }
    }
    public void setCourses(List<Course> course){
        mCourses = course;
        notifyDataSetChanged();
    }

}
