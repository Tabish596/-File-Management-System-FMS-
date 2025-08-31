package com.ftm.main.mock.features.jpa.annotations.namedQueries;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Main {

    @PersistenceContext
    private EntityManager entityManager;

    public void getEmployees(){
        List<Employee> employees = entityManager
                .createNamedQuery("Employee.findByDepartment", Employee.class)
                .setParameter("dept","IT")
                .getResultList();
    }
}
