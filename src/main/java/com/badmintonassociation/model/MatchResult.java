package com.badmintonassociation.model;

/**
 * 羽毛球比赛实体类
 * 该类表示羽毛球运动协会的比赛信息，对应数据库中的 Matches 表。
 * 包含比赛的时间安排、场地分配等核心信息，用于比赛组织和管理系统。
 * 继承自 BaseEntity，具备统一的实体对象标识能力。
 * 
 * @author guoYiFu，guoZiMeng
 * @since 2025-07-03
 */



public class MatchResult extends BaseEntity {
    private int resultId;
    private int matchId;
    private int playerId;
    private int rankId; // Modified from rank
    private int score;
    private boolean recordBroken;

    // Constructor
    public MatchResult() {
        super();
    }

    public MatchResult(int resultId, int matchId, int playerId, int rankId, int score, boolean recordBroken) {
        super();
        this.resultId = resultId;
        this.matchId = matchId;
        this.playerId = playerId;
        this.rankId = rankId;
        this.score = score;
        this.recordBroken = recordBroken;
    }

    // 实现抽象方法
    @Override
    public int getId() {
        return resultId;
    }

    @Override
    public void setId(int id) {
        this.resultId = id;
    }

    @Override
    public String getDisplayName() {
        return "成绩-" + resultId + " (排名:" + rankId + ")";
    }

    // Getters and Setters
    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isRecordBroken() {
        return recordBroken;
    }

    public void setRecordBroken(boolean recordBroken) {
        this.recordBroken = recordBroken;
    }

    @Override
    public String toString() {
        return "MatchResult{" +
                "resultId=" + resultId +
                ", matchId=" + matchId +
                ", playerId=" + playerId +
                ", rankId=" + rankId +
                ", score=" + score +
                ", recordBroken=" + recordBroken +
                '}';
    }
}
