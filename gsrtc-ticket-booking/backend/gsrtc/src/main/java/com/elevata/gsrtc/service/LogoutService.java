package com.elevata.gsrtc.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public interface LogoutService {

    boolean logoutDepotAdmin(HttpServletRequest request,
                             HttpServletResponse response, int depotId);
}
