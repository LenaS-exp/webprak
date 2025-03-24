package ru.msu.cmc.webprak.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name = "aircrafts")
@Getter
@Setter
@ToString
@Transactional
@AllArgsConstructor
@NoArgsConstructor

public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aircraft_id",
            columnDefinition = "serial",
            insertable = false,
            updatable = false)
    private Integer id;

    @Lob
    @Column(name = "model", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String modelName;

    @Column(name = "max_range")
    private Double maxRange;

    @Column(name = "max_altitude")
    private Double maxAltitude;

    @Column(name = "speed")
    private Double cruisingSpeed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aircraft)) return false;
        Aircraft aircraft = (Aircraft) o;
        return getId().equals(aircraft.getId()) && getModelName().equals(aircraft.getModelName()) && getCruisingSpeed().equals(aircraft.getCruisingSpeed()) && getMaxAltitude().equals(aircraft.getMaxAltitude()) && getMaxRange().equals(aircraft.getMaxRange());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getModelName(), getCruisingSpeed(), getMaxAltitude(), getMaxRange());
    }

}
