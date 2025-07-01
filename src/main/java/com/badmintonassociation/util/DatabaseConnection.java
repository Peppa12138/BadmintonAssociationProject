package com.badmintonassociation.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    // ANSI 颜色代码
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";
    
    static {
        try {
            System.out.print(CYAN + "正在加载数据库驱动");
            showLoadingDots(3);
            
            String driver = EnvLoader.getEnv("DB_DRIVER", "com.mysql.cj.jdbc.Driver");
            Class.forName(driver);
            
            System.out.println(GREEN + " ✅ 数据库驱动加载成功" + RESET);
        } catch (ClassNotFoundException e) {
            System.out.println(RED + " ❌ 数据库驱动加载失败: " + e.getMessage() + RESET);
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            String url = EnvLoader.getEnv("DB_URL");
            String username = EnvLoader.getEnv("DB_USERNAME");
            String password = EnvLoader.getEnv("DB_PASSWORD");
            
            if (url == null || username == null || password == null) {
                throw new SQLException("数据库配置信息不完整，请检查 .env 文件");
            }
            
            System.out.println(BLUE + "📍 数据库地址: " + url + RESET);
            System.out.print(CYAN + "正在建立数据库连接");
            
            // 显示进度条动画
            showProgressBar(15);
            
            Connection connection = DriverManager.getConnection(url, username, password);
            
            if (connection != null) {
                System.out.println();
                showSuccessAnimation();
                printConnectionInfo(connection);
            }
            
            return connection;
        } catch (SQLException e) {
            System.out.println();
            showErrorAnimation();
            System.out.println(RED + "❌ 数据库连接失败！" + RESET);
            System.out.println(RED + "错误信息: " + e.getMessage() + RESET);
            return null;
        }
    }
    
    /**
     * 显示加载点点点动画
     */
    private static void showLoadingDots(int seconds) {
        try {
            for (int i = 0; i < seconds; i++) {
                Thread.sleep(500);
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 显示进度条动画
     */
    private static void showProgressBar(int steps) {
        try {
            for (int i = 0; i <= steps; i++) {
                StringBuilder bar = new StringBuilder();
                bar.append("\r").append(CYAN).append("正在建立数据库连接 [");
                
                // 创建进度条
                int progress = (i * 20) / steps;
                for (int j = 0; j < 20; j++) {
                    if (j < progress) {
                        bar.append(GREEN).append("█").append(CYAN);
                    } else {
                        bar.append("░");
                    }
                }
                
                int percentage = (i * 100) / steps;
                bar.append("] ").append(percentage).append("%").append(RESET);
                
                System.out.print(bar.toString());
                Thread.sleep(80);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 显示成功动画
     */
    private static void showSuccessAnimation() {
        try {
            String[] successFrames = {
                YELLOW + "🔗 连接中...",
                YELLOW + "🔗✨ 验证中...",
                YELLOW + "🔗✨🎯 建立中...",
                GREEN + "🔗✨🎯✅ 连接成功！"
            };
            
            for (String frame : successFrames) {
                System.out.print("\r" + frame + RESET);
                Thread.sleep(400);
            }
            
            System.out.println();
            System.out.println(GREEN + BOLD + "🎉 数据库连接成功！" + RESET);
            
            // 成功横幅
            printSuccessBanner();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 显示错误动画
     */
    private static void showErrorAnimation() {
        try {
            String[] errorFrames = {
                RED + "🔗 连接中...",
                RED + "⚠️ 出现问题...",
                RED + "💥 连接失败！"
            };
            
            for (String frame : errorFrames) {
                System.out.print("\r" + frame + RESET);
                Thread.sleep(500);
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 显示成功横幅
     */
    private static void printSuccessBanner() {
        System.out.println(GREEN + "╔══════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(GREEN + "║" + RESET + CYAN + "                                                          " + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + CYAN + "           🏸 羽毛球协会管理系统 🏸                        " + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + CYAN + "                                                          " + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + YELLOW + "              🎉 数据库连接成功！🎉                        " + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + CYAN + "                                                          " + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + BLUE + "              🚀 系统正在启动中... 🚀                      " + GREEN + "║" + RESET);
        System.out.println(GREEN + "║" + RESET + CYAN + "                                                          " + GREEN + "║" + RESET);
        System.out.println(GREEN + "╚══════════════════════════════════════════════════════════╝" + RESET);
        
        try {
            Thread.sleep(1500); // 暂停1.5秒显示效果
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 显示连接信息
     */
    private static void printConnectionInfo(Connection connection) {
        try {
            System.out.println(BLUE + "📊 连接信息:" + RESET);
            System.out.println(CYAN + "   • 数据库产品: " + connection.getMetaData().getDatabaseProductName() + RESET);
            System.out.println(CYAN + "   • 数据库版本: " + connection.getMetaData().getDatabaseProductVersion() + RESET);
            System.out.println(CYAN + "   • 驱动版本: " + connection.getMetaData().getDriverVersion() + RESET);
            System.out.println(GREEN + "🔥 系统就绪，准备为您服务！" + RESET);
            System.out.println("═".repeat(60));
        } catch (SQLException e) {
            System.out.println(YELLOW + "⚠️ 无法获取数据库详细信息" + RESET);
        }
    }
}