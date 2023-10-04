package com.michw.Warehouse_Manager.service;

import com.michw.Warehouse_Manager.dto.AuthRequest;
import com.michw.Warehouse_Manager.dto.RegisterRequest;
import com.michw.Warehouse_Manager.entity.Role;
import com.michw.Warehouse_Manager.entity.User;
import com.michw.Warehouse_Manager.exceptions.EmailFormatException;
import com.michw.Warehouse_Manager.exceptions.EmailTakenException;
import com.michw.Warehouse_Manager.exceptions.PasswordFormatException;
import com.michw.Warehouse_Manager.exceptions.UserNotFoundException;
import com.michw.Warehouse_Manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public ResponseEntity<String> register(RegisterRequest request) {
        try {
            validateRequest(request);
            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return ResponseEntity.ok(jwtToken);
        } catch (EmailTakenException | EmailFormatException | PasswordFormatException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<String> authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            var jwtToken = jwtService.generateToken(user);
            return ResponseEntity.ok(jwtToken);
        } catch (AuthenticationException | UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


    public void validateRequest(RegisterRequest request){
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        if(!EmailValidator.getInstance().isValid(request.getEmail())){
            throw new EmailFormatException("Please provide valid e-mail address");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new EmailTakenException("Email already taken");
        }
        if (!request.getPassword().matches(pattern)){
            throw new PasswordFormatException("Please make sure password matches the following requirements: " +
                    "Minimum eight characters, at least one lowercase and uppercase letter, one number and one special character:");
        }


    }

}

