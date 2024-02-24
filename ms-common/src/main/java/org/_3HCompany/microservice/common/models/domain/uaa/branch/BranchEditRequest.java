package org._3HCompany.microservice.common.models.domain.uaa.branch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchEditRequest {

    private Integer id;
    private Integer parentId;
    private String name;
    private Integer code;
    private Integer degree;
    private String description;
}
