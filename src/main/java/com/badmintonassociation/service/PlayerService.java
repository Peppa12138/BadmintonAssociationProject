package com.badmintonassociation.service;

import com.badmintonassociation.dao.PlayerDAO;
import com.badmintonassociation.model.Player;
import com.badmintonassociation.model.MatchResult;
import java.util.List;

public class PlayerService extends BaseService<Player, PlayerDAO> {

    public PlayerService(PlayerDAO playerDAO) {
        super(playerDAO);
    }

    public List<Player> getAllPlayers() {
        return executeWithExceptionHandling(
            () -> dao.getAllPlayers(),
            null
        );
    }

    public boolean createPlayer(Player player) {
        return executeBooleanOperation(
            () -> dao.createPlayer(player)
        );
    }
    
    public List<MatchResult> getLatestPlayerResults(int playerId, int limit) {
        return executeWithExceptionHandling(
            () -> dao.getLatestPlayerResults(playerId, limit),
            null
        );
    }
    // Additional methods...
}
