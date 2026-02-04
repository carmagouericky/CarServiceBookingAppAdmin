package com.example.carservicebookingappadmin;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PendingBookingAdapter extends RecyclerView.Adapter<PendingBookingAdapter.ViewHolder> {

    private List<BookingItem> bookings;
    private OnBookingActionListener listener;

    public interface OnBookingActionListener {
        void onApprove(int position, String mechanicName);
        void onDecline(int position);
    }

    public PendingBookingAdapter(List<BookingItem> bookingList, OnBookingActionListener listener) {
        this.bookings = bookings;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PendingBookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pending_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingBookingAdapter.ViewHolder holder, int position) {
        BookingItem item = bookings.get(position);
        holder.tvPlate.setText(item.getPlate());
        holder.btnApprove.setOnClickListener(v -> {
            String mechanic = holder.etMechanic.getText().toString().trim();
            listener.onApprove(position, mechanic);
        });
        holder.btnDecline.setOnClickListener(v -> {
            listener.onDecline(position);
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlate;
        EditText etMechanic;
        Button btnApprove, btnDecline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlate = itemView.findViewById(R.id.tv_plate_pending);
            etMechanic = itemView.findViewById(R.id.et_mechanic);
            btnApprove = itemView.findViewById(R.id.btn_approve);
            btnDecline = itemView.findViewById(R.id.btn_decline);
        }
    }
}
