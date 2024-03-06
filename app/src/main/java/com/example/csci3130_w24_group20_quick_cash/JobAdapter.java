package com.example.csci3130_w24_group20_quick_cash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder>{

    private final List<JobPosting> joblist;

    public JobAdapter(List <JobPosting> joblist){
        this.joblist = joblist;
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
        holder.textEmployerName.setText(job.getEmployerName());
        holder.textJobLocation.setText(job.getJobLocation());
        holder.textJobSalary.setText(job.getJobSalary());
        holder.textDescription.setText(job.getJobDescription());
        holder.textJobType.setText(job.getJobType());
        holder.textOtherDetails.setText(job.getOtherDetails());
        holder.textDatePosted.setText(job.getDatePosted());

    }

    @Override
    public int getItemCount(){
        return joblist.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {

        TextView textJobTitle, textEmployerName, textJobLocation, textJobSalary, textDescription, textJobType, textOtherDetails, textDatePosted;

        public JobViewHolder(@NonNull View itemView){
            super(itemView);
            textJobTitle = itemView.findViewById(R.id.textJobTitle);
            textEmployerName = itemView.findViewById(R.id.textEmployerName);
            textJobLocation = itemView.findViewById(R.id.textJobLocation);
            textJobSalary = itemView.findViewById(R.id.textJobSalary);
            textDescription = itemView.findViewById(R.id.textDescription);
            textJobType = itemView.findViewById(R.id.textJobType);
            textOtherDetails = itemView.findViewById(R.id.textOtherDetails);
            textDatePosted = itemView.findViewById(R.id.textDatePosted);
        }
    }
}
