package com.example.demo.common.config.filter;

import com.example.demo.member.model.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.common.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final String secretKey;
    private final MemberRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);


        String token;
        if (header != null && header.startsWith("Bearer ")) {
            token = header.split(" ")[1];
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        String username = JwtUtils.getUsername(token, secretKey);
        Long userId = JwtUtils.getUserId(token, secretKey);


        if (!JwtUtils.validate(token, username, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<Member> result = repository.findByEmail(username);
        Member member = result.get();


        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                Member.builder().id(userId).email(username).build(), null,
                member.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
