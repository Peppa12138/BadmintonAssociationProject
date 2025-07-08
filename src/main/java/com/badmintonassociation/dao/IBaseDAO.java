
package com.badmintonassociation.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * 基础数据访问对象接口
 * 采用泛型设计，支持不同实体类型的数据访问操作，确保类型安全性。
    getAll() - 查询所有记录（Read）
    create() - 创建新记录（Create）
 * @author huJunYang
 * @since 2025-07-04
 */
public interface IBaseDAO<T> {
    List<T> getAll() throws SQLException;
    void create(T entity) throws SQLException;
}
