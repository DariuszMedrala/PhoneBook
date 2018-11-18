package p.dariuszmedrala.PhoneBook.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import p.dariuszmedrala.PhoneBook.models.UserSession;
import p.dariuszmedrala.PhoneBook.models.forms.ContactForm;
import p.dariuszmedrala.PhoneBook.models.services.ContactService;

import javax.validation.Valid;

@Controller
public class ContactController {

    final ContactService contactService;
    final UserSession userSession;

    @Autowired
    public ContactController(ContactService contactService, UserSession userSession) {
        this.contactService = contactService;
        this.userSession = userSession;
    }

    @GetMapping("/contact/add")
    public String showAddContactForm(Model model) {

        if (!userSession.isLogin()) {
            return "redirect:/login";
        }

        model.addAttribute("contactForm", new ContactForm());
        return "addContact";
    }

    @PostMapping("/contact/add")
    public String getDataFromAddContactForm(@ModelAttribute @Valid ContactForm contactForm,
                                            BindingResult bindingResult,
                                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("hasErrors", "Niektóre z podanych danych są niepoprawne");
            return "addContact";
        }
            contactService.addContact(contactForm);
            return "redirect:/menu";
    }

    @GetMapping("/contact/show")
    public String showAllContact(Model model){
        if(!userSession.isLogin()){
            return "redirect:/login";
        }
        model.addAttribute("contacts", contactService.findAllContactsByUserId());
        return "contactList";
    }


    @GetMapping("/contact/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") int id) {
        contactService.deleteContact(id);
        return "redirect:/contact/show";
    }

}
