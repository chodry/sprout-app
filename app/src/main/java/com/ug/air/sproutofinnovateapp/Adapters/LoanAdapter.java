package com.ug.air.sproutofinnovateapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ug.air.sproutofinnovateapp.Models.Application;
import com.ug.air.sproutofinnovateapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.LoanViewHolder> {

    Context context;
    List<Application> applicationList;
    private OnItemClickListener mListener;

    public LoanAdapter(Context context, List<Application> applicationList) {
        this.context = context;
        this.applicationList = applicationList;
    }

    public interface OnItemClickListener {
        void onEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public LoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_application, parent, false);
        LoanViewHolder loanViewHolder = new LoanViewHolder(view, mListener);
        return loanViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoanViewHolder holder, int position) {
        String name = applicationList.get(position).getApplicant().getFirst_name() + " " + applicationList.get(position).getApplicant().getLast_name();
        holder.name.setText(name);
        String address = applicationList.get(position).getLocation().getVillage() + "-" + applicationList.get(position).getLocation().getParish() + "-" + applicationList.get(position).getLocation().getCounty() + "-" + applicationList.get(position).getLocation().getSubcounty() + "-" + applicationList.get(position).getLocation().getDistrict();
        String tel_1 = applicationList.get(position).getApplicant().getTelephone_number_1();
        String tel_2 = applicationList.get(position).getApplicant().getTelephone_number_2();
        Log.d("SOIL Data", "onBindViewHolder: "+ tel_2);

        holder.contact.setText(tel_1 + " / " + tel_2);
//
        holder.address.setText(address);
        holder.amount.setText(putComma(applicationList.get(position).getAmount()));
        holder.source.setText(applicationList.get(position).getSource_of_income());
        holder.income.setText(putComma(applicationList.get(position).getIncome()) + " " + applicationList.get(position).getWeekly_or_monthly());

        String duration = applicationList.get(position).getDuration_of_payment() + " " + applicationList.get(position).getTime_line();

        holder.duration.setText(duration);
        holder.collateral.setText(applicationList.get(position).getCollateral());
        holder.age.setText(String.valueOf(applicationList.get(position).getApplicant().getAge()) + " years");
        holder.gender.setText(applicationList.get(position).getApplicant().getGender());
        holder.interest.setText(String.valueOf(applicationList.get(position).getInterest()) + "%");
        holder.next.setText(applicationList.get(position).getGuarantor());
        holder.next_relation.setText(applicationList.get(position).getGuarantor_relationship());
        holder.next_contact.setText(applicationList.get(position).getGuarantor_telephone_number());
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public class LoanViewHolder extends RecyclerView.ViewHolder {

        TextView name, contact, age, gender, address, source, income, amount, duration, collateral, interest, next, next_relation, next_contact;
        LinearLayout linearLayout1, linearLayout2;
        ImageView edit;

        public LoanViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            name = itemView.findViewById(R.id.username);
            contact = itemView.findViewById(R.id.contact);
            source = itemView.findViewById(R.id.source);
            income = itemView.findViewById(R.id.income);
            address = itemView.findViewById(R.id.address);
            age = itemView.findViewById(R.id.age);
            gender = itemView.findViewById(R.id.gender);
            interest = itemView.findViewById(R.id.interest);
            amount = itemView.findViewById(R.id.amount);
            duration = itemView.findViewById(R.id.duration);
            collateral = itemView.findViewById(R.id.collateral);
            next = itemView.findViewById(R.id.next);
            next_contact = itemView.findViewById(R.id.next_contact);
            next_relation = itemView.findViewById(R.id.relationship);
            linearLayout1 = itemView.findViewById(R.id.whole_layout);
            linearLayout2 = itemView.findViewById(R.id.new_layout);
            edit = itemView.findViewById(R.id.edit);

            linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("SOIL EROSION", "onClick: its been clicked");
                    Log.d("SOIL EROSION", "onClick: " + listener);
                    if (listener != null){
                        Log.d("SOIL EROSION", "onClick: its been clicked 1");
                        int position = getAdapterPosition();
                        Log.d("SOIL EROSION", "onClick: " + position);
                        if (position != RecyclerView.NO_POSITION){
                            Log.d("SOIL EROSION", "onClick: its been clicked 2");
                            if (linearLayout2.getVisibility() == View.VISIBLE){
                                Log.d("SOIL EROSION", "onClick: its visible");
                                linearLayout2.setVisibility(View.GONE);
                            }else {
                                Log.d("SOIL EROSION", "onClick: its gone");
                                linearLayout2.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            });
//
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });
        }
    }

    private String putComma(int amount){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String yourFormattedString = formatter.format(amount);

        return "UGX. " + yourFormattedString;

    }
}
