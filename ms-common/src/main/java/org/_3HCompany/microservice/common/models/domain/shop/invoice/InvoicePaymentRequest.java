package org._3HCompany.microservice.common.models.domain.shop.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org._3HCompany.microservice.common.models.enums.PaymentTypeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoicePaymentRequest {

    private Long id;
    private String code;
    private Integer paymentDate;
    private PaymentTypeEnum paymentTypeEnum;
    private String comment;
}
