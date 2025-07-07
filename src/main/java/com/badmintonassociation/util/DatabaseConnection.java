package com.badmintonassociation.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接工具类
 * 提供数据库连接的创建和管理功能
 */
public class DatabaseConnection {
    
    static {
        try {
            String driver = EnvLoader.getEnv("DB_DRIVER", "com.mysql.cj.jdbc.Driver");
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.err.println("数据库驱动加载失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     * @return 数据库连接对象，如果连接失败则返回null
     */
    public static Connection getConnection() {
        try {
            String url = EnvLoader.getEnv("DB_URL");
            String username = EnvLoader.getEnv("DB_USERNAME");
            String password = EnvLoader.getEnv("DB_PASSWORD");
            
            if (url == null || username == null || password == null) {
                throw new SQLException("数据库配置信息不完整，请检查 .env 文件");
            }
            
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("数据库连接失败: " + e.getMessage());
            return null;
        }
    }
}