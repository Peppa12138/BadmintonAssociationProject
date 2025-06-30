package com.badmintonassociation.dao;

import com.badmintonassociation.model.Player;
import com.badmintonassociation.model.MatchResult;
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

    public List<Integer> getEligiblePlayers(Time startTime, Time endTime) throws SQLException {
        String query = "SELECT player_id FROM Players WHERE player_id NOT IN "
                + "(SELECT player_id FROM PlayerMatches WHERE match_id IN "
                + "(SELECT match_id FROM Matches WHERE (start_time < ? AND end_time > ?)))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTime(1, endTime);
            preparedStatement.setTime(2, startTime);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Integer> players = new ArrayList<>();
                while (resultSet.next()) {
                    players.add(resultSet.getInt("player_id"));
                }
                return players;
            }
        }
    }
    
    public List<MatchResult> getLatestPlayerResults(int playerId, int limit) throws SQLException {
        String query = "SELECT mr.match_id, mr.rank_id, mr.score, mr.record_broken "
                + "FROM MatchResults mr "
                + "JOIN Matches m ON m.match_id = mr.match_id "
                + "WHERE mr.player_id = ? "
                + "ORDER BY m.date DESC, m.start_time DESC "
                + "LIMIT ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, playerId);
            preparedStatement.setInt(2, limit);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<MatchResult> results = new ArrayList<>();
                while (resultSet.next()) {
                    MatchResult result = new MatchResult();
                    result.setMatchId(resultSet.getInt("match_id"));
                    result.setRankId(resultSet.getInt("rank_id"));
                    result.setScore(resultSet.getInt("score"));
                    result.setRecordBroken(resultSet.getBoolean("record_broken"));
                    results.add(result);
                }
                return results;
            }
        }
    }
    // Additional methods such as create, update, delete...
}
