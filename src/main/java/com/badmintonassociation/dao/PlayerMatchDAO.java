
package com.badmintonassociation.dao;

import com.badmintonassociation.model.PlayerMatch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;





/* 选手比赛关联数据访问
 * - 查询所有选手参赛记录
 * - 创建新的选手参赛关联
 * - 管理选手与比赛的多对多关系
 * - 支持比赛参与者的动态管理
 *  @author yuZhongShui
 *  @since 2025-07-03
 */
public class PlayerMatchDAO implements IBaseDAO<PlayerMatch> {
    private Connection connection;

    public PlayerMatchDAO(Connection connection) {
        this.connection = connection;
    }

    
    @Override
    public List<PlayerMatch> getAll() throws SQLException {
        return getAllPlayerMatches();
    }
    
    
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

    
    @Override
    public void create(PlayerMatch playerMatch) throws SQLException {
        createPlayerMatch(playerMatch);
    }
    
    
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

    
}
