package com.example.solaristemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter{
    private ArrayList<Course> courses; // list of courses received from MainActivity
    private OnNoteListener mOnNoteListener; // OnNoteListener received from MainActivity
    private Context context; // Context received from MainActivity

    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView major_number;
        public TextView class_name;
        public TextView professor;
        public TextView time;
        public Button edit_button;
        public Button delete_button;

        OnNoteListener onNoteListener;

        public CourseViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            major_number = itemView.findViewById(R.id.major_and_number);
            class_name = itemView.findViewById(R.id.name);
            professor = itemView.findViewById(R.id.professor);
            time = itemView.findViewById(R.id.time);
            edit_button = itemView.findViewById(R.id.editBtn);
            delete_button = itemView.findViewById(R.id.deleteBtn);

            this.onNoteListener = onNoteListener;
            edit_button.setOnClickListener(this);
            delete_button.setOnClickListener(this);
        }

        /**
         * Getter methods
         */
        public TextView getMajor_number() {
            return major_number;
        }

        public TextView getClass_name() {
            return class_name;
        }

        public TextView getProfessor() {
            return professor;
        }

        public TextView getTime() {
            return time;
        }

        /**
         * onClick method for edit and delete buttons
         * @param v widget argument
         */
        @Override
        public void onClick(View v) {
            boolean delete;
            if (v.getId() == R.id.editBtn) {
                delete = false;
            }
            else {
                delete = true;
            }
            onNoteListener.onNoteClick(getAdapterPosition(), delete);
        }
    }

    public CourseAdapter(ArrayList<Course> courses, OnNoteListener onNoteListener, Context context) {
        this.courses = courses;
        this.mOnNoteListener = onNoteListener;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.uni_class, parent, false);
        return new CourseViewHolder(v, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CourseViewHolder courseHolder = (CourseViewHolder) holder;
        courseHolder.getClass_name().setText(courses.get(position).getName());
        String major_number = courses.get(position).getMajor() + " " + courses.get(position).getCourse_num();
        courseHolder.getMajor_number().setText(major_number);
        courseHolder.getProfessor().setText(courses.get(position).getProfessor());
        courseHolder.getTime().setText(courses.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    /**
     * interface for the onNoteListener
     */
    public interface OnNoteListener {
        void onNoteClick(int position, boolean delete);
    }
}
