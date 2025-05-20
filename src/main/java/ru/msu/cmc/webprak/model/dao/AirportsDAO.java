package ru.msu.cmc.webprak.model.dao;


import lombok.Getter;

import ru.msu.cmc.webprak.model.entity.Airports;

import java.util.Collection;


public interface AirportsDAO extends BaseDAO<Airports> {
    @Getter
    class Filter {
        private String airportName;
        private String airportCity;

        public static FilterBuilder builder() {
            return new FilterBuilder();
        }

        public static class FilterBuilder {
            private String airportName;
            private String airportCity;

            public FilterBuilder airportName(String airportName) {
                this.airportName = airportName;
                return this;
            }

            public FilterBuilder airportCity(String airportCity) {
                this.airportCity = airportCity;
                return this;
            }

            public Filter build() {
                Filter filter = new Filter();
                filter.airportName = this.airportName;
                filter.airportCity = this.airportCity;
                return filter;
            }
        }
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Airports> getAirportsByFilter(Filter filter);
}
