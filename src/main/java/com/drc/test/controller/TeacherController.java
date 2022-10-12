package com.drc.test.controller;

import com.drc.test.security.JwtUtil;
import com.drc.test.Util.Validation;
import com.drc.test.security.authentication.AuthenticationRequest;
import com.drc.test.security.authentication.AuthenticationResponse;
import com.drc.test.entity.Teacher;
import com.drc.test.exception.GlobalError;
import com.drc.test.security.CustomUserDetailsService;
import com.drc.test.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    ResponseEntity<?> createTeacher(@RequestBody Teacher teacher) {
        if (!Validation.emailValidation(teacher.getEmail())) {
            throw new GlobalError("Email is not valid !!", 400, HttpStatus.BAD_REQUEST.value());
        }
        if (!Validation.ageValidation(teacher.getAge())) {
            throw new GlobalError("Age is not valid !!", 400, HttpStatus.BAD_REQUEST.value());
        }
        Teacher existingTeacher = teacherService.checkEmailExistOrNot(teacher.getEmail());
        if (existingTeacher != null) {
            throw new GlobalError("Teacher already exist!!", 302, HttpStatus.FOUND.value());
        }
        return new ResponseEntity<>(teacherService.save(teacher), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            // Step - 1 : Validating username and password using AuthenticationManager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception("Invalid Username or Password !!!");
        }

        //Step - 2 : Get username from the UserDetailsService
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        //Step- 3 : Generating Token from JwtUtil class
        String token = jwtUtil.generateToken(userDetails);


        //Step - 4 : Send Token as a response to the client
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
