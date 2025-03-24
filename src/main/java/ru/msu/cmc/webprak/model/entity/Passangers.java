package ru.msu.cmc.webprak.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name = "passangers")
@Getter
@Setter
@ToString
@Transactional
@AllArgsConstructor
@NoArgsConstructor

public class Passangers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passanger_id",
            columnDefinition = "serial",
            insertable = false,
            updatable = false)
    private Integer id;

    @Lob
    @Column(name = "passanger_name", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Lob
    @Column(name = "passanger_surname", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String surname;

    @Lob
    @Column(name = "address")
    @Type(type = "org.hibernate.type.TextType")
    private String address;

    @Lob
    @Column(name = "email", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String email;

    @Lob
    @Column(name = "phone_number", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String phoneNumber;

    @Lob
    @Column(name = "password_hash", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passangers)) return false;
        Passangers Passangers = (Passangers) o;
        return getId().equals(Passangers.getId()) && getName().equals(Passangers.getName()) && getSurname().equals(Passangers.getSurname()) && getAddress().equals(Passangers.getAddress()) && getEmail().equals(Passangers.getEmail()) && getPhoneNumber().equals(Passangers.getPhoneNumber()) && getPassword().equals(Passangers.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getAddress(), getEmail(), getPhoneNumber(), getPassword());
    }
}
