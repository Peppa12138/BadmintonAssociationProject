package com.badmintonassociation.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnvLoader {
    private static final Map<String, String> envVars = new HashMap<>();
    
    static {
        loadEnvFile();
    }
    
    private static void loadEnvFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(".env"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // 跳过空行和注释
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                
                // 解析 KEY=VALUE 格式
                int equalIndex = line.indexOf('=');
                if (equalIndex > 0) {
                    String key = line.substring(0, equalIndex).trim();
                    String value = line.substring(equalIndex + 1).trim();
                    envVars.put(key, value);
                }
            }
            System.out.println("✓ .env 文件加载成功");
        } catch (IOException e) {
            System.err.println("✗ .env 文件读取失败: " + e.getMessage());
        }
    }
    
    public static String getEnv(String key) {
        return envVars.get(key);
    }
    
    public static String getEnv(String key, String defaultValue) {
        return envVars.getOrDefault(key, defaultValue);
    }
}