package ru.msu.cmc.webprak.model.dao;

import lombok.Getter;
import ru.msu.cmc.webprak.model.entity.Airlines;

import java.util.Collection;


public interface AirlinesDAO extends BaseDAO<Airlines> {
    @Getter
    class Filter {
        private String airlineName;
        private String airlineEmail;

        public static FilterBuilder builder() {
            return new FilterBuilder();
        }

        public static class FilterBuilder {
            private String airlineName;
            private String airlineEmail;

            public FilterBuilder airlineName(String airlineName) {
                this.airlineName = airlineName;
                return this;
            }

            public FilterBuilder airlineEmail(String airlineEmail) {
                this.airlineEmail = airlineEmail;
                return this;
            }

            public Filter build() {
                Filter filter = new Filter();
                filter.airlineName = this.airlineName;
                filter.airlineEmail = this.airlineEmail;
                return filter;
            }
        }
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Airlines> getAirlinesByFilter(Filter filter);
}

