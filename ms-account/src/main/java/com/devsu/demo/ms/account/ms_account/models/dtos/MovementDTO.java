package com.devsu.demo.ms.account.ms_account.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties("accountDTO")
public class MovementDTO {


    private Long id;
    private String movementType;
    private BigDecimal transactionValue;
    private BigDecimal balance;
    private Date createdDate;
    private AccountDTO accountDTO;
}


