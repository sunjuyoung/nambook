package com.example.springApi.controller;

import com.example.springApi.util.CustomJWTException;
import com.example.springApi.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiRefreshController {

    @RequestMapping("/api/member/refresh")
    public Map<String,Object> refresh(@RequestHeader("Authorization")String authHeader , String refreshToken){

        if(refreshToken == null){
            throw new CustomJWTException("Refresh Token is null");
        }

        if(authHeader == null || authHeader.length() < 7){
            throw new CustomJWTException("Authorization Header is null or invalid");
        }

        String accessToken = authHeader.substring(7);

        //accessToken 만료되지 않았다면
        if(checkExpiredToken(accessToken) == false){
            return Map.of("accessToken",accessToken,"refreshToken",refreshToken);
        }

        //refreshToken 검증
        Map<String,Object> claims = JWTUtil.validateToken(refreshToken);

        String newAccessToken = JWTUtil.generateToken(claims,10);

        String newRefreshToken = checkTime((Integer)claims.get("exp")) == true? JWTUtil.generateToken(claims,60*12):refreshToken;

        return Map.of("accessToken",newAccessToken,"refreshToken",newRefreshToken);
    }

    //토큰 만료시간 체크 ,30분 이내면 true
    private boolean checkTime(Integer exp) {

        //exp를 날짜로 변환
        Date expDate = new Date((long) exp * 1000);
        //현재 시간과의 차이 계산
        long gap = expDate.getTime() - System.currentTimeMillis();

        //분단위 계산
        long leftMin = gap / 60000;

        return leftMin < 30;
    }

    private boolean checkExpiredToken(String accessToken) {
        try {
            JWTUtil.validateToken(accessToken);
        }catch (CustomJWTException e){
            if(e.getMessage().equals("Expired")){
                return true;
            }
        }
        return false;
    }
}
