package ru.msu.cmc.webprak.model.entity;

import lombok.*;
import ru.msu.cmc.webprak.model.entity.Aircraft;
import ru.msu.cmc.webprak.model.entity.Airlines;
import ru.msu.cmc.webprak.model.entity.Airports;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name = "flights")
@Getter
@Setter
@ToString
@Transactional
@AllArgsConstructor
@NoArgsConstructor

public class Flights {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id",
            columnDefinition = "serial",
            insertable = false,
            updatable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "airline_id", nullable = false)
    @ToString.Exclude
    private Airlines airlineId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "departure_airport", nullable = false)
    @ToString.Exclude
    private Airports DepAirport;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "arrival_airport", nullable = false)
    @ToString.Exclude
    private Airports ArrAirport;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "aircraft_id", nullable = false)
    @ToString.Exclude
    private Aircraft aircraftId;

    @Column(name = "scheduled_departure", nullable = false)
    private LocalDateTime timeDep;

    @Column(name = "scheduled_arrival", nullable = false)
    private LocalDateTime timeArr;

    @Column(name = "seat_num", nullable = false)
    private Integer seatNum;

    @Column(name = "available_seat_num", nullable = false)
    private Integer availableSeatNum;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flights)) return false;
        Flights flights = (Flights) o;
        return getId().equals(flights.getId()) && getAirlineId().equals(flights.getAirlineId()) && getDepAirport().equals(flights.getDepAirport()) && getArrAirport().equals(flights.getArrAirport()) && getAircraftId().equals(flights.getAircraftId()) && getTimeDep().equals(flights.getTimeDep()) && getTimeArr().equals(flights.getTimeArr()) && getSeatNum().equals(flights.getSeatNum()) && getAvailableSeatNum().equals(flights.getAvailableSeatNum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAirlineId(), getDepAirport(), getArrAirport(), getAircraftId(), getTimeDep(), getTimeArr(), getSeatNum(), getAvailableSeatNum());
    }

}
