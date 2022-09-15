package com.webapp.crudwebapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.webapp.crudwebapp.model.Employee;
import com.webapp.crudwebapp.repository.EmployeeRepository;

@Service
public class EmployeeServiceIMPL implements EmployeeService {
    @Autowired
    private EmployeeRepository empRepo;

    @Override
    public List<Employee> getAllEmnployees() {
        return empRepo.findAll();
    }

    @Override
    public void saveEmployee(Employee employee) {
        this.empRepo.save(employee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        // "find by id" it return the Optional object so, we optional
        Optional<Employee> optional = empRepo.findById(id);
        Employee employee = null;

        if (optional.isPresent()) {
            employee = optional.get();
        } else {
            throw new RuntimeException("Employee not found for ID : -> " + id);
        }
        return employee;
    }

    @Override
    public void deleteEmployeeById(long id) {
        this.empRepo.deleteById(id);
    }

    @Override
    public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.empRepo.findAll(pageable);
    }

}
