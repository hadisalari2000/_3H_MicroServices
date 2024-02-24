package org._3HCompany.microservice.common.models.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org._3HCompany.microservice.common.models.enums.PaymentTypeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private Long id;
    private String code;
    private Integer paymentDate;
    private PaymentTypeEnum paymentTypeEnum;
    private String comment;
}