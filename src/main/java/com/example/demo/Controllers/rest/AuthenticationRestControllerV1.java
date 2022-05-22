package com.example.demo.Controllers.rest;


import com.example.demo.DTO.UserDTO;
import com.example.demo.DTO.WalletDTO;
import com.example.demo.Entiti.Role;
import com.example.demo.Entiti.Users;
import com.example.demo.JWT.JwtTokenProvider;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.UserService;
import com.example.demo.Services.WalletServiseImp;
import com.example.demo.sequriti.AuthenticationRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping("api/auth")
@RestController
public class AuthenticationRestControllerV1 {
    @Autowired
    UserService userService;
    public final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    WalletServiseImp walletServiseImp;


    public AuthenticationRestControllerV1(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request,HttpServletResponse response_user) {
        System.out.println("HEllo WORL");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            Users user = userRepository.findAllByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
            String token = jwtTokenProvider.createToken(request.getEmail(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", request.getEmail());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("message","Invalid email/password combination");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("reg")

    public ResponseEntity<?> reg(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult){
        Map<Object, Object> response = new HashMap<>();



        try {
            UserDTO user = userService.findByEmail(userDTO.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this email is already registered\n!");
        }
        catch (NullPointerException err){
            //Validation
            if(bindingResult.hasErrors()){

                if(bindingResult.hasFieldErrors("password")){

                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be 5-15 characters long!");
                }
                if(bindingResult.hasFieldErrors("username")){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
                }
                if(bindingResult.hasFieldErrors("email")){

                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is not valid!");
                }


            }
            userDTO.setRole(Role.USER);
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userService.saveUser(userDTO);
            UserDTO user = userService.findByEmail(userDTO.getEmail());
            WalletDTO walletDTO = new WalletDTO();
            walletDTO.setAdres(user.getEmail());
            walletDTO.setBalance(0);
            walletDTO.setUser(user);
            walletServiseImp.save(walletDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("successfully created!");

        }

    }
    @GetMapping("/login_error")
    public ResponseEntity<?> login_error(){

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("log in first!");
    }


}