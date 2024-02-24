package org._3HCompany.microservice.common.models.enums;

import lombok.Getter;

@Getter
public enum InvoiceStatusEnum {

    DOWNLOADED("DOWNLOADED", "دانلود شده"),
    DRAFT("DRAFT", "پیش نویس"),
    PAID("PAID", "پرداخت شده"),
    PARTIAL_PAYMENT("PARTIAL_PAYMENT", "پرداخت جزئی"),
    PAST_DUE("PAST_DUE", "تاریخ سررسید گذشته"),
    SENT("SENT", "ارسال شده");

    private final String title;
    private final String name;

    InvoiceStatusEnum(String name, String title) {
        this.name = name;
        this.title = title;
    }
}
