package ru.msu.cmc.webprak.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name = "airlines")
@Getter
@Setter
@ToString
@Transactional
@AllArgsConstructor
@NoArgsConstructor

public class Airlines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airline_id",
            columnDefinition = "serial",
            insertable = false,
            updatable = false)
    private Integer id;

    @Lob
    @Column(name = "airline_name", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String airlineName;

    @Column(name = "email")
    @Type(type = "org.hibernate.type.TextType")
    private String airlineEmail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airlines)) return false;
        Airlines airlines = (Airlines) o;
        return getId().equals(airlines.getId()) && getAirlineName().equals(airlines.getAirlineName()) && getAirlineEmail().equals(airlines.getAirlineEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAirlineName(), getAirlineEmail());
    }
}
