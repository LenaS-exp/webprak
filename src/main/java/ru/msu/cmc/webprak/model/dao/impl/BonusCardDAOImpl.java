package ru.msu.cmc.webprak.model.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.msu.cmc.webprak.model.HibernateConfiguration;
import ru.msu.cmc.webprak.model.dao.BonusCardDAO;
import ru.msu.cmc.webprak.model.entity.BonusCard;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
public class BonusCardDAOImpl extends BaseDAOImpl<BonusCard> implements BonusCardDAO {

    public BonusCardDAOImpl() {
        super(BonusCard.class);
    }

    @Override
    public Collection<BonusCard> getBonusCardByFilter(Filter filter) throws HibernateException {
        Session session = HibernateConfiguration.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<BonusCard> criteriaQuery = builder.createQuery(BonusCard.class);
        Root<BonusCard> root = criteriaQuery.from(BonusCard.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getTotalkmMin() != null) {
            Integer totalkmMin = filter.getTotalkmMin();
            predicates.add(builder.le(builder.literal(totalkmMin), root.get("totalkm")));
        }
        if (filter.getTotalkmMax() != null) {
            Integer totalkmMax = filter.getTotalkmMax();
            predicates.add(builder.ge(builder.literal(totalkmMax), root.get("totalkm")));
        }
        if (filter.getUsedkmMin() != null) {
            Integer usedkmMin = filter.getUsedkmMin();
            predicates.add(builder.le(builder.literal(usedkmMin), root.get("usedkm")));
        }
        if (filter.getUsedkmMax() != null) {
            Integer usedkmMax = filter.getUsedkmMax();
            predicates.add(builder.ge(builder.literal(usedkmMax), root.get("usedkm")));
        }

        if (predicates.size() != 0)
            criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<BonusCard> result = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        return result;
    }
}
