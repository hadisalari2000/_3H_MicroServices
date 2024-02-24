package org._3HCompany.microservices.uaa.services;

import lombok.RequiredArgsConstructor;
import org._3HCompany.microservice.common.models.domain.uaa.branch.BranchAddRequest;
import org._3HCompany.microservice.common.models.domain.uaa.branch.BranchEditRequest;
import org._3HCompany.microservice.common.models.domain.uaa.branch.BranchUserRequest;
import org._3HCompany.microservice.common.models.dto.application.base.BaseDTO;
import org._3HCompany.microservice.common.models.dto.application.base.PagerDTO;
import org._3HCompany.microservice.common.models.enums.ColumnEnum;
import org._3HCompany.microservice.common.models.enums.TableEnum;
import org._3HCompany.microservice.common.models.filter.DataTableFilterRequest;
import org._3HCompany.microservices.uaa.util.PageEntity;
import org._3HCompany.microservices.uaa.models.entity.Branch;
import org._3HCompany.microservices.uaa.models.entity.User;
import org._3HCompany.microservices.uaa.models.mapper.BranchMapper;
import org._3HCompany.microservices.uaa.repository.BranchRepository;
import org._3HCompany.microservices.uaa.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchService extends PageEntity {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;
    private final UserRepository userRepository;

    public BaseDTO getAllParents() {
        List<Branch> branches = branchRepository.findAllByParentIsNull();

        if (branches.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.BRANCH);

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.BRANCH);
        baseDTO.setData(branches.stream().map(branchMapper::entityToDto).collect(Collectors.toList()));
        return baseDTO;
    }

    public BaseDTO getAllByParentId(Integer parentId) {
        List<Branch> branches = branchRepository.findAllByParent_Id(parentId);

        if (branches.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.BRANCH, ColumnEnum.PARENT_ID.getTitle(), parentId.toString());

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.BRANCH);
        baseDTO.setData(branches.stream().map(branchMapper::entityToDto).collect(Collectors.toList()));
        return baseDTO;
    }

    public BaseDTO getsByFilter(DataTableFilterRequest filterRequest) {

        Pageable pageRequest = createPageable(
                filterRequest.getPageNumber(),
                filterRequest.getRowsInPage(),
                generateSort(filterRequest.getSortMeta()));

        Page<Branch> list;
        if (!filterRequest.getFilterMeta().isEmpty()) {
            Specification<Branch> constructionInvoiceSpecification = generateSearch(filterRequest.getFilterMeta());
            list = branchRepository.findAll(constructionInvoiceSpecification, pageRequest);
        } else {
            list = branchRepository.findAll(pageRequest);
        }

        if (list.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.BRANCH);

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.BRANCH);
        PagerDTO pagerDTO=PagerDTO.builder()
                .collection(list.stream().map(branchMapper::entityToDto).collect(Collectors.toList()))
                .build();
        baseDTO.setData(pagerDTO);
        return baseDTO;
    }

    public BaseDTO add(BranchAddRequest request) {
        Optional<Branch> currentBranch;
        currentBranch = branchRepository.findByName(request.getName());
        if (currentBranch.isPresent())
            return BaseDTO.getDuplicateError(TableEnum.BRANCH, null);

        Branch branch = branchMapper.requestToEntity(request);
        branch = branchRepository.save(branch);

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.BRANCH);
        baseDTO.setData(branchMapper.entityToDto(branch));
        return baseDTO;
    }

    public BaseDTO update(BranchEditRequest request) {

        Optional<Branch> currentBranch;
        currentBranch = branchRepository.findByName(request.getName());
        if (currentBranch.isPresent() && !Objects.equals(currentBranch.get().getId(), request.getId()))
            return BaseDTO.getDuplicateError(TableEnum.BRANCH, null);

        currentBranch = branchRepository.findById(request.getId());
        if (currentBranch.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.BRANCH);

        Branch branch = currentBranch.get();
        branch.setName(request.getName());
        branch.setCode(request.getCode());
        branch.setDegree(request.getDegree());
        branch.setParent(branchRepository.findById(request.getParentId()).orElse(null));
        branch.setDescription(request.getDescription());

        branchRepository.save(branch);

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.BRANCH);
        baseDTO.setData(branchMapper.entityToDto(branch));
        return baseDTO;
    }

    public BaseDTO deleteById(Integer id) {
        Optional<Branch> currentBranch;
        currentBranch = branchRepository.findById(id);
        if (currentBranch.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.BRANCH);

        Branch branch = currentBranch.get();

        branch.setIsDeleted(true);
        branchRepository.save(branch);

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.BRANCH);
        baseDTO.setData(null);
        return baseDTO;
    }

    public BaseDTO getById(Integer id) {
        Optional<Branch> currentBranch;
        currentBranch = branchRepository.findById(id);
        if (currentBranch.isEmpty())
            return BaseDTO.getNotFoundError(TableEnum.BRANCH);

        Branch branch = currentBranch.get();

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.BRANCH);
        baseDTO.setData(branchMapper.entityToDto(branch));
        return baseDTO;
    }

    public BaseDTO addUser(BranchUserRequest request) {
        Optional<Branch> currentBranch = branchRepository.findById(request.getBranchId());
        if (currentBranch.isEmpty()) {
            return BaseDTO.getNotFoundError(TableEnum.BRANCH);
        }

        Optional<User> user = userRepository.findById(request.getUserId());
        if (user.isEmpty()) {
            return BaseDTO.getNotFoundError(TableEnum.USER);
        }

        User existingUser = user.get();
        Branch branch = currentBranch.get();

        List<Branch> branches = existingUser.getBranches();
        if (!branches.contains(branch)) {
            branches.add(branch);
            userRepository.save(existingUser);
        }

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.BRANCH);
        baseDTO.setData(branchMapper.entityToDto(branch));
        return baseDTO;
    }

    public BaseDTO deleteUser(BranchUserRequest request) {
        Optional<Branch> currentBranch = branchRepository.findById(request.getBranchId());
        if (currentBranch.isEmpty()) {
            return BaseDTO.getNotFoundError(TableEnum.BRANCH);
        }

        Branch branch = currentBranch.get();
        Optional<User> user = userRepository.findById(request.getUserId());

        if (user.isPresent()) {
            List<Branch> branches = user.get().getBranches();
            branches.removeIf(b -> b.getId().equals(branch.getId()));
            userRepository.save(user.get());
        } else {
            return BaseDTO.getNotFoundError(TableEnum.USER);
        }

        BaseDTO baseDTO = BaseDTO.getSuccess(TableEnum.BRANCH);
        baseDTO.setData(branchMapper.entityToDto(branch));
        return baseDTO;
    }
}