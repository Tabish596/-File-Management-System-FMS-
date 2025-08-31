package com.ftm.main.mock.features.jpa.annotations.compositeKeys;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @Embedded //not a primary/Composite key but used for grouping a reusable group of fields.
    private Address address;

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name="street",column=@Column(name="office_street")),
                    @AttributeOverride(name="city",column=@Column(name="office_city")),
                    @AttributeOverride(name="pinCode",column=@Column(name="office_pin"))
            }
    )
    private Address officeAddress;
}
