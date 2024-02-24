package org._3HCompany.microservices.uaa.services;

import lombok.RequiredArgsConstructor;
import org._3HCompany.microservice.common.models.dto.application.base.BaseDTO;
import org._3HCompany.microservice.common.models.enums.TableEnum;
import org._3HCompany.microservices.uaa.util.PageEntity;
import org._3HCompany.microservices.uaa.models.entity.Person;
import org._3HCompany.microservices.uaa.models.mapper.PersonMapper;
import org._3HCompany.microservices.uaa.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PersonService extends PageEntity {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public BaseDTO getById(Long id) {

        Optional<Person> currentPerson;
        currentPerson = personRepository.findById(id);
        if (currentPerson.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.PERSON);

        Person person = currentPerson.get();
        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.PERSON);
        baseDTO.setData(personMapper.entityToDto(person));
        return baseDTO;
    }

}
