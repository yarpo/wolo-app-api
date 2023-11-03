package pl.pjwstk.woloappapi.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pjwstk.woloappapi.model.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Getter
public class EventRepositoryImpl implements EventRepositoryCustom{

    private final EntityManager entityManager;

    @Override
    public List<Event> findAllByFilter(String district, LocalDate startDate,
                                       LocalDate endDate, Long category, Long organizer,
                                       Integer ageRestriction) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
            Root<Event> root = criteriaQuery.from(Event.class);

            List<Predicate> predicates = new ArrayList<>();
            if(district!=null){
                Join<Event, AddressToEvent> addressToEventJoin =
                        root.join("addressToEvent", JoinType.INNER);
                Join<AddressToEvent, Address> addressJoin =
                        addressToEventJoin.join("address", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(addressJoin.get("district"), district));
            }
            if (startDate != null) {
                Join<Event, AddressToEvent> addressToEventJoin = root.join("addressToEvent", JoinType.INNER);
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
