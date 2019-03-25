package net.artemkv.marvelserver.specifications;

import net.artemkv.marvelserver.domain.CreatorModel;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CreatorWithFullName implements Specification<CreatorModel> {
    private String fullName;

    public CreatorWithFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public Predicate toPredicate(
        Root<CreatorModel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        if (fullName == null || fullName.trim().length() == 0) {
            return cb.isTrue(cb.literal(true)); // always true = no filtering
        }
        return cb.like(
            cb.lower(root.get("fullName")),
            "%" + fullName.toLowerCase() + "%");
    }
}
