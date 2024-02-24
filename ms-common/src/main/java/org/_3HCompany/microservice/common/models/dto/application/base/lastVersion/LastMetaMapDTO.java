package org._3HCompany.microservice.common.models.dto.application.base.lastVersion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LastMetaMapDTO {
    private String key;
    private String message;
}
