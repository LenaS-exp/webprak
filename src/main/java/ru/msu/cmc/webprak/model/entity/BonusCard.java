package ru.msu.cmc.webprak.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;

import java.util.Objects;


@Entity
@Table(name = "bonuscard")
@Getter
@Setter
@ToString
@Transactional
@AllArgsConstructor
@NoArgsConstructor

public class BonusCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bonus_card_id",
            columnDefinition = "serial",
            insertable = false,
            updatable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "passanger_id", nullable = false)
    @ToString.Exclude
    private Passangers passangerId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "airline_id", nullable = false)
    @ToString.Exclude
    private Airlines airlineId;

    @Column(name = "status", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String cardStatus;

    @Column(name = "total_km", nullable = false)
    private Integer totalkm;

    @Column(name = "used_km", nullable = false)
    private Integer usedkm;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BonusCard)) return false;
        BonusCard that = (BonusCard) o;
        return getId().equals(that.getId()) && getPassangerId().equals(that.getPassangerId()) && getAirlineId().equals(that.getAirlineId()) && getTotalkm().equals(that.getTotalkm()) && getUsedkm().equals(that.getUsedkm());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPassangerId(), getAirlineId(), getTotalkm(), getUsedkm());
    }

}
