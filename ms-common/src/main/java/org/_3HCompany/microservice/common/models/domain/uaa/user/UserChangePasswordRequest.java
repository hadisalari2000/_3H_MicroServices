package org._3HCompany.microservice.common.models.domain.uaa.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePasswordRequest {

    private Long id;
    private String username;
    private String lastPassword;
    private String newPassword;
    private String newPasswordConfirm;
}
