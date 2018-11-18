package p.dariuszmedrala.PhoneBook.models.services;


import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.dariuszmedrala.PhoneBook.models.UserSession;
import p.dariuszmedrala.PhoneBook.models.entities.ContactEntity;
import p.dariuszmedrala.PhoneBook.models.entities.UserEntity;
import p.dariuszmedrala.PhoneBook.models.forms.ContactForm;
import p.dariuszmedrala.PhoneBook.models.repositories.ContactRepository;


import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    final UserSession userSession;
    final ContactRepository contactRepository;
    @Autowired
    public ContactService(ContactRepository contactRepository, UserSession userSession){
        this.contactRepository = contactRepository;
        this.userSession = userSession;

    }

    public void addContact(ContactForm contactForm) {
        ContactEntity newContact = new ContactEntity();
        newContact.setName(contactForm.getName());
        newContact.setSurname(contactForm.getSurname());
        newContact.setNumber(contactForm.getPhoneNumber());
        newContact.setUser(userSession.getUserEntity());

        contactRepository.save(newContact);
    }

    public List<ContactEntity> getContacts(UserEntity userEntity) {
       return contactRepository.findAllContactsByLogin(userEntity.getLogin());
    }


    public List<ContactEntity> findAllContactsByUserId() {
        return contactRepository.findAllByUser_Id(userSession.getUserEntity().getId());
    }

    public void deleteContact(int id) {
        if (userSession.isLogin()) {
            if (contactRepository.findAllByUser_Id(userSession.getUserEntity().getId())
                    .stream()
                    .anyMatch(s-> s.getId() == id)) {
                contactRepository.deleteById(id);
            }
        }
    }


}
