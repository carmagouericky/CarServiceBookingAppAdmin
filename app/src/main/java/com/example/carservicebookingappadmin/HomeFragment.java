package com.example.carservicebookingappadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carservicebookingappadmin.models.Booking;
import com.example.carservicebookingappadmin.models.BookingStatus;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookingAdapter adapter;
    private List<Booking> bookings;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_today_services);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        bookings = new ArrayList<>();
        adapter = new BookingAdapter(bookings);  // No buttons
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadBookings();

        return view;
    }

    private void loadBookings() {
        db.collection("bookings")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    bookings.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String customerId = doc.getString("customerId");
                        String customerName = doc.getString("customerName");
                        String serviceType = doc.getString("serviceType");
                        String date = doc.getString("date");
                        String bookingId = doc.getId();
                        String serviceCenter = doc.getString("serviceCenter");
                        String statusStr = doc.getString("status");

                        BookingStatus status = BookingStatus.PENDING;
                        if (statusStr != null) {
                            try {
                                status = BookingStatus.valueOf(statusStr.toUpperCase());
                            } catch (IllegalArgumentException ignored) {}
                        }

                        Booking booking = new Booking(
                                customerId != null ? customerId : "",
                                customerName != null ? customerName : "Customer",
                                serviceType != null ? serviceType : "Service",
                                date != null ? date : "Unknown Date",
                                bookingId,
                                serviceCenter != null ? serviceCenter : "",
                                status
                        );

                        bookings.add(booking);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Failed to load bookings: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}


