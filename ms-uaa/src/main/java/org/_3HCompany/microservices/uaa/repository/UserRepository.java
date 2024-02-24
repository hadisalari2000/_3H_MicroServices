package org._3HCompany.microservices.uaa.repository;

import org._3HCompany.microservices.uaa.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByPersonEmail(String email);
    Optional<User> findUserByPersonMobileNumber(String email);

}