package org._3HCompany.microservices.uaa.models.mapper;

import org._3HCompany.microservice.common.models.dto.uaa.UserRoleDto;
import org._3HCompany.microservices.uaa.models.entity.UserRole;
import org.mapstruct.*;

@Mapper
public interface UserRoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userUserName", target = "user.username")
    @Mapping(source = "userFirstName", target = "user.person.firstName")
    @Mapping(source = "userLastName", target = "user.person.lastName")
    @Mapping(source = "userIsActivated", target = "user.isActivated")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "roleTitle", target = "role.title")
    @Mapping(source = "roleName", target = "role.name")
    @Mapping(source = "roleIsActivated", target = "role.isActivated")
    @Mapping(source = "roleId", target = "role.id")
    UserRole toEntity(UserRoleDto userRoleDto);

    @Mapping(target = "userUserName", source = "user.username")
    @Mapping(target = "userFirstName", source = "user.person.firstName")
    @Mapping(target = "userLastName", source = "user.person.lastName")
    @Mapping(target = "userIsActivated", source = "user.isActivated")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "roleTitle", source = "role.title")
    @Mapping(target = "roleName", source = "role.name")
    @Mapping(target = "roleIsActivated", source = "role.isActivated")
    @Mapping(target = "roleId", source = "role.id")
    UserRoleDto toDto(UserRole userRole);
}