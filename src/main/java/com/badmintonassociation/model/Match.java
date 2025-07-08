package com.badmintonassociation.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 羽毛球比赛实体类
 * 该类表示羽毛球运动协会的比赛信息，对应数据库中的 Matches 表。
 * 包含比赛的时间安排、场地分配等核心信息，用于比赛组织和管理系统。
 * 继承自 BaseEntity，具备统一的实体对象标识能力。
 * @author huJunYang
 * @since 2025-07-03
 */
public class Match extends BaseEntity {
    private int matchId;
    private Date date;
    private Timestamp startTime;
    private Timestamp endTime;

    // Constructor
    public Match() {
        super();
    }

    public Match(int matchId, Date date, Timestamp startTime, Timestamp endTime) {
        super();
        this.matchId = matchId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // 实现抽象方法
    @Override
    public int getId() {
        return matchId;
    }

    @Override
    public void setId(int id) {
        this.matchId = id;
    }

    @Override
    public String getDisplayName() {
        return "比赛-" + matchId + (date != null ? " (" + date + ")" : "");
    }

    // Getters and Setters
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
        setId(matchId); // 同步更新基类的 id
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

    // 保留原有的 toString 方法，确保兼容性
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
