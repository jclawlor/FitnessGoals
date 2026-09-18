package dev.jclawlor.FitnessGoals.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
    private UserDetailsServiceImpl userDetailsService;


	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain)
	        throws ServletException, IOException {
		
		System.out.println("JwtAuthFilter executing");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("Authentication: " + auth);

		if (auth != null) {
		    System.out.println("Authorities: " + auth.getAuthorities());
		}

	    // Skip filter if already authenticated (e.g. @WithMockUser in tests)
	    if (SecurityContextHolder.getContext().getAuthentication() != null) {
	    	System.out.println("Already authenticated");
	    	filterChain.doFilter(request, response);
	        return;
	    }
		
	    
	    final String authHeader = request.getHeader("Authorization");
	    
	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    final String token = authHeader.substring(7);
	    System.out.println(token);
	    System.out.println("Is token valid: " + jwtUtil.isTokenValid(token));

	    if (jwtUtil.isTokenValid(token)) {
	        String username = jwtUtil.extractUsername(token);
	        
	        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	        
	        UsernamePasswordAuthenticationToken authToken =
	                new UsernamePasswordAuthenticationToken(
	                        userDetails, null, userDetails.getAuthorities());

	        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	        SecurityContextHolder.getContext().setAuthentication(authToken);
	    }

	    filterChain.doFilter(request, response);
	}
}