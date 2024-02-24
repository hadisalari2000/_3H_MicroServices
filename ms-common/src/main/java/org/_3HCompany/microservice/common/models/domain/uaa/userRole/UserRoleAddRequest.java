package org._3HCompany.microservice.common.models.domain.uaa.userRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleAddRequest {
    private Long userId;
    private Long roleId;
}
