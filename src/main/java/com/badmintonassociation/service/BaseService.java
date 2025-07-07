package com.badmintonassociation.service;

import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Service层基础抽象类
 * 提供统一的异常处理和通用方法
 */
public abstract class BaseService<T, DAO> {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    protected DAO dao;
    
    /**
     * 构造函数 - 注入主要的DAO
     */
    public BaseService(DAO dao) {
        this.dao = dao;
    }
    
    /**
     * 安全执行boolean返回类型的操作
     */
    protected boolean executeBooleanOperation(SQLOperation operation) {
        try {
            operation.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 安全执行有返回值的操作
     */
    protected <R> R executeWithExceptionHandling(SQLSupplier<R> operation, R defaultValue) {
        try {
            return operation.get();
        } catch (SQLException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
    
    /**
     * 函数式接口，用于无返回值的数据库操作
     */
    @FunctionalInterface
    protected interface SQLOperation {
        void execute() throws SQLException;
    }
    
    /**
     * 函数式接口，用于有返回值的数据库操作
     */
    @FunctionalInterface
    protected interface SQLSupplier<R> {
        R get() throws SQLException;
    }
}
