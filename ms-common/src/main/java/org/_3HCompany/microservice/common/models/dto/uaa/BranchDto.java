package org._3HCompany.microservice.common.models.dto.uaa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchDto implements Serializable {
    private Integer id;
    private String name;
    private Integer code;
    private Integer degree;
    private String description;
    private Boolean isDeleted;
    private BranchDto parent;
}