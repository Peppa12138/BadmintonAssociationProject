package com.badmintonassociation.dao;

import com.badmintonassociation.model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {
    private Connection connection;

    public PlayerDAO(Connection connection) {
        this.connection = connection;
    }

    // Get all players
    public List<Player> getAllPlayers() throws SQLException {
        String query = "SELECT * FROM Players";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        List<Player> players = new ArrayList<>();
        while (resultSet.next()) {
            Player player = new Player(
                    resultSet.getInt("player_id"),
                    resultSet.getString("name"),
                    resultSet.getString("gender"),
                    resultSet.getString("level"));
            players.add(player);
        }
        return players;
    }

    // Create a new player record
    public void createPlayer(Player player) throws SQLException {
        String query = "INSERT INTO Players (name, gender, level) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, player.getName());
        preparedStatement.setString(2, player.getGender());
        preparedStatement.setString(3, player.getLevel());
        preparedStatement.executeUpdate();
    }
    // Additional methods such as create, update, delete...
}
