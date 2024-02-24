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
public class RoleDto implements Serializable {
    private Long id;
    private String name;
    private String title;
    private Boolean isActivated;
}