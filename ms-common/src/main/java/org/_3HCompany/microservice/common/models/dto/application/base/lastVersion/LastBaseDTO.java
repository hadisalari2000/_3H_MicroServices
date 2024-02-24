package org._3HCompany.microservice.common.models.dto.application.base.lastVersion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LastBaseDTO<T> {
    private LastMetaDTO meta;
    private T data;

    public LastBaseDTO(LastMetaDTO metaDTO) {
        this.meta = metaDTO;
    }
}
