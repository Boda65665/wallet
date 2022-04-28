package com.example.demo.Controllers;

import com.example.demo.DTO.CardDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.JWT.JwtTokenProvider;
import com.example.demo.Services.CardServise;
import com.example.demo.Services.CardServiseImp;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("api/bank")
@RestController
public class MainController {
    private final JwtTokenProvider jwtTokenProvider;

    public MainController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    UserService userService;
    @Autowired
    CardServiseImp cardServiseImp;
    @Value("${jwt.headre}")
    private String authorizationHeader;

    @PostMapping("new_card")
    private ResponseEntity<?> new_card(@RequestBody CardDTO cardDTO, HttpServletRequest request, @RequestHeader Map<String, String> headers) {


        String payment_system = cardDTO.getPayment_system();
        String number = "";
        System.out.println(headers.get("authorization"));
        String token = "";
        System.out.println(headers);
        String email = jwtTokenProvider.getUsername(headers.get("authorization"));
        UserDTO userDTO = userService.findByEmail(email);
        CardDTO cardDTO1 = cardServiseImp.findByUser(userDTO);
        if (cardDTO1 != null) {
            Map<Object, Object> response = new HashMap<>();
            String error = "1 пользователь может иметь только 1 карту!";

            response.put("error", error);


            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        System.out.println(userDTO);
        System.out.println("email "+email);
        if (payment_system.equals("MS") || payment_system.equals("MIR") || payment_system.equals("VISA")) {


            if (payment_system.equals("MIR")) {
                number += "2333";


            }

            if (payment_system.equals("VISA")) {
                number += "4333";


            }

            if (payment_system.equals("MS")) {
                number += "5333";

            }
        }
         else {
                Map<Object, Object> response = new HashMap<>();
                String error = "Выберите верную платежную систему: MIR: 'MIR'; VISA:'VISA'; MASTER CARD: 'MS'.";

                response.put("error",error);


                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);        }
         for (int i=0;i<11;i++){
             number+= String.valueOf((int) (Math.random()*9));

         }
         cardDTO.setNumber(number);
         cardDTO.setBalance(123);
         cardDTO.setUserDTO(userDTO);
        Map<Object, Object> map = new HashMap<>();
        Map<Object, Object> response = new HashMap<>();


        map.put("number",cardDTO.getNumber());
        map.put("payment_system",cardDTO.getPayment_system());
        map.put("balance",cardDTO.getBalance());
        map.put("owner_card",cardDTO.getUserDTO().getEmail());
        response.put("response",map);
        cardServiseImp.cardSave(cardDTO);



        return ResponseEntity.ok(response);

    }

}

