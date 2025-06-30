package com.badmintonassociation.dao;

import com.badmintonassociation.model.Court;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourtDAO {
    private Connection connection;

    public CourtDAO(Connection connection) {
        this.connection = connection;
    }

    // Get all courts
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

    // Additional methods such as create, update, delete...
}
