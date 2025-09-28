package com.medisupply.supervisioncadenfriov2.dto;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
@Data
public class RequestDto {
    @NotNull
    private Double temperature;

    @NotNull
    private String type;

    @NotNull
    private String location;
}
