package ru.msu.cmc.webprak.model.dao;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cmc.webprak.model.entity.Airlines;

import java.util.Collection;

public interface AirlinesDAO extends BaseDAO<Airlines>{
    @Builder
    @Getter
    class Filter {
        private String airlineName;
        private String airlineEmail;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Airlines> getAirlinesByFilter(Filter filter);
}
