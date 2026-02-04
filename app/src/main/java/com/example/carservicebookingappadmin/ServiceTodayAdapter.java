package com.example.carservicebookingappadmin;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServiceTodayAdapter extends RecyclerView.Adapter<ServiceTodayAdapter.ViewHolder> {

    private List<ServiceItem> serviceList;

    public ServiceTodayAdapter(List<ServiceItem> serviceList) {
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceTodayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service_today, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceTodayAdapter.ViewHolder holder, int position) {
        ServiceItem item = serviceList.get(position);
        holder.tvCarPlate.setText(item.getPlate());
        holder.tvMechanic.setText("Mech " + item.getMechanic());
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCarPlate, tvMechanic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCarPlate = itemView.findViewById(R.id.tv_plate);
            tvMechanic = itemView.findViewById(R.id.tv_mechanic);
        }
    }
}
