package com.nit.repository;

import com.nit.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository  extends JpaRepository<Employee,Integer> {
}
