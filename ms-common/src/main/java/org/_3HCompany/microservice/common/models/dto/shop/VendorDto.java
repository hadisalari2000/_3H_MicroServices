package org._3HCompany.microservice.common.models.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorDto implements Serializable {

    private Integer id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String postalCode;
}