package com.badmintonassociation.dao;

import com.badmintonassociation.model.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private Connection connection;

    public ReservationDAO(Connection connection) {
        this.connection = connection;
    }

    // Get all reservations
    public List<Reservation> getAllReservations() throws SQLException {
        String query = "SELECT * FROM Reservations";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        List<Reservation> reservations = new ArrayList<>();
        while (resultSet.next()) {
            Reservation reservation = new Reservation(
                    resultSet.getInt("reservation_id"),
                    resultSet.getInt("court_id"),
                    (Integer) resultSet.getObject("player_id"),
                    (Integer) resultSet.getObject("match_id"),
                    resultSet.getTimestamp("start_time"),
                    resultSet.getTimestamp("end_time"));
            reservations.add(reservation);
        }
        return reservations;
    }

    // Check availability based on start time only
    public boolean isStartTimeAvailable(int courtId, Timestamp startTime) throws SQLException {
        String query = "SELECT COUNT(*) FROM Reservations WHERE court_id = ? AND start_time = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, courtId);
            preparedStatement.setTimestamp(2, startTime);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) == 0; // No conflict if count is zero
                }
            }
        }
        return false;
    }

    // Check if court is available during the given time span
    public boolean isCourtAvailable(int courtId, Timestamp startTime, Timestamp endTime) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM Reservations WHERE court_id = ? " +
                "AND ((start_time <= ? AND end_time > ?) OR (start_time < ? AND end_time >= ?))";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, courtId);
        preparedStatement.setTimestamp(2, startTime);
        preparedStatement.setTimestamp(3, startTime);
        preparedStatement.setTimestamp(4, endTime);
        preparedStatement.setTimestamp(5, endTime);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt("count");
        return count == 0;
    }

    // Reserve court for training or match
    public void createReservation(Reservation reservation) throws SQLException {
        String query = "INSERT INTO Reservations (court_id, player_id, match_id, start_time, end_time) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, reservation.getCourtId());
        preparedStatement.setObject(2, reservation.getPlayerId(), Types.INTEGER);
        preparedStatement.setObject(3, reservation.getMatchId(), Types.INTEGER);
        preparedStatement.setTimestamp(4, reservation.getStartTime());
        preparedStatement.setTimestamp(5, reservation.getEndTime());
        preparedStatement.executeUpdate();
    }

    public boolean isEndTimeAvailable(int courtId, Timestamp endTime) throws SQLException {
        String query = "SELECT COUNT(*) FROM Reservations WHERE court_id = ? AND start_time = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, courtId);
            preparedStatement.setTimestamp(2, endTime);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) == 0; // No conflict if count is zero
                }
            }
        }
        return false;
    }
    
    public List<Reservation> getRecentReservations(int courtId, int limit) throws SQLException {
        String query = "SELECT * FROM Reservations WHERE court_id = ? ORDER BY start_time DESC LIMIT ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, courtId);
            preparedStatement.setInt(2, limit);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Reservation> reservations = new ArrayList<>();
                while (resultSet.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setReservationId(resultSet.getInt("reservation_id"));
                    reservation.setCourtId(resultSet.getInt("court_id"));
                    reservation.setPlayerId(resultSet.getInt("player_id"));
                    reservation.setStartTime(resultSet.getTimestamp("start_time"));
                    reservation.setEndTime(resultSet.getTimestamp("end_time"));
                    reservations.add(reservation);
                }
                return reservations;
            }
        }
    }
    
    public int getReservationCountForPlayerOnDate(int playerId, LocalDate date) throws SQLException {
        String query = "SELECT COUNT(*) FROM Reservations WHERE player_id = ? AND DATE(start_time) = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, playerId);
            preparedStatement.setDate(2, Date.valueOf(date));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }

    // Additional methods such as update, delete...
}
