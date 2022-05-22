package com.example.demo.Controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
//    @Autowired
//    public JwtTokenProvider jwtTokenProvider;

//
//    @GetMapping("/login")
//    public String login_get(Model model){
//        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO();
//        model.addAttribute("auth",authenticationRequestDTO);
//
//        return "main/login";
//    }
//    @PostMapping("/login")
//    public String login_post(HttpServletResponse response,HttpServletRequest request, @ModelAttribute("auth") @Valid AuthenticationRequestDTO authenticationRequestDTO, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "main/login";
//        }
//        Cookie cookie = new Cookie("JWT", "jwtTokenProvider.resolveToken(request)");//создаем объект Cookie,
//        //в конструкторе указываем значения для name и value
//        cookie.setPath("/");//устанавливаем путь
//        cookie.setMaxAge(86400);//здесь устанавливается время жизни куки
//        response.addCookie(cookie);//добавляем Cookie в запрос
//        return "redirect:/main";
//    }
//
//}
    @GetMapping("")
    public String test(){
    return "main/home";
    }
}
