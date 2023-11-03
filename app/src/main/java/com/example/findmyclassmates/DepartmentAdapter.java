package com.example.findmyclassmates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder> {
    private ArrayList<DepartmentItem> departmentList;

    public DepartmentAdapter(ArrayList<DepartmentItem> departmentList) {
        this.departmentList = departmentList;
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

        // Handle the click event for the dropdown button
        holder.expandButton.setOnClickListener(v -> {
            if (holder.subRecyclerView.getVisibility() == View.GONE) {
                holder.subRecyclerView.setVisibility(View.VISIBLE);
                holder.expandButton.setImageResource(android.R.drawable.arrow_up_float);
            } else {
                holder.subRecyclerView.setVisibility(View.GONE);
                holder.expandButton.setImageResource(android.R.drawable.arrow_down_float);
            }
        });

        SubItemAdapter subItemAdapter = new SubItemAdapter(departmentItem.getSubItems());
        holder.subRecyclerView.setAdapter(subItemAdapter);
    }

    @Override
    public int getItemCount() {
        return departmentList.size();
    }

    public static class DepartmentViewHolder extends RecyclerView.ViewHolder {
        TextView departmentName;
        ImageButton expandButton;
        RecyclerView subRecyclerView;

        public DepartmentViewHolder(@NonNull View itemView) {
            super(itemView);
            departmentName = itemView.findViewById(R.id.departmentName);
            expandButton = itemView.findViewById(R.id.expandButton);
            subRecyclerView = itemView.findViewById(R.id.subRecyclerView);
        }
    }
}
