package com.example.springApi.service;

import com.example.springApi.domain.Member;
import com.example.springApi.domain.MemberRole;
import com.example.springApi.dto.MemberDTO;
import com.example.springApi.dto.MemberModifyDTO;
import com.example.springApi.repository.MemberRepository;
import com.example.springApi.util.CustomJWTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    // 해당 이메일을 가진 회원이 없다면  임의비밀번호를 암호화해서 저장
    // 카카오 로그인 후에는 회원정보를 수정할 수 있도록 구성해 사용자가 비밀번호를 설정할 수 있도록 한다.
    private final PasswordEncoder passwordEncoder;


    //카카오 로그인 회원
    public MemberDTO getKakaoMember(String accessToken){
        String email = getEmailFromKakao(accessToken);

        log.info("email: "+email);
        return null;
    }


    private String getEmailFromKakao(String accessToken) {
        String kakaoGetUserUrl = "https://kapi.kakao.com/v2/user/me";


        if(accessToken == null){
            throw new RuntimeException("Access Token is null");
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+accessToken);
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String>entity = new HttpEntity<>(headers);


        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(kakaoGetUserUrl).build();

        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET,entity,LinkedHashMap.class);
        log.info("response: "+response);
        LinkedHashMap<String,LinkedHashMap> bodyMap = response.getBody();

        log.info("body: "+bodyMap.get("id"));

//        LinkedHashMap<String,String> kakaoAccount = bodyMap.get("id");
//
//        log.info("kakaoAccount: "+kakaoAccount);


        return String.valueOf(bodyMap.get("id"));

    }

    //구글 로그인 회원
    public MemberDTO getGoogleMember(String accessToken){
        //credential decoding
        String email = getEmailFromGoogle(accessToken);
        Optional<Member> result = memberRepository.findById(email);
        if(result.isPresent()){
            MemberDTO memberDTO = entityToDTO(result.get());
            return memberDTO;
        }
        //기존 회원이 아니라면
        Member member = makeSocialMember(email);
        memberRepository.save(member);
        MemberDTO memberDTO = entityToDTO(member);
        return memberDTO;


    }

    private String getEmailFromGoogle(String accessToken) {
        if(accessToken == null){
            throw new CustomJWTException("google accessToken is null");
        }
        String googleGetUserUrl = "https://oauth2.googleapis.com/tokeninfo?id_token="+accessToken;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(googleGetUserUrl, HttpMethod.GET,null,LinkedHashMap.class);
        LinkedHashMap<String,String> bodyMap = response.getBody();

        return bodyMap.get("email");

    }



    private MemberDTO entityToDTO(Member member){
        return new MemberDTO(
                member.getEmail(),
                member.getPw(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList().stream().map(memberRole -> memberRole.name()).toList()
        );
    }

    private Member makeSocialMember(String email){
        String tempPw = passwordEncoder.encode("temp1234");
        String nickname = email.split("@")[0];

        Member member =  Member.builder()
                .email(email)
                .pw(tempPw)
                .nickname(nickname)
                .social(true)
                .build();

        member.addRole(MemberRole.USER);
        return member;
    }

    //회원정보 수정
    public void modifyMember(MemberModifyDTO modifyDTO){
        Member member = memberRepository.findById(modifyDTO.getEmail()).orElseThrow();
        member.changeNickname(modifyDTO.getNickname());
        member.changePw(passwordEncoder.encode(modifyDTO.getPw()));
        member.changeSocial(false);
    }
}
