package com.drc.test.security.filter;

import com.drc.test.security.JwtUtil;
import com.drc.test.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        //Step - 1 : Get the authorization Header from the request
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        //Step - 2 : Check whether the requestHeader is not null and is it starts with Bearer or not
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Step - 3 : Find the token and username from the token
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        // Step - 4 : Check whether the username is null or not
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Step - 5 : Fetch the userDetailsService by username from the CustomUserDetailsService
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Step - 6 : Validate the token and userDetails
            if (jwtUtil.validateToken(jwt, userDetails)) {

                // Step - 7 : Get validate username and password token
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Step - 8 : Set token details from the request
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        // Step - 9 : Continue with Chain
        chain.doFilter(request, response);
    }

}