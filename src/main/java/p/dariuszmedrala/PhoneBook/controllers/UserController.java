package p.dariuszmedrala.PhoneBook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import p.dariuszmedrala.PhoneBook.models.UserSession;
import p.dariuszmedrala.PhoneBook.models.forms.LoginForm;
import p.dariuszmedrala.PhoneBook.models.forms.UserForm;
import p.dariuszmedrala.PhoneBook.models.services.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    final UserService userService;
    final UserSession userSession;

    @Autowired
    public UserController(UserService userService, UserSession userSession) {
        this.userService = userService;
        this.userSession = userSession;
    }

    @GetMapping("/registration/add")
    public String showAddRegistrationForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "registration";
    }

    @PostMapping("/registration/add")
    public String addNewRegistrationEntity(@ModelAttribute @Valid UserForm userForm,
                                           BindingResult bindingResult,
                                           Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("registerInfo", "Błędne dane");
            return "registration";
        }

        if (userService.doesLoginExist(userForm.getLogin())) {
            model.addAttribute("isLoginTaken", true);
            return "registration";
        }
            userService.addRegistration(userForm);
            return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }
    @PostMapping("/login")
    public String loginData(@ModelAttribute LoginForm loginForm, Model model) {
        if (userService.tryLogin(loginForm)) {
            return "redirect:/menu";
        }

        model.addAttribute("loginInfo", "Login or password are incorrect");
        return "login";

    }
    @GetMapping("/menu")
    public String mainMenu() {
        if (!userSession.isLogin()) {
            return "redirect:/login";
        }
        return "usermenu";
    }
    @GetMapping("/logout")
    public String logout() {
        userSession.setLogin(false);
        return "redirect:/login";
    }
}
