package org._3HCompany.microservices.uaa.models.mapper;


import org._3HCompany.microservice.common.models.domain.uaa.branch.BranchAddRequest;
import org._3HCompany.microservice.common.models.dto.uaa.BranchDto;
import org._3HCompany.microservices.uaa.models.entity.Branch;
import org.mapstruct.*;

@Mapper
public interface BranchMapper {

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "children", ignore = true)
    Branch requestToEntity(BranchAddRequest request);

    @Mapping(target = "children", ignore = true)
    Branch dtoToEntity(BranchDto dto);

    BranchDto entityToDto(Branch branch);

}