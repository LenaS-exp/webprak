package ru.msu.cmc.webprak.model.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.model.HibernateConfiguration;
import ru.msu.cmc.webprak.model.dao.FlightsDAO;
import ru.msu.cmc.webprak.model.entity.Flights;
import ru.msu.cmc.webprak.utils.TimeConvertUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
@Repository
public class FlightsDAOImpl extends BaseDAOImpl<Flights> implements FlightsDAO {

    public FlightsDAOImpl() {
        super(Flights.class);
    }

    @Override
    public Collection<Flights> getFlightsByFilter(Filter filter) throws HibernateException {
        Session session = HibernateConfiguration.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Flights> criteriaQuery = builder.createQuery(Flights.class);
        Root<Flights> root = criteriaQuery.from(Flights.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getTimeDepMin() != null) {
            predicates.add(builder.greaterThan(root.get("timeDep"), filter.getTimeDepMin()));
        }
        if (filter.getTimeDepMax() != null) {
            predicates.add(builder.lessThan(root.get("timeDep"), filter.getTimeDepMax()));
        }
        if (filter.getTimeArrMin() != null) {
            predicates.add(builder.greaterThan(root.get("timeArr"), filter.getTimeArrMin()));
        }
        if (filter.getTimeArrMax() != null) {
            predicates.add(builder.lessThan(root.get("timeArr"), filter.getTimeArrMax()));
        }
        if (filter.getSeatMin() != null) {
            Integer cntSeatsMin = filter.getSeatMin();
            predicates.add(builder.le(builder.literal(cntSeatsMin), root.get("seatNum")));
        }
        if (filter.getSeatMax() != null) {
            Integer cntSeatsMax = filter.getSeatMax();
            predicates.add(builder.ge(builder.literal(cntSeatsMax), root.get("seatNum")));
        }
        if (filter.getAvailableSeatsMin() != null) {
            Integer cntAvailableSeatsMin = filter.getAvailableSeatsMin();
            predicates.add(builder.le(builder.literal(cntAvailableSeatsMin), root.get("availableSeatNum")));
        }
        if (filter.getAvailableSeatsMax() != null) {
            Integer cntAvailableSeatsMax = filter.getAvailableSeatsMax();
            predicates.add(builder.ge(builder.literal(cntAvailableSeatsMax), root.get("availableSeatNum")));
        }

        if (predicates.size() != 0)
            criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<Flights> result = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        return result;
    }
}
