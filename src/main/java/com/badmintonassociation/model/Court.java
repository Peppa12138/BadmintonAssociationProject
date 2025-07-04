package com.badmintonassociation.model;

public class Court extends BaseEntity {
    private int courtId;
    private String location;
    private String description;

    // Constructor
    public Court() {
        super();
    }

    public Court(int courtId, String location, String description) {
        super();
        this.courtId = courtId;
        this.location = location;
        this.description = description;
    }

    // 实现抽象方法
    @Override
    public int getId() {
        return courtId;
    }

    @Override
    public void setId(int id) {
        this.courtId = id;
    }

    @Override
    public String getDisplayName() {
        return location != null ? location + " - " + description : "场地-" + courtId;
    }

    // Getters and Setters
    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
        setId(courtId); // 同步更新基类的 id
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

    // 保留原有的 toString 方法，确保兼容性
    @Override
    public String toString() {
        return "Court{" +
                "courtId=" + courtId +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
