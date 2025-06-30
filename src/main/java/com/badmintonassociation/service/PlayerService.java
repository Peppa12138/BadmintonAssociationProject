package com.badmintonassociation.service;

import com.badmintonassociation.dao.PlayerDAO;
import com.badmintonassociation.model.Player;
import com.badmintonassociation.model.MatchResult;
import java.sql.SQLException;
import java.util.List;

public class PlayerService {
    private PlayerDAO playerDAO;

    public PlayerService(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public List<Player> getAllPlayers() {
        try {
            return playerDAO.getAllPlayers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createPlayer(Player player) {
        try {
            playerDAO.createPlayer(player);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<MatchResult> getLatestPlayerResults(int playerId, int limit) {
        try {
            return playerDAO.getLatestPlayerResults(playerId, limit);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Additional methods...
}
