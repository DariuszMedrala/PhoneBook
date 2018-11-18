package p.dariuszmedrala.PhoneBook.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.dariuszmedrala.PhoneBook.models.UserSession;
import p.dariuszmedrala.PhoneBook.models.entities.UserEntity;
import p.dariuszmedrala.PhoneBook.models.forms.LoginForm;
import p.dariuszmedrala.PhoneBook.models.forms.UserForm;
import p.dariuszmedrala.PhoneBook.models.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    final UserRepository userRepository;
    final UserSession userSession;
    final PasswordHashingService passwordHashingService;
    @Autowired
    public UserService(UserRepository userRepository,
                       UserSession userSession,
                       PasswordHashingService passwordHashingService) {
        this.userRepository = userRepository;
        this.userSession = userSession;
        this.passwordHashingService = passwordHashingService;
    }
    public void addRegistration(UserForm userForm) {
        UserEntity newRegistration = new UserEntity();
        newRegistration.setLogin(userForm.getLogin());
        newRegistration.setPassword(passwordHashingService.hash(userForm.getPassword()));

        userRepository.save(newRegistration);
    }

    public boolean doesLoginExist(String login) {
        return userRepository.existsByLogin(login);
    }

    public boolean tryLogin(LoginForm loginForm) {
      Optional<UserEntity> userOptional = userRepository.getUserByLogin(loginForm.getLogin());
      if (userOptional.isPresent()) {
          if(passwordHashingService.match(loginForm.getPassword(), userOptional.get().getPassword())) {
              userSession.setLogin(true);
              userSession.setUserEntity(userOptional.get());
          }
      }

      return userSession.isLogin();
    }

}
