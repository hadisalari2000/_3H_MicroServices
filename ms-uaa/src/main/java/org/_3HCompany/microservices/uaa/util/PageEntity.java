package org._3HCompany.microservices.uaa.util;

import org._3HCompany.microservice.common.models.enums.Operators;
import org._3HCompany.microservice.common.models.filter.FilterMeta;
import org._3HCompany.microservice.common.models.filter.SortMeta;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class PageEntity<T> {

    protected Pageable createPageable(Integer page, Integer pageSize) {
        pageSize = pageSize != null ? pageSize : 10;
        return page != null ? PageRequest.of(page - 1, pageSize, Sort.by("id").ascending()) : null;
    }

    protected Pageable createPageable(Integer page, Integer pageSize, Sort sort) {
        pageSize = pageSize != null ? pageSize : 10;
        return page != null ? PageRequest.of(page - 1, pageSize, sort) : null;
    }

    protected Specification<T> generateSearch(List<FilterMeta> filterMetaList)  {

        List<SearchSpecification> specList = filterMetaList.stream()
                .map((Function<FilterMeta, SearchSpecification>) SearchSpecification::new)
                .collect(Collectors.toList());

        return andSpecification(specList).orElseThrow(() -> new IllegalArgumentException("No criteria provided"));
    }

    private Optional<Specification<T>> andSpecification(List<SearchSpecification> searchSpecifications){
        Iterator<SearchSpecification> itr = searchSpecifications.iterator();
        if (itr.hasNext()) {
            Specification<T> spec = Specification.where(itr.next());
            while (itr.hasNext()) {
                spec = spec.and(itr.next());
            }
            return Optional.of(spec);
        }
        return Optional.empty();
    }

    public Sort generateSort(List<SortMeta> sortMetas){
        List<Sort> sortList=generateSortList(sortMetas);
        return andSort(sortList).orElse(Sort.unsorted());
    }

    private Optional<Sort> andSort(List<Sort> sorts){
        Iterator<Sort> itr = sorts.iterator();
        if (itr.hasNext()) {
            Sort sort = (itr.next());
            while (itr.hasNext()) {
                sort = sort.and(itr.next());
            }
            return Optional.of(sort);
        }
        return Optional.empty();
    }

    private List<Sort> generateSortList(List<SortMeta> sortMetas) {
        return sortMetas.stream().map((sortMeta)
                -> switch (Objects.requireNonNull(Operators.getOperator(sortMeta.getDirection()))) {
            case ASCENDING -> Sort.by(Sort.Order.asc(sortMeta.getFiledName()));
            case DESCENDING -> Sort.by(Sort.Order.desc(sortMeta.getFiledName()));
            default -> null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
