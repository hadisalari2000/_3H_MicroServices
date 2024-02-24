package org._3HCompany.microservices.uaa.models.mapper;

import org._3HCompany.microservice.common.models.domain.uaa.role.RoleAddRequest;
import org._3HCompany.microservice.common.models.dto.uaa.RoleDto;
import org._3HCompany.microservices.uaa.models.entity.Role;
import org.mapstruct.*;

@Mapper
public interface RoleMapper {
    @Mapping(target = "userRoles", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "isActivated", ignore = true)
    @Mapping(target = "id", ignore = true)
    Role requestToEntity(RoleAddRequest request);

    @Mapping(target = "userRoles", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    Role dtoToEntity(RoleDto roleDto);

    RoleDto entityToDto(Role role);

}