package org._3HCompany.microservices.uaa.services;

import org._3HCompany.microservice.common.handler.exception.ResourceNotFoundException;
import org._3HCompany.microservice.common.models.dto.uaa.UserRoleDto;
import org._3HCompany.microservice.common.models.enums.ColumnEnum;
import org._3HCompany.microservice.common.models.enums.TableEnum;
import org._3HCompany.microservices.uaa.models.entity.UserRole;
import org._3HCompany.microservices.uaa.models.mapper.UserRoleMapper;
import org._3HCompany.microservices.uaa.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRoleMapper userRoleMapper;

    public UserRoleService(UserRoleRepository userRoleRepository, UserRoleMapper userRoleMapper) {
        this.userRoleRepository = userRoleRepository;
        this.userRoleMapper = userRoleMapper;
    }

    public UserRoleDto getUserRoleById(Long userRoleId){

        UserRole userRole = userRoleRepository.findById(userRoleId).orElseThrow(
                () -> new ResourceNotFoundException(TableEnum.USER_ROLE.getName(), ColumnEnum.ID.getName(), userRoleId)
        );
        return userRoleMapper.toDto(userRole);
    }
}

