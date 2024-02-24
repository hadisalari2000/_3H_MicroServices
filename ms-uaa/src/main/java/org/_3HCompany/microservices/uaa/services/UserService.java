package org._3HCompany.microservices.uaa.services;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org._3HCompany.microservice.common.models.domain.uaa.user.UserAddRequest;
import org._3HCompany.microservice.common.models.domain.uaa.user.UserChangeActivationRequest;
import org._3HCompany.microservice.common.models.domain.uaa.user.UserEditRequest;
import org._3HCompany.microservice.common.models.dto.application.base.BaseDTO;
import org._3HCompany.microservice.common.models.dto.application.base.PagerDTO;
import org._3HCompany.microservice.common.models.enums.ColumnEnum;
import org._3HCompany.microservice.common.models.enums.RequestTypeEnum;
import org._3HCompany.microservice.common.models.enums.TableEnum;
import org._3HCompany.microservice.common.models.filter.DataTableFilterRequest;
import org._3HCompany.microservices.uaa.util.PageEntity;
import org._3HCompany.microservices.uaa.models.entity.Person;
import org._3HCompany.microservices.uaa.models.entity.User;
import org._3HCompany.microservices.uaa.models.mapper.UserMapper;
import org._3HCompany.microservices.uaa.repository.PersonRepository;
import org._3HCompany.microservices.uaa.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService extends PageEntity {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PersonRepository personRepository;

    public BaseDTO getById(Long id) {

        Optional<User> currentUser;
        currentUser = userRepository.findById(id);
        if (currentUser.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.USER);

        User user = currentUser.get();
        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.USER);
        baseDTO.setData(userMapper.entityToDto(user));
        return baseDTO;
    }

    public BaseDTO add(UserAddRequest request) {

        List<String> errorMessage = userValidation(request);

        if (!errorMessage.isEmpty()) {
            return BaseDTO.getDuplicateError(TableEnum.USER, errorMessage);
        }

        User user = userMapper.requestToEntity(request);
        user = userRepository.save(user);

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.USER, RequestTypeEnum.INSERT);
        baseDTO.setData(userMapper.entityToDto(user));
        return baseDTO;
    }

    private List<String> userValidation(UserAddRequest request) {
        List<String> errorMessage = new ArrayList<>();

        if (request.getPassword().equals(request.getConfirmPassword()))
            errorMessage.add(ColumnEnum.PASSWORD.getTitle());

        if (userRepository.findUserByUsername(request.getUsername()).isPresent())
            errorMessage.add(ColumnEnum.USERNAME.getTitle());

        if (userRepository.findUserByPersonEmail(request.getEmail()).isPresent())
            errorMessage.add(ColumnEnum.EMAIL.getTitle());

        if (userRepository.findUserByPersonMobileNumber(request.getMobile()).isPresent())
            errorMessage.add(ColumnEnum.MOBILE_NUMBER.getTitle());

        return errorMessage;
    }

    public BaseDTO update(UserEditRequest request) {

        List<String> errorMessage = new ArrayList<>();
        Optional<User> currentUser;

        currentUser = userRepository.findUserByUsername(request.getUserName());
        if (currentUser.isPresent() && !currentUser.get().getId().equals(request.getId()))
            errorMessage.add(ColumnEnum.USERNAME.getTitle());

        currentUser = userRepository.findUserByPersonEmail(request.getEmail());
        if (currentUser.isPresent() && !currentUser.get().getId().equals(request.getId()))
            errorMessage.add(ColumnEnum.EMAIL.getTitle());

        currentUser = userRepository.findUserByPersonEmail(request.getMobile());
        if (currentUser.isPresent() && !currentUser.get().getId().equals(request.getId()))
            errorMessage.add(ColumnEnum.MOBILE_NUMBER.getTitle());

        if (!errorMessage.isEmpty()) {
            return BaseDTO.getDuplicateError(TableEnum.USER, errorMessage);
        }

        currentUser = userRepository.findById(request.getId());
        if (currentUser.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.USER);

        User user = currentUser.get();
        Optional<Person> person = personRepository.findPersonByUserId(user.getId());

        if (person.isPresent()) {
            Person newPerson = person.get();
            newPerson.setFirstName(request.getFirstName());
            newPerson.setLastName(request.getLastName());
            newPerson.setEmail(request.getEmail());
            newPerson.setMobileNumber(request.getMobile());
            newPerson.setPostalCode(request.getPostalCode());
            personRepository.save(newPerson);
        }

        user.setUsername(request.getUserName());
        userRepository.save(user);

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.USER);
        baseDTO.setData(userMapper.entityToDto(user));
        return baseDTO;
    }

    public BaseDTO changeActivation(UserChangeActivationRequest request) {

        Optional<User> currentUser;
        currentUser = userRepository.findById(request.getId());
        if (currentUser.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.USER);

        User user = currentUser.get();
        user.setIsActivated(request.getIsActivated());
        userRepository.save(user);

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.USER);
        baseDTO.setData(userMapper.entityToDto(user));
        return baseDTO;
    }

    public BaseDTO deleteById(Long id) {

        Optional<User> currentUser;
        currentUser = userRepository.findById(id);
        if (currentUser.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.USER);

        User user = currentUser.get();

        user.setIsDeleted(true);
        userRepository.save(user);

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.USER);
        baseDTO.setData(null);
        return baseDTO;
    }

    public BaseDTO getsByFilter(DataTableFilterRequest filterRequest) {

        Pageable pageRequest = createPageable(
                filterRequest.getPageNumber(),
                filterRequest.getRowsInPage(),
                generateSort(filterRequest.getSortMeta()));

        Page<User> list;
        if (!filterRequest.getFilterMeta().isEmpty()) {
            Specification<User> constructionInvoiceSpecification = generateSearch(filterRequest.getFilterMeta());
            list = userRepository.findAll(constructionInvoiceSpecification, pageRequest);
        } else {
            list = userRepository.findAll(pageRequest);
        }

        if (list.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.USER);

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.USER);
        PagerDTO pagerDTO=PagerDTO.builder()
                .collection(list.stream().map(userMapper::entityToDto).collect(Collectors.toList()))
                .build();
        baseDTO.setData(pagerDTO);
        return baseDTO;
    }

}