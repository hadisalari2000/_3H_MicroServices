package org._3HCompany.microservices.uaa.repository;

import org._3HCompany.microservices.uaa.models.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer>, JpaSpecificationExecutor<Branch> {
    List<Branch> findAllByParentIsNull();

    List<Branch> findAllByParent_Id(Integer parentId);

    Optional<Branch> findByCode(Integer code);
    Optional<Branch> findByName(String name);
}