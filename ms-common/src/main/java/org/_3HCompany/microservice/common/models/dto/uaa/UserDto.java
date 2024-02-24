package org._3HCompany.microservice.common.models.dto.uaa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String username;
    private String password;
    private Boolean isActivated;
    private Boolean isDeleted;
    private PersonDto person;
    private List<BranchDto> branches;
}