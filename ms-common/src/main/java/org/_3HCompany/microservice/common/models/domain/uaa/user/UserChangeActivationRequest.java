package org._3HCompany.microservice.common.models.domain.uaa.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserChangeActivationRequest {

    private Long id;
    private Boolean isActivated;
}
