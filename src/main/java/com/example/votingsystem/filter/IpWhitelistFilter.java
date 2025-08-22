package com.example.votingsystem.filter;

import com.example.votingsystem.service.IpWhitelistService;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class IpWhitelistFilter extends OncePerRequestFilter {

    private final IpWhitelistService ipWhitelistService;

    public IpWhitelistFilter(IpWhitelistService ipWhitelistService) {
        this.ipWhitelistService = ipWhitelistService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Apply whitelist check only to the login endpoint
        if (request.getRequestURI().equals("/api/login")) {
            String clientIp = request.getRemoteAddr();
            if (!ipWhitelistService.isAllowed(clientIp)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Access Denied: IP address " + clientIp + " is not whitelisted.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}