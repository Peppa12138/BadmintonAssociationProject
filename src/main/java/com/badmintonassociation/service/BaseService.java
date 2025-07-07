
package com.badmintonassociation.service;

import java.sql.SQLException;
import java.util.logging.Logger;


public abstract class BaseService<T, DAO> {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    protected DAO dao;
    
    public BaseService(DAO dao) {
        this.dao = dao;
    }
    
    // 处理无返回值的数据库操作
    protected boolean executeBooleanOperation(SQLOperation operation) {
        try {
            operation.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 处理有返回值的数据库操作
    protected <R> R executeWithExceptionHandling(SQLSupplier<R> operation, R defaultValue) {
        try {
            return operation.get();
        } catch (SQLException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
    
    // 接口
    @FunctionalInterface
    protected interface SQLOperation {
        void execute() throws SQLException;
    }
    
    // 接口
    @FunctionalInterface
    protected interface SQLSupplier<R> {
        R get() throws SQLException;
    }
}
