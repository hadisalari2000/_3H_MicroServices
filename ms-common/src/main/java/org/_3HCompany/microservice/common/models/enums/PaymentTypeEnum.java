package org._3HCompany.microservice.common.models.enums;

import lombok.Getter;

@Getter
public enum PaymentTypeEnum {

    CASH("cash", "پرداخت نقدی"),
    SHEBA("sheba", "شبا"),
    CART("cart", "کارت به کارت"),
    POS("pos", "دستگاه کارتخوان");

    private final String title;
    private final String name;

    PaymentTypeEnum(String name, String title) {
        this.name = name;
        this.title = title;
    }
}
