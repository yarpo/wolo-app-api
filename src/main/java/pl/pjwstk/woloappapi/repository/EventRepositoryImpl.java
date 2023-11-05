package pl.pjwstk.woloappapi.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Getter
public class EventRepositoryImpl implements EventRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Event> findAllByFilter(String localization, LocalDate startDate,
                                       LocalDate endDate, Long category, Long organizer,
                                       Integer ageRestriction) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
            Root<Event> root = criteriaQuery.from(Event.class);

            List<Predicate> predicates = new ArrayList<>();
            if(localization!=null){
                Join<Event, AddressToEvent> addressToEventJoin =
                        root.join("addressToEvents", JoinType.INNER);
                Join<AddressToEvent, Address> addressJoin =
                        addressToEventJoin.join("address", JoinType.INNER);
                Join<Address, District> districtJoin =
                        addressJoin.join("district", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(districtJoin.get("name"), localization));
            }
            if (startDate != null) {
                Join<Event, AddressToEvent> addressToEventJoin = root.join("addressToEvents", JoinType.INNER);
                Join<AddressToEvent, Shift> shiftJoin = addressToEventJoin.join("shift", JoinType.INNER);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(shiftJoin.get("date"), startDate));
            }

            if (endDate != null) {
                Join<Event, AddressToEvent> addressToEventJoin = root.join("addressToEvent", JoinType.INNER);
                Join<AddressToEvent, Shift> shiftJoin = addressToEventJoin.join("shift", JoinType.INNER);
                predicates.add(criteriaBuilder.lessThanOrEqualTo(shiftJoin.get("date"), endDate));
            }

            if (category != null) {
                Join<Event, Category> categoryJoin = root.join("category", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(categoryJoin.get("id"), category));
            }

            if (organizer != null) {
                Join<Event, Organisation> organisationJoin = root.join("organisation", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(organisationJoin.get("id"), organizer));
            }

            if (ageRestriction != null) {
                Join<Event, AddressToEvent> addressToEventJoin = root.join("addressToEvent", JoinType.INNER);
                Join<AddressToEvent, Shift> shiftJoin = addressToEventJoin.join("shift", JoinType.INNER);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(shiftJoin.get("requiredMinAge"), ageRestriction));
            }

            criteriaQuery.select(root).distinct(true);
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

            TypedQuery<Event> query = entityManager.createQuery(criteriaQuery);
            return query.getResultList();

        } else {
            throw new RuntimeException("EntityManager is null. Unable to create query.");
        }
    }




}
