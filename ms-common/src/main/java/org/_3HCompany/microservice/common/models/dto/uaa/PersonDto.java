package org._3HCompany.microservice.common.models.dto.uaa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String postalCode;
    private Boolean mobileConfirmed;
    private Boolean emailConfirmed;
}