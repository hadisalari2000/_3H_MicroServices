package org._3HCompany.microservice.common.models.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {

    private Long id;
    private String name;
    private String eName;
    private String shortDescription;
    private String description;
    private Long buyPrice;

    private Integer price1;
    private Integer price2;
    private Integer price3;
    private Integer price4;
    private Integer setLevel;
    private String summary;

    private Integer quantity;
    private Boolean isExist;
    private Boolean isSpecial;
    private Integer visitCount;
    private VendorDto vendor;
    private BrandDto brand;
    private List<CategoryDto> categories;
}