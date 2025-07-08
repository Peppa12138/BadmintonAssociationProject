package com.badmintonassociation.dao;

import com.badmintonassociation.model.Match;
import com.badmintonassociation.model.MatchResult;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*比赛数据访问
 * 该类负责比赛信息的数据库访问操作。
 * 提供比赛查询、创建等数据库操作方法，支持比赛管理和统计系统的数据需求。
 * - 查询所有比赛信息
 * - 创建新的比赛记录 (预留接口)
 * - 支持比赛状态查询和管理
 * - 为比赛统计系统提供数据支持
 *  @author guoYiFu
 *  @since 2025-07-04
 */


public class MatchDAO implements IBaseDAO<Match> {
    private Connection connection;

    public MatchDAO(Connection connection) {
        this.connection = connection;
    }

    // 查询所有比赛 - 实现 IBaseDAO 接口
    @Override
    public List<Match> getAll() throws SQLException {
        return getAllMatches();
    }
    
    // 原有方法保持不变，供向后兼容
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
    


    // 创建新比赛
    @Override
    public void create(Match match) throws SQLException {
        createMatch(match);
    }
    public void createMatch(Match match) throws SQLException {
        String query = "INSERT INTO Matches (date, start_time, end_time) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, match.getDate());
        preparedStatement.setTimestamp(2, match.getStartTime()); // Updated to setTimestamp
        preparedStatement.setTimestamp(3, match.getEndTime()); // Updated to setTimestamp
        preparedStatement.executeUpdate();
    }


    

    // 查找空闲场地
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



    // 创建比赛记录
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

    
}
