package ru.msu.cmc.webprak.model.dao;

import lombok.Getter;

import ru.msu.cmc.webprak.model.entity.Passangers;

import java.util.Collection;


public interface PassangersDAO extends BaseDAO<Passangers> {
    @Getter
    class Filter {
        private String name;
        private String surname;
        private String address;
        private String email;
        private String phoneNumber;

        public static FilterBuilder builder() {
            return new FilterBuilder();
        }

        public static class FilterBuilder {
            private String name;
            private String surname;
            private String address;
            private String email;
            private String phoneNumber;

            public FilterBuilder name(String name) {
                this.name = name;
                return this;
            }

            public FilterBuilder surname(String surname) {
                this.surname = surname;
                return this;
            }

            public FilterBuilder address(String address) {
                this.address = address;
                return this;
            }

            public FilterBuilder email(String email) {
                this.email = email;
                return this;
            }

            public FilterBuilder phoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
                return this;
            }

            public Filter build() {
                Filter filter = new Filter();
                filter.name = this.name;
                filter.surname = this.surname;
                filter.address = this.address;
                filter.email = this.email;
                filter.phoneNumber = this.phoneNumber;
                return filter;
            }
        }
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Passangers> getPassangersByFilter(Filter filter);
    Collection<Passangers> findClientsByFilters(String flightNumber, Integer airlineId, String ticketStatus);
}