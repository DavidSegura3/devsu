package com.devsu.demo.ms.client.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HttpEnum {

    NOT_CONTENT("204");

    private final String value;
}
