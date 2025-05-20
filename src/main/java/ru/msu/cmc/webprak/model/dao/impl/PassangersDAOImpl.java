package ru.msu.cmc.webprak.model.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.model.HibernateConfiguration;
import ru.msu.cmc.webprak.model.dao.PassangersDAO;
import ru.msu.cmc.webprak.model.entity.Airlines;
import ru.msu.cmc.webprak.model.entity.Flights;
import ru.msu.cmc.webprak.model.entity.Passangers;
import ru.msu.cmc.webprak.model.entity.Tickets;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
@Repository
public class PassangersDAOImpl extends BaseDAOImpl<Passangers> implements PassangersDAO {

    public PassangersDAOImpl() {
        super(Passangers.class);
    }

    @Override
    public Collection<Passangers> getPassangersByFilter(Filter filter) throws HibernateException {
        Session session = HibernateConfiguration.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Passangers> criteriaQuery = builder.createQuery(Passangers.class);
        Root<Passangers> root = criteriaQuery.from(Passangers.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getName() != null) {
            String pattern = "%" + filter.getName() + "%";
            predicates.add(builder.like(root.get("name"), pattern));
        }
        if (filter.getSurname() != null) {
            String pattern = "%" + filter.getSurname() + "%";
            predicates.add(builder.like(root.get("surname"), pattern));
        }
        if (filter.getAddress() != null) {
            String pattern = "%" + filter.getAddress() + "%";
            predicates.add(builder.like(root.get("address"), pattern));
        }
        if (filter.getEmail() != null) {
            String pattern = "%" + filter.getEmail() + "%";
            predicates.add(builder.like(root.get("email"), pattern));
        }
        if (filter.getPhoneNumber() != null) {
            String pattern = "%" + filter.getPhoneNumber() + "%";
            predicates.add(builder.like(root.get("phoneNumber"), pattern));
        }

        if (predicates.size() != 0)
            criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<Passangers> result = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        return result;
    }
    @Override
    public Collection<Passangers> findClientsByFilters(String flightNumber, Integer airlineId, String ticketStatus) {
        Session session = HibernateConfiguration.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Passangers> query = builder.createQuery(Passangers.class);

        Root<Tickets> ticketRoot = query.from(Tickets.class);
        Join<Tickets, Passangers> passengerJoin = ticketRoot.join("passangerId");
        Join<Tickets, Flights> flightJoin = ticketRoot.join("flightId");

        List<Predicate> predicates = new ArrayList<>();

        if (flightNumber != null && !flightNumber.isEmpty()) {
            try {
                Integer flightId = Integer.parseInt(flightNumber);
                predicates.add(builder.equal(flightJoin.get("id"), flightId));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Номер рейса должен быть числом");
            }
        }

        if (airlineId != null) {
            Join<Flights, Airlines> airlineJoin = flightJoin.join("airlineId");
            predicates.add(builder.equal(airlineJoin.get("id"), airlineId));
        }

        if (ticketStatus != null && !ticketStatus.isEmpty()) {
            predicates.add(builder.equal(ticketRoot.get("ticketStatus"), ticketStatus));
        }

        query.select(passengerJoin);

        if (!predicates.isEmpty()) {
            query.where(builder.and(predicates.toArray(new Predicate[0])));
        }

        query.distinct(true);
        Collection<Passangers> result =  session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return result;
    }
}
