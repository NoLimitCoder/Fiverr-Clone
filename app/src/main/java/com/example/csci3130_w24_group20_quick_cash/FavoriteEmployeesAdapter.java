package com.example.csci3130_w24_group20_quick_cash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoriteEmployeesAdapter extends RecyclerView.Adapter<FavoriteEmployeesAdapter.ViewHolder> {

    private List<String> favoriteEmployeesList;

    public FavoriteEmployeesAdapter(List<String> favoriteEmployeesList) {
        this.favoriteEmployeesList = favoriteEmployeesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String employeeName = favoriteEmployeesList.get(position);
        holder.bind(employeeName);
    }

    @Override
    public int getItemCount() {
        return favoriteEmployeesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewEmployeeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEmployeeName = itemView.findViewById(R.id.textViewEmployeeName);
        }

        public void bind(String employeeName) {
            textViewEmployeeName.setText(employeeName);
        }
    }
}
