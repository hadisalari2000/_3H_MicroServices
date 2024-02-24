package org._3HCompany.microservice.common.models.domain.shop.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceEditRequest {
    private Long id;
    private String code;
    private Integer orderDate;
    private String invoiceStatus;
    private Long customerId;
    private Long sellerId;
    private Integer branchId;
    private String comment;
    private List<InvoiceItemRequest> invoiceItems;
    private List<InvoicePaymentRequest> paymentItems;
}
