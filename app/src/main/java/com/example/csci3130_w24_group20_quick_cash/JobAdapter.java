package com.example.csci3130_w24_group20_quick_cash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder>{

    private static List<JobPosting> joblist;
    private static OnJobItemClickListener clickListener;

    public interface OnJobItemClickListener {
        void onJobItemClick(JobPosting jobPosting);

    }

    public JobAdapter(List <JobPosting> joblist, OnJobItemClickListener clickListener){
        this.joblist = joblist;
        this.clickListener = clickListener;
    }

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
    }

    @Override
    public int getItemCount(){
        return joblist.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textJobTitle, textJobSalary, textJobType;

        public JobViewHolder(@NonNull View itemView){
            super(itemView);
            textJobTitle = itemView.findViewById(R.id.textJobTitle);
            textJobSalary = itemView.findViewById(R.id.textJobSalary);
            textJobType = itemView.findViewById(R.id.textJobType);

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
