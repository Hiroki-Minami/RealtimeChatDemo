package com.hirokiminami.chatroom.back.controller;

//import com.hirokiminami.chatroom.back2.config.JwtService;
import com.hirokiminami.chatroom.back.dto.auth.LoginRequest;
import com.hirokiminami.chatroom.back.dto.auth.LoginResponse;
import com.hirokiminami.chatroom.back.dto.auth.RegisterRequest;
import com.hirokiminami.chatroom.back.model.User;
import com.hirokiminami.chatroom.back.model.supportive.UserProjection;
import com.hirokiminami.chatroom.back.service.UserService;
//import com.hirokiminami.chatroom.back2.util.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    private static final Logger Log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SecurityContextRepository securityContextRepository;
    @Autowired
    private UserService userService;
//    @Autowired
//    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        UserProjection user = userService.getProjectedUserByEmail(loginRequest.getEmail());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        securityContextRepository.saveContext(securityContext, request, response);

        LoginResponse loginResponse = new LoginResponse(true, user);
        return ResponseEntity.ok(loginResponse);
    }

    // TODO: register
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest registerRequest, HttpServletRequest request, HttpServletResponse response) {
        // TODO: validation
        // password
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // check if there is another user using same email.
//        UserProjection sameEmailUser = userService.getProjectedUserByEmail(registerRequest.getEmail());
        if (userService.existUserByEmail(registerRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // create a record
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setNickName(registerRequest.getNickName());
        String encodedPassword = userService.encodePassword(registerRequest.getPassword());
        user.setPassword(encodedPassword);
        User created = userService.createUser(user);
        UserProjection userProjection = userService.getProjectedUserById(created.getId());
        // auth
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getPassword()));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        securityContextRepository.saveContext(securityContext, request, response);
//        LoginResponse loginResponse = new LoginResponse(jwtService.generateToken(authentication), new AuthorizedUser(user));
        // return
//        return ResponseEntity.ok(loginResponse);
        LoginResponse loginResponse = new LoginResponse(true, userProjection);
        return ResponseEntity.ok(loginResponse);
    }
    // if success return the token as well
}
