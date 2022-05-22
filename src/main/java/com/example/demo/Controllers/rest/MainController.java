package com.example.demo.Controllers.rest;

import com.example.demo.DTO.PayDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.DTO.WalletDTO;
import com.example.demo.JWT.JwtTokenProvider;

import com.example.demo.RequestBody.Histoire;
import com.example.demo.RequestBody.Pay;
import com.example.demo.Services.PayServiseImp;
import com.example.demo.Services.UserService;
import com.example.demo.Services.WalletServiseImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("api/bank")
@RestController
public class MainController {
    public final JwtTokenProvider jwtTokenProvider;

    public  final PayServiseImp payServiseImp;
    public  final UserService userService;
    public final WalletServiseImp walletServiseImp;



    @Autowired
    public MainController(JwtTokenProvider jwtTokenProvider, PayServiseImp payServiseImp, UserService userService, WalletServiseImp walletServiseImp) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.payServiseImp = payServiseImp;
        this.userService = userService;
        this.walletServiseImp = walletServiseImp;
    }

    @GetMapping("my_wallet")
    public ResponseEntity<?> my_wallet(@RequestHeader Map<String, String> headers) {
        String email = jwtTokenProvider.getUsername(headers.get("authorization"));
        UserDTO userDTO = userService.findByEmail(email);
        WalletDTO walletDTO = walletServiseImp.findByUserDTO(userDTO);
        Map<Object, Object> response = new HashMap<>();
        Map<Object, Object> wallet = new HashMap<>();
        wallet.put("adres", walletDTO.getAdres());
        wallet.put("balance", walletDTO.getBalance());
        response.put("response", wallet);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@Valid @RequestBody Pay pay,BindingResult bindingResult, @RequestHeader Map<String, String> headers) {
        String email = jwtTokenProvider.getUsername(headers.get("authorization"));
        UserDTO userDTO = userService.findByEmail(email);
        WalletDTO walletDTO = walletServiseImp.findByUserDTO(userDTO);
        PayDTO payDTO = new PayDTO();
        //Validation
            if(bindingResult.hasFieldErrors("to")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wallet address field must not be empty\n!");

            }
            if(bindingResult.hasFieldErrors("summa")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can send from 1 to 100000r!");



        }

        String comment =  pay.getComment();
        String to_adres = pay.getTo();
        int summa = pay.getSumma();









        if (to_adres.equals(walletDTO.getAdres())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You cannot send money to yourself!");

        }
        if (summa > walletDTO.getBalance()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You don't have enough funds!");
        }

            try {
                payDTO.setSumma(summa);
                payDTO.setComment(comment);
                WalletDTO to_wallet = walletServiseImp.findByAdres(to_adres);
                payDTO.setTo_walletDTO(to_wallet);
                payDTO.setFrom_walletDTO(walletDTO);
                payServiseImp.save(payDTO);
                walletDTO.setBalance(walletDTO.getBalance()-summa);
                to_wallet.setBalance(to_wallet.getBalance()+summa);
                walletServiseImp.save(walletDTO);
                walletServiseImp.save(to_wallet);

                return ResponseEntity.status(HttpStatus.CREATED).body("Payment sent successfully!");
            }
            catch (NullPointerException error){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No wallet found with this address!");
            }

    }
    @PostMapping("/histoire")
    public ResponseEntity<?> histoire(HttpServletRequest request, @Valid @RequestBody Histoire histoire, BindingResult bindingResult, @RequestHeader Map<String, String> headers){
        //Number of Payments
        String email = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        UserDTO userDTO = userService.findByEmail(email);
        WalletDTO walletDTO = walletServiseImp.findByUserDTO(userDTO);
        List<PayDTO> payDTOS;
        //Validation
        if (bindingResult.hasFieldErrors("rows")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("rows must be at least 1!");

        }
        int rows = histoire.getRows();

        //    in/out//all
        String operation = histoire.getOperation();
        if ("in".equals(operation)) {
            payDTOS = payServiseImp.findAllByFrom_wallet(walletDTO);
        } else if ("out".equals(operation)) {
            payDTOS = payServiseImp.findAllByTo_wallet(walletDTO);
        }
        else {
            List<PayDTO> pay_from = payServiseImp.findAllByFrom_wallet(walletDTO);
            List<PayDTO> pay_to = payServiseImp.findAllByTo_wallet(walletDTO);
            boolean pay_fromEmpty = (pay_from == null) || pay_from.isEmpty();
            boolean pay_toEmpty = (pay_to == null) || pay_to.isEmpty();
            if (pay_fromEmpty & pay_toEmpty) {

                payDTOS = new ArrayList<>();
            } else if (pay_fromEmpty) {
                payDTOS = pay_to;
            } else if (pay_toEmpty) {
                payDTOS = pay_from;
            } else {
                // оба непустые — объединяем
                ArrayList<PayDTO> result = new ArrayList<>(
                        pay_from.size() + pay_to.size());
                result.addAll(pay_from);
                result.addAll(pay_to);
                payDTOS = result;
            }
        }
        if (rows>payDTOS.size() || rows>100){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The rows value must not exceed the number of your transactions and must not exceed 100!");

        }
        List<Map<?,?>> payList = new ArrayList<>();
        Map<Object, Object> pay;
        Map<String, Object> responce;
        int end_index = payDTOS.size()-rows;
        int start_index = payDTOS.size()-1;
        for (int i = start_index;i>=end_index;i--) {
            pay  = new HashMap<>();
            responce = new HashMap<>();
            pay.put("summa",payDTOS.get(i).getSumma());
            pay.put("comment",payDTOS.get(i).getComment());
            pay.put("id",payDTOS.get(i).getId());
            pay.put("for",payDTOS.get(i).getFrom_walletDTO().getAdres());
            pay.put("to",payDTOS.get(i).getTo_walletDTO().getAdres());
            responce.put("response",pay);
            payList.add(responce);

        }






        return ResponseEntity.ok(payList);
    }


}
