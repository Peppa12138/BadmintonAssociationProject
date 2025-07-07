package com.badmintonassociation.service;

import com.badmintonassociation.dao.ReservationDAO;
import com.badmintonassociation.dao.MatchDAO;
import com.badmintonassociation.model.Reservation;
import com.badmintonassociation.model.Match;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class ReservationService extends BaseService<Reservation, ReservationDAO> {
    private MatchDAO matchDAO;

    public ReservationService(ReservationDAO reservationDAO, MatchDAO matchDAO) {
        super(reservationDAO);
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
        return executeBooleanOperation(() -> {
            if (dao.isCourtAvailable(courtId, startTime, endTime)) {
                Reservation reservation = new Reservation();
                reservation.setCourtId(courtId);
                reservation.setStartTime(startTime);
                reservation.setEndTime(endTime);
                reservation.setPlayerId(playerId);
                reservation.setMatchId(matchId);

                dao.createReservation(reservation);
            } else {
                throw new SQLException("场地在指定时间不可用");
            }
        });
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
        return executeBooleanOperation(() -> {
            // First check if court is available
            if (!dao.isCourtAvailable(courtId, startTime, endTime)) {
                throw new SQLException("场地在指定时间不可用");
            }
            
            // Create match
            Match match = new Match();
            match.setMatchId(matchId);
            match.setStartTime(startTime);
            match.setEndTime(endTime);
            matchDAO.createMatch(match);
            
            // Create reservation
            Reservation reservation = new Reservation();
            reservation.setCourtId(courtId);
            reservation.setStartTime(startTime);
            reservation.setEndTime(endTime);
            reservation.setPlayerId(null);
            reservation.setMatchId(matchId);
            dao.createReservation(reservation);
        });
    }
    
    // Check if court is available based on start time only
    public boolean isStartTimeAvailable(int courtId, Timestamp startTime) {
        return executeWithExceptionHandling(
            () -> dao.isStartTimeAvailable(courtId, startTime),
            false
        );
    }
    
    public boolean isEndTimeAvailable(int courtId, Timestamp endTime) {
        return executeWithExceptionHandling(
            () -> dao.isEndTimeAvailable(courtId, endTime),
            false
        );
    }

    // Check if court is available
    public boolean isCourtAvailable(int courtId, Timestamp startTime, Timestamp endTime) {
        return executeWithExceptionHandling(
            () -> dao.isCourtAvailable(courtId, startTime, endTime),
            false
        );
    }

    public List<Reservation> getRecentReservations(int courtId) {
        return executeWithExceptionHandling(
            () -> dao.getRecentReservations(courtId, 10),
            null
        );
    }
    
    public boolean canPlayerReserveOnDate(int playerId, LocalDate date) {
        return executeWithExceptionHandling(
            () -> {
                int reservationCount = dao.getReservationCountForPlayerOnDate(playerId, date);
                return reservationCount < 2;
            },
            false
        );
    }

    // Additional business logic methods...
}
