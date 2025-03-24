package ru.msu.cmc.webprak.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@ToString
@Transactional
@AllArgsConstructor
@NoArgsConstructor

public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id",
            columnDefinition = "serial",
            insertable = false,
            updatable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    @ToString.Exclude
    private Flights flightId;

    @Column(name = "status", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String ticketStatus;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "passenger_id", nullable = false)
    @ToString.Exclude
    private Passangers passangerId;

    @Column(name = "fare_conditions", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String fareConditions;

    @Column(name = "price", nullable = false)
    private Double ticketPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tickets)) return false;
        Tickets tickets = (Tickets) o;
        return getId().equals(tickets.getId()) && getFlightId().equals(tickets.getFlightId()) && getTicketStatus().equals(tickets.getTicketStatus()) && getPassangerId().equals(tickets.getPassangerId()) && getFareConditions().equals(tickets.getFareConditions()) && getTicketPrice().equals(tickets.getTicketPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFlightId(), getTicketStatus(), getPassangerId(), getFareConditions(), getTicketPrice());
    }
}
