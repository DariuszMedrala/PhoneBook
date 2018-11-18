package p.dariuszmedrala.PhoneBook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import p.dariuszmedrala.PhoneBook.models.UserSession;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexController {

    final UserSession userSession;
    @Autowired
    public IndexController(UserSession userSession) {
        this.userSession = userSession;
    }

    @GetMapping("/")

    public String index(){
        if(!userSession.isLogin()) {
           return "redirect:/login";
        }
        return "redirect:/menu" ;

    }
}
