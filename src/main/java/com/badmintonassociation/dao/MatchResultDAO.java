package com.badmintonassociation.dao;

import com.badmintonassociation.model.MatchResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



/* 比赛结果数据访问
 * - 查询所有比赛结果
 * 提供比赛结果查询、创建等数据库操作方法，
 * 支持比赛统计和分析系统的数据需求。
 * 
 *  @author huJunYang，huKeHan
 *  @since 2025-07-04
 */
public class MatchResultDAO implements IBaseDAO<MatchResult> {
    private Connection connection;

    public MatchResultDAO(Connection connection) {
        this.connection = connection;
    }

    // 查询所有比赛结果
    @Override
    public List<MatchResult> getAll() throws SQLException {
        return getAllMatchResults();
    }
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

    

    // 创建新比赛结果
    @Override
    public void create(MatchResult matchResult) throws SQLException {
        createMatchResult(matchResult);
    }
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



    // 按比赛ID查询结果
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



    // 批量插入比赛结果
    public void insertMatchResult(int matchId, int playerId, int rankId, int score, boolean recordBroken)
            throws SQLException {
        String query = "INSERT INTO MatchResults (match_id, player_id, rank_id, score, record_broken) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, matchId);
            preparedStatement.setInt(2, playerId);
            preparedStatement.setInt(3, rankId);
            preparedStatement.setInt(4, score);
            preparedStatement.setBoolean(5, recordBroken);
            preparedStatement.executeUpdate();
        }
    }

    
}
