package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.config.JwtProvider;
import com.elevata.gsrtc.dto.AdminLoginDTO;
import com.elevata.gsrtc.dto.EmailRequestDTO;
import com.elevata.gsrtc.dto.OtpRequestDTO;
import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.entity.BusDepot;
import com.elevata.gsrtc.entity.OtpDetails;
import com.elevata.gsrtc.entity.RefreshToken;
import com.elevata.gsrtc.repository.AppUserRepository;
import com.elevata.gsrtc.repository.BusDepotRepository;
import com.elevata.gsrtc.repository.OtpDetailsRepository;
import com.elevata.gsrtc.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class LoginServiceImpl implements LoginService {
    private final AppUserRepository userRepository;
    private final OtpDetailsRepository otpRepository;
    private final EmailService emailService;
    private final RefreshTokenService refreshTokenService;
    private final JwtProvider jwtProvider;
    private final CookieService cookieService;
    private final BusDepotRepository depotRepository;

    @Autowired
    public LoginServiceImpl(AppUserRepository userRepository, OtpDetailsRepository otpRepository, EmailService emailService, RefreshTokenService refreshTokenService, JwtProvider jwtProvider, CookieService cookieService, BusDepotRepository depotRepository) {
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
        this.emailService = emailService;
        this.refreshTokenService = refreshTokenService;
        this.jwtProvider = jwtProvider;
        this.cookieService = cookieService;
        this.depotRepository = depotRepository;
    }

    @Transactional
    @Override
    public void processLogin(EmailRequestDTO emailRequestDTO) {
        String email = emailRequestDTO.getEmail();

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (user != null) {
            otpRepository.findByUser(user)
                    .ifPresent(existing -> otpRepository.delete(existing));

            String otp = String.valueOf(OtpService.generateOtp());

            OtpDetails otpDetail = new OtpDetails();
            otpDetail.setOtp(otp);
            otpDetail.setCreatedAt(LocalDateTime.now());
            otpDetail.setExpiresAt(LocalDateTime.now().plusMinutes(5));
            otpDetail.setUser(user);

            otpRepository.save(otpDetail);
            emailService.sendEmail(email, "Login code", "Your OTP code: " + otp);
        } else {
            System.out.println(email);
            System.out.println("User with email " + email + " not found.");
        }
    }

    @Transactional
    @Override
    public void verify(OtpRequestDTO request, HttpServletResponse response) {
        AppUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        OtpDetails otpDetails = otpRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otpDetails.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new RuntimeException("OTP expired");

        if (!otpDetails.getOtp().equals(request.getOtp()))
            throw new RuntimeException("Invalid OTP");

        otpRepository.delete(otpDetails);

        addTokensToCookie(response, user);
    }

    @Transactional
    @Override
    public String verify(AdminLoginDTO dto, HttpServletResponse response) {
        System.out.println("-".repeat(30) + "Inside verify method");

        AppUser user = null;

        if (!dto.getEmail().equalsIgnoreCase("arhanmominn@gmail.com")) {
            BusDepot depot = depotRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email " + dto.getEmail()));

            System.out.println("-".repeat(30) + "Inside depot if method");


            if (!depot.getEmail().equals(dto.getEmail())) {
                return "Invalid email";
            }

            if (!depot.getPassword().equals(dto.getPassword())) {
                return "Incorrect password";
            }

            user = userRepository.findByBusDepotDepotId(depot.getDepotId())
                    .orElseThrow(() -> new RuntimeException("User not found for depot "
                            + depot.getDepotName()));

        } else {
            System.out.println("-".repeat(30) + "Inside else method");

            user = userRepository.findUserByEmail(dto.getEmail());
                    /*.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new RuntimeException("-".repeat(30) + "User with email " + dto.getEmail() + " not found."));*/
//            System.out.println("-".repeat(30) + "After fetching user");

            if (!user.getEmail().equals(dto.getEmail())) {
                return "Invalid email";
            }

            if (!user.getPassword().equals(dto.getPassword())) {
                return "Incorrect password";
            }
        }

        addTokensToCookie(response, user);

        return "Verified depot admin";
    }

    private void addTokensToCookie(HttpServletResponse response, AppUser user) {
        String accessToken = jwtProvider.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.createOrUpdateRefreshToken(user);

        cookieService.addAccessTokenCookie(response, accessToken);
        cookieService.addRefreshTokenCookie(response, refreshToken.getToken());
    }
}