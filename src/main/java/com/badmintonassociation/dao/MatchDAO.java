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

    // 查询所有比赛
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

    // 查询比赛结果 
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
    
    // 创建比赛记录1
    public void createMatch(Match match) throws SQLException {
        String query = "INSERT INTO Matches (date, start_time, end_time) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, match.getDate());
        preparedStatement.setTimestamp(2, match.getStartTime()); // Updated to setTimestamp
        preparedStatement.setTimestamp(3, match.getEndTime()); // Updated to setTimestamp
        preparedStatement.executeUpdate();
    }


    
    // 查询指定时间段内的比赛
    public int selectFreeCourt(Time startTime, Time endTime) throws SQLException {
        String query = "SELECT court_id FROM Courts WHERE court_id NOT IN "
                + "(SELECT court_id FROM Matches WHERE (start_time < ? AND end_time > ?)) LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTime(1, endTime);
            preparedStatement.setTime(2, startTime);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("court_id");
                }
            }
        }
        throw new SQLException("No court available.");
    }



    // 创建比赛记录2(重载)
    public int createMatch(Date date, Time startTime, Time endTime, int courtId) throws SQLException {
        String query = "INSERT INTO Matches (date, start_time, end_time, court_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, date);
            preparedStatement.setTime(2, startTime);
            preparedStatement.setTime(3, endTime);
            preparedStatement.setInt(4, courtId);
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        throw new SQLException("Creating match failed, no ID obtained.");
    }

    // Additional methods such as create, update, delete...
}
