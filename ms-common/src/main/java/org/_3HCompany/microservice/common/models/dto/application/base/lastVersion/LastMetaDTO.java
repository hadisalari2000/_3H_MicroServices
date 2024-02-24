package org._3HCompany.microservice.common.models.dto.application.base.lastVersion;

import lombok.Builder;
import lombok.Data;
import org._3HCompany.microservice.common.util.ApplicationProperties;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class LastMetaDTO {

    Set<LastMetaMapDTO> success;
    Set<LastMetaMapDTO> errors;
    Set<LastMetaMapDTO> warnings;

    public LastMetaDTO(Set<LastMetaMapDTO> errors, Set<LastMetaMapDTO> warnings) {
        this.errors = errors;
        this.warnings = warnings;
    }

    public LastMetaDTO(Set<LastMetaMapDTO> success, Set<LastMetaMapDTO> errors, Set<LastMetaMapDTO> warnings) {
        this.success = success;
        this.errors = errors;
        this.warnings = warnings;
    }

    public LastMetaDTO() {
    }

    public static LastMetaDTO getInstance() {
        Set<LastMetaMapDTO> success = new HashSet<>();
        success.add(LastMetaMapDTO.builder()
                .key("successful")
                .message(ApplicationProperties.getProperty("successful"))
                .build());
        return new LastMetaDTO(success, null, null);
    }
}
