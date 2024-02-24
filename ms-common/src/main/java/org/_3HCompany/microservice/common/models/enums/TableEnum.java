package org._3HCompany.microservice.common.models.enums;

import lombok.Getter;

@Getter
public enum TableEnum {

    CATEGORY("category","دسته بندی کالا","دسته بندی کالا"),
    BRAND("brand","برند","برند"),
    VENDOR("vendor","فروشنده","فروشنده"),
    INVOICE_ITEM("invoice_item","اقلام فاکتور","اقلام فاکتور"),
    INVOICE("invoice","فاکتور","فاکتور"),
    PAYMENT("payment","پرداخت","پرداخت"),
    PRODUCT("product","کالا","کالا"),
    PRODUCT_IMAGE("product_image","تصاویر محصولات","تصاویر محصولات"),

    SLIDER("slider","اسلایدر","اسلایدر"),
    UnRegisterMobile("un_register_mobile","موبایل های تایید نشده","موبایل های تایید نشده"),

    USER("user"," کاربر"," کاربر"),
    PERSON("person"," شخص"," شخص"),
    ROLE("role"," نقش "," نقش "),
    BRANCH("branch","شعبه","شعبه"),
    USER_ROLE("user_role"," نقش-کاربر"," نقش-کاربر");
    
    private final String title;
    private final String name;
    private final String description;

    TableEnum(String name,String title,String description){
        this.name=name;
        this.title=title;
        this.description=description;
    }
}
