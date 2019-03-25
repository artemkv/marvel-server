package net.artemkv.marvelserver.specifications;

import net.artemkv.marvelserver.domain.CreatorModel;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class CreatorWithModifiedDate implements Specification<CreatorModel> {
    private Date modified;

    public CreatorWithModifiedDate(Date modified) {
        this.modified = modified;
    }

    @Override
    public Predicate toPredicate(
        Root<CreatorModel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        if (modified == null) {
            return cb.isTrue(cb.literal(true)); // always true = no filtering
        }
        return cb.greaterThan(root.get("modified"), modified);
    }
}
