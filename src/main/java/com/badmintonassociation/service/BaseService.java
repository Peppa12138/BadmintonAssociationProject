
package com.badmintonassociation.service;

import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * 业务服务层基础抽象类
 * 该抽象类为所有业务服务类提供通用的基础设施和工具方法。
 * 封装了数据库操作的异常处理模式，提供统一的日志管理和错误处理机制。
 * 采用泛型设计和模板方法模式，为子类提供可复用的代码框架。
 * @author guoYiFu
 * @since 2025-07-03
 */
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
