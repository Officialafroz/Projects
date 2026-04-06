package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.TicketDTO;
import com.elevata.gsrtc.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/end-user/ticket")
public class TicketController {
    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/tickets")
    public List<TicketDTO> findAllByUserEmail(@RequestParam String email) {
        return ticketService.getTickets(email);
    }

    @GetMapping("/search")
    public TicketDTO getTicket(@RequestParam String pnr) {
        return ticketService.getTicket(pnr);
    }

}
