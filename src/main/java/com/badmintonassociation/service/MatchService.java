package com.badmintonassociation.service;

import com.badmintonassociation.dao.MatchDAO;
import com.badmintonassociation.dao.MatchResultDAO;
import com.badmintonassociation.model.MatchResult;

import java.sql.SQLException;
import java.util.List;

public class MatchService {
    private MatchDAO matchDAO;
    private MatchResultDAO matchResultDAO;

    public MatchService(MatchDAO matchDAO, MatchResultDAO matchResultDAO) {
        this.matchDAO = matchDAO;
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

    // Additional business logic methods...
}
