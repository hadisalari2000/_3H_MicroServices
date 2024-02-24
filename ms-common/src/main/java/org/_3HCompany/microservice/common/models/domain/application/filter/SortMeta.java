package org._3HCompany.microservice.common.models.domain.application.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SortMeta {
    private String filedName;
    private String direction;
}
