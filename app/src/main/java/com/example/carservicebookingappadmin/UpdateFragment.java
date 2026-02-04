package com.example.carservicebookingappadmin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carservicebookingappadmin.models.Booking;
import com.example.carservicebookingappadmin.models.BookingStatus;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UpdateFragment extends Fragment {

    private BookingAdapter adapter;
    private List<Booking> bookings;
    private final String selectedCenter = "Karen"; // Set your admin's service center
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewUpdate);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        bookings = new ArrayList<>();

        adapter = new BookingAdapter(bookings, true, new BookingAdapter.OnBookingActionListener() {
            @Override
            public void onAccept(Booking booking, int position) {
                FirebaseFirestore.getInstance().collection("bookings")
                        .document(booking.getBookingId())
                        .update("status", "APPROVED")
                        .addOnSuccessListener(unused -> {
                            booking.setStatus(BookingStatus.APPROVED);
                            adapter.notifyItemChanged(position);
                            Toast.makeText(requireContext(), "Booking approved", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(requireContext(), "Failed to approve: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onDecline(Booking booking, int position) {
                FirebaseFirestore.getInstance().collection("bookings")
                        .document(booking.getBookingId())
                        .update("status", "DECLINED")
                        .addOnSuccessListener(unused -> {
                            booking.setStatus(BookingStatus.DECLINED);
                            adapter.notifyItemChanged(position);
                            Toast.makeText(requireContext(), "Booking declined", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(requireContext(), "Failed to decline: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadPendingBookingsByBranch();
    }

    private void loadPendingBookingsByBranch() {
        db.collection("bookings")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    bookings.clear();

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String customerId = doc.getString("customerId");
                        String customerName = doc.getString("customerName");
                        String serviceType = doc.getString("serviceType");
                        String date = doc.getString("date");
                        String bookingId = doc.getId(); // Use document ID
                        String serviceCenter = doc.getString("serviceCenter");
                        String statusStr = doc.getString("status");

                        BookingStatus status = BookingStatus.PENDING;
                        if (statusStr != null) {
                            try {
                                status = BookingStatus.valueOf(statusStr.toUpperCase());
                            } catch (IllegalArgumentException ignored) {}
                        }

                        if (serviceCenter != null && serviceCenter.equalsIgnoreCase(selectedCenter) && status == BookingStatus.PENDING) {
                            Booking booking = new Booking(
                                    customerId != null ? customerId : "",
                                    customerName != null ? customerName : "Customer",
                                    serviceType != null ? serviceType : "Service",
                                    date != null ? date : "Unknown Date",
                                    bookingId,
                                    serviceCenter,
                                    status
                            );

                            bookings.add(booking);
                        }
                    }

                    adapter.notifyDataSetChanged();

                    if (bookings.isEmpty() && isAdded()) {
                        Toast.makeText(requireContext(), "No pending bookings for " + selectedCenter, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    if (isAdded()) {
                        Toast.makeText(requireContext(), "Error loading bookings: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}


