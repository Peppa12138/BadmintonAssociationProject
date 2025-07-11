package com.badmintonassociation.service;

import com.badmintonassociation.dao.MatchDAO;
import com.badmintonassociation.dao.MatchResultDAO;
import com.badmintonassociation.dao.PlayerDAO;
import com.badmintonassociation.dao.PlayerMatchDAO;
import com.badmintonassociation.model.Match;
import com.badmintonassociation.model.MatchResult;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.sql.Date;
import java.util.Random;


/**
 * 比赛业务服务类
 * 该类负责比赛相关的业务逻辑处理，作为控制器层和数据访问层之间的桥梁。
 * 继承自 BaseService，具备统一的异常处理和日志记录能力。
 * 提供比赛创建、选手分配、成绩记录等业务功能，支持自动化比赛调度和管理系统。
 * 
 * @author huJunYang，huKeHan，yuZhongShui，liSiHan
 * @since 2025-07-03
 */


public class MatchService extends BaseService<Match, MatchDAO> {
    private PlayerDAO playerDAO;
    private PlayerMatchDAO playerMatchDAO;
    private MatchResultDAO matchResultDAO;

    // 构造函数
    public MatchService(MatchDAO matchDAO, PlayerDAO playerDAO, PlayerMatchDAO playerMatchDAO,MatchResultDAO matchResultDAO) {
        super(matchDAO);
        this.playerDAO = playerDAO;
        this.playerMatchDAO = playerMatchDAO;
        this.matchResultDAO = matchResultDAO;
    }

    // 比赛结果记录
    public boolean recordMatchResult(MatchResult matchResult) {
        return executeBooleanOperation(
            () -> matchResultDAO.createMatchResult(matchResult)
        );
    }

    // 获取比赛结果
    public List<MatchResult> getMatchResults(int matchId) {
        return executeWithExceptionHandling(
            () -> matchResultDAO.getResultsByMatchId(matchId),
            null
        );
    }



    // 比赛创建流程
    public boolean createMatchWithPlayers(LocalDate date, LocalTime startTime, LocalTime endTime) {
        return executeBooleanOperation(() -> {
            // Step 1: Select a free court
            int courtId = dao.selectFreeCourt(Time.valueOf(startTime), Time.valueOf(endTime));

            // Step 2: Create match record
            int matchId = dao.createMatch(Date.valueOf(date), Time.valueOf(startTime), Time.valueOf(endTime),courtId);

            // Step 3: Select players for the match
            List<Integer> eligiblePlayers = playerDAO.getEligiblePlayers(Time.valueOf(startTime),
                    Time.valueOf(endTime));
            if (eligiblePlayers.size() < 10) {
                throw new SQLException("没有足够的选手参与比赛。");
            }

            List<Integer> selectedPlayers = selectRandomPlayers(eligiblePlayers, 10);

            // Step 4: Insert selected players into PlayerMatches
            for (int playerId : selectedPlayers) {
                playerMatchDAO.insertPlayerMatch(matchId, playerId);
            }

            // Step 5: Generate match results and insert into MatchResults
            Random rand = new Random();
            for (int i = 0; i < selectedPlayers.size(); i++) {
                int playerId = selectedPlayers.get(i);
                int rankId = i + 1;
                int score = rand.nextInt(31); // Score up to 30
                boolean recordBroken = score > 25; // Example threshold

                matchResultDAO.insertMatchResult(matchId, playerId, rankId, score, recordBroken);
            }
        });
    }

    private List<Integer> selectRandomPlayers(List<Integer> eligiblePlayers, int count) {
        Collections.shuffle(eligiblePlayers);
        return eligiblePlayers.subList(0, count);
    }
    
}
