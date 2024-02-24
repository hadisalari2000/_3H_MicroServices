package org._3HCompany.microservice.common.models.domain.shop.vendor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorAddRequest {
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String postalCode;
}
