package com.badmintonassociation.service;

import com.badmintonassociation.dao.ReservationDAO;
import com.badmintonassociation.dao.MatchDAO;
import com.badmintonassociation.model.Reservation;
import com.badmintonassociation.model.Match;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class ReservationService {
    private ReservationDAO reservationDAO;
    private MatchDAO matchDAO;

    public ReservationService(ReservationDAO reservationDAO, MatchDAO matchDAO) {
        this.reservationDAO = reservationDAO;
        this.matchDAO = matchDAO;
    }

    /**
     * Reserves a court for either training or a match.
     *
     * @param courtId   The ID of the court being reserved.
     * @param startTime The desired start time for the reservation.
     * @param endTime   The desired end time for the reservation.
     * @param playerId  The ID of the player reserving the court (null if reserved
     *                  for a match).
     * @param matchId   The ID of the match (null if reserved by a player).
     * @return true if the reservation was successful, false otherwise.
     */
    public boolean reserveCourt(int courtId, Timestamp startTime, Timestamp endTime, Integer playerId,
            Integer matchId) {
        try {
            if (reservationDAO.isCourtAvailable(courtId, startTime, endTime)) {
                Reservation reservation = new Reservation();
                reservation.setCourtId(courtId);
                reservation.setStartTime(startTime);
                reservation.setEndTime(endTime);
                reservation.setPlayerId(playerId);
                reservation.setMatchId(matchId);

                reservationDAO.createReservation(reservation);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Schedules a new match and reserves the necessary court.
     *
     * @param matchId   The ID of the match to be scheduled.
     * @param courtId   The ID of the court where the match will occur.
     * @param date      The date of the match.
     * @param startTime The starting time of the match.
     * @param endTime   The ending time of the match.
     * @return true if the match was successfully scheduled, false otherwise.
     */
    public boolean scheduleMatch(int matchId, int courtId, Timestamp startTime, Timestamp endTime) {
        try {
            Match match = new Match();
            match.setMatchId(matchId);
            match.setStartTime(startTime);
            match.setEndTime(endTime);

            // Check availability and create reservation
            if (reserveCourt(courtId, startTime, endTime, null, matchId)) {
                matchDAO.createMatch(match);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Check if court is available based on start time only
    public boolean isStartTimeAvailable(int courtId, Timestamp startTime) {
        try {
            return reservationDAO.isStartTimeAvailable(courtId, startTime);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isEndTimeAvailable(int courtId, Timestamp endTime) {
        try {
            return reservationDAO.isEndTimeAvailable(courtId, endTime);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if court is available
    public boolean isCourtAvailable(int courtId, Timestamp startTime, Timestamp endTime) {
        try {
            return reservationDAO.isCourtAvailable(courtId, startTime, endTime);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reservation> getRecentReservations(int courtId) {
        try {
            return reservationDAO.getRecentReservations(courtId, 10);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean canPlayerReserveOnDate(int playerId, LocalDate date) {
        try {
            int reservationCount = reservationDAO.getReservationCountForPlayerOnDate(playerId, date);
            return reservationCount < 2;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Additional business logic methods...
}
