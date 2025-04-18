package com.devsu.demo.ms.account.ms_account.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {

    private Long id;
    private Integer accountNumber;
    private String accountType;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#,##0.00")
    private BigDecimal initialBalance;
    private Boolean status;
    private ClientDTO client;
    private List<MovementDTO> movements;
}


