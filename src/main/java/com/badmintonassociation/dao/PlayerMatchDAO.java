package com.badmintonassociation.dao;

import com.badmintonassociation.model.PlayerMatch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerMatchDAO {
    private Connection connection;

    public PlayerMatchDAO(Connection connection) {
        this.connection = connection;
    }

    // Get all player matches
    public List<PlayerMatch> getAllPlayerMatches() throws SQLException {
        String query = "SELECT * FROM PlayerMatches";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        List<PlayerMatch> playerMatches = new ArrayList<>();
        while (resultSet.next()) {
            PlayerMatch playerMatch = new PlayerMatch(
                    resultSet.getInt("player_match_id"),
                    resultSet.getInt("match_id"),
                    resultSet.getInt("player_id"));
            playerMatches.add(playerMatch);
        }
        return playerMatches;
    }

    // Create a new player match record
    public void createPlayerMatch(PlayerMatch playerMatch) throws SQLException {
        String query = "INSERT INTO PlayerMatches (match_id, player_id) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, playerMatch.getMatchId());
        preparedStatement.setInt(2, playerMatch.getPlayerId());
        preparedStatement.executeUpdate();
    }

    public void insertPlayerMatch(int matchId, int playerId) throws SQLException {
        String query = "INSERT INTO PlayerMatches (match_id, player_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, matchId);
            preparedStatement.setInt(2, playerId);
            preparedStatement.executeUpdate();
        }
    }

    // Additional methods such as update, delete...
}
