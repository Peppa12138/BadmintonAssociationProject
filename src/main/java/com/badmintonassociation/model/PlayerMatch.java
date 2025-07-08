package com.badmintonassociation.model;


/**
 * 羽毛球比赛实体类
 * 该类表示羽毛球运动协会的比赛信息，对应数据库中的 Matches 表。
 * 包含比赛的时间安排、场地分配等核心信息，用于比赛组织和管理系统。
 * 继承自 BaseEntity，具备统一的实体对象标识能力。
 * @author guoYiFu
 * @since 2025-07-03
 */



public class PlayerMatch extends BaseEntity {
    private int playerMatchId;
    private int matchId;
    private int playerId;

    // Constructor
    public PlayerMatch() {
        super();
    }

    public PlayerMatch(int playerMatchId, int matchId, int playerId) {
        super();
        this.playerMatchId = playerMatchId;
        this.matchId = matchId;
        this.playerId = playerId;
    }

    // 实现抽象方法
    @Override
    public int getId() {
        return playerMatchId;
    }

    @Override
    public void setId(int id) {
        this.playerMatchId = id;
    }

    @Override
    public String getDisplayName() {
        return "参赛记录-" + playerMatchId + " (选手:" + playerId + "-比赛:" + matchId + ")";
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
