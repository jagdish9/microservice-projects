package com.appservice.apicallservice.controller;

import com.appservice.apicallservice.service.ApiCallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matches")
public class ApiCallController {

    private static final Logger log = LoggerFactory.getLogger(ApiCallController.class);

    private final ApiCallService apiCallService;

    public ApiCallController(ApiCallService apiCallService) {
        this.apiCallService = apiCallService;
    }

    @GetMapping("/{team}/{year}")
    public int totalMatchByTeamInYear(@PathVariable String team,
                          @PathVariable String year) {
        log.info("Calling api...");
        return apiCallService.getTotalMatchesByTeamInYear(team, year);
    }

    @GetMapping("/{team}")
    public int totalMatchCount(@PathVariable String team) {
        log.info("Calling api for total match");
        int totalGoals = 0;
        try {
            totalGoals =  apiCallService.getTotalMatchCount(team);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return totalGoals;
    }

    @GetMapping("/drawn-matches/{year}")
    public int countNumberOfDrawnMatches(@PathVariable int year) {
        log.info("Calling api to get drawn matches count");
        return apiCallService.getNumberOfDrawnMatches(year);
    }
}
