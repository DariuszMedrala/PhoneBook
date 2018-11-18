package p.dariuszmedrala.PhoneBook.models.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import p.dariuszmedrala.PhoneBook.models.entities.ContactEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends CrudRepository<ContactEntity, Integer> {
    @Query(nativeQuery = true, value = "SELECT contact.name, contact.surname, contact.phonenumber FROM contact JOIN user ON contact.user_id = user.id WHERE login = ?1 ")
    List<ContactEntity> findAllContactsByLogin(String login);
    boolean existsBySurname(String surname);
    List<ContactEntity> findAllByUser_Id(int id);

}
