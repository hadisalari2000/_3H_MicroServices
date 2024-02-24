package org._3HCompany.microservices.uaa.services;

import lombok.RequiredArgsConstructor;
import org._3HCompany.microservice.common.models.domain.uaa.role.RoleAddRequest;
import org._3HCompany.microservice.common.models.domain.uaa.role.RoleChangeActivationRequest;
import org._3HCompany.microservice.common.models.domain.uaa.role.RoleEditRequest;
import org._3HCompany.microservice.common.models.dto.application.base.BaseDTO;
import org._3HCompany.microservice.common.models.dto.application.base.PagerDTO;
import org._3HCompany.microservice.common.models.enums.TableEnum;
import org._3HCompany.microservice.common.models.filter.DataTableFilterRequest;
import org._3HCompany.microservices.uaa.util.PageEntity;
import org._3HCompany.microservices.uaa.models.entity.Role;
import org._3HCompany.microservices.uaa.models.mapper.RoleMapper;
import org._3HCompany.microservices.uaa.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService extends PageEntity {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public BaseDTO getById(Long id) {

        Optional<Role> currentRole;
        currentRole=roleRepository.findById(id);
        if(currentRole.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.ROLE);

        Role role=currentRole.get();

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.ROLE);
        baseDTO.setData(roleMapper.entityToDto(role));
        return baseDTO;
    }

    public BaseDTO add(RoleAddRequest request) {

        Optional<Role> currentRole;
        currentRole = roleRepository.findRoleByName(request.getName());
        if (currentRole.isPresent())
            return BaseDTO.getDuplicateError(TableEnum.ROLE,null);

        Role role = roleMapper.requestToEntity(request);
        role = roleRepository.save(role);

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.ROLE);
        baseDTO.setData(roleMapper.entityToDto(role));
        return baseDTO;
    }

    public BaseDTO update(RoleEditRequest request) {

        Optional<Role> currentRole;
        currentRole = roleRepository.findRoleByName(request.getName());
        if (currentRole.isPresent() && !Objects.equals(currentRole.get().getId(), request.getId()))
            return BaseDTO.getDuplicateError(TableEnum.ROLE,null);

        currentRole = roleRepository.findById(request.getId());
        if (currentRole.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.ROLE);

        Role role = currentRole.get();
        role.setName(request.getName());
        role.setTitle(request.getTitle());

        roleRepository.save(role);

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.ROLE);
        baseDTO.setData(roleMapper.entityToDto(role));
        return baseDTO;
    }

    public BaseDTO changeActivation(RoleChangeActivationRequest request) {

        Optional<Role> currentRole;
        currentRole=roleRepository.findById(request.getId());
        if(currentRole.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.ROLE);

        Role role=currentRole.get();
        role.setIsActivated(request.getIsActivated());

        roleRepository.save(role);

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.ROLE);
        baseDTO.setData(roleMapper.entityToDto(role));
        return baseDTO;
    }

    public BaseDTO deleteById(Long id) {

        Optional<Role> currentRole;
        currentRole=roleRepository.findById(id);
        if(currentRole.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.ROLE);

        Role role=currentRole.get();

        role.setIsDeleted(true);
        roleRepository.save(role);

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.ROLE);
        baseDTO.setData(null);
        return baseDTO;
    }

    public BaseDTO getsByFilter(DataTableFilterRequest filterRequest) {

        Pageable pageRequest = createPageable(
                filterRequest.getPageNumber(),
                filterRequest.getRowsInPage(),
                generateSort(filterRequest.getSortMeta()));

        Page<Role> list;
        if (!filterRequest.getFilterMeta().isEmpty()) {
            Specification<Role> constructionInvoiceSpecification = generateSearch(filterRequest.getFilterMeta());
            list = roleRepository.findAll(constructionInvoiceSpecification, pageRequest);
        } else {
            list = roleRepository.findAll(pageRequest);
        }

        if(list.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.ROLE);

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.ROLE);

        PagerDTO pagerDTO=PagerDTO.builder()
                .collection(list.stream().map(roleMapper::entityToDto).collect(Collectors.toList()))
                .build();
        baseDTO.setData(pagerDTO);
        return baseDTO;
    }
}