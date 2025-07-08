
package com.badmintonassociation.service;

import com.badmintonassociation.dao.ReservationDAO;
import com.badmintonassociation.dao.MatchDAO;
import com.badmintonassociation.model.Reservation;
import com.badmintonassociation.model.Match;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;


/**
 * 预订业务服务类
 * 该类负责场地预订相关的业务逻辑处理，作为控制器层和数据访问层之间的桥梁。
 * 继承自 BaseService，具备统一的异常处理和日志记录能力。
 * 提供场地预订、时间冲突检测、预订管理等业务功能，支持场地资源调度和预订管理系统。
 * @author guoYiFu
 * @since 2025-07-03
 */




public class ReservationService extends BaseService<Reservation, ReservationDAO> {
    private MatchDAO matchDAO;

    public ReservationService(ReservationDAO reservationDAO, MatchDAO matchDAO) {
        super(reservationDAO);
        this.matchDAO = matchDAO;
    }

    // 预约场地
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

    // 安排比赛
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
