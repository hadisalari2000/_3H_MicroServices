package org._3HCompany.microservices.uaa.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mobile_register_code")
public class UnRegisterMobile {

    @Id
    private String mobile;

    @Column(nullable = false)
    private String randomCode;
}