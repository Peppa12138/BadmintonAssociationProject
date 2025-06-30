package com.badmintonassociation.model;

public class Player {
    private int playerId;
    private String name;
    private String gender;
    private String level;

    // Constructor
    public Player() {
    }

    public Player(int playerId, String name, String gender, String level) {
        this.playerId = playerId;
        this.name = name;
        this.gender = gender;
        this.level = level;
    }

    // Getters and Setters
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
