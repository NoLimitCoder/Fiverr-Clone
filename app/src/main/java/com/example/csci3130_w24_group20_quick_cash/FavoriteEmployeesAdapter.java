package com.example.csci3130_w24_group20_quick_cash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/**
 * Adapter class for displaying a list of favorite employees in a RecyclerView.
 */
public class FavoriteEmployeesAdapter extends RecyclerView.Adapter<FavoriteEmployeesAdapter.ViewHolder> {

    private List<String> favoriteEmployeesList;


    /**
     * Constructs a new FavoriteEmployeesAdapter with the provided list of favorite employees.
     *
     * @param favoriteEmployeesList The list of favorite employees to be displayed.
     */
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

    /**
     * ViewHolder class for holding the views associated with each item in the RecyclerView.
     */

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewEmployeeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEmployeeName = itemView.findViewById(R.id.textViewEmployeeName);
        }

        /**
         * Binds the employee name to the TextView.
         *
         * @param employeeName The name of the favorite employee to be displayed.
         */

        public void bind(String employeeName) {
            textViewEmployeeName.setText(employeeName);
        }
    }
}
