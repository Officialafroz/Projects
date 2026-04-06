package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.TicketDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketService {
    List<TicketDTO> getTickets(String email);

    TicketDTO getTicket(String pnr);
}
