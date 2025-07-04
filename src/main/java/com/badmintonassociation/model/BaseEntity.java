package com.badmintonassociation.model;

/**
 * 基础实体抽象类
 * 提供所有实体类的通用标识方法
 */
public abstract class BaseEntity {
    
    // 抽象方法 - 子类必须实现
    public abstract int getId();
    public abstract void setId(int id);
    public abstract String getDisplayName();
    
    // 重写 equals 和 hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BaseEntity that = (BaseEntity) obj;
        return getId() == that.getId();
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(getId());
    }
}
