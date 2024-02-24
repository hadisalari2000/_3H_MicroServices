package org._3HCompany.microservices.uaa.repository;

import org._3HCompany.microservices.uaa.models.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {
    Optional<Person> findPersonByEmail(String email);
    Optional<Person> findPersonByMobileNumber(String mobileNumber);
    Optional<Person> findPersonByUserId(Long userId);
}