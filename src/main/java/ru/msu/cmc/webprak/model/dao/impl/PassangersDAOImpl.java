package ru.msu.cmc.webprak.model.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.msu.cmc.webprak.model.HibernateConfiguration;
import ru.msu.cmc.webprak.model.dao.PassangersDAO;
import ru.msu.cmc.webprak.model.entity.Passangers;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
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
}
