package org._3HCompany.microservice.common.models.domain.application.slider;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SliderAddRequest implements Serializable {

    private Integer slideNumber;
    private String title1;
    private String title2;
    private String description;
    private Boolean isHidden;
    private String mainImageName;

    private String minorImageName1;
    private String minorImageName2;
    private String minorImageName3;
    private String minorImageName4;
    private String minorImageName5;
    private String minorImageName6;
}