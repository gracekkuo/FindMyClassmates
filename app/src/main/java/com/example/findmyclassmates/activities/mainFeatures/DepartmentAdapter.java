package com.example.findmyclassmates.activities.mainFeatures;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyclassmates.R;
import com.example.findmyclassmates.models.Course;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Arrays;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder> {
    private ArrayList<DepartmentItem> departmentList;
    FirebaseDatabase db;
    DatabaseReference reference;
    private Context context;
    Course nowCourse;
    //private boolean[] isExpanded;

    public DepartmentAdapter(ArrayList<DepartmentItem> departmentList, Context context) {
        this.departmentList = departmentList;
        this.context = context;
        //isExpanded = new boolean[departmentList.size()];
        //Arrays.fill(isExpanded, false);
    }

    @NonNull
    @Override
    public DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.department_courses, parent, false);
        return new DepartmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentViewHolder holder, int position) {
        DepartmentItem departmentItem = departmentList.get(position);
        holder.departmentName.setText(departmentItem.getDepartmentName());

        //get firebase setup
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Courses");

        //get dept
        String[] dept = departmentItem.getDepartmentName().split("\\s+");

        //populate coursenames
        holder.course1.setText(dept[0]+" - "+departmentItem.getSubItems()[0]);
        holder.course2.setText(dept[0]+" - "+departmentItem.getSubItems()[1]);
        holder.course3.setText(dept[0]+" - "+departmentItem.getSubItems()[2]);
        holder.course4.setText(dept[0]+" - "+departmentItem.getSubItems()[3]);
        holder.course5.setText(dept[0]+" - "+departmentItem.getSubItems()[4]);
        holder.course6.setText(dept[0]+" - "+departmentItem.getSubItems()[5]);

        // Handle the click event for the dropdown button
        holder.expandButton.setOnClickListener(v -> {
            if (holder.subRecyclerView.getVisibility() == View.GONE) {
                holder.subRecyclerView.setVisibility(View.VISIBLE);
                holder.expandButton.setImageResource(android.R.drawable.arrow_up_float);
                //notifyItemChanged(position); // Notify the adapter to update the view
            } else {
                holder.subRecyclerView.setVisibility(View.GONE);
                holder.expandButton.setImageResource(android.R.drawable.arrow_down_float);
                //notifyItemChanged(position); // Notify the adapter to update the view
            }
        });

        TextView textViewCourse1 = holder.course1;
        TextView textViewCourse2 = holder.course2;
        TextView textViewCourse3 = holder.course3;
        TextView textViewCourse4 = holder.course4;
        TextView textViewCourse5 = holder.course5;
        TextView textViewCourse6 = holder.course6;
        textViewCourse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DepartmentItem departmentItem = departmentList.get(holder.getBindingAdapterPosition());
                holder.departmentName.setText(departmentItem.getDepartmentName());
                // find the course
                courseFinder courseFinder = new courseFinder(reference);
                String[] dept = departmentItem.getDepartmentName().split("\\s+");
                System.out.println(dept[0]+departmentItem.getSubItems()[0]);
                Course course = courseFinder.findCourse(dept[0],departmentItem.getSubItems()[0], context); //change subitems index
            }
        });
        textViewCourse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DepartmentItem departmentItem = departmentList.get(holder.getBindingAdapterPosition());
                holder.departmentName.setText(departmentItem.getDepartmentName());
                // find the course
                courseFinder courseFinder = new courseFinder(reference);
                String[] dept = departmentItem.getDepartmentName().split("\\s+");
                System.out.println(dept[0]+departmentItem.getSubItems()[0]);
                Course course = courseFinder.findCourse(dept[0],departmentItem.getSubItems()[1], context); //change subitems index
            }
        });
        textViewCourse3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DepartmentItem departmentItem = departmentList.get(holder.getBindingAdapterPosition());
                holder.departmentName.setText(departmentItem.getDepartmentName());
                // find the course
                courseFinder courseFinder = new courseFinder(reference);
                String[] dept = departmentItem.getDepartmentName().split("\\s+");
                System.out.println(dept[0]+departmentItem.getSubItems()[0]);
                Course course = courseFinder.findCourse(dept[0],departmentItem.getSubItems()[2], context); //change subitems index
            }
        });
        textViewCourse4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DepartmentItem departmentItem = departmentList.get(holder.getBindingAdapterPosition());
                holder.departmentName.setText(departmentItem.getDepartmentName());
                // find the course
                courseFinder courseFinder = new courseFinder(reference);
                String[] dept = departmentItem.getDepartmentName().split("\\s+");
                System.out.println(dept[0]+departmentItem.getSubItems()[0]);
                Course course = courseFinder.findCourse(dept[0],departmentItem.getSubItems()[3], context); //change subitems index
            }
        });
        textViewCourse5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DepartmentItem departmentItem = departmentList.get(holder.getBindingAdapterPosition());
                holder.departmentName.setText(departmentItem.getDepartmentName());
                // find the course
                courseFinder courseFinder = new courseFinder(reference);
                String[] dept = departmentItem.getDepartmentName().split("\\s+");
                System.out.println(dept[0]+departmentItem.getSubItems()[0]);
                Course course = courseFinder.findCourse(dept[0],departmentItem.getSubItems()[4], context); //change subitems index
            }
        });
        textViewCourse6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DepartmentItem departmentItem = departmentList.get(holder.getBindingAdapterPosition());
                holder.departmentName.setText(departmentItem.getDepartmentName());
                // find the course
                courseFinder courseFinder = new courseFinder(reference);
                String[] dept = departmentItem.getDepartmentName().split("\\s+");
                System.out.println(dept[0]+departmentItem.getSubItems()[0]);
                Course course = courseFinder.findCourse(dept[0],departmentItem.getSubItems()[5], context); //change subitems index
            }
        });


    }

    @Override
    public int getItemCount() {
        return departmentList.size();
    }

    public static class DepartmentViewHolder extends RecyclerView.ViewHolder {
        TextView departmentName;
        ImageButton expandButton;
        LinearLayout subRecyclerView;

        TextView course1;
        TextView course2;
        TextView course3;
        TextView course4;
        TextView course5;
        TextView course6;

        public DepartmentViewHolder(@NonNull View itemView) {
            super(itemView);
            departmentName = itemView.findViewById(R.id.departmentName);
            expandButton = itemView.findViewById(R.id.expandButton);
            subRecyclerView = itemView.findViewById(R.id.courseList);
            course1 = itemView.findViewById(R.id.course1);
            course2 = itemView.findViewById(R.id.course2);
            course3 = itemView.findViewById(R.id.course3);
            course4 = itemView.findViewById(R.id.course4);
            course5 = itemView.findViewById(R.id.course5);
            course6 = itemView.findViewById(R.id.course6);
        }
    }
}
