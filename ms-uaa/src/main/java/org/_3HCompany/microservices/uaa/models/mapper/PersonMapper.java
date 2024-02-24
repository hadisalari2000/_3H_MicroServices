package org._3HCompany.microservices.uaa.models.mapper;

import org._3HCompany.microservice.common.models.domain.uaa.user.UserAddRequest;
import org._3HCompany.microservice.common.models.dto.uaa.PersonDto;
import org._3HCompany.microservices.uaa.models.entity.Person;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {

    Person requestToEntity(UserAddRequest request);

    Person dtoToEntity(PersonDto personDto);

    PersonDto entityToDto(Person person);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Person partialUpdate(PersonDto personDto, @MappingTarget Person person);
}