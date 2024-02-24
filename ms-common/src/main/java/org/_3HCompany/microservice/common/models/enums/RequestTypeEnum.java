package org._3HCompany.microservice.common.models.enums;

import lombok.Getter;

@Getter
public enum RequestTypeEnum {

    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete"),
    GET("get");

    private final String name;

    RequestTypeEnum(String name){
        this.name=name;
    }
}
