package ru.msu.cmc.webprak.model.dao;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cmc.webprak.model.entity.Aircraft;

import java.util.Collection;

public interface AircraftDAO extends BaseDAO<Aircraft> {
    @Builder
    @Getter
    class Filter {
        private String modelName;
        private Double maxRange;
        private Double maxAltitude;
        private Double cruisingSpeed;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Aircraft> getAircraftByFilter(Filter filter);
}
