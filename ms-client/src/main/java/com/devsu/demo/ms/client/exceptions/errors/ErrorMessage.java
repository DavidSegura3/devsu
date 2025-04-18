package com.devsu.demo.ms.client.exceptions.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@Schema(description = "Error message information")
public class ErrorMessage {

    @Schema(description = "HTTP error code")
    private Integer statusCode;

    @Schema(description = "Catch message error")
    private String message;

    @Schema(description = "Path of where the error is thrown")
    private String route;
}
