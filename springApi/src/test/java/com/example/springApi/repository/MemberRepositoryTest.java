package com.example.springApi.repository;

import com.example.springApi.domain.Member;
import com.example.springApi.domain.MemberRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMember() {
        for (int i = 0; i < 12; i++) {
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .pw(passwordEncoder.encode("1111"))
                    .nickname("nick" + i)
                    .social(false)
                    .build();

            member.addRole(MemberRole.USER);
            if (i >= 5) {
                member.addRole(MemberRole.MANAGER);
            }
            if (i >= 10) {
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        }


    }

    @Test
    public void testRead() {

        String email = "user1@aaa.com";
        Member member = memberRepository.getWithRoles(email);
    }


}