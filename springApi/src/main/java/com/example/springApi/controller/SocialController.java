package com.example.springApi.controller;

import com.example.springApi.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SocialController {

    private final MemberService memberService;

    @GetMapping("/api/member/kakao")
    public String[] getMemberFromKakao(String accessToken){
        log.info("accessToken: "+accessToken);
        memberService.getKakaoMember(accessToken);
        return new String[]{"AAA","BBB"};
    }

    @GetMapping("/api/member/google")
    public String[] getMemberFromGoogle(String credential){
        log.info("credential: "+credential);
        memberService.getGoogleMember(credential);
        return new String[]{"AAA","BBB"};
    }
}
