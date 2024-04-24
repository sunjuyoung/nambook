package com.example.springApi.security.filter;

import com.example.springApi.dto.MemberDTO;
import com.example.springApi.util.JWTUtil;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Slf4j
public class JWTCheckFilter extends OncePerRequestFilter {


    //필터링을 하지 않을 경로나 메소드(GET,POST)을 지정
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        log.info("---------Check Uri : " + path);

        //api/member 경로의 경우 필터링을 하지 않음
        if(path.startsWith("/api/member/")){
            return true;
        }
        if(path.startsWith("/api/products/view/")){
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.info("---------JWTCheckFilter doFilterInternal----------");


        String authHeaderStr =  request.getHeader("Authorization");
        try {
            String accessToken = authHeaderStr.substring(7);
            Map<String,Object> claims = JWTUtil.validateToken(accessToken);

            String email = (String)claims.get("email");
            String nickname = (String)claims.get("nickname");
            Boolean social = (Boolean)claims.get("social");
            List<String> roles = (List<String>)claims.get("roleNames");
            String pw = (String)claims.get("pw");

            MemberDTO memberDTO = new MemberDTO(email,pw,nickname,social.booleanValue(),roles);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(memberDTO, pw, memberDTO.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


            filterChain.doFilter(request, response);
        }catch (Exception e){
            log.info("JWT Exception : " + e.getMessage());

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error","ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.println(msg);
            writer.close();
        }



    }




}
