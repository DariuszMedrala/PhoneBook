package p.dariuszmedrala.PhoneBook.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "user")
@Data
public class UserEntity {
    @Id @GeneratedValue
    private int id;
    private String login;
    private String password;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ContactEntity> contacts;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
