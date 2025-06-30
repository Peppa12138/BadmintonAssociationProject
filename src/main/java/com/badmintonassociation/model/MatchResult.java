package com.badmintonassociation.model;

public class MatchResult {
    private int resultId;
    private int matchId;
    private int playerId;
    private int rankId; // Modified from rank
    private int score;
    private boolean recordBroken;

    // Constructor
    public MatchResult() {
    }

    public MatchResult(int resultId, int matchId, int playerId, int rankId, int score, boolean recordBroken) {
        this.resultId = resultId;
        this.matchId = matchId;
        this.playerId = playerId;
        this.rankId = rankId;
        this.score = score;
        this.recordBroken = recordBroken;
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
