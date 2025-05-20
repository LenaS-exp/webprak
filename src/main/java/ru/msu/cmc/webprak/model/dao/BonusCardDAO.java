package ru.msu.cmc.webprak.model.dao;


import lombok.Getter;

import ru.msu.cmc.webprak.model.entity.BonusCard;

import java.util.Collection;


public interface BonusCardDAO extends BaseDAO<BonusCard> {
    @Getter
    class Filter {
        private Integer totalkmMin;
        private Integer totalkmMax;
        private Integer usedkmMin;
        private Integer usedkmMax;
        private Integer userId;

        public static FilterBuilder builder() {
            return new FilterBuilder();
        }

        public static class FilterBuilder {
            private Integer totalkmMin;
            private Integer totalkmMax;
            private Integer usedkmMin;
            private Integer usedkmMax;
            private Integer userId;

            public FilterBuilder totalkmMin(Integer totalkmMin) {
                this.totalkmMin = totalkmMin;
                return this;
            }

            public FilterBuilder totalkmMax(Integer totalkmMax) {
                this.totalkmMax = totalkmMax;
                return this;
            }

            public FilterBuilder usedkmMin(Integer usedkmMin) {
                this.usedkmMin = usedkmMin;
                return this;
            }

            public FilterBuilder usedkmMax(Integer usedkmMax) {
                this.usedkmMax = usedkmMax;
                return this;
            }

            public FilterBuilder userId(Integer userId) {
                this.userId = userId;
                return this;
            }

            public Filter build() {
                Filter filter = new Filter();
                filter.totalkmMin = this.totalkmMin;
                filter.totalkmMax = this.totalkmMax;
                filter.usedkmMin = this.usedkmMin;
                filter.usedkmMax = this.usedkmMax;
                filter.userId = this.userId;
                return filter;
            }
        }
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<BonusCard> getBonusCardByFilter(Filter filter);
}