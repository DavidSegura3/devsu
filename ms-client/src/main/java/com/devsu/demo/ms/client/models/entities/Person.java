package com.devsu.demo.ms.client.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import static jakarta.persistence.InheritanceType.JOINED;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "persons")
@Inheritance(strategy = JOINED)
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotEmpty
    @NotBlank
    @NotNull
    @Column(name = "name")
    private String name;

    @NotEmpty
    @NotBlank
    @NotNull
    @Column(name = "gender")
    private String gender;

    @NotNull
    @Column(name = "age")
    private Integer age;

    @NotEmpty
    @NotBlank
    @NotNull
    @Column(name = "identification", unique = true)
    private String identification;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_date")
    private Date createdDate;

    @PrePersist
    private void beforePersist(){
        this.createdUser = "devsu";
        this.createdDate = new Date();
    }
}


