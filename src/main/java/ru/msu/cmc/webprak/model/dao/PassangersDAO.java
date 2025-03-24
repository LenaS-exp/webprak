package ru.msu.cmc.webprak.model.dao;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cmc.webprak.model.entity.Passangers;

import java.util.Collection;

public interface PassangersDAO extends BaseDAO<Passangers> {
    @Builder
    @Getter
    class Filter {

        private String name;
        private String surname;
        private String address;
        private String email;
        private String phoneNumber;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }

    Collection<Passangers> getPassangersByFilter(Filter filter);
}
