package com.example.solaristemplate;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClassInListAdapter extends RecyclerView.Adapter<ClassInListAdapter.classinlistViewHolder> {
    private ArrayList<Course> mCourseList;

    public static class classinlistViewHolder extends RecyclerView.ViewHolder {
        public TextView major_number;
        public TextView class_name;
        public TextView professor;
        public TextView time;

        public classinlistViewHolder(@NonNull View itemView) {
            super(itemView);
            major_number = itemView.findViewById(R.id.major_and_number);
            class_name = itemView.findViewById(R.id.name);
            professor = itemView.findViewById(R.id.professor);
            time = itemView.findViewById(R.id.time);
        }
    }

    public ClassInListAdapter(ArrayList<Course> courseList) {
        mCourseList = courseList;
    }

    @NonNull
    @Override
    public classinlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uni_class, parent, false);
        return new classinlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull classinlistViewHolder holder, int position) {
        Course course = mCourseList.get(position);

        holder.major_number.setText(course.getMajor() + " " + course.getCourse_num());
        holder.class_name.setText(course.getName());
        holder.professor.setText(course.getProfessor());
        holder.time.setText(course.getTime());
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }
}
