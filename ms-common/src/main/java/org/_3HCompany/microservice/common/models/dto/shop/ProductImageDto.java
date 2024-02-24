package org._3HCompany.microservice.common.models.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDto {

    private Long id;
    private String name;
    private String imageUrl;
    private Integer ordering;
    private Boolean isHidden;
    private ProductDto product;
}