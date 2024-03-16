package com.example.csci3130_w24_group20_quick_cash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter class for RecyclerView to display a list of job applications.
 */
public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> {

    private static List<ApplicationPosting> applicationList;
    private static OnApplicationItemClickListener clickListener;

    /**
     * Interface definition for a callback to be invoked when an application item is clicked.
     */
    public interface OnApplicationItemClickListener {
        /**
         * Called when an application item is clicked.
         *
         * @param applicationPosting The clicked application posting object.
         */
        void onApplicationItemClick(ApplicationPosting applicationPosting);
    }

    /**
     * Constructor for ApplicationAdapter.
     *
     * @param applicationList The list of job applications to be displayed.
     * @param clickListener   The click listener for application items.
     */
    public ApplicationAdapter(List<ApplicationPosting> applicationList, OnApplicationItemClickListener clickListener) {
        this.applicationList = applicationList;
        this.clickListener = clickListener;
    }

    /**
     * Updates the list of job applications.
     *
     * @param applicationList The updated list of job applications.
     */
    public void updateApplications(List<ApplicationPosting> applicationList) {
        this.applicationList = applicationList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_item, parent, false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        ApplicationPosting application = applicationList.get(position);

        holder.textJobTitle.setText(application.getJobTitle());
        holder.textApplicantEmail.setText(application.getApplicantEmail());
        holder.textApplicantAvailability.setText(application.getApplicantAvailability());
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    /**
     * ViewHolder class for application items.
     */
    public static class ApplicationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textJobTitle, textApplicantEmail, textApplicantAvailability;

        /**
         * Constructor for ApplicationViewHolder.
         *
         * @param itemView The view for the application item.
         */
        public ApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            textJobTitle = itemView.findViewById(R.id.textJobTitle);
            textApplicantEmail = itemView.findViewById(R.id.textApplicantEmail);
            textApplicantAvailability = itemView.findViewById(R.id.textApplicantAvailability);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && clickListener != null) {
                clickListener.onApplicationItemClick(applicationList.get(position));
            }
        }
    }
}