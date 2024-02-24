package org._3HCompany.microservice.common.models.domain.shop.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItemRequest {
    private Long id;
    private Long price;
    private Integer qtyOriginal;
    private Integer qtySupplement;
    private Long productId;
}
