package org._3HCompany.microservice.common.models.dto.application.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagerDTO<T> {

    private Collection<T> collection;
    private Integer totalElements;
    private Integer totalPages;
    private Long totalRecords;
    private Integer currentPage;

}
