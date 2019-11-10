package com.anaadihsoft.user.Controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.anaadihsoft.common.external.AuthProvider;
import com.anaadihsoft.common.master.User;
import com.anaadihsoft.user.Model.JwtAuthenticationResponse;
import com.anaadihsoft.user.Payload.ApiResponse;
import com.anaadihsoft.user.Payload.LoginRequest;
import com.anaadihsoft.user.Payload.SignUpRequest;
import com.anaadihsoft.user.Repository.UserRepository;
import com.anaadihsoft.user.Service.TokenProvider;
import com.anaadihsoft.user.exception.BadRequestException;
import com.anaadihsoft.user.oauth2.UserPrincipal;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"*","http://localhost:4200"}, maxAge = 3600)
public class AuthController {
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private TokenProvider tokenProvider;
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

	@PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    	
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        UserPrincipal user=(UserPrincipal)authentication.getPrincipal();
        if(user!=null)
        {
        	if(user.isBlocked()) {
        	throw new BadRequestException("You are not allowed to login");
        	}
        	if(user.isDeactivated())
        	{
        		throw new BadRequestException("Your account have been deactivated.");
        	}
        }
        return ResponseEntity.ok(new JwtAuthenticationResponse(token,(UserPrincipal) authentication.getPrincipal()));
       // return ResponseEntity.ok(new AuthResponse(token));
    }
	
	 @PostMapping("/signup")
	    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
	        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
	            throw new BadRequestException("Email address already in use.");
	        }

	        // Creating user's account
	        User user = new User();
	        user.setName(signUpRequest.getName());
	        user.setEmail(signUpRequest.getEmail());
	        user.setPassword(signUpRequest.getPassword());
	        user.setProvider(AuthProvider.local);
	        user.setPassword(passwordEncoder.encode(user.getPassword()));

	        User result = userRepository.save(user);

	        URI location = ServletUriComponentsBuilder
	                .fromCurrentContextPath().path("/user/me")
	                .buildAndExpand(result.getId()).toUri();

	        return ResponseEntity.created(location)
	                .body(new ApiResponse(true, "User registered successfully@"));
	    }
}
