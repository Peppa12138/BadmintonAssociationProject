package com.badmintonassociation.model;

/**
 * 基础实体抽象类
 * 该抽象类为所有数据库实体类提供通用的基础功能和标准规范。
 * 定义了实体对象的核心标识方法，确保所有子实体类都具备统一的标识能力。
 * 作为实体继承体系的根类，为 ORM 映射和对象管理提供基础支持。
 * @author guoYiFu，huJunYang
 * @since 2025-07-03
 */



public abstract class BaseEntity {
    
    
    public abstract int getId();
    public abstract void setId(int id);
    public abstract String getDisplayName();
    
    

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
