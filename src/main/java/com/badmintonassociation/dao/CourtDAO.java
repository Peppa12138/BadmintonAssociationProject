package com.badmintonassociation.dao;

import com.badmintonassociation.model.Court;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 场地数据访问
 * 该类负责场地信息的数据库访问操作。
 * 提供场地查询、创建等数据库操作方法，支持场地管理和预订系统的数据需求。
 * - 查询所有可用场地信息
 * - 创建新的场地记录 (预留接口)
 * - 支持场地状态查询和管理
 * - 为场地预订系统提供数据支持
 * @author huJunYang，huKeHan
 * @since 2025-07-03
 */


public class CourtDAO implements IBaseDAO<Court> {
    private Connection connection;

    public CourtDAO(Connection connection) {
        this.connection = connection;
    }

    // Get all courts - 实现 IBaseDAO 接口
    @Override
    public List<Court> getAll() throws SQLException {
        return getAllCourts();
    }
    
    // 原有方法保持不变，供向后兼容
    public List<Court> getAllCourts() throws SQLException {
        String query = "SELECT * FROM Courts";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        List<Court> courts = new ArrayList<>();
        while (resultSet.next()) {
            Court court = new Court(
                    resultSet.getInt("court_id"),
                    resultSet.getString("location"),
                    resultSet.getString("description"));
            courts.add(court);
        }
        return courts;
    }

    @Override
    public void create(Court court) throws SQLException {
        throw new UnsupportedOperationException("Create court not implemented yet");
    }
}
