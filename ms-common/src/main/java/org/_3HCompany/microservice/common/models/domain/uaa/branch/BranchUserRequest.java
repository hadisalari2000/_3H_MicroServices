package org._3HCompany.microservice.common.models.domain.uaa.branch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchUserRequest {
    private Integer branchId;
    private Long userId;
}
