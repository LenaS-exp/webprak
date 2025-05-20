package ru.msu.cmc.webprak.model.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.model.HibernateConfiguration;
import ru.msu.cmc.webprak.model.dao.TicketsDAO;
import ru.msu.cmc.webprak.model.entity.Tickets;

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
public class TicketsDAOImpl extends BaseDAOImpl<Tickets> implements TicketsDAO {

    public TicketsDAOImpl() {
        super(Tickets.class);
    }

    @Override
    public void payTicket(Integer ticketId) {
        Session session = HibernateConfiguration.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Tickets ticket = session.get(Tickets.class, ticketId);
        if (ticket != null) {
            ticket.setTicketStatus("PAID");
            session.update(ticket);
        }
        session.getTransaction().commit();
    }

    @Override
    public Collection<Tickets> getTicketsByFilter(Filter filter) throws HibernateException {
        Session session = HibernateConfiguration.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Tickets> criteriaQuery = builder.createQuery(Tickets.class);
        Root<Tickets> root = criteriaQuery.from(Tickets.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.getTicketStatus() != null) {
            String pattern = "%" + filter.getTicketStatus() + "%";
            predicates.add(builder.like(root.get("ticketStatus"), pattern));
        }
        if (filter.getFareConditions() != null) {
            String pattern = "%" + filter.getFareConditions() + "%";
            predicates.add(builder.like(root.get("fareConditions"), pattern));
        }

        if (filter.getPassangerId() != null) {
            Integer passangerId = filter.getPassangerId();
            predicates.add(builder.equal(root.get("passangerId"), passangerId));
        }

        if (filter.getPriceMin() != null) {
            Double priceMin = filter.getPriceMin();
            predicates.add(builder.le(builder.literal(priceMin), root.get("ticketPrice")));
        }

        if (filter.getPriceMax() != null) {
            Double priceMax = filter.getPriceMax();
            predicates.add(builder.ge(builder.literal(priceMax), root.get("ticketPrice")));
        }

        if (predicates.size() != 0)
            criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<Tickets> result = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        return result;
    }
}
