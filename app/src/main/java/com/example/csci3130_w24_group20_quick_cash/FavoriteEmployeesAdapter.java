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


    /**
     * Constructs a new FavoriteEmployeesAdapter.
     *
     * @param favoriteEmployeesList The list of favorite employees to display.
     */

    public FavoriteEmployeesAdapter(List<String> favoriteEmployeesList) {
        this.favoriteEmployeesList = favoriteEmployeesList;
    }


    /**
     * Inflates the layout for a favorite employee item.
     *
     * @param parent   The parent view group.
     * @param viewType The type of view.
     * @return A new ViewHolder instance.
     */

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_employee, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds favorite employee data to the view holder.
     *
     * @param holder   The view holder to bind data to.
     * @param position The position of the item in the list.
     */

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String employeeName = favoriteEmployeesList.get(position);
        holder.bind(employeeName);
    }

    /**
     * Gets the total number of favorite employees in the list.
     *
     * @return The total number of favorite employees.
     */

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

        /**
         * Binds the employee name to the TextView.
         *
         * @param employeeName The name of the employee.
         */

        public void bind(String employeeName) {
            textViewEmployeeName.setText(employeeName);
        }
    }
}
