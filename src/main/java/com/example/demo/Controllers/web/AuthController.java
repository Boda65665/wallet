package com.example.demo.Controllers.web;

import com.example.demo.DTO.UserDTO;
import com.example.demo.DTO.WalletDTO;
import com.example.demo.Entiti.Role;
import com.example.demo.JWT.JwtTokenProvider;
import com.example.demo.Services.UserServiceImplement;
import com.example.demo.Services.WalletServiseImp;
import com.example.demo.sequriti.AuthenticationRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class AuthController {
    private final AuthenticationManager authenticationManager;

    public final UserServiceImplement userServiceImplement;
    public final JwtTokenProvider jwtTokenProvider;
    public  final PasswordEncoder passwordEncoder;

    public final WalletServiseImp walletServiseImp;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserServiceImplement userServiceImplement, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, WalletServiseImp walletServiseImp) {
        this.authenticationManager = authenticationManager;
        this.userServiceImplement = userServiceImplement;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.walletServiseImp = walletServiseImp;
    }


    @GetMapping("/login")
    public String login_get(Model model){
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO();
        model.addAttribute("auth",authenticationRequestDTO);

        return "main/login";
    }
    @PostMapping("/login")
    public String login_post(Model model,HttpServletResponse response, HttpServletRequest request, @ModelAttribute("auth") @Valid AuthenticationRequestDTO authenticationRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "main/login";
        }
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getEmail(), authenticationRequestDTO.getPassword()));

            Cookie cookie = new Cookie("JWT", jwtTokenProvider.createToken(authenticationRequestDTO.getEmail(),authenticationRequestDTO.getPassword()));//создаем объект Cookie,
                //в конструкторе указываем значения для name и value
                cookie.setPath("/");//устанавливаем путь
                cookie.setMaxAge(86400);//здесь устанавливается время жизни куки
                response.addCookie(cookie);//добавляем Cookie в запрос
                return "redirect:/main";


        }
        catch (AuthenticationException err){
            model.addAttribute("err_pas_email","Неверный пароль или email");

            return "main/login";
        }

    }
    @GetMapping("/reg")
    public String reg_get(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user",userDTO);
        return "main/reg";

    }
    @PostMapping("/reg")
    public String reg_post(Model model,@ModelAttribute("user") @Valid UserDTO userDTO,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "main/reg";
        }
        try {
            System.out.println(userServiceImplement.findByEmail(userDTO.getEmail()));
            model.addAttribute("err","User с такой пчтой уже зареган");
            System.out.println("hello");
            return "main/reg";



        }
        catch (NullPointerException er){
            userDTO.setRole(Role.USER);
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userServiceImplement.saveUser(userDTO);
            UserDTO user = userServiceImplement.findByEmail(userDTO.getEmail());
            WalletDTO walletDTO = new WalletDTO();
            walletDTO.setAdres(user.getEmail());
            walletDTO.setBalance(0);
            walletDTO.setUser(user);
            walletServiseImp.save(walletDTO);
            return "redirect:/login";

        }


    }
}


