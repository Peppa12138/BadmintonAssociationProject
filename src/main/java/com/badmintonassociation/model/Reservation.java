package com.badmintonassociation.model;

import java.sql.Timestamp;

public class Reservation {
    private int reservationId;
    private int courtId;
    private Integer playerId; // Can be null
    private Integer matchId; // Can be null
    private Timestamp startTime;
    private Timestamp endTime;

    // Constructor
    public Reservation() {
    }

    public Reservation(int reservationId, int courtId, Integer playerId, Integer matchId, Timestamp startTime,
            Timestamp endTime) {
        this.reservationId = reservationId;
        this.courtId = courtId;
        this.playerId = playerId;
        this.matchId = matchId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) { // 确保此方法存在
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) { // 确保此方法存在
        this.endTime = endTime;
    }
    
    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", courtId=" + courtId +
                ", playerId=" + playerId +
                ", matchId=" + matchId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
