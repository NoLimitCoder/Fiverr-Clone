package com.example.csci3130_w24_group20_quick_cash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class JobOfferAdapter extends RecyclerView.Adapter<JobOfferAdapter.ViewHolder> {

    private List<JobOffer> jobOffers;

    private OnGoingJobItemClickListener clickListener;

    public interface OnGoingJobItemClickListener {
        void OnGoingJobItemClickListener(JobOffer jobOffer);
    }

    /**
     * Constructor for JobOfferAdapter.
     *
     * @param jobOffers The list of ongoing job offers to be displayed.
     * @param clickListener The click listener for job items.
     */
    public JobOfferAdapter(List<JobOffer> jobOffers, OnGoingJobItemClickListener clickListener) {
        this.jobOffers = jobOffers;
        this.clickListener = clickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle;
        TextView jobSalary;
        TextView startDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.textJobTitle);
            jobSalary = itemView.findViewById(R.id.textJobSalary);
            startDate = itemView.findViewById(R.id.textJobStartDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && clickListener != null) {
                        JobOffer jobOffer = jobOffers.get(position);
                        clickListener.OnGoingJobItemClickListener(jobOffer);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_job_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobOffer jobOffer = jobOffers.get(position);
        holder.jobTitle.setText(jobOffer.getJobTitle());
        holder.jobSalary.setText(jobOffer.getSalary());
        holder.startDate.setText(jobOffer.getStartDate());
    }

    @Override
    public int getItemCount() {
        return jobOffers.size();
    }
}
