package org._3HCompany.microservices.uaa.util;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org._3HCompany.microservice.common.models.enums.Operators;
import org._3HCompany.microservice.common.models.filter.FilterMeta;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

    private FilterMeta filterMeta;

    @Override
    public Specification<T> and(Specification<T> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<T> or(Specification<T> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(
            @NonNull Root<T> root,
            @NonNull CriteriaQuery<?> criteriaQuery,
            @NonNull CriteriaBuilder criteriaBuilder) {

        String tableName = "";
        String fieldName = filterMeta.getFiledName();
        criteriaQuery.distinct(true);

        if (filterMeta.getFiledName().contains(".")) {
            tableName = filterMeta.getFiledName().split("\\.")[0];
            fieldName = filterMeta.getFiledName().split("\\.")[1];
        }

        if (filterMeta.getFiledValue() == null) {
            return tableName.isEmpty()
                    ? criteriaBuilder.isNull(root.get(fieldName))
                    : criteriaBuilder.isNull(root.get(tableName).get(fieldName));
        }

        return switch (Objects.requireNonNull(Operators.getOperator(filterMeta.getOperator()))) {

            case EQUAL -> tableName.isEmpty()
                    ? criteriaBuilder.equal(root.get(fieldName), filterMeta.getFiledValue())
                    : criteriaBuilder.equal(root.get(tableName).get(fieldName), filterMeta.getFiledValue());

            case LIKE -> tableName.isEmpty()
                    ? criteriaBuilder.like(root.get(fieldName), "%"+filterMeta.getFiledValue().toString()+"%")
                    : criteriaBuilder.like(root.get(tableName).get(fieldName), "%"+filterMeta.getFiledValue().toString()+"%");

            case NOTEQUAL -> tableName.isEmpty()
                    ? criteriaBuilder.notEqual(root.get(fieldName), filterMeta.getFiledValue())
                    : criteriaBuilder.notEqual(root.get(tableName).get(fieldName), filterMeta.getFiledValue());

            case GREATER_THAN -> tableName.isEmpty()
                    ? criteriaBuilder.greaterThan(root.get(fieldName), filterMeta.getFiledValue().toString())
                    : criteriaBuilder.greaterThan(root.get(tableName).get(fieldName), filterMeta.getFiledValue().toString());

            case LESS_THAN -> tableName.isEmpty()
                    ? criteriaBuilder.lessThan(root.get(fieldName), filterMeta.getFiledValue().toString())
                    : criteriaBuilder.lessThan(root.get(tableName).get(fieldName), filterMeta.getFiledValue().toString());

            case GREATER_THAN_EQUAL -> tableName.isEmpty()
                    ? criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), filterMeta.getFiledValue().toString())
                    : criteriaBuilder.greaterThanOrEqualTo(root.get(tableName).get(fieldName), filterMeta.getFiledValue().toString());

            case LESS_THAN_EQUAL -> tableName.isEmpty()
                    ? criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), filterMeta.getFiledValue().toString())
                    : criteriaBuilder.lessThanOrEqualTo(root.get(tableName).get(fieldName), filterMeta.getFiledValue().toString());

            case IN -> {
                List<Objects> items = (List<Objects>) filterMeta.getFiledValue();

                if (items.isEmpty())
                    yield criteriaBuilder.isNotNull(root.get("id"));

                yield tableName.isEmpty()
                        ? criteriaBuilder.and(root.get(fieldName).in(items))
                        : criteriaBuilder.and(root.get(tableName).get(fieldName).in(items));
            }
            default -> null;
        };
    }
}
