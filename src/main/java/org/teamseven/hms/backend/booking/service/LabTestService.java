package org.teamseven.hms.backend.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.booking.entity.TestRepository;
import org.teamseven.hms.backend.booking.entity.TestStatus;

import java.util.UUID;

@Service
public class LabTestService {
    @Autowired private TestRepository testRepository;

    public boolean updateTestStatus(UUID testId, TestStatus newStatus) {
        return testRepository.setTestStatus(newStatus, testId) == 1;
    }
}
