package com.badmintonassociation.dao;

import com.badmintonassociation.model.Match;
import com.badmintonassociation.model.MatchResult;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchDAO {
    private Connection connection;

    public MatchDAO(Connection connection) {
        this.connection = connection;
    }

    // Get all matches
    public List<Match> getAllMatches() throws SQLException {
        String query = "SELECT * FROM Matches";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        List<Match> matches = new ArrayList<>();
        while (resultSet.next()) {
            Match match = new Match(
                    resultSet.getInt("match_id"),
                    resultSet.getDate("date"),
                    resultSet.getTimestamp("start_time"),
                    resultSet.getTimestamp("end_time"));
            matches.add(match);
        }
        return matches;
    }

    // Add this method to MatchResultDAO
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
    
    // Create a new match record
    // Create a new match record
    public void createMatch(Match match) throws SQLException {
        String query = "INSERT INTO Matches (date, start_time, end_time) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, match.getDate());
        preparedStatement.setTimestamp(2, match.getStartTime()); // Updated to setTimestamp
        preparedStatement.setTimestamp(3, match.getEndTime()); // Updated to setTimestamp
        preparedStatement.executeUpdate();
    }

    // Additional methods such as create, update, delete...
}
