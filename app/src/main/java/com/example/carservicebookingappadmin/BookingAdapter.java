package com.example.carservicebookingappadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carservicebookingappadmin.models.Booking;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookingList;
    private boolean isUpdateMode;
    private OnBookingActionListener actionListener;

    // Constructor for view-only mode (HomeFragment)
    public BookingAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList;
        this.isUpdateMode = false;
        this.actionListener = null;
    }

    // Constructor for update mode (UpdateFragment)
    public BookingAdapter(List<Booking> bookingList, boolean isUpdateMode, OnBookingActionListener listener) {
        this.bookingList = bookingList;
        this.isUpdateMode = isUpdateMode;
        this.actionListener = listener;
    }

    public interface OnBookingActionListener {
        void onAccept(Booking booking, int position);
        void onDecline(Booking booking, int position);
    }

    public void updateBookings(List<Booking> newBookings) {
        this.bookingList = newBookings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.customerNameTextView.setText(booking.getCustomerName());
        holder.serviceTypeTextView.setText(booking.getServiceType());
        holder.dateTextView.setText(booking.getDate());
        holder.textStatus.setText("Status: " + booking.getStatus());

        if (isUpdateMode) {
            holder.btnAccept.setVisibility(View.VISIBLE);
            holder.btnDecline.setVisibility(View.VISIBLE);

            holder.btnAccept.setOnClickListener(v -> {
                if (actionListener != null) {
                    actionListener.onAccept(booking, holder.getAdapterPosition());
                }
            });

            holder.btnDecline.setOnClickListener(v -> {
                if (actionListener != null) {
                    actionListener.onDecline(booking, holder.getAdapterPosition());
                }
            });

        } else {
            holder.btnAccept.setVisibility(View.GONE);
            holder.btnDecline.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView customerNameTextView, serviceTypeTextView, dateTextView, textStatus;
        Button btnAccept, btnDecline;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            customerNameTextView = itemView.findViewById(R.id.customerNameTextView);
            serviceTypeTextView = itemView.findViewById(R.id.serviceTypeTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            textStatus = itemView.findViewById(R.id.textStatus);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnDecline = itemView.findViewById(R.id.btnDecline);
        }
    }
}


