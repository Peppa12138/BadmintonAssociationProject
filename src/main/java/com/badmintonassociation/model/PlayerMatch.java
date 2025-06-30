package com.badmintonassociation.model;

public class PlayerMatch {
    private int playerMatchId;
    private int matchId;
    private int playerId;

    // Constructor
    public PlayerMatch() {
    }

    public PlayerMatch(int playerMatchId, int matchId, int playerId) {
        this.playerMatchId = playerMatchId;
        this.matchId = matchId;
        this.playerId = playerId;
    }

    // Getters and Setters
    public int getPlayerMatchId() {
        return playerMatchId;
    }

    public void setPlayerMatchId(int playerMatchId) {
        this.playerMatchId = playerMatchId;
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

    @Override
    public String toString() {
        return "PlayerMatch{" +
                "playerMatchId=" + playerMatchId +
                ", matchId=" + matchId +
                ", playerId=" + playerId +
                '}';
    }
}
