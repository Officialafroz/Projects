package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.SearchResultDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface SearchBusService {
    List<SearchResultDTO> getSearchResult(
            LocalDate journeyDate, String source, String destination, int requestedSeats
    );
}
