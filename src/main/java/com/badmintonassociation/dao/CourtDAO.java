package com.badmintonassociation.dao;

import com.badmintonassociation.model.Court;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    // Create court - 实现 IBaseDAO 接口
    @Override
    public void create(Court court) throws SQLException {
        // 可以在这里实现创建场地的逻辑
        // 目前为空实现，等待具体的业务需求
        throw new UnsupportedOperationException("Create court not implemented yet");
    }

    // Additional methods such as create, update, delete...
}
