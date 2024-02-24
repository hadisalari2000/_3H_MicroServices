package org._3HCompany.microservice.common.models.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org._3HCompany.microservice.common.models.enums.SliderEnum;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SliderDto implements Serializable {

    private Integer id;
    private SliderEnum sliderEnum;
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