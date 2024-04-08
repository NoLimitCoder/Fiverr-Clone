package com.example.csci3130_w24_group20_quick_cash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter class for RecyclerView to display a list of job postings.
 */
public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder>{

    private static List<JobPosting> joblist;
    private static OnJobItemClickListener clickListener;

    /**
     * Interface definition for a callback to be invoked when a job item is clicked.
     */
    public interface OnJobItemClickListener {
        /**
         * Called when a job item is clicked.
         *
         * @param jobPosting The clicked job posting object.
         */
        void onJobItemClick(JobPosting jobPosting);
    }

    /**
     * Constructor for JobAdapter.
     *
     * @param joblist The list of job postings to be displayed.
     * @param clickListener The click listener for job items.
     */
    public JobAdapter(List<JobPosting> joblist, OnJobItemClickListener clickListener){
        this.joblist = joblist;
        this.clickListener = clickListener;
    }

    /**
     * Updates the list of job postings.
     *
     * @param joblist The updated list of job postings.
     */
    public void updateJobPostings(List<JobPosting> joblist){
        this.joblist = joblist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position){
        JobPosting job = joblist.get(position);

        holder.textJobTitle.setText(job.getJobTitle());
        holder.textJobSalary.setText(job.getJobSalary());
        holder.textJobType.setText(job.getJobType());
        holder.textJobCountry.setText(job.getJobCountry());
        holder.textJobCity.setText(job.getJobCity());
    }

    @Override
    public int getItemCount(){
        return joblist.size();
    }

    /**
     * ViewHolder class for job items.
     */
    public static class JobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textJobTitle;
        TextView textJobSalary;
        TextView textJobType;
        TextView textJobCountry;
        TextView textJobCity;

        /**
         * Constructor for JobViewHolder.
         *
         * @param itemView The view for the job item.
         */
        public JobViewHolder(@NonNull View itemView){
            super(itemView);
            textJobTitle = itemView.findViewById(R.id.textJobTitle);
            textJobSalary = itemView.findViewById(R.id.textJobSalary);
            textJobType = itemView.findViewById(R.id.textJobType);
            textJobCountry = itemView.findViewById(R.id.textJobCountry);
            textJobCity = itemView.findViewById(R.id.textJobCity);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && clickListener != null){
                clickListener.onJobItemClick(joblist.get(position));
            }
        }
    }
}
