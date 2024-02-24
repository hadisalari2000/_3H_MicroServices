package org._3HCompany.microservice.common.models.domain.shop.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterRequest {
    private Integer pageNumber;
    private Integer rowsInPage;
    private String name;
    private List<Integer> categories;
}
