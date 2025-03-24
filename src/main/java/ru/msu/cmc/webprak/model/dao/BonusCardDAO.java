package ru.msu.cmc.webprak.model.dao;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cmc.webprak.model.entity.BonusCard;

import java.util.Collection;

public interface BonusCardDAO extends BaseDAO<BonusCard> {
    @Builder
    @Getter
    class Filter {
        private Integer totalkmMin;
        private Integer totalkmMax;
        private Integer usedkmMin;
        private Integer usedkmMax;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<BonusCard> getBonusCardByFilter(Filter filter);
}
