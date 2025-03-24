package ru.msu.cmc.webprak.model.dao;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cmc.webprak.model.entity.Flights;

import java.time.LocalDateTime;
import java.util.Collection;

public interface FlightsDAO extends BaseDAO<Flights> {
    @Builder
    @Getter
    class Filter {
        private LocalDateTime timeDepMin;
        private LocalDateTime timeDepMax;
        private LocalDateTime timeArrMin;
        private LocalDateTime timeArrMax;
        private Integer seatMin;
        private Integer seatMax;
        private Integer availableSeatsMin;
        private Integer availableSeatsMax;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Flights> getFlightsByFilter(Filter filter);
}
