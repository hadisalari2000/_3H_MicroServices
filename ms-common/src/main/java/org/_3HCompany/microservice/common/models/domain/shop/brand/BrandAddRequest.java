package org._3HCompany.microservice.common.models.domain.shop.brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandAddRequest {

    private String name;
    private String urlTitle;
    private String description;
}
