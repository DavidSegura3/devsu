package com.devsu.demo.ms.client.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactInformationClientDTO {

    @NotEmpty
    @NotBlank
    @NotNull
    private String name;
    @NotEmpty
    @NotBlank
    @NotNull
    private String gender;
    private String address;
    private String phone;
}


