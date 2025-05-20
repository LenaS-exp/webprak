package ru.msu.cmc.webprak.model.dao;


import lombok.Getter;
import ru.msu.cmc.webprak.model.entity.Aircraft;

import java.util.Collection;

public interface AircraftDAO extends BaseDAO<Aircraft> {
    @Getter
    class Filter {
        private String modelName;
        private Double maxRange;
        private Double maxAltitude;
        private Double cruisingSpeed;

        public static FilterBuilder builder() {
            return new FilterBuilder();
        }

        public static class FilterBuilder {
            private String modelName;
            private Double maxRange;
            private Double maxAltitude;
            private Double cruisingSpeed;

            public FilterBuilder modelName(String modelName) {
                this.modelName = modelName;
                return this;
            }

            public FilterBuilder maxRange(Double maxRange) {
                this.maxRange = maxRange;
                return this;
            }

            public FilterBuilder maxAltitude(Double maxAltitude) {
                this.maxAltitude = maxAltitude;
                return this;
            }

            public FilterBuilder cruisingSpeed(Double cruisingSpeed) {
                this.cruisingSpeed = cruisingSpeed;
                return this;
            }

            public Filter build() {
                Filter filter = new Filter();
                filter.modelName = this.modelName;
                filter.maxRange = this.maxRange;
                filter.maxAltitude = this.maxAltitude;
                filter.cruisingSpeed = this.cruisingSpeed;
                return filter;
            }
        }
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Aircraft> getAircraftByFilter(Filter filter);
}