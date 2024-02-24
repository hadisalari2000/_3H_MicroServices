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
public class UserRoleDto implements Serializable {
    private Long userId;
    private String userUserName;
    private String userFirstName;
    private String userLastName;
    private Boolean userIsActivated;
    private Long roleId;
    private String roleName;
    private String roleTitle;
    private Boolean roleIsActivated;
}