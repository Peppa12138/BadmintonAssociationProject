package com.badmintonassociation.model;

public class Court {
    private int courtId;
    private String location;
    private String description;

    // Constructor
    public Court() {
    }

    public Court(int courtId, String location, String description) {
        this.courtId = courtId;
        this.location = location;
        this.description = description;
    }

    // Getters and Setters
    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Court{" +
                "courtId=" + courtId +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
