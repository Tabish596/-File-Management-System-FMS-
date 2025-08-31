package com.ftm.main.mock.features.jpa.annotations.namedQueries;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity

@NamedQueries(
        {
                @NamedQuery(
                        name="Employee.findByDepartment",
                        query="SELECT e from Employee e where e.department = :dept"
                ),
                @NamedQuery(
                        name="Employee.findByName",
                        query="Select e from Employee e where e.name = :name"
                )
        }
)
public class Employee {
    @Id
    private Long id;

    private String name;
    private String department;
}
