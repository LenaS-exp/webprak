package ru.msu.cmc.webprak.model.dao;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cmc.webprak.model.entity.Tickets;

import java.util.Collection;

public interface TicketsDAO extends BaseDAO<Tickets> {
    @Builder
    @Getter
    class Filter {
        private String ticketStatus;
        private Double priceMax;
        private Double priceMin;
        private String fareConditions;
        private Integer passangerId;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Tickets> getTicketsByFilter(Filter filter);
}
