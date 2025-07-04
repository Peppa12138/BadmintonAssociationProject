package com.badmintonassociation.service;

import com.badmintonassociation.dao.MatchDAO;
import com.badmintonassociation.dao.MatchResultDAO;
import com.badmintonassociation.dao.PlayerDAO;
import com.badmintonassociation.dao.PlayerMatchDAO;
import com.badmintonassociation.model.MatchResult;
// import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.sql.Date;
import java.util.Random;

public class MatchService {
    private MatchDAO matchDAO;
    private PlayerDAO playerDAO;
    private PlayerMatchDAO playerMatchDAO;
    private MatchResultDAO matchResultDAO;

    public MatchService(MatchDAO matchDAO, PlayerDAO playerDAO, PlayerMatchDAO playerMatchDAO,MatchResultDAO matchResultDAO) {
        this.matchDAO = matchDAO;
        this.playerDAO = playerDAO;
        this.playerMatchDAO = playerMatchDAO;
        this.matchResultDAO = matchResultDAO;
    }

    /**
     * Records the result for a given match.
     *
     * @param matchResult The result of a match including rank, score, and record
     *                    status.
     * @return true if the match result was successfully recorded, false otherwise.
     */
    public boolean recordMatchResult(MatchResult matchResult) {
        try {
            matchResultDAO.createMatchResult(matchResult);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves all results from a given match.
     *
     * @param matchId The ID of the match whose results are to be retrieved.
     * @return A list of MatchResult objects containing the result data.
     */
    public List<MatchResult> getMatchResults(int matchId) {
        try {
            return matchResultDAO.getResultsByMatchId(matchId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // public boolean createMatch(LocalDate date, LocalTime startTime, LocalTime endTime) {
    //     try {
    //         matchDAO.createMatch(Date.valueOf(date), Time.valueOf(startTime), Time.valueOf(endTime));
    //         return true;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

    public boolean createMatchWithPlayers(LocalDate date, LocalTime startTime, LocalTime endTime) {
        try {
            // Step 1: Select a free court
            int courtId = matchDAO.selectFreeCourt(Time.valueOf(startTime), Time.valueOf(endTime));

            // Step 2: Create match record
            int matchId = matchDAO.createMatch(Date.valueOf(date), Time.valueOf(startTime), Time.valueOf(endTime),courtId);

            // Step 3: Select players for the match
            List<Integer> eligiblePlayers = playerDAO.getEligiblePlayers(Time.valueOf(startTime),
                    Time.valueOf(endTime));
            if (eligiblePlayers.size() < 10) {
                System.out.println("没有足够的选手参与比赛。");
                return false;
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

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<Integer> selectRandomPlayers(List<Integer> eligiblePlayers, int count) {
        Collections.shuffle(eligiblePlayers);
        return eligiblePlayers.subList(0, count);
    }
    // Additional business logic methods...
}
