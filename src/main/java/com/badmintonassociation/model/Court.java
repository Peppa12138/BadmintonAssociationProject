
package com.badmintonassociation.model;

/**
 * 羽毛球场地实体类
 * 该类表示羽毛球运动协会的场地信息，对应数据库中的 Courts 表。
 * 包含场地的基本信息如位置、描述等属性，用于场地管理和预订系统。
 * 继承自 BaseEntity，具备统一的实体对象标识能力。

 * 
 * @author huJunYang
 * @since 2025-07-03
 */



public class Court extends BaseEntity {
    private int courtId;
    private String location;
    private String description;


    public Court() {
        super();
    }

    public Court(int courtId, String location, String description) {
        super();
        this.courtId = courtId;
        this.location = location;
        this.description = description;
    }


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
