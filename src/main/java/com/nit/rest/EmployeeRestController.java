package com.nit.rest;

import com.nit.entity.Employee;
import com.nit.repository.IEmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee API", description = "CRUD operations for Employee management")
@Slf4j  //initialized by lombok
public class EmployeeRestController {

    @Autowired
    private IEmployeeRepository repository;

    @Operation(summary = "Create a new Employee")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        log.info("Creating new employee: {}", employee);
        Employee saved = repository.save(employee);
        log.debug("Employee saved with ID: {}", saved.getId());
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "Get all Employees")
    @ApiResponse(responseCode = "200", description = "List of all employees")
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        log.info("Fetching all employees");
        List<Employee> list = repository.findAll();
        log.debug("Total employees found: {}", list.size());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get Employee by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee found"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        log.info("Fetching employee with ID: {}", id);
        Optional<Employee> optional = repository.findById(id);
        if (optional.isPresent()) {
            log.debug("Employee found: {}", optional.get());
            return ResponseEntity.ok(optional.get());
        } else {
            log.error("Employee not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update an existing Employee")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee updated"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee updatedData) {
        log.info("Updating employee with ID: {}", id);
        return repository.findById(id)
                .map(emp -> {
                    log.debug("Current employee data: {}", emp);
                    emp.setName(updatedData.getName());
                    emp.setAddress(updatedData.getAddress());
                    emp.setPhone(updatedData.getPhone());
                    emp.setSalary(updatedData.getSalary());
                    Employee updated = repository.save(emp);
                    log.debug("Updated employee: {}", updated);
                    return ResponseEntity.ok(updated);
                }).orElseGet(() -> {
                    log.error("Cannot update — employee not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Delete Employee by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Employee deleted"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        log.info("Deleting employee with ID: {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.debug("Employee deleted with ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            log.error("Cannot delete — employee not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
