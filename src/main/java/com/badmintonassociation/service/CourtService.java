package com.badmintonassociation.service;

import com.badmintonassociation.dao.CourtDAO;
import com.badmintonassociation.model.Court;

import java.sql.SQLException;
import java.util.List;

public class CourtService {
    private CourtDAO courtDAO;

    public CourtService(CourtDAO courtDAO) {
        this.courtDAO = courtDAO;
    }

    public List<Court> getAllCourts() {
        try {
            return courtDAO.getAllCourts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Additional methods...
}
