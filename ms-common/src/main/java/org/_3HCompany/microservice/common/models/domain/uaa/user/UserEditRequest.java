package org._3HCompany.microservice.common.models.domain.uaa.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEditRequest {

    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String postalCode;
}
