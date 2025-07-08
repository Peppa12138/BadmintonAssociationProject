package com.badmintonassociation.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接管理器
 * 该类负责数据库连接的创建和管理，提供统一的数据库访问入口。
 * 使用 JDBC 技术连接 MySQL 数据库，支持通过环境变量配置数据库连接参数。
 * @author yuZhongShui
 * @since 2025-07-03
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