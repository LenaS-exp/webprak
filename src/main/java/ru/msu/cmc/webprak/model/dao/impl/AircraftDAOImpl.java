package ru.msu.cmc.webprak.model.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.msu.cmc.webprak.model.HibernateConfiguration;
import ru.msu.cmc.webprak.model.dao.AircraftDAO;
import ru.msu.cmc.webprak.model.entity.Aircraft;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
public class AircraftDAOImpl extends BaseDAOImpl<Aircraft> implements AircraftDAO {

    public AircraftDAOImpl() {
        super(Aircraft.class);
    }

    @Override
    public Collection<Aircraft> getAircraftByFilter(Filter filter) throws HibernateException {
        Session session = HibernateConfiguration.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Aircraft> criteriaQuery = builder.createQuery(Aircraft.class);
        Root<Aircraft> root = criteriaQuery.from(Aircraft.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.getModelName() != null) {
            String pattern = "%" + filter.getModelName() + "%";
            predicates.add(builder.like(root.get("modelName"), pattern));
        }

        if (filter.getMaxRange() != null) {
            Double maxRange = filter.getMaxRange();
            predicates.add(builder.le(builder.literal(maxRange), root.get("maxRange")));
        }

        if (filter.getMaxAltitude() != null) {
            Double altitude = filter.getMaxAltitude();
            predicates.add(builder.le(builder.literal(altitude), root.get("maxAltitude")));
        }

        if (filter.getCruisingSpeed() != null) {
            Double speed = filter.getCruisingSpeed();
            predicates.add(builder.le(builder.literal(speed), root.get("cruisingSpeed")));
        }

        if (predicates.size() != 0)
            criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<Aircraft> result = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        return result;
    }
}
