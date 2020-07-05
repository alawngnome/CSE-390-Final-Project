/**
 * @authors Kevin Chao 112031000 and Samuel Ng 112330868
 * CSE 390 Final Project
 * SolarisTemplate App (Schedule Viewer for Students)
 */

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

/**
 *
 * I refer to Chapters 5-6 of Iversen and Eierman's textbook for setting up the adapter class.
 *
 * Adapter for RecyclerView for implementing the list of courses on the Home tab.
 */
public class CourseAdapter extends RecyclerView.Adapter{
    /**
     * List of courses received from MainActivity.
     */
    private ArrayList<Course> courses;
    /**
     * OnNoteListener received from MainActivity for reacting to clicks on the card.
     */
    private OnNoteListener mOnNoteListener;
    /**
     * Context received from MainActivity
     */
    private Context context;

    /**
     * Inner ViewHolder class for RecyclerView, which functions as an OnClickListener for edit and delete buttons.
     */
    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * TextViews present on each card.
         */
        public TextView major_number, class_name, professor, time;
        /**
         * Buttons present on each card.
         */
        public Button edit_button, delete_button;

        /**
         * OnNoteListener passed in from the adapter class.
         */
        OnNoteListener onNoteListener;

        /**
         * Constructor for the CourseViewHolder.
         * @param itemView view of each card
         * @param onNoteListener listener for responding to clicks on the card
         */
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
         * Getter methods for the above fields
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
         * onClick method for edit and delete buttons.
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

    /**
     * Constructor for the CourseAdapter
     * @param courses list of courses
     * @param onNoteListener listener for responding to clicks on cards
     * @param context context of activity instantiating the adapter
     */
    public CourseAdapter(ArrayList<Course> courses, OnNoteListener onNoteListener, Context context) {
        this.courses = courses;
        this.mOnNoteListener = onNoteListener;
        this.context = context;
    }

    /**
     * Method for displaying items in RecyclerView onCreate.
     * @param parent
     * @param viewType
     * @return new CourseViewHolder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.uni_class, parent, false);
        return new CourseViewHolder(v, mOnNoteListener);
    }

    /**
     * Method for modifying elements on each card in RecyclerView.
     * @param holder ViewHolder for all the cards
     * @param position position on the list
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CourseViewHolder courseHolder = (CourseViewHolder) holder;
        courseHolder.getClass_name().setText(courses.get(position).getName());
        String major_number = courses.get(position).getMajor() + " " + courses.get(position).getCourse_num();
        courseHolder.getMajor_number().setText(major_number);
        courseHolder.getProfessor().setText(courses.get(position).getProfessor());
        courseHolder.getTime().setText(courses.get(position).getTime());
    }

    /**
     * Method for accessing size of the list
     * @return number of items in RecyclerView
     */
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
