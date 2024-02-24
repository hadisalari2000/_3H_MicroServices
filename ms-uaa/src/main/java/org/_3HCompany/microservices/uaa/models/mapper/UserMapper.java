package org._3HCompany.microservices.uaa.models.mapper;

import org._3HCompany.microservice.common.models.domain.uaa.user.UserAddRequest;
import org._3HCompany.microservice.common.models.dto.uaa.UserDto;
import org._3HCompany.microservices.uaa.models.entity.User;
import org.mapstruct.*;

@Mapper
public interface UserMapper {

    @Mapping(target = "userRoles", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "isActivated", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "branches", ignore = true)
    User requestToEntity(UserAddRequest request);

    @Mapping(target = "userRoles", ignore = true)
    User dtoToEntity(UserDto userDto);

    UserDto entityToDto(User user);
}