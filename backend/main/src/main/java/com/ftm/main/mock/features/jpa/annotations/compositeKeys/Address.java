package com.ftm.main.mock.features.jpa.annotations.compositeKeys;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String street;
    private String city;
    private String pinCode;
}
