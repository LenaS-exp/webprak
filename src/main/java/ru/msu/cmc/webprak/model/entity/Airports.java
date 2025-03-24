package ru.msu.cmc.webprak.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name = "airports")
@Getter
@Setter
@ToString
@Transactional
@AllArgsConstructor
@NoArgsConstructor

public class Airports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id",
            columnDefinition = "serial",
            insertable = false,
            updatable = false)
    private Integer id;

    @Lob
    @Column(name = "airport_name", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String airportName;

    @Lob
    @Column(name = "city", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String airportCity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airports)) return false;
        Airports airports = (Airports) o;
        return getId().equals(airports.getId()) && getAirportName().equals(airports.getAirportName()) && getAirportCity().equals(airports.getAirportCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAirportName(), getAirportCity());
    }

}
