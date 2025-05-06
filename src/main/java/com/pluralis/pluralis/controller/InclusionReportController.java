package com.pluralis.pluralis.controller;

import com.pluralis.pluralis.service.EmployeeService;
import com.pluralis.pluralis.service.TrainingParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inclusion-report")
public class InclusionReportController {

    private final EmployeeService employeeService;
    private final TrainingParticipationService participationService;

    @GetMapping
    public Map<String, Object> generateReport() {
        Map<String, Object> report = new HashMap<>();

        report.put("totalEmployees", employeeService.getAllEmployees().size());
        report.put("totalTrainingParticipations", participationService.getTotalParticipations());
        return report;
    }
}

