package com.badmintonassociation.service;

import com.badmintonassociation.dao.PlayerDAO;
import com.badmintonassociation.model.Player;
import com.badmintonassociation.model.MatchResult;
import java.util.List;


/**
 * 选手业务服务类
 * 该类负责选手相关的业务逻辑处理，作为控制器层和数据访问层之间的桥梁。
 * 继承自 BaseService，具备统一的异常处理和日志记录能力。
 * 提供选手查询、创建、成绩统计等业务功能，支持选手管理和比赛参与系统。
 * @author guoYiFu
 * @since 2025-07-03
 */


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
