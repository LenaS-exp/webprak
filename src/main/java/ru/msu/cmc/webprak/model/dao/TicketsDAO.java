package ru.msu.cmc.webprak.model.dao;


import lombok.Getter;

import ru.msu.cmc.webprak.model.entity.Tickets;

import java.util.Collection;


public interface TicketsDAO extends BaseDAO<Tickets> {
    @Getter
    class Filter {
        private String ticketStatus;
        private Double priceMax;
        private Double priceMin;
        private String fareConditions;
        private Integer passangerId;

        public static FilterBuilder builder() {
            return new FilterBuilder();
        }

        public static class FilterBuilder {
            private String ticketStatus;
            private Double priceMax;
            private Double priceMin;
            private String fareConditions;
            private Integer passangerId;

            public FilterBuilder ticketStatus(String ticketStatus) {
                this.ticketStatus = ticketStatus;
                return this;
            }

            public FilterBuilder priceMax(Double priceMax) {
                this.priceMax = priceMax;
                return this;
            }

            public FilterBuilder priceMin(Double priceMin) {
                this.priceMin = priceMin;
                return this;
            }

            public FilterBuilder fareConditions(String fareConditions) {
                this.fareConditions = fareConditions;
                return this;
            }

            public FilterBuilder passangerId(Integer passangerId) {
                this.passangerId = passangerId;
                return this;
            }

            public Filter build() {
                Filter filter = new Filter();
                filter.ticketStatus = this.ticketStatus;
                filter.priceMax = this.priceMax;
                filter.priceMin = this.priceMin;
                filter.fareConditions = this.fareConditions;
                filter.passangerId = this.passangerId;
                return filter;
            }
        }
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Tickets> getTicketsByFilter(Filter filter);
    void payTicket(Integer ticketId);
}