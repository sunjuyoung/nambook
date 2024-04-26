package com.example.springApi.controller;

import com.example.springApi.dto.MemberDTO;
import com.example.springApi.dto.MemberModifyDTO;
import com.example.springApi.service.MemberService;
import com.example.springApi.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SocialController {

    private final MemberService memberService;

    @GetMapping("/api/member/kakao")
    public String[] getMemberFromKakao(String accessToken){
        log.info("getMemberFromKakao");
        memberService.getKakaoMember(accessToken);
        return new String[]{"AAA","BBB"};
    }

    @GetMapping("/api/member/google")
    public Map<String, Object> getMemberFromGoogle(String credential){
        log.info("getMemberFromGoogle");
        MemberDTO googleMember = memberService.getGoogleMember(credential);
        //일반 로그인과 동일하게 API서버 접근시 사용할 JWT 토큰을 발급한다.
        Map<String, Object> claims = googleMember.getClaims();

        String jwtAccessToken = JWTUtil.generateToken(claims, 10);
        String jwtRefreshToken = JWTUtil.generateToken(claims, 60 * 12);

        claims.put("accessToken", jwtAccessToken);
        claims.put("refreshToken", jwtRefreshToken);

        return claims;
    }

    @PutMapping("/api/member/modify")
    public Map<String,String> modifyMember(@RequestBody MemberModifyDTO memberDTO){
        log.info("modifyMember");
        memberService.modifyMember(memberDTO);
        return Map.of("result","modified");
    }
}
