package org._3HCompany.microservice.common.models.domain.shop.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAddRequest {

    private Long parentId;
    private String name;
    private String urlTitle;
    private String description;
}
