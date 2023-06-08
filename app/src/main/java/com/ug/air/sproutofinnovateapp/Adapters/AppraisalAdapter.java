package com.ug.air.sproutofinnovateapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ug.air.sproutofinnovateapp.Models.Appraisal;
import com.ug.air.sproutofinnovateapp.R;

import java.util.List;

public class AppraisalAdapter extends RecyclerView.Adapter<AppraisalAdapter.AppraisalViewHolder> {

    Context context;
    List<Appraisal> appraisalList;

    public AppraisalAdapter(Context context, List<Appraisal> appraisalList) {
        this.context = context;
        this.appraisalList = appraisalList;
    }

    @NonNull
    @Override
    public AppraisalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppraisalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.appraisal, parent,  false));
    }

    @Override
    public void onBindViewHolder(@NonNull AppraisalViewHolder holder, int position) {
        Appraisal appraisal = appraisalList.get(position);
        holder.textView1.setText(appraisal.getName());
        holder.textView2.setText(appraisal.getDate());
    }

    @Override
    public int getItemCount() {
        return appraisalList.size();
    }

    public class AppraisalViewHolder extends RecyclerView.ViewHolder {

        TextView textView1, textView2;

        public AppraisalViewHolder(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.name);
            textView2 = itemView.findViewById(R.id.date);
        }
    }
}
