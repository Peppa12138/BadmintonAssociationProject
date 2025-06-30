package com.badmintonassociation.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Match {
    private int matchId;
    private Date date;
    private Timestamp startTime;
    private Timestamp endTime;

    // Constructor
    public Match() {
    }

    public Match(int matchId, Date date, Timestamp startTime, Timestamp endTime) {
        this.matchId = matchId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        return "Match{" +
                "matchId=" + matchId +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
