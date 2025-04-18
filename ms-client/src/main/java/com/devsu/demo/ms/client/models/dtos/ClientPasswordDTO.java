package com.devsu.demo.ms.client.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientPasswordDTO {

    @NotEmpty
    @NotBlank
    @NotNull
    private String password;
}


