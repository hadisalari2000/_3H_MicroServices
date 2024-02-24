package org._3HCompany.microservice.common.models.enums;

import lombok.Getter;

@Getter
public enum ColumnEnum {

    ID("id","کلید جدول"),
    EMAIL("email","ایمیل"),
    USERNAME("username","نام کاربری"),
    PASSWORD("password","گذرواژه"),
    FILE("file","فایل"),
    PARENT_ID("parentId","دسته بندی اصلی "),
    SELLER("seller","فروشنده"),
    CUSTOMER("customer","مشتری"),
    MOBILE_NUMBER("mobile_number","شماره همراه");

    private final String name;
    private final String title;

    ColumnEnum(String name, String title){
        this.name=name;
        this.title=title;
    }
}
