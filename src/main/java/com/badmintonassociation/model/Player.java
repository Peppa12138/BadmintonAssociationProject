package com.badmintonassociation.model;

/**
 * 羽毛球选手实体类
 * 该类表示羽毛球运动协会的注册选手信息，对应数据库中的 Players 表。
 * 包含选手的个人基本信息和技术水平等级，用于选手管理、比赛组织和成绩记录。
 * 继承自 BaseEntity，具备统一的实体对象标识能力。
 * 
 * @author guoYiFu
 * @since 2025-07-03

 */
public class Player extends BaseEntity {
    private int playerId;
    private String name;
    private String gender;
    private String level;

    // Constructor
    public Player() {
        super();
    }

    public Player(int playerId, String name, String gender, String level) {
        super();
        this.playerId = playerId;
        this.name = name;
        this.gender = gender;
        this.level = level;
    }

    // 实现抽象方法
    @Override
    public int getId() {
        return playerId;
    }

    @Override
    public void setId(int id) {
        this.playerId = id;
    }

    @Override
    public String getDisplayName() {
        return name != null ? name + " (" + level + ")" : "选手-" + playerId;
    }

    // Getters and Setters
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
        setId(playerId); // 同步更新基类的 id
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
