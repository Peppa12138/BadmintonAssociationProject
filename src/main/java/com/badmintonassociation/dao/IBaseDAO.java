
package com.badmintonassociation.dao;

import java.sql.SQLException;
import java.util.List;


public interface IBaseDAO<T> {
    List<T> getAll() throws SQLException;
    void create(T entity) throws SQLException;
}
