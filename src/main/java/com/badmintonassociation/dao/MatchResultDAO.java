package com.badmintonassociation.dao;

import com.badmintonassociation.model.MatchResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchResultDAO {
    private Connection connection;

    public MatchResultDAO(Connection connection) {
        this.connection = connection;
    }

    // Get all match results
    public List<MatchResult> getAllMatchResults() throws SQLException {
        String query = "SELECT * FROM MatchResults";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        List<MatchResult> matchResults = new ArrayList<>();
        while (resultSet.next()) {
            MatchResult matchResult = new MatchResult(
                    resultSet.getInt("result_id"),
                    resultSet.getInt("match_id"),
                    resultSet.getInt("player_id"),
                    resultSet.getInt("rank_id"), // Use rank_id
                    resultSet.getInt("score"),
                    resultSet.getBoolean("record_broken"));
            matchResults.add(matchResult);
        }
        return matchResults;
    }

    // Create a new match result record
    public void createMatchResult(MatchResult matchResult) throws SQLException {
        String query = "INSERT INTO MatchResults (match_id, player_id, rank_id, score, record_broken) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, matchResult.getMatchId());
        preparedStatement.setInt(2, matchResult.getPlayerId());
        preparedStatement.setInt(3, matchResult.getRankId());
        preparedStatement.setInt(4, matchResult.getScore());
        preparedStatement.setBoolean(5, matchResult.isRecordBroken());
        preparedStatement.executeUpdate();
    }

    // Get match results by match ID
    public List<MatchResult> getResultsByMatchId(int matchId) throws SQLException {
        String query = "SELECT * FROM MatchResults WHERE match_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, matchId);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<MatchResult> matchResults = new ArrayList<>();
        while (resultSet.next()) {
            MatchResult matchResult = new MatchResult(
                    resultSet.getInt("result_id"),
                    resultSet.getInt("match_id"),
                    resultSet.getInt("player_id"),
                    resultSet.getInt("rank_id"),
                    resultSet.getInt("score"),
                    resultSet.getBoolean("record_broken"));
            matchResults.add(matchResult);
        }
        return matchResults;
    }
    // Additional methods such as update, delete...
}
