package com.devsu.demo.ms.client.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "clients")
@Builder(toBuilder = true)
@PrimaryKeyJoinColumn(name = "person_id")
@Schema(description = "Client information")
public class Client extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "client_id", unique = true)
    @Schema(description = "Unique client Id")
    private Long clientId;

    @NotEmpty
    @NotBlank
    @NotNull
    @Column(name = "password", unique = true)
    private String password;

    @NotNull
    @Column(name = "status")
    private Boolean status;
}


