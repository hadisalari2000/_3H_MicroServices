package org._3HCompany.microservices.uaa.repository;

import org._3HCompany.microservices.uaa.models.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole> {
    List<UserRole> findByUser_Id(Long id);

    List<UserRole> findByRole_Id(Long id);

}