package com.example.springApi.security;

import com.example.springApi.dto.MemberDTO;
import com.example.springApi.util.JWTUtil;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
public class ApiLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        log.info("ApiLoginSuccessHandler --- Login Success");
       MemberDTO memberDTO =  (MemberDTO)authentication.getPrincipal();

        Map<String, Object> claims = memberDTO.getClaims();
        String accessToken = JWTUtil.generateToken(claims, 10); //10분
        String refreshToken = JWTUtil.generateToken(claims, 60*12); //12시간
        claims.put("accessToken",accessToken);
        claims.put("refreshToken",refreshToken);

        Gson gson = new Gson();
        String jsonString = gson.toJson(claims);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonString);
        printWriter.close();

    }
}
