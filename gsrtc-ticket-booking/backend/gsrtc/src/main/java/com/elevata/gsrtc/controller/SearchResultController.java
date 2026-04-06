package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.SearchResultDTO;
import com.elevata.gsrtc.service.SearchBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/searchBus")
public class SearchResultController {
    private final SearchBusService searchBusService;

    @Autowired
    public SearchResultController(SearchBusService searchBusService) {
        this.searchBusService = searchBusService;
    }

    @GetMapping("/results")
    public List<SearchResultDTO> getBuses(
            @RequestParam LocalDate journeyDate,
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam int requestedSeats
    ) {
        if (requestedSeats > 6) {
            throw new RuntimeException("Seat must be less than equal to 6");
        }

        return searchBusService.getSearchResult(journeyDate, source, destination, requestedSeats);
    }
}
