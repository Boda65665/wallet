package com.example.demo.Controllers.web;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.DTO.PayDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.DTO.WalletDTO;
import com.example.demo.JWT.JwtTokenProvider;
import com.example.demo.Mail.Email;
import com.example.demo.RequestBody.Pay;
import com.example.demo.Services.OrderServiceImp;
import com.example.demo.Services.PayServiseImp;
import com.example.demo.Services.UserServiceImplement;
import com.example.demo.Services.WalletServiseImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class PayController {
    PayDTO payDTO = new PayDTO();

    public final OrderServiceImp orderServiceImp;
    public final UserServiceImplement userServiceImplement;
    public final JwtTokenProvider jwtTokenProvider;
    public  final PasswordEncoder passwordEncoder;
    public final PayServiseImp payServiseImp;
    public final WalletServiseImp walletServiseImp;
    public Email email = new Email();
    @Autowired
    public PayController(OrderServiceImp orderServiceImp, UserServiceImplement userServiceImplement, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, PayServiseImp payServiseImp, WalletServiseImp walletServiseImp) {
        this.orderServiceImp = orderServiceImp;
        this.userServiceImplement = userServiceImplement;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.payServiseImp = payServiseImp;
//        this.payServiseImp = payServiseImp;
        this.walletServiseImp = walletServiseImp;
    }
    @GetMapping("/pay")
    public String wallet(Model model, HttpServletRequest request){
        System.out.println();
        String token = jwtTokenProvider.resolveToken(request);
        UserDTO userDTO = userServiceImplement.findByEmail(jwtTokenProvider.getUsername(token));
        WalletDTO walletDTO = walletServiseImp.findByUserDTO(userDTO);
        model.addAttribute("wallet",walletDTO);
        Pay pay = new Pay();
        model.addAttribute("pay",pay);

        return "main/pay";

    }
    @PostMapping("/pay")
    public String pay(Model model,@ModelAttribute Pay pay, HttpServletRequest request) throws MessagingException, IOException {
        if(pay==null){
            return "redirect:/pay";
        }
        UserDTO userDTO = userServiceImplement.findByEmail(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request)));
        OrderDTO orderDTO = new OrderDTO();

        int getRandomValue = (int) (Math.random()*(999999-111111)) + 111111;
        String text = "Ваш код: " + getRandomValue;
        System.out.println(getRandomValue);
        orderDTO.setTo_adres(pay.getTo());
        Date date = new Date();
        orderDTO.setDate(date);
        orderDTO.setCode(getRandomValue);
        orderDTO.setFrom_adres(userDTO.getWalletDTO().getAdres());
        orderDTO.setComment(pay.getComment());
        orderDTO.setSumma(pay.getSumma());

        if(orderDTO.getTo_adres().equals(orderDTO.getFrom_adres()))
        {
            model.addAttribute("err_1","Нельзя переводить самому себе");
            return "main/pay";
        }
        if(orderDTO.getSumma()>userDTO.getWalletDTO().getBalance() || orderDTO.getSumma()<1 || orderDTO.getSumma()>100000){
            model.addAttribute("err_2","Сумма платежа не должна быть меньше 1 рубля и не больше 100к и не должна превышатьваш баланс");
            return "main/pay";

        }
        orderServiceImp.save(orderDTO);

        //Берем список плажей и берем id последнего платежа
        //Знаю выглядит не очень но по др не придумал
        List<OrderDTO> orderDTOS = orderServiceImp.findByFrom(orderDTO.getFrom_adres());
        int id = orderDTOS.get(orderDTOS.size()-1).getId();
        email.sendEmail(text,orderDTO.getFrom_adres());


        return "redirect:/PaymentConfirmation?id="+id;

    }
    @GetMapping("/PaymentConfirmation")
    public String confirmPayGet(@RequestParam(required = false,name = "send_email") String send_email,HttpServletRequest httpServletRequest,@RequestParam(required = false,name = "id") int id,Model model,HttpServletRequest request){

        try {
            OrderDTO orderDTO = orderServiceImp.findById(id);

            if(send_email!=null){
                int getRandomValue = (int) (Math.random()*(999999-111111)) + 111111;
                String text = "Ваш код: " + getRandomValue;
                email.sendEmail(text,orderDTO.getFrom_adres());
                orderDTO.setCode(getRandomValue);
                orderServiceImp.save(orderDTO);
                return "redirect:/PaymentConfirmation?id="+id;


            }
            WalletDTO walletDTO = walletServiseImp.findByAdres(orderDTO.getFrom_adres());
            UserDTO userDTO = userServiceImplement.findByEmail(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(httpServletRequest)));

            if (!walletDTO.getUser().getEmail().equals(userDTO.getEmail())) {
                return "redirect:/pay";
            }
            model.addAttribute("action", "/PaymentConfirmation?id="+id);
            model.addAttribute("id",id);

            return "main/pay_confirm";
        }
        catch (NullPointerException | IOException | MessagingException err){
            return "redirect:/pay";

        }
    }
    @PostMapping("/PaymentConfirmation")
    public String confirmPayPost(Model model,@RequestParam(required = false,name = "id") int id,HttpServletRequest request) {
        try {
            int code = Integer.parseInt(request.getParameter("code"));
            String email = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
            UserDTO userDTO = userServiceImplement.findByEmail(email);
            WalletDTO walletDTO = walletServiseImp.findByUserDTO(userDTO);
            OrderDTO orderDTO = orderServiceImp.findById(id);
            if (code == orderDTO.getCode()) {
                payDTO.setFrom_walletDTO(walletServiseImp.findByAdres(orderDTO.getFrom_adres()));
                payDTO.setTo_walletDTO(walletServiseImp.findByAdres(orderDTO.getTo_adres()));
                payDTO.setSumma(orderDTO.getSumma());
                payDTO.setComment(orderDTO.getComment());
                payServiseImp.save(payDTO);





                //Берем список плажей и берем id последнего платежа

                List<PayDTO> payDTOS = payServiseImp.findAllByFrom_wallet(walletServiseImp.findByUserDTO(userDTO));
                int id_pay = payDTOS.get(payDTOS.size()-1).getId();
                orderServiceImp.deleteById(orderDTO.getId());
                model.addAttribute("pay", payDTO);
                return "redirect:/payment_successful?id=" + id_pay;

            } else {
                model.addAttribute("incorect_code", "Неверный код");
                return "main/pay_confirm";
            }

        } catch (NumberFormatException err) {

            model.addAttribute("code_str", "Код должен быть числом");
            return "main/pay_confirm";
        }
    }



    @GetMapping("payment_successful")
    public String paymentSuccessful(@RequestParam(required = false,name = "id") int id,Model model) {
        try{
            payDTO = payServiseImp.findById(id);
            model.addAttribute("pay",payDTO);
            return "main/payment_successful";
        }
        catch (NullPointerException err){
            return "redirect:/pay";
        }
    }
}
