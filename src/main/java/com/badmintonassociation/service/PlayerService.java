package com.badmintonassociation.service;

import com.badmintonassociation.dao.PlayerDAO;
import com.badmintonassociation.model.Player;
import com.badmintonassociation.model.MatchResult;
import java.util.List;

public class PlayerService extends BaseService<Player, PlayerDAO> {

    public PlayerService(PlayerDAO playerDAO) {
        super(playerDAO);
    }

    // 获取所有运动员
    public List<Player> getAllPlayers() {
        return executeWithExceptionHandling(
            () -> dao.getAllPlayers(),
            null
        );
    }


    // 创建新运动员
    public boolean createPlayer(Player player) {
        return executeBooleanOperation(
            () -> dao.createPlayer(player)
        );
    }
    

    // 获取运动员最近成绩
    public List<MatchResult> getLatestPlayerResults(int playerId, int limit) {
        return executeWithExceptionHandling(
            () -> dao.getLatestPlayerResults(playerId, limit),
            null
        );
    }
    // Additional methods...
}
