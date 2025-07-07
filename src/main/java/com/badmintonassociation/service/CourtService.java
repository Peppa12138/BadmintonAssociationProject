package com.badmintonassociation.service;

import com.badmintonassociation.dao.CourtDAO;
import com.badmintonassociation.model.Court;

import java.util.List;

public class CourtService extends BaseService<Court, CourtDAO> {

    public CourtService(CourtDAO courtDAO) {
        super(courtDAO);
    }

    public List<Court> getAllCourts() {
        return executeWithExceptionHandling(
            () -> dao.getAllCourts(),
            null
        );
    }

    // Additional methods...
}
