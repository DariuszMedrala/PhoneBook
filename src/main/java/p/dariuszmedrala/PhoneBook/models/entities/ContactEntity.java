package p.dariuszmedrala.PhoneBook.models.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "contact")
public class ContactEntity {
    @Id @GeneratedValue
    private int id;
    private String name;
    private String surname;
    @Column (name = "phonenumber")
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
