package org._3HCompany.microservice.common.models.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterMeta {
    private String filedName;
    private Object filedValue;
    private String operator;
    private String type;
}
