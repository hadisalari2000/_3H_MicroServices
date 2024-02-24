package org._3HCompany.microservice.common.models.enums;

import lombok.Getter;

@Getter
public enum SliderEnum {

    HOME_SLIDER("home_slider","اسلایدر صفحه اصلی");

    private final String title;
    private final String name;

    SliderEnum(String name, String title){
        this.name=name;
        this.title=title;
    }
}
