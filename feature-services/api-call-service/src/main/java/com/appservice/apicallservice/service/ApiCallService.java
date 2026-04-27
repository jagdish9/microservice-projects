package com.appservice.apicallservice.service;

import com.appservice.apicallservice.entity.ApiResponse;
import com.appservice.apicallservice.entity.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;

@Service
public class ApiCallService {

    private static final Logger log = LoggerFactory.getLogger(ApiCallService.class);

    @Value("${api.base-url}")
    public String baseUrl;

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public int getTotalMatchesByTeamInYear(String team, String year) {
        RestTemplate restTemplate = new RestTemplate();

        int totalGoals = 0;

        //team as team1
        totalGoals = totalGoals + getGoals(restTemplate, team, year, "team1", "team1goals");

        //team as team2
        //totalGoals = totalGoals + getGoals(restTemplate, team, year, "team1", "team1goals");

        return totalGoals;
    }

    private int getGoals(RestTemplate restTemplate, String team, String year, String queryParam, String goalField) {
        log.info("Calling rest Api to collect data");
        int page = 1;
        int totalPages = 1;
        int goals = 0;

        do {
            String url = baseUrl + "?" + queryParam + "=" + team + "&page=" + page;

            ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);

            for(Data data : response.getData()) {
                if(goalField.equals("team1goals") && year.equals(String.valueOf(data.getYear()))) {
                    goals += Integer.parseInt(data.getTeam1goals());
                }
            }

            totalPages = response.getTotal_pages();
            page++;

        } while (page <= totalPages);

        log.info("Total pages: {}", totalPages);
        log.info("Goals {} by {}", goals, team);
        return goals;
    }

    public int getTotalMatchCount(String team) throws Exception {
        int totalGoals = 0;

        totalGoals += fetchGoals(team);

        return totalGoals;
    }

    private int fetchGoals(String team) throws IOException, InterruptedException {
        int page = 1;
        int totalPages = 1;
        int goals = 0;

        do {
            String url = baseUrl + "?" + "team1=" + team + "&page=" + page;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            ApiResponse apiResponse =
                    mapper.readValue(response.body(), ApiResponse.class);

            for(Data d : apiResponse.getData()) {
                if(d.getTeam1().equals(team)) {
                    goals++;
                }
            }

            totalPages = apiResponse.getTotal_pages();
            page++;

        } while(page <= totalPages);

        return goals;
    }

    public int getNumberOfDrawnMatches(int year) {
        int page = 1;
        int totalPages = 1;
        int drawnMatches = 0;

        RestTemplate restTemplate = new RestTemplate();

        do {
            String url = baseUrl + "?year=" + year + "&page=" + page;

            log.info("Calling api: {}", url);

            ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);

            drawnMatches += response.getData().stream()
                    .filter(data -> data.getTeam1goals().equals(data.getTeam2goals()))
                    .toList()
                    .size();

            totalPages = response.getTotal_pages();
            page++;

        } while (page <= totalPages);

        return drawnMatches;
    }
}
