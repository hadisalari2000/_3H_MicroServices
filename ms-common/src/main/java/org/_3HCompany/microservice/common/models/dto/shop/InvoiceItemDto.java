package org._3HCompany.microservice.common.models.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItemDto {
    private Long id;
    private Long price;
    private Integer qtyOriginal;
    private Integer qtySupplement;
    private ProductDto product;
}