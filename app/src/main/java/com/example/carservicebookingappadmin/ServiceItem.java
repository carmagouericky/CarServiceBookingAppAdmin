package com.example.carservicebookingappadmin;


public class ServiceItem {
    private String plate;
    private String mechanic;

    public ServiceItem(String plate, String mechanic) {
        this.plate = plate;
        this.mechanic = mechanic;
    }

    public String getPlate() {
        return plate;
    }

    public String getMechanic() {
        return mechanic;
    }
}
