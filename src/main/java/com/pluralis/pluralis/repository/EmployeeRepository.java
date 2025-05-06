package com.pluralis.pluralis.repository;

import com.pluralis.pluralis.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
