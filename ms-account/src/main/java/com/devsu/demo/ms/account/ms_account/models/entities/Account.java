package com.devsu.demo.ms.account.ms_account.models.entities;

import com.devsu.demo.ms.account.ms_account.models.dtos.ClientDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "accounts")
@Builder(toBuilder = true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @Column(name = "account_number", unique = true)
    private Integer accountNumber;

    @NotEmpty
    @NotBlank
    @NotNull
    @Column(name = "account_type")
    private String accountType;

    @NotNull
    @Column(name = "initial_balance")
    private BigDecimal initialBalance;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_date")
    private Date createdDate;

    @OneToMany(mappedBy = "account", cascade = ALL, orphanRemoval = true, fetch = EAGER)
    private List<Movement> movements;

    @Transient
    private ClientDTO client;

    @PrePersist
    private void beforePersist(){
        this.status = true;
        this.createdUser = "devsu";
        this.createdDate = new Date();
    }
}


