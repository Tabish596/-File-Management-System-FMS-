package com.ftm.main.mock.features.jpa.annotations.namedQueries;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findByName(@Param("name") String name);  //auto maps to Employee Entity Named Query

    @Query("select e from Employee e where e.department = :dept")
    List<Employee> findByDepartment(@Param("dept") String name);
}
