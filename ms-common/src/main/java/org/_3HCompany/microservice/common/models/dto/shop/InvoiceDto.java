package org._3HCompany.microservice.common.models.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org._3HCompany.microservice.common.models.dto.uaa.BranchDto;
import org._3HCompany.microservice.common.models.dto.uaa.PersonDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private Long id;
    private String code;
    private Integer orderDate;
    private String invoiceStatus;
    private PersonDto customer;
    private PersonDto seller;
    private BranchDto branch;
    private String comment;
    private List<InvoiceItemDto> invoiceItems;
    private List<PaymentDto> payments;
}