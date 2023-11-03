package com.example.findmyclassmates.activities.mainFeatures;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder> {
    private String[] subItems;

    public SubItemAdapter(String[] subItems) {
        this.subItems = subItems;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemViewHolder holder, int position) {
        holder.subItemText.setText(subItems[position]);
    }

    @Override
    public int getItemCount() {
        return subItems.length;
    }

    public static class SubItemViewHolder extends RecyclerView.ViewHolder {
        TextView subItemText;

        public SubItemViewHolder(@NonNull View itemView) {
            super(itemView);
            subItemText = itemView.findViewById(android.R.id.text1);
        }
    }
}