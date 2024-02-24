package org._3HCompany.microservice.common.models.domain.application.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataTableFilterRequest  implements Serializable {
    private Integer pageNumber;
    private Integer rowsInPage;
    private List<SortMeta> sortMeta;
    private List<FilterMeta> filterMeta;
}
