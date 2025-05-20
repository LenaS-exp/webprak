package ru.msu.cmc.webprak.model.dao;


import lombok.Getter;
import ru.msu.cmc.webprak.model.entity.Flights;

import java.time.LocalDateTime;
import java.util.Collection;

public interface FlightsDAO extends BaseDAO<Flights> {
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

        public static FilterBuilder builder() {
            return new FilterBuilder();
        }

        public static class FilterBuilder {
            private LocalDateTime timeDepMin;
            private LocalDateTime timeDepMax;
            private LocalDateTime timeArrMin;
            private LocalDateTime timeArrMax;
            private Integer seatMin;
            private Integer seatMax;
            private Integer availableSeatsMin;
            private Integer availableSeatsMax;

            public FilterBuilder timeDepMin(LocalDateTime timeDepMin) {
                this.timeDepMin = timeDepMin;
                return this;
            }

            public FilterBuilder timeDepMax(LocalDateTime timeDepMax) {
                this.timeDepMax = timeDepMax;
                return this;
            }

            public FilterBuilder timeArrMin(LocalDateTime timeArrMin) {
                this.timeArrMin = timeArrMin;
                return this;
            }

            public FilterBuilder timeArrMax(LocalDateTime timeArrMax) {
                this.timeArrMax = timeArrMax;
                return this;
            }

            public FilterBuilder seatMin(Integer seatMin) {
                this.seatMin = seatMin;
                return this;
            }

            public FilterBuilder seatMax(Integer seatMax) {
                this.seatMax = seatMax;
                return this;
            }

            public FilterBuilder availableSeatsMin(Integer availableSeatsMin) {
                this.availableSeatsMin = availableSeatsMin;
                return this;
            }

            public FilterBuilder availableSeatsMax(Integer availableSeatsMax) {
                this.availableSeatsMax = availableSeatsMax;
                return this;
            }

            public Filter build() {
                Filter filter = new Filter();
                filter.timeDepMin = this.timeDepMin;
                filter.timeDepMax = this.timeDepMax;
                filter.timeArrMin = this.timeArrMin;
                filter.timeArrMax = this.timeArrMax;
                filter.seatMin = this.seatMin;
                filter.seatMax = this.seatMax;
                filter.availableSeatsMin = this.availableSeatsMin;
                filter.availableSeatsMax = this.availableSeatsMax;
                return filter;
            }
        }
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Flights> getFlightsByFilter(Filter filter);
}