package org._3HCompany.microservices.uaa.repository;

import org._3HCompany.microservices.uaa.models.entity.UnRegisterMobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnRegisterMobileRepository extends JpaRepository<UnRegisterMobile, String> {
}