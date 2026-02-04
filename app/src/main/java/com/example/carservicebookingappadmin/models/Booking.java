package com.example.carservicebookingappadmin.models;

public class Booking {
    private String customerId;
    private String customerName;
    private String serviceType;
    private String date;
    private BookingStatus status;
    private String bookingId;
    private String serviceCenter;

    //  TRUE no-argument constructor for Firestore
    public Booking() {}

    // Optional constructor for manual use
    public Booking(String customerId, String customerName, String serviceType, String date,
                   String bookingId, String serviceCenter, BookingStatus status) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.serviceType = serviceType;
        this.date = date;
        this.bookingId = bookingId;
        this.serviceCenter = serviceCenter;
        this.status = status;
    }

    //  Getters
    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getDate() {
        return date;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getServiceCenter() {
        return serviceCenter;
    }

    //  for compatibility with some fragments
    public String getGarageBranch() {
        return serviceCenter;
    }

    // Setters
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setServiceCenter(String serviceCenter) {
        this.serviceCenter = serviceCenter;
    }
}


